package com.example.testingskripsinew.model;

public class UserModel {
    private String Nama;
    private String Password;
    private String NPM;

    public UserModel() {
    }

    public UserModel(String nama, String password, String npm) {
        Nama = nama;
        Password = password;
        NPM = npm;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getNPM() {
        return NPM;
    }

    public void setNPM(String npm) {
        NPM = npm;
    }
}


