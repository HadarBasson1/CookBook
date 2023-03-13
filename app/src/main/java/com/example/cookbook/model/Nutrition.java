package com.example.cookbook.model;

import com.google.gson.annotations.SerializedName;

public class Nutrition {
    @SerializedName("uri")
    private String uri;

    @SerializedName("calories")
    private float calories;

    @SerializedName("totalWeight")
    private float totalWeight;

    @SerializedName("dietLabels")
    private String[] dietLabels;

    @SerializedName("healthLabels")
    private String[] healthLabels;

    @SerializedName("cautions")
    private String[] cautions;

    @SerializedName("totalNutrients")
    private NutrientInfo totalNutrients;

    @SerializedName("totalDaily")
    private NutrientInfo totalDaily;

    public String getUri() {
        return uri;
    }

    public float getCalories() {
        return calories;
    }

    public float getTotalWeight() {
        return totalWeight;
    }

    public String[] getDietLabels() {
        return dietLabels;
    }

    public String[] getHealthLabels() {
        return healthLabels;
    }

    public String[] getCautions() {
        return cautions;
    }

    public NutrientInfo getTotalNutrients() {
        return totalNutrients;
    }

    public NutrientInfo getTotalDaily() {
        return totalDaily;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public void setTotalWeight(float totalWeight) {
        this.totalWeight = totalWeight;
    }

    public void setDietLabels(String[] dietLabels) {
        this.dietLabels = dietLabels;
    }

    public void setHealthLabels(String[] healthLabels) {
        this.healthLabels = healthLabels;
    }

    public void setCautions(String[] cautions) {
        this.cautions = cautions;
    }

    public void setTotalNutrients(NutrientInfo totalNutrients) {
        this.totalNutrients = totalNutrients;
    }

    public void setTotalDaily(NutrientInfo totalDaily) {
        this.totalDaily = totalDaily;
    }
}
