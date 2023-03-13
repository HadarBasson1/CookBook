package com.example.cookbook.model;

import com.google.gson.annotations.SerializedName;

public class NutrientInfoItem {
    @SerializedName("label")
    private String label;

    @SerializedName("quantity")
    private float quantity;

    @SerializedName("unit")
    private String unit;

    public float getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
