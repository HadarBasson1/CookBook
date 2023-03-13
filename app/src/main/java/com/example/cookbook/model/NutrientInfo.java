package com.example.cookbook.model;

import com.google.gson.annotations.SerializedName;

public class NutrientInfo {
    @SerializedName("ENERC_KCAL")
    private NutrientInfoItem energy;

    @SerializedName("FAT")
    private NutrientInfoItem fat;

    @SerializedName("FASAT")
    private NutrientInfoItem saturatedFat;

    @SerializedName("CHOCDF")
    private NutrientInfoItem carbohydrates;

    @SerializedName("FIBTG")
    private NutrientInfoItem fiber;

    @SerializedName("SUGAR")
    private NutrientInfoItem sugar;

    @SerializedName("PROCNT")
    private NutrientInfoItem protein;

    @SerializedName("CHOLE")
    private NutrientInfoItem cholesterol;

    @SerializedName("NA")
    private NutrientInfoItem sodium;

    public NutrientInfoItem getEnergy() {
        return energy;
    }

    public NutrientInfoItem getFat() {
        return fat;
    }

    public NutrientInfoItem getSaturatedFat() {
        return saturatedFat;
    }

    public NutrientInfoItem getCarbohydrates() {
        return carbohydrates;
    }

    public NutrientInfoItem getFiber() {
        return fiber;
    }

    public NutrientInfoItem getSugar() {
        return sugar;
    }

    public NutrientInfoItem getProtein() {
        return protein;
    }

    public NutrientInfoItem getCholesterol() {
        return cholesterol;
    }

    public NutrientInfoItem getSodium() {
        return sodium;
    }

    public void setEnergy(NutrientInfoItem energy) {
        this.energy = energy;
    }

    public void setFat(NutrientInfoItem fat) {
        this.fat = fat;
    }

    public void setSaturatedFat(NutrientInfoItem saturatedFat) {
        this.saturatedFat = saturatedFat;
    }

    public void setCarbohydrates(NutrientInfoItem carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public void setFiber(NutrientInfoItem fiber) {
        this.fiber = fiber;
    }

    public void setSugar(NutrientInfoItem sugar) {
        this.sugar = sugar;
    }

    public void setProtein(NutrientInfoItem protein) {
        this.protein = protein;
    }

    public void setCholesterol(NutrientInfoItem cholesterol) {
        this.cholesterol = cholesterol;
    }

    public void setSodium(NutrientInfoItem sodium) {
        this.sodium = sodium;
    }
}
