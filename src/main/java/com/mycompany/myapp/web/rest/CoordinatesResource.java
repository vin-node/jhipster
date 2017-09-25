package com.mycompany.myapp.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mycompany.myapp.domain.Coordinates;

import com.mycompany.myapp.repository.CoordinatesRepository;
import com.mycompany.myapp.web.rest.util.HeaderUtil;
import com.mycompany.myapp.web.rest.util.PaginationUtil;
import com.mycompany.myapp.service.dto.CoordinatesDTO;
import com.mycompany.myapp.service.mapper.CoordinatesMapper;
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
 * REST controller for managing Coordinates.
 */
@RestController
@RequestMapping("/api")
public class CoordinatesResource {

    private final Logger log = LoggerFactory.getLogger(CoordinatesResource.class);

    private static final String ENTITY_NAME = "coordinates";

    private final CoordinatesRepository coordinatesRepository;

    private final CoordinatesMapper coordinatesMapper;

    public CoordinatesResource(CoordinatesRepository coordinatesRepository, CoordinatesMapper coordinatesMapper) {
        this.coordinatesRepository = coordinatesRepository;
        this.coordinatesMapper = coordinatesMapper;
    }

    /**
     * POST  /coordinates : Create a new coordinates.
     *
     * @param coordinatesDTO the coordinatesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coordinatesDTO, or with status 400 (Bad Request) if the coordinates has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/coordinates")
    @Timed
    public ResponseEntity<CoordinatesDTO> createCoordinates(@RequestBody CoordinatesDTO coordinatesDTO) throws URISyntaxException {
        log.debug("REST request to save Coordinates : {}", coordinatesDTO);
        if (coordinatesDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new coordinates cannot already have an ID")).body(null);
        }
        Coordinates coordinates = coordinatesMapper.toEntity(coordinatesDTO);
        coordinates = coordinatesRepository.save(coordinates);
        CoordinatesDTO result = coordinatesMapper.toDto(coordinates);
        return ResponseEntity.created(new URI("/api/coordinates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /coordinates : Updates an existing coordinates.
     *
     * @param coordinatesDTO the coordinatesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coordinatesDTO,
     * or with status 400 (Bad Request) if the coordinatesDTO is not valid,
     * or with status 500 (Internal Server Error) if the coordinatesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/coordinates")
    @Timed
    public ResponseEntity<CoordinatesDTO> updateCoordinates(@RequestBody CoordinatesDTO coordinatesDTO) throws URISyntaxException {
        log.debug("REST request to update Coordinates : {}", coordinatesDTO);
        if (coordinatesDTO.getId() == null) {
            return createCoordinates(coordinatesDTO);
        }
        Coordinates coordinates = coordinatesMapper.toEntity(coordinatesDTO);
        coordinates = coordinatesRepository.save(coordinates);
        CoordinatesDTO result = coordinatesMapper.toDto(coordinates);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coordinatesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /coordinates : get all the coordinates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of coordinates in body
     */
    @GetMapping("/coordinates")
    @Timed
    public ResponseEntity<List<CoordinatesDTO>> getAllCoordinates(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Coordinates");
        Page<Coordinates> page = coordinatesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/coordinates");
        return new ResponseEntity<>(coordinatesMapper.toDto(page.getContent()), headers, HttpStatus.OK);
    }

    /**
     * GET  /coordinates/:id : get the "id" coordinates.
     *
     * @param id the id of the coordinatesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coordinatesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/coordinates/{id}")
    @Timed
    public ResponseEntity<CoordinatesDTO> getCoordinates(@PathVariable Long id) {
        log.debug("REST request to get Coordinates : {}", id);
        Coordinates coordinates = coordinatesRepository.findOne(id);
        CoordinatesDTO coordinatesDTO = coordinatesMapper.toDto(coordinates);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(coordinatesDTO));
    }

    /**
     * DELETE  /coordinates/:id : delete the "id" coordinates.
     *
     * @param id the id of the coordinatesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/coordinates/{id}")
    @Timed
    public ResponseEntity<Void> deleteCoordinates(@PathVariable Long id) {
        log.debug("REST request to delete Coordinates : {}", id);
        coordinatesRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
