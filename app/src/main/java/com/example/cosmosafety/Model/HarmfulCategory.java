package com.example.cosmosafety.Model;

public class HarmfulCategory {
    private long ID;
    private String Name;
    private int Icon;

    public HarmfulCategory() {
    }

    public HarmfulCategory(long ID, String name, int icon) {
        this.ID = ID;
        Name = name;
        Icon = icon;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getIcon() {
        return Icon;
    }

    public void setIcon(int icon) {
        Icon = icon;
    }
}
