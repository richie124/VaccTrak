package com.coolkidz.vacctrak.models;

import java.util.ArrayList;
import java.util.List;

public class VtUser {

    private int id;
    private String userName;
    private String password;
    private List<Integer> vaccCenterAccesses = new ArrayList<Integer>();

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public List<Integer> getVaccCenterAccesses() {
        return vaccCenterAccesses;
    }

    public void setVaccCenterAccesses(List<Integer> avaccCenterAccesses) {
        this.vaccCenterAccesses = avaccCenterAccesses;
    }
}
