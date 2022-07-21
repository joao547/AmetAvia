package br.edu.ifpe.tads.ametavia.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.Date;

@IgnoreExtraProperties
public class Volunteer {
    private String email;
    private String nome;
    private Date dataNascimento;
    private String genero;
    private String bio;

    public Volunteer(){}

    public Volunteer(String email, String nome, Date dataNascimento, String genero, String bio) {
        this.email = email;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.genero = genero;
        this.bio = bio;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public String getGenero() {
        return genero;
    }

    public String getBio() {
        return bio;
    }
}
