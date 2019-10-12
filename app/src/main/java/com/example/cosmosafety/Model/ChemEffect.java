package com.example.cosmosafety.Model;

public class ChemEffect {
    private String Chemicals;
    private Long ID;
    private String Effects;
    private String Meaning;

    public ChemEffect() {
    }

    public ChemEffect(String chemicals, Long ID, String effects, String meaning) {
        Chemicals = chemicals;
        this.ID = ID;
        Effects = effects;
        Meaning = meaning;
    }

    public String getChemicals() {
        return Chemicals;
    }

    public void setChemicals(String chemicals) {
        Chemicals = chemicals;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getEffects() {
        return Effects;
    }

    public void setEffects(String effects) {
        Effects = effects;
    }

    public String getMeaning() {
        return Meaning;
    }

    public void setMeaning(String meaning) {
        Meaning = meaning;
    }

    public String[] chemLst()
    {
        String[] tmp = Chemicals.split(",");
        for(int i = 0; i<tmp.length; i++)
        {
            tmp[i] = tmp[i].replaceAll("\\s","");
        }
        return tmp;
    }
}
