package model;

import java.util.Objects;

public class ModelFile {
    private String city;
    private String street;
    private int house;
    private int floor;

    public ModelFile(String city,
                     String street,
                     int house,
                     int floor) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.floor = floor;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public int getHouse() {
        return house;
    }

    public int getFloor() {
        return floor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ModelFile)) return false;
        ModelFile modelFile = (ModelFile) o;
        return house == modelFile.house &&
                floor == modelFile.floor &&
                Objects.equals(city, modelFile.city) &&
                Objects.equals(street, modelFile.street);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, street, house, floor);
    }

    @Override
    public String toString() {
        return "ModelFile {" +
                "city= " + city +
                ", street= " + street +
                ", house= " + house +
                ", floor= " + floor +
                "}";
    }
}