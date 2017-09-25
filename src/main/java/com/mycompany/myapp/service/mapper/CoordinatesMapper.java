package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CoordinatesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Coordinates and its DTO CoordinatesDTO.
 */
@Mapper(componentModel = "spring", uses = {LocationMapper.class, })
public interface CoordinatesMapper extends EntityMapper <CoordinatesDTO, Coordinates> {

    @Mapping(source = "location.id", target = "locationId")
    CoordinatesDTO toDto(Coordinates coordinates); 

    @Mapping(source = "locationId", target = "location")
    Coordinates toEntity(CoordinatesDTO coordinatesDTO); 
    default Coordinates fromId(Long id) {
        if (id == null) {
            return null;
        }
        Coordinates coordinates = new Coordinates();
        coordinates.setId(id);
        return coordinates;
    }
}
