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

public class User {

    @PrimaryKey
    @NonNull
    public String id="";
    public String name="";
    public String phone="";
    public String address="";
    public String imgUrl="";
    public Long lastUpdated=0L;



    public User(){
    }

    public User(String name, @NonNull String id, String phone, String address, String imgUrl) {
        this.name = name;
        this.id = id;
        this.phone = phone;
        this.address = address;
        this.imgUrl = imgUrl;
    }

    static final String ID = "id";
    static final String NAME = "name";
    static final String PHONE = "phone";
    static final String ADDRESS = "address";
    static final String AVATAR = "avatar";
    static final String COLLECTION = "users";
    static final String LAST_UPDATED = "lastUpdated";
    static final String LOCAL_LAST_UPDATED = "recipes_local_last_update";

    public static User fromJson(Map<String,Object> json){
        String name = (String)json.get(NAME);
        String id = (String)json.get(ID);
        String phone = (String)json.get(PHONE);
        String address = (String)json.get(ADDRESS);
        String avatar = (String)json.get(AVATAR);
        User user = new User(name,id,phone,address,avatar);
        try{
            Timestamp time = (Timestamp) json.get(LAST_UPDATED);
            user.setLastUpdated((long) time.getSeconds());

        }catch(Exception e){

        }
        return user;
    }






    public Map<String,Object> toJson(){
        Map<String, Object> json = new HashMap<>();
        json.put(NAME, getName());
        json.put(ID, getId());
        json.put(PHONE, getPhone());
        json.put(ADDRESS, getAddress());
        json.put(AVATAR, getImgUrl());
        json.put(LAST_UPDATED, FieldValue.serverTimestamp());
        return json;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

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

    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public Long getLastUpdated() {
        return lastUpdated;
    }
}
