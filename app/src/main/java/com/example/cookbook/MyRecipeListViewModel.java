package com.example.cookbook;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.cookbook.model.Model;
import com.example.cookbook.model.Recipe;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MyRecipeListViewModel extends ViewModel {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private LiveData<List<Recipe>> data=Model.instance().getFilterRecipes(mAuth.getUid());


    LiveData<List<Recipe>> getData(String id){
        return data;
    }

}
