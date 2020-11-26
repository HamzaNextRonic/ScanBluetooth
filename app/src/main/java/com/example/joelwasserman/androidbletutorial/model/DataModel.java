package com.example.joelwasserman.androidbletutorial.model;

public class DataModel {

    String name;
    String address;
    String valeur;
    String time;

    public DataModel(String name, String address, String valeur, String time) {
        this.name = name;
        this.address = address;
        this.valeur = valeur;
        this.time = time;
    }

    public DataModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getValeur() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur = valeur;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
