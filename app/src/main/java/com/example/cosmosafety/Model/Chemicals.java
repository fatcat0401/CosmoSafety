package com.example.cosmosafety.Model;

public class Chemicals {
    private long ID;
    private String Chemical;

    public Chemicals(long ID, String name) {
        this.ID = ID;
        this.Chemical = name;
    }

    public Chemicals()
    {
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getChemical() {
        return Chemical;
    }

    public void setChemical(String chemical) {
        Chemical = chemical;
    }
}
