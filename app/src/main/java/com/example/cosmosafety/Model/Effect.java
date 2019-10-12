package com.example.cosmosafety.Model;

public class Effect {
    private long ID;
    private String Effects;
    private String Product_ID;

    public Effect() {
    }

    public Effect(long ID, String effects, String product_ID) {
        this.ID = ID;
        Effects = effects;
        Product_ID = product_ID;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getEffects() {
        return Effects;
    }

    public void setEffects(String effects) {
        Effects = effects;
    }

    public String getProduct_ID() {
        return Product_ID;
    }

    public void setProduct_ID(String product_ID) {
        Product_ID = product_ID;
    }

    public String[] getProductLst()
    {
        String[] tmp = this.Product_ID.split(",");
        for(int i = 0; i<tmp.length; i++)
        {
            tmp[i] = tmp[i].replaceAll("\\s","");
        }


        return tmp;
    }

    public boolean isBelonged(String pID)
    {
        String[] tmp = this.Product_ID.split(",");
        //String pID = Long.toString(ID);
        for(int i = 0; i<tmp.length; i++)
        {
            tmp[i]=tmp[i].replaceAll("\\s","");
            int a = Integer.parseInt(tmp[i]);
            int b = Integer.parseInt(pID);
            //if(tmp[i].equals(pID))
            if(a==b)
                return true;
        }


        return false;

    }
}
