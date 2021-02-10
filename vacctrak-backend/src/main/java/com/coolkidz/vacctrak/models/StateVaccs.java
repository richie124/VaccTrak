package com.coolkidz.vacctrak.models;

public class StateVaccs {

    private String stateName;
    private int singleDoses;
    private int doubleDoses;

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getStateName() {
        return this.stateName;
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
