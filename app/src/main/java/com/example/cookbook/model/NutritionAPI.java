package com.example.cookbook.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NutritionAPI {
    @GET("api/nutrition-data")
    Call<NutritionResponse> getNutritionData(
            @Query("app_id") String appId,
            @Query("app_key") String appKey,
            @Query("nutrition-type") String nutritionType,
            @Query("ingr") String ingr
    );
}


// https://api.edamam.com/api/nutrition-data?app_id=73c41008&app_key=05e00d422cee8579657eb67ec11d4d06%09&nutrition-type=cooking&ingr=100%20gram%20pasta