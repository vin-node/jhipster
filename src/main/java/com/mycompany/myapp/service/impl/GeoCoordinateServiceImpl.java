package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.service.GeoCoordinateService;
import com.mycompany.myapp.domain.GeoCoordinate;
import com.mycompany.myapp.repository.GeoCoordinateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing GeoCoordinate.
 */
@Service
@Transactional
public class GeoCoordinateServiceImpl implements GeoCoordinateService{

    private final Logger log = LoggerFactory.getLogger(GeoCoordinateServiceImpl.class);

    private final GeoCoordinateRepository geoCoordinateRepository;

    public GeoCoordinateServiceImpl(GeoCoordinateRepository geoCoordinateRepository) {
        this.geoCoordinateRepository = geoCoordinateRepository;
    }

    /**
     * Save a geoCoordinate.
     *
     * @param geoCoordinate the entity to save
     * @return the persisted entity
     */
    @Override
    public GeoCoordinate save(GeoCoordinate geoCoordinate) {
        log.debug("Request to save GeoCoordinate : {}", geoCoordinate);
        return geoCoordinateRepository.save(geoCoordinate);
    }

    /**
     *  Get all the geoCoordinates.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GeoCoordinate> findAll(Pageable pageable) {
        log.debug("Request to get all GeoCoordinates");
        return geoCoordinateRepository.findAll(pageable);
    }

    /**
     *  Get one geoCoordinate by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public GeoCoordinate findOne(Long id) {
        log.debug("Request to get GeoCoordinate : {}", id);
        return geoCoordinateRepository.findOne(id);
    }

    /**
     *  Delete the  geoCoordinate by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GeoCoordinate : {}", id);
        geoCoordinateRepository.delete(id);
    }
}
