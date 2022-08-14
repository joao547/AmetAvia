package br.edu.ifpe.tads.ametavia.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

import lombok.Data;

@IgnoreExtraProperties
@Data
public class Volunteer {
    private String email;
    private String name;
    private Date birthDate;
    private String gender;
    private String bio;
    private Address address;
    private String image;

    public Volunteer() {}

    public Volunteer(String email, String name, Date birthDate, String gender, String bio) {
        this.email = email;
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
        this.bio = bio;
    }
}
