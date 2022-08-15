package br.edu.ifpe.tads.ametavia.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Ong implements Serializable {
    private String name;
    private String email;
    private String bio;
    private String whatsapp;
    private ArrayList<String> images = new ArrayList<String>();
    private Address address = new Address();

    public Ong () {}
    public Ong(String name, Address address, String email, String bio, String whatsapp) {
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.whatsapp = whatsapp;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public void setImages(ArrayList<String> images) {
        this.images = images;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }
}
