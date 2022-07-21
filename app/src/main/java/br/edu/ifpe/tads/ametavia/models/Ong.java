package br.edu.ifpe.tads.ametavia.models;

public class Ong {
    private String name;
    private String endereco;
    private String email;
    private String bio;
    private String urlPath;

    public Ong () {}
    public Ong(String name, String endereco, String email, String bio) {
        this.name = name;
        this.endereco = endereco;
        this.email = email;
        this.bio = bio;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getEmail() {
        return email;
    }

    public String getUrlPath() {return urlPath;}

}
