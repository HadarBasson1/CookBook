package com.example.cookbook.model;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cookbook.MyApplication;
import com.google.firebase.firestore.FieldValue;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Recipe {
    @PrimaryKey
    @NonNull
    public String key="";
    public String title="";
    public String category="";
    public String difficulty="";
    public String duration="";
    public String editor="";
    public String imgUrl="";
    public String instructions="";
    public String isDeleted="";
    public Long lastUpdated=0L;

    public Recipe(){
    }

    public Recipe(String title, String category, String difficulty, String duration, String editor, String imgUrl,String instructions,String key,String isDeleted) {
        this.title = title;
        this.category = category;
        this.difficulty = difficulty;
        this.duration = duration;
        this.editor = editor;
        this.imgUrl = imgUrl;
        this.instructions=instructions;
        this.key=key;
        this.isDeleted=isDeleted;
    }


    static final String TITLE = "title";
    static final String CATEGORY = "category";
    static final String DIFFICULTY = "difficulty";
    static final String DURATION = "duration";
    static final String EDITOR = "editor";
    static final String AVATAR = "avatar";
    static final String INSTRUCTIONS = "instructions";
    static final String KEY = "key";
    static final String COLLECTION = "recipes";
    static final String IS_DELETED = "isDeleted";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "recipes_local_last_update";


    public static Recipe fromJson(Map<String,Object> json){
        String title = (String)json.get(TITLE);
        String category = (String)json.get(CATEGORY);
        String difficulty = (String)json.get(DIFFICULTY);
        String duration = (String)json.get(DURATION);
        String editor = (String)json.get(EDITOR);
        String avatar = (String)json.get(AVATAR);
        String instructions = (String)json.get(INSTRUCTIONS);
        String key=(String)json.get(KEY);
        String isDeleted=(String) json.get(IS_DELETED);
        Recipe rc = new Recipe(title,category,difficulty,duration,editor,avatar,instructions,key,isDeleted);
        try{
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            rc.setLastUpdated((long) time.getSeconds());

        }catch(Exception e){

        }
        return rc;
    }



    @NonNull
    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getDuration() {
        return duration;
    }

    public String getEditor() {
        return editor;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getKey() {
        return key;
    }

    public String getDeleted() {
        return isDeleted;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setDeleted(String deleted) {
        isDeleted = deleted;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    //
    public static Long getLocalLastUpdate() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong(LOCAL_LAST_UPDATED, 0);
    }
//
    public static void setLocalLastUpdate(Long time) {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong(LOCAL_LAST_UPDATED,time);
        editor.commit();
    }

    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(TITLE, getTitle());
        json.put(CATEGORY, getCategory());
        json.put(DIFFICULTY, getDifficulty());
        json.put(DURATION, getDuration());
        json.put(EDITOR, getEditor());
        json.put(AVATAR, getImgUrl());
        json.put(KEY, getKey());
        json.put(IS_DELETED, getDeleted());
        json.put(INSTRUCTIONS, getInstructions());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
    }


}
