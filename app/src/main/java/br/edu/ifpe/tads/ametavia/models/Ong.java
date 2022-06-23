package br.edu.ifpe.tads.ametavia.models;

public class Ong {
    private String name;
    private String bio;

    public Ong () {}
    public Ong (String name, String bio) {
        this.name = name;
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
