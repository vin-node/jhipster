package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Location.
 */
@Entity
@Table(name = "location")
public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "coordinates_as_string")
    private String coordinatesAsString;

    @Column(name = "coordinates")
    private String coordinates;

    @ManyToOne
    private Coordinates coords;

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    private Set<Coordinates> coords = new HashSet<>();

    @OneToMany(mappedBy = "location")
    @JsonIgnore
    private Set<N> bs = new HashSet<>();

    @OneToMany(mappedBy = "cc")
    @JsonIgnore
    private Set<C> cs = new HashSet<>();

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Location name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCoordinatesAsString() {
        return coordinatesAsString;
    }

    public Location coordinatesAsString(String coordinatesAsString) {
        this.coordinatesAsString = coordinatesAsString;
        return this;
    }

    public void setCoordinatesAsString(String coordinatesAsString) {
        this.coordinatesAsString = coordinatesAsString;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public Location coordinates(String coordinates) {
        this.coordinates = coordinates;
        return this;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public Coordinates getCoords() {
        return coords;
    }

    public Location coords(Coordinates coordinates) {
        this.coords = coordinates;
        return this;
    }

    public void setCoords(Coordinates coordinates) {
        this.coords = coordinates;
    }

    public Set<Coordinates> getCoords() {
        return coords;
    }

    public Location coords(Set<Coordinates> coordinates) {
        this.coords = coordinates;
        return this;
    }

    public Location addCoords(Coordinates coordinates) {
        this.coords.add(coordinates);
        coordinates.setLocation(this);
        return this;
    }

    public Location removeCoords(Coordinates coordinates) {
        this.coords.remove(coordinates);
        coordinates.setLocation(null);
        return this;
    }

    public void setCoords(Set<Coordinates> coordinates) {
        this.coords = coordinates;
    }

    public Set<N> getBs() {
        return bs;
    }

    public Location bs(Set<N> ns) {
        this.bs = ns;
        return this;
    }

    public Location addB(N n) {
        this.bs.add(n);
        n.setLocation(this);
        return this;
    }

    public Location removeB(N n) {
        this.bs.remove(n);
        n.setLocation(null);
        return this;
    }

    public void setBs(Set<N> ns) {
        this.bs = ns;
    }

    public Set<C> getCs() {
        return cs;
    }

    public Location cs(Set<C> cs) {
        this.cs = cs;
        return this;
    }

    public Location addC(C c) {
        this.cs.add(c);
        c.setCc(this);
        return this;
    }

    public Location removeC(C c) {
        this.cs.remove(c);
        c.setCc(null);
        return this;
    }

    public void setCs(Set<C> cs) {
        this.cs = cs;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        if (location.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), location.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", coordinatesAsString='" + getCoordinatesAsString() + "'" +
            ", coordinates='" + getCoordinates() + "'" +
            "}";
    }
}
