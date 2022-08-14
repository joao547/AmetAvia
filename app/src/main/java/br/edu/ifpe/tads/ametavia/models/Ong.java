package br.edu.ifpe.tads.ametavia.models;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.Data;

@Data
public class Ong implements Serializable {
    private String name;
    private String email;
    private String bio;
    private ArrayList<String> images = new ArrayList<String>();
    private Address address = new Address();

    public Ong () {}
    public Ong(String name, String endereco, String email, String bio) {
        this.name = name;
        this.email = email;
        this.bio = bio;
    }
}
