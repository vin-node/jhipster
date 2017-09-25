package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.GeoCoordinate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing GeoCoordinate.
 */
public interface GeoCoordinateService {

    /**
     * Save a geoCoordinate.
     *
     * @param geoCoordinate the entity to save
     * @return the persisted entity
     */
    GeoCoordinate save(GeoCoordinate geoCoordinate);

    /**
     *  Get all the geoCoordinates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<GeoCoordinate> findAll(Pageable pageable);

    /**
     *  Get the "id" geoCoordinate.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    GeoCoordinate findOne(Long id);

    /**
     *  Delete the "id" geoCoordinate.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
