package model;

import java.util.Objects;

public class Address {
    private String city;
    private String street;
    private int house;
    private int floor;

    public Address(String city,
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
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return house == address.house &&
                floor == address.floor &&
                Objects.equals(city, address.city) &&
                Objects.equals(street, address.street);
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