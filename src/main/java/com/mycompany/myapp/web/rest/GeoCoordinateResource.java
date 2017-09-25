package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.GeoCoordinate;
import com.mycompany.myapp.service.GeoCoordinateService;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GeoCoordinate.
 */
@RestController
@RequestMapping("/api")
public class GeoCoordinateResource {

    private final Logger log = LoggerFactory.getLogger(GeoCoordinateResource.class);

    private static final String ENTITY_NAME = "geoCoordinate";

    private final GeoCoordinateService geoCoordinateService;

    public GeoCoordinateResource(GeoCoordinateService geoCoordinateService) {
        this.geoCoordinateService = geoCoordinateService;
    }

    /**
     * POST  /geo-coordinates : Create a new geoCoordinate.
     *
     * @param geoCoordinate the geoCoordinate to create
     * @return the ResponseEntity with status 201 (Created) and with body the new geoCoordinate, or with status 400 (Bad Request) if the geoCoordinate has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/geo-coordinates")
    @Timed
    public ResponseEntity<GeoCoordinate> createGeoCoordinate(@RequestBody GeoCoordinate geoCoordinate) throws URISyntaxException {
        log.debug("REST request to save GeoCoordinate : {}", geoCoordinate);
        if (geoCoordinate.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new geoCoordinate cannot already have an ID")).body(null);
        }
        GeoCoordinate result = geoCoordinateService.save(geoCoordinate);
        return ResponseEntity.created(new URI("/api/geo-coordinates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /geo-coordinates : Updates an existing geoCoordinate.
     *
     * @param geoCoordinate the geoCoordinate to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated geoCoordinate,
     * or with status 400 (Bad Request) if the geoCoordinate is not valid,
     * or with status 500 (Internal Server Error) if the geoCoordinate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/geo-coordinates")
    @Timed
    public ResponseEntity<GeoCoordinate> updateGeoCoordinate(@RequestBody GeoCoordinate geoCoordinate) throws URISyntaxException {
        log.debug("REST request to update GeoCoordinate : {}", geoCoordinate);
        if (geoCoordinate.getId() == null) {
            return createGeoCoordinate(geoCoordinate);
        }
        GeoCoordinate result = geoCoordinateService.save(geoCoordinate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, geoCoordinate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /geo-coordinates : get all the geoCoordinates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of geoCoordinates in body
     */
    @GetMapping("/geo-coordinates")
    @Timed
    public ResponseEntity<List<GeoCoordinate>> getAllGeoCoordinates(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of GeoCoordinates");
        Page<GeoCoordinate> page = geoCoordinateService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/geo-coordinates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /geo-coordinates/:id : get the "id" geoCoordinate.
     *
     * @param id the id of the geoCoordinate to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the geoCoordinate, or with status 404 (Not Found)
     */
    @GetMapping("/geo-coordinates/{id}")
    @Timed
    public ResponseEntity<GeoCoordinate> getGeoCoordinate(@PathVariable Long id) {
        log.debug("REST request to get GeoCoordinate : {}", id);
        GeoCoordinate geoCoordinate = geoCoordinateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(geoCoordinate));
    }

    /**
     * DELETE  /geo-coordinates/:id : delete the "id" geoCoordinate.
     *
     * @param id the id of the geoCoordinate to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/geo-coordinates/{id}")
    @Timed
    public ResponseEntity<Void> deleteGeoCoordinate(@PathVariable Long id) {
        log.debug("REST request to delete GeoCoordinate : {}", id);
        geoCoordinateService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
