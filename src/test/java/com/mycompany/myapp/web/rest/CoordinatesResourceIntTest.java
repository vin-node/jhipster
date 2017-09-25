package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.AppApp;

import com.mycompany.myapp.domain.Coordinates;
import com.mycompany.myapp.repository.CoordinatesRepository;
import com.mycompany.myapp.service.dto.CoordinatesDTO;
import com.mycompany.myapp.service.mapper.CoordinatesMapper;
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
 * Test class for the CoordinatesResource REST controller.
 *
 * @see CoordinatesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppApp.class)
public class CoordinatesResourceIntTest {

    @Autowired
    private CoordinatesRepository coordinatesRepository;

    @Autowired
    private CoordinatesMapper coordinatesMapper;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCoordinatesMockMvc;

    private Coordinates coordinates;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CoordinatesResource coordinatesResource = new CoordinatesResource(coordinatesRepository, coordinatesMapper);
        this.restCoordinatesMockMvc = MockMvcBuilders.standaloneSetup(coordinatesResource)
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
    public static Coordinates createEntity(EntityManager em) {
        Coordinates coordinates = new Coordinates();
        return coordinates;
    }

    @Before
    public void initTest() {
        coordinates = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoordinates() throws Exception {
        int databaseSizeBeforeCreate = coordinatesRepository.findAll().size();

        // Create the Coordinates
        CoordinatesDTO coordinatesDTO = coordinatesMapper.toDto(coordinates);
        restCoordinatesMockMvc.perform(post("/api/coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordinatesDTO)))
            .andExpect(status().isCreated());

        // Validate the Coordinates in the database
        List<Coordinates> coordinatesList = coordinatesRepository.findAll();
        assertThat(coordinatesList).hasSize(databaseSizeBeforeCreate + 1);
        Coordinates testCoordinates = coordinatesList.get(coordinatesList.size() - 1);
    }

    @Test
    @Transactional
    public void createCoordinatesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = coordinatesRepository.findAll().size();

        // Create the Coordinates with an existing ID
        coordinates.setId(1L);
        CoordinatesDTO coordinatesDTO = coordinatesMapper.toDto(coordinates);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCoordinatesMockMvc.perform(post("/api/coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordinatesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Coordinates in the database
        List<Coordinates> coordinatesList = coordinatesRepository.findAll();
        assertThat(coordinatesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCoordinates() throws Exception {
        // Initialize the database
        coordinatesRepository.saveAndFlush(coordinates);

        // Get all the coordinatesList
        restCoordinatesMockMvc.perform(get("/api/coordinates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coordinates.getId().intValue())));
    }

    @Test
    @Transactional
    public void getCoordinates() throws Exception {
        // Initialize the database
        coordinatesRepository.saveAndFlush(coordinates);

        // Get the coordinates
        restCoordinatesMockMvc.perform(get("/api/coordinates/{id}", coordinates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coordinates.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCoordinates() throws Exception {
        // Get the coordinates
        restCoordinatesMockMvc.perform(get("/api/coordinates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoordinates() throws Exception {
        // Initialize the database
        coordinatesRepository.saveAndFlush(coordinates);
        int databaseSizeBeforeUpdate = coordinatesRepository.findAll().size();

        // Update the coordinates
        Coordinates updatedCoordinates = coordinatesRepository.findOne(coordinates.getId());
        CoordinatesDTO coordinatesDTO = coordinatesMapper.toDto(updatedCoordinates);

        restCoordinatesMockMvc.perform(put("/api/coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordinatesDTO)))
            .andExpect(status().isOk());

        // Validate the Coordinates in the database
        List<Coordinates> coordinatesList = coordinatesRepository.findAll();
        assertThat(coordinatesList).hasSize(databaseSizeBeforeUpdate);
        Coordinates testCoordinates = coordinatesList.get(coordinatesList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCoordinates() throws Exception {
        int databaseSizeBeforeUpdate = coordinatesRepository.findAll().size();

        // Create the Coordinates
        CoordinatesDTO coordinatesDTO = coordinatesMapper.toDto(coordinates);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCoordinatesMockMvc.perform(put("/api/coordinates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(coordinatesDTO)))
            .andExpect(status().isCreated());

        // Validate the Coordinates in the database
        List<Coordinates> coordinatesList = coordinatesRepository.findAll();
        assertThat(coordinatesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCoordinates() throws Exception {
        // Initialize the database
        coordinatesRepository.saveAndFlush(coordinates);
        int databaseSizeBeforeDelete = coordinatesRepository.findAll().size();

        // Get the coordinates
        restCoordinatesMockMvc.perform(delete("/api/coordinates/{id}", coordinates.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Coordinates> coordinatesList = coordinatesRepository.findAll();
        assertThat(coordinatesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coordinates.class);
        Coordinates coordinates1 = new Coordinates();
        coordinates1.setId(1L);
        Coordinates coordinates2 = new Coordinates();
        coordinates2.setId(coordinates1.getId());
        assertThat(coordinates1).isEqualTo(coordinates2);
        coordinates2.setId(2L);
        assertThat(coordinates1).isNotEqualTo(coordinates2);
        coordinates1.setId(null);
        assertThat(coordinates1).isNotEqualTo(coordinates2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CoordinatesDTO.class);
        CoordinatesDTO coordinatesDTO1 = new CoordinatesDTO();
        coordinatesDTO1.setId(1L);
        CoordinatesDTO coordinatesDTO2 = new CoordinatesDTO();
        assertThat(coordinatesDTO1).isNotEqualTo(coordinatesDTO2);
        coordinatesDTO2.setId(coordinatesDTO1.getId());
        assertThat(coordinatesDTO1).isEqualTo(coordinatesDTO2);
        coordinatesDTO2.setId(2L);
        assertThat(coordinatesDTO1).isNotEqualTo(coordinatesDTO2);
        coordinatesDTO1.setId(null);
        assertThat(coordinatesDTO1).isNotEqualTo(coordinatesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(coordinatesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(coordinatesMapper.fromId(null)).isNull();
    }
}
