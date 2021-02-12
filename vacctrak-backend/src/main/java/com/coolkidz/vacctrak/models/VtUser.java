package com.coolkidz.vacctrak.models;

public class VtUser {

    private int id;
    private String fName;
    private String lName;
    private String userName;
    private String password;
    private int VaccCenterId;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getfName() {
        return this.fName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getlName() {
        return this.lName;
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

    public void setVaccCenterId(int VaccCenterId) {
        this.VaccCenterId = VaccCenterId;
    }

    public int getVaccCenterId() {
        return this.VaccCenterId;
    }

}
