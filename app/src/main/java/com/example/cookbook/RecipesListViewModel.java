package com.example.cookbook;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.cookbook.model.Model;
import com.example.cookbook.model.Recipe;

import java.util.List;

public class RecipesListViewModel extends ViewModel {
    private LiveData<List<Recipe>> data = Model.instance().getAllRecipes();

    LiveData<List<Recipe>> getData(){
        return data;
    }
}
