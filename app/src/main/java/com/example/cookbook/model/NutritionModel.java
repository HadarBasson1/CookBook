package com.example.cookbook.model;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NutritionModel {
    final public static NutritionModel instance = new NutritionModel();

    final String BASE_URL = "https://api.edamam.com/";
    Retrofit retrofit;
    NutritionAPI nutritionAPI;

    private NutritionModel(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        nutritionAPI = retrofit.create(NutritionAPI.class);
    }

    public LiveData<NutrientInfo> searchInfoByTitle(String title){
        MutableLiveData<NutrientInfo> data = new MutableLiveData<>();

        Call<NutritionResponse> call = nutritionAPI.getNutritionData(
                "73c41008",
                "05e00d422cee8579657eb67ec11d4d06",
                "cooking",
                title
//                "100 gram pasta"
        );
//        Call<NutritionResponse> call = nutritionAPI.getNutritionData(appId, appKey, "cooking", query);
        call.enqueue(new Callback<NutritionResponse>() {
            @Override
            public void onResponse(Call<NutritionResponse> call, Response<NutritionResponse> response) {
                if (response.isSuccessful()) {
                    NutritionResponse nutrition = response.body();
                    // Extract the nutrient values from the NutritionResponse object
                    float sugar = nutrition.getTotalNutrients().getSugar().getQuantity();
                    float protein = nutrition.getTotalNutrients().getProtein().getQuantity();
                    float cholesterol = nutrition.getTotalNutrients().getCholesterol().getQuantity();
                    data.setValue(nutrition.getTotalNutrients());
//                float kcal = nutrition.getCalories();
                } else {
                    Log.d("TAG", "----- searchNutrientInfo response error");
                }
            }


                @Override
                public void onFailure (Call < NutritionResponse > call, Throwable t){
                    Log.d("TAG", "----- searchNutrientInfo fail");
                }

        });
        return data;
    }
}
