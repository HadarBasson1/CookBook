package com.example.cookbook.model;

import com.google.gson.annotations.SerializedName;

public class NutritionResponse {
    @SerializedName("totalNutrients")
    private NutrientInfo totalNutrients;

//    @SerializedName("calories")
//    private float calories;

    public NutrientInfo getTotalNutrients() {
        return totalNutrients;
    }

//    public float getCalories() {
//        return calories;
//    }
}




