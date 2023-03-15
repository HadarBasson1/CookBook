package com.example.cookbook.model;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.cookbook.MyApplication;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.Query;

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
    final public MutableLiveData<LoadingState> EventUsersListLoadingState = new MutableLiveData<LoadingState>(LoadingState.NOT_LOADING);

    private LiveData<List<Recipe>> recipeList;
    public LiveData<List<Recipe>> getAllRecipes() {
        if(recipeList == null){
            recipeList = localDb.recipeDao().getAll();
            refreshAllRecipes();
            refreshAllUsers();
        }
        return recipeList;
    }

    private LiveData<List<Recipe>> filterList;
    public LiveData<List<Recipe>> getFilterRecipes(String id) {
        filterList=getAllRecipes();
        filterList = localDb.recipeDao().getRecipeById(id);
        return  filterList;
    }



    public LiveData<User> exist_user;
    public LiveData<User> getExsitUser() {
        if(exist_user == null){
            SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
            String id= sharedPref.getString("ID_USER", "user_name");
            exist_user = localDb.userDao().getPropsById(id);
        }
        return exist_user;
    }


    public void logOut() {
        exist_user=null;
    }

//    private LiveData<List<User>> userList;
//    public LiveData<List<User>> getAllUsers() {
//        if(userList == null){
//            userList = localDb.userDao().getAllUsers();
//            refreshAllUsers();
//        }
//        return userList;
//    }

    public void refreshFilterRecipes(String id){
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
                    if(recipe.getEditor()==id){
                        localDb.recipeDao().insertAll(recipe);
                        if (time < recipe.getLastUpdated()){
                            time = recipe.getLastUpdated();
                        }
                    }
                }
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // update local last update
                Recipe.setLocalLastUpdate(time);
                EventRecipesListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
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
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // update local last update
                Recipe.setLocalLastUpdate(time);
                EventRecipesListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }



    public void refreshAllUsers(){
        EventUsersListLoadingState.setValue(LoadingState.LOADING);
        // get local last update
        Long localLastUpdate = User.getLocalLastUpdate();
        // get all updated recorde from firebase since local last update
        firebaseModel.getAllUsersSince(localLastUpdate,list->{
            executor.execute(()->{
                Log.d("TAG", " firebase return : " + list.size());
                Long time = localLastUpdate;
                for(User user:list){
                    // insert new records into ROOM
                    localDb.userDao().insertAll(user);
                    if (time < user.getLastUpdated()){
                        time = user.getLastUpdated();
                    }
                }
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // update local last update
                User.setLocalLastUpdate(time);
                EventUsersListLoadingState.postValue(LoadingState.NOT_LOADING);
            });
        });
    }




    public void getPropsById(String userId,Listener<User> listener){

        LiveData<User> userLiveData=localDb.userDao().getPropsById(userId);
//        listener.onComplete(userLiveData.getValue());
        userLiveData.observeForever(new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null) {
                    userLiveData.removeObserver(this);
                    listener.onComplete(user);
                }
            }
        });
//        LiveData<User> userLiveData= localDb.userDao().getPropsById(userId);
//        String [] props= new String[2];
//        props[0]=userLiveData.getValue().name;
//        props[1]=userLiveData.getValue().imgUrl;
//        return props;
//        userLiveData = localDb.userDao().getPropsById(userId);
//        final String[] props = new String[2];
//        userLiveData.observeForever(new Observer<User>() {
//            @Override
//            public void onChanged(User user) {
//                if (user != null) {
//                    props[0] = user.name;
//                    props[1] = user.imgUrl;
//                    userLiveData.removeObserver(this);
//                    listener.onComplete(props);
//                }
//            }
//        });


    }



    public void addRecipe(Recipe recipe, Listener<Void> listener){
        firebaseModel.addRecipe(recipe,(Void)->{
            refreshAllRecipes();
            listener.onComplete(null);
        });
    }

    public void addUser(User user, Listener<Void> listener){
        firebaseModel.addUser(user,(Void)->{
            refreshAllUsers();
            listener.onComplete(null);
        });
    }

    public void updateUser(String id,String name,String phone,String address,String imgUrl, Listener<Void> listener) {
        firebaseModel.updateUser(id, name, phone, address, new Listener<Void>() {
            @Override
            public void onComplete(Void data) {
                refreshAllUsers();
                listener.onComplete(null);
            }
        });
    }


    public void updateRecipe(String title, String category, String time, String level, String inst, String imgUrl, Listener<Void> listener) {
        firebaseModel.updateRecipe(title, category, time, level,inst,imgUrl, new Listener<Void>() {
            @Override
            public void onComplete(Void data) {
                refreshAllRecipes();
                listener.onComplete(null);
            }
        });
    }



    public void uploadImage(String name, Bitmap bitmap, Listener<String> listener) {
        firebaseModel.uploadImage(name,bitmap,listener);
    }

//    public void getUserPropById(String id,Listener<String[]>listener) {
//      firebaseModel.getUserPropById(id,props->{
//          listener.onComplete(props);
//      });
//    }

}
