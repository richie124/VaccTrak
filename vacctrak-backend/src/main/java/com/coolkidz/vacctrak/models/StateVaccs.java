package com.coolkidz.vacctrak.models;

public class StateVaccs {
    private String stateAbbr;
    private int singleDoses;
    private int doubleDoses;

    public StateVaccs(String stateAbbr, int singleDoses, int doubleDoses) {
        this.stateAbbr = stateAbbr;
        this.singleDoses = singleDoses;
        this.doubleDoses = doubleDoses;
    }

    public void setStateAbbr(String stateAbbr) {
        this.stateAbbr = stateAbbr;
    }

    public String getStateAbbr() {
        return this.stateAbbr;
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
