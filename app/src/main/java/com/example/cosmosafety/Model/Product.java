package com.example.cosmosafety.Model;

public class Product {

    private long ID;
    private String brands, categories, countries,created_datetime,ingredients,last_modified_datetime,product_name;
    String catInName = "";

    public Product(long ID, String brands, String categories, String countries, String created_datetime, String ingredients, String last_modified_datetime, String product_name) {
        this.ID = ID;
        this.brands = brands;
        this.categories = categories;
        this.countries = countries;
        this.created_datetime = created_datetime;
        this.ingredients = ingredients;
        this.last_modified_datetime = last_modified_datetime;
        this.product_name = product_name;
    }

    public Product()
    {

    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getBrands() {
        return brands;
    }

    public void setBrands(String brands) {
        this.brands = brands;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getCountries() {
        return countries;
    }

    public void setCountries(String countries) {
        this.countries = countries;
    }

    public String getCreated_datetime() {
        return created_datetime;
    }

    public void setCreated_datetime(String created_datetime) {
        this.created_datetime = created_datetime;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getLast_modified_datetime() {
        return last_modified_datetime;
    }

    public void setLast_modified_datetime(String last_modified_datetime) {
        this.last_modified_datetime = last_modified_datetime;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCategoriesList()
    {
        String[] tmp = this.categories.split(",");

        for(int i = 0; i<tmp.length; i++)
        {
            tmp[i]=tmp[i].replaceAll("\\s","");
        }

        return  tmp[tmp.length - 1];
    }

}
