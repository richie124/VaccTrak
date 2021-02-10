package com.coolkidz.vacctrak.models;

public class VaccCenter {

    private int id;
    private String name;
    private String address;
    private String city;
    private String state;
    private int zipcode;
    private int singleDoses;
    private int doubleDoses;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return this.address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
    }

    public void zipcode(int zipcode) {
        this.singleDoses = zipcode;
    }

    public int getZipcode() {
        return this.zipcode;
    }

    public void setSingleDoses(int singleDoses) {
        this.singleDoses = singleDoses;
    }

    public int getSingleDoses() {
        return this.singleDoses;
    }

    public void setDoubleDoses(int doubleDoses) {
        this.doubleDoses = doubleDoses;
    }

    public int getDoubleDoses() {
        return this.doubleDoses;
    }

}
