package br.edu.ifpe.tads.ametavia.models;

import lombok.Data;

@Data
public class Address {
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
}
