package br.edu.ifpe.tads.ametavia.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class Address implements Serializable {
    private double latitude;
    private double longitude;
    private String street;
    private String number;
    private String postalCode;
    private String city;
    private String state;
    private String country;

    public Address() {}
    public Address(double latitude, double longitude, String street, String number ,String postalCode, String state, String city, String country) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
        this.state = state;
        this.city = city;
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String formatted() {
        StringBuilder formatted = new StringBuilder(this.street);
        formatted.append(", ");
        formatted.append(this.number);
        formatted.append(", ");
        formatted.append(this.city);
        formatted.append(" - ");
        formatted.append(this.state);
        formatted.append(", ");
        formatted.append(this.postalCode);
        return formatted.toString();
    }
}
