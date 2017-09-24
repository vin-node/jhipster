package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Coordinates;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Coordinates entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoordinatesRepository extends JpaRepository<Coordinates, Long> {

}
