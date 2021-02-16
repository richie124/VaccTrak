package com.coolkidz.vacctrak.models;

public class CenterPermission {

    private int permId;
    private int userId;
    private int vacCenterId;


    public void setPermId(int permId) {
        this.permId = permId;
    }

    public int getPermId() {
        return this.permId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setVacCenterId(int vacCenterId) {
        this.vacCenterId = vacCenterId;
    }

    public int getVacCenterId() {
        return this.vacCenterId;
    }
}
