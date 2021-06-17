package com.epam.rd.fp.model;

import com.epam.rd.fp.model.enums.Language;

import java.util.Objects;

public class Location {
    private int id;
    private String country;
    private String city;
    private String street;
    private String house;
    private String room;
    private Language language;

    @Override
    public String toString() {
        return "Location{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", house='" + house + '\'' +
                ", room='" + room + '\'' +
                ", language='" + language + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id == location.id && Objects.equals(country, location.country) && Objects.equals(city, location.city) && Objects.equals(street, location.street) && Objects.equals(house, location.house) && Objects.equals(room, location.room) && language == location.language;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, country, city, street, house, room, language);
    }
}
