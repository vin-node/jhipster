package com.mycompany.myapp.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Coordinates entity.
 */
public class CoordinatesDTO implements Serializable {

    private Long id;

    private Long locationId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CoordinatesDTO coordinatesDTO = (CoordinatesDTO) o;
        if(coordinatesDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coordinatesDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoordinatesDTO{" +
            "id=" + getId() +
            "}";
    }
}
