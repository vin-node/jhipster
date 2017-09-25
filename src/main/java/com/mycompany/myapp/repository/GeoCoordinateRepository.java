package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.GeoCoordinate;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GeoCoordinate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeoCoordinateRepository extends JpaRepository<GeoCoordinate, Long> {

}
