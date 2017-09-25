package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AppApp;

import com.mycompany.myapp.domain.GeoCoordinate;
import com.mycompany.myapp.repository.GeoCoordinateRepository;
import com.mycompany.myapp.service.GeoCoordinateService;
import com.mycompany.myapp.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the GeoCoordinateResource REST controller.
 *
 * @see GeoCoordinateResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class GeoCoordinateResourceIntTest {

    private static final Double DEFAULT_LAT = 1D;
    private static final Double UPDATED_LAT = 2D;

    private static final Double DEFAULT_LON = 1D;
    private static final Double UPDATED_LON = 2D;

    @Autowired
    private GeoCoordinateRepository geoCoordinateRepository;

    @Autowired
    private GeoCoordinateService geoCoordinateService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGeoCoordinateMockMvc;

    private GeoCoordinate geoCoordinate;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GeoCoordinateResource geoCoordinateResource = new GeoCoordinateResource(geoCoordinateService);
        this.restGeoCoordinateMockMvc = MockMvcBuilders.standaloneSetup(geoCoordinateResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GeoCoordinate createEntity(EntityManager em) {
        GeoCoordinate geoCoordinate = new GeoCoordinate()
            .lat(DEFAULT_LAT)
            .lon(DEFAULT_LON);
        return geoCoordinate;
    }

    @Before
    public void initTest() {
        geoCoordinate = createEntity(em);
    }

    @Test
    @Transactional
    public void createGeoCoordinate() throws Exception {
        int databaseSizeBeforeCreate = geoCoordinateRepository.findAll().size();

        // Create the GeoCoordinate
        restGeoCoordinateMockMvc.perform(post("/api/geo-coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geoCoordinate)))
            .andExpect(status().isCreated());

        // Validate the GeoCoordinate in the database
        List<GeoCoordinate> geoCoordinateList = geoCoordinateRepository.findAll();
        assertThat(geoCoordinateList).hasSize(databaseSizeBeforeCreate + 1);
        GeoCoordinate testGeoCoordinate = geoCoordinateList.get(geoCoordinateList.size() - 1);
        assertThat(testGeoCoordinate.getLat()).isEqualTo(DEFAULT_LAT);
        assertThat(testGeoCoordinate.getLon()).isEqualTo(DEFAULT_LON);
    }

    @Test
    @Transactional
    public void createGeoCoordinateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = geoCoordinateRepository.findAll().size();

        // Create the GeoCoordinate with an existing ID
        geoCoordinate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeoCoordinateMockMvc.perform(post("/api/geo-coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geoCoordinate)))
            .andExpect(status().isBadRequest());

        // Validate the GeoCoordinate in the database
        List<GeoCoordinate> geoCoordinateList = geoCoordinateRepository.findAll();
        assertThat(geoCoordinateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGeoCoordinates() throws Exception {
        // Initialize the database
        geoCoordinateRepository.saveAndFlush(geoCoordinate);

        // Get all the geoCoordinateList
        restGeoCoordinateMockMvc.perform(get("/api/geo-coordinates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(geoCoordinate.getId().intValue())))
            .andExpect(jsonPath("$.[*].lat").value(hasItem(DEFAULT_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].lon").value(hasItem(DEFAULT_LON.doubleValue())));
    }

    @Test
    @Transactional
    public void getGeoCoordinate() throws Exception {
        // Initialize the database
        geoCoordinateRepository.saveAndFlush(geoCoordinate);

        // Get the geoCoordinate
        restGeoCoordinateMockMvc.perform(get("/api/geo-coordinates/{id}", geoCoordinate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(geoCoordinate.getId().intValue()))
            .andExpect(jsonPath("$.lat").value(DEFAULT_LAT.doubleValue()))
            .andExpect(jsonPath("$.lon").value(DEFAULT_LON.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGeoCoordinate() throws Exception {
        // Get the geoCoordinate
        restGeoCoordinateMockMvc.perform(get("/api/geo-coordinates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeoCoordinate() throws Exception {
        // Initialize the database
        geoCoordinateService.save(geoCoordinate);

        int databaseSizeBeforeUpdate = geoCoordinateRepository.findAll().size();

        // Update the geoCoordinate
        GeoCoordinate updatedGeoCoordinate = geoCoordinateRepository.findOne(geoCoordinate.getId());
        updatedGeoCoordinate
            .lat(UPDATED_LAT)
            .lon(UPDATED_LON);

        restGeoCoordinateMockMvc.perform(put("/api/geo-coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGeoCoordinate)))
            .andExpect(status().isOk());

        // Validate the GeoCoordinate in the database
        List<GeoCoordinate> geoCoordinateList = geoCoordinateRepository.findAll();
        assertThat(geoCoordinateList).hasSize(databaseSizeBeforeUpdate);
        GeoCoordinate testGeoCoordinate = geoCoordinateList.get(geoCoordinateList.size() - 1);
        assertThat(testGeoCoordinate.getLat()).isEqualTo(UPDATED_LAT);
        assertThat(testGeoCoordinate.getLon()).isEqualTo(UPDATED_LON);
    }

    @Test
    @Transactional
    public void updateNonExistingGeoCoordinate() throws Exception {
        int databaseSizeBeforeUpdate = geoCoordinateRepository.findAll().size();

        // Create the GeoCoordinate

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGeoCoordinateMockMvc.perform(put("/api/geo-coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(geoCoordinate)))
            .andExpect(status().isCreated());

        // Validate the GeoCoordinate in the database
        List<GeoCoordinate> geoCoordinateList = geoCoordinateRepository.findAll();
        assertThat(geoCoordinateList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGeoCoordinate() throws Exception {
        // Initialize the database
        geoCoordinateService.save(geoCoordinate);

        int databaseSizeBeforeDelete = geoCoordinateRepository.findAll().size();

        // Get the geoCoordinate
        restGeoCoordinateMockMvc.perform(delete("/api/geo-coordinates/{id}", geoCoordinate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GeoCoordinate> geoCoordinateList = geoCoordinateRepository.findAll();
        assertThat(geoCoordinateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeoCoordinate.class);
        GeoCoordinate geoCoordinate1 = new GeoCoordinate();
        geoCoordinate1.setId(1L);
        GeoCoordinate geoCoordinate2 = new GeoCoordinate();
        geoCoordinate2.setId(geoCoordinate1.getId());
        assertThat(geoCoordinate1).isEqualTo(geoCoordinate2);
        geoCoordinate2.setId(2L);
        assertThat(geoCoordinate1).isNotEqualTo(geoCoordinate2);
        geoCoordinate1.setId(null);
        assertThat(geoCoordinate1).isNotEqualTo(geoCoordinate2);
    }
}
