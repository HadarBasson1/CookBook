package com.example.cookbook.model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    private static final Model _instance = new Model();

    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private FirebaseModel firebaseModel = new FirebaseModel();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();

    public static Model instance(){
        return _instance;
    }
    private Model(){
    }

    public interface Listener<T>{
        void onComplete(T data);
    }


    public enum LoadingState{
        LOADING,
        NOT_LOADING
    }
    final public MutableLiveData<LoadingState> EventRecipesListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);


    private LiveData<List<Recipe>> recipeList;
    public LiveData<List<Recipe>> getAllRecipes() {
        if(recipeList == null){
            recipeList = localDb.recipeDao().getAll();
            refreshAllRecipes();
        }
        return recipeList;
    }

    public void refreshAllRecipes(){
        EventRecipesListLoadingState.setValue(LoadingState.LOADING);
        // get local last update

      Long localLastUpdate = Recipe.getLocalLastUpdate();

        // get all updated recorde from firebase since local last update
        firebaseModel.getAllRecipesSince(localLastUpdate,list->{
            executor.execute(()->{
                Log.d("TAG", " firebase return : " + list.size());
                Long time = localLastUpdate;
                for(Recipe recipe:list){
                    // insert new records into ROOM
                    localDb.recipeDao().insertAll(recipe);
                    if (time < recipe.getLastUpdated()){
                        time = recipe.getLastUpdated();
                    }
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // update local last update
                Recipe.setLocalLastUpdate(time);
                EventRecipesListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }

    public void addRecipe(Recipe recipe, Listener<Void> listener){
        firebaseModel.addRecipe(recipe,(Void)->{
            refreshAllRecipes();
            listener.onComplete(null);
        });
    }

    public void addUser(User user, Listener<Void> listener){
        firebaseModel.addUser(user,(Void)->{
//            refreshAllRecipes();
            listener.onComplete(null);
        });
    }

    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        firebaseModel.uploadImage(name,bitmap,listener);
    }
}
