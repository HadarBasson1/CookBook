package com.example.cookbook.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("select * from Recipe where isDeleted = :noDeleted")
    LiveData<List<Recipe>> getAll(String noDeleted);

    @Query("select * from Recipe where editor = :nameEditor")
    Recipe getRecipeByEditor(String nameEditor);

    @Query("select * from Recipe where editor = :nameEditor and isDeleted = :noDeleted" )
    LiveData<List<Recipe>> getRecipeById(String nameEditor,String noDeleted);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Recipe... recipes);

    @Delete
    void delete(Recipe recipe);
}
