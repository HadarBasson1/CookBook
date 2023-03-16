package com.example.cookbook.model;

import static android.content.ContentValues.TAG;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;

public class FirebaseModel {
    FirebaseFirestore db;
    FirebaseStorage storage;

    FirebaseModel(){
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
        storage = FirebaseStorage.getInstance();

    }

    public void getAllRecipesSince(Long since, Model.Listener<List<Recipe>> callback){

            db.collection(Recipe.COLLECTION)
                    .whereGreaterThanOrEqualTo(Recipe.LAST_UPDATED, new Timestamp(since, 0))
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            List<Recipe> list = new LinkedList<>();
                            if (task.isSuccessful()) {
                                QuerySnapshot jsonsList = task.getResult();
                                for (DocumentSnapshot json : jsonsList) {
                                    Recipe recipe = Recipe.fromJson(json.getData());
                                    list.add(recipe);
                                }
                            }
                            callback.onComplete(list);
                        }
                    });

    }

    public void getAllUsersSince(Long since, Model.Listener<List<User>> callback){

        db.collection(User.COLLECTION)
                .whereGreaterThanOrEqualTo(User.LAST_UPDATED, new Timestamp(since, 0))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<User> list = new LinkedList<>();
                        if (task.isSuccessful()) {
                            QuerySnapshot jsonsList = task.getResult();
                            for (DocumentSnapshot json : jsonsList) {
                                User user = User.fromJson(json.getData());
                                list.add(user);
                            }
                        }
                        callback.onComplete(list);
                    }
                });

    }

//    public User getExistUser(String id, Model.Listener<User> listener){
//
//        db.collection(User.COLLECTION).document(id)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            QuerySnapshot jsonsList = task.getResult();
//                            for (DocumentSnapshot json : jsonsList) {
//                                User user = User.fromJson(json.getData());
//                            }
//                        }
//                        listener.onComplete(use);
//                    }
//                });
//
//    }

    public void addRecipe(Recipe recipe, Model.Listener<Void> listener) {
        db.collection(Recipe.COLLECTION).document(recipe.getKey()).set(recipe.toJson())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete(null);
                    }
                });
    }

    public void addUser(User user, Model.Listener<Void> listener) {
        db.collection(User.COLLECTION).document(user.getId()).set(user.toJson())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete(null);
                    }
                });
    }

    public void updateUser(String id,String name,String phone,String address,String imgUrl, Model.Listener<Void> listener){

        DocumentReference user_update = db.collection(User.COLLECTION).document(id);
        user_update
                .update("avatar",imgUrl,"name",name,"address", address,"phone",phone,"lastUpdated", FieldValue.serverTimestamp())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onComplete(null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onComplete(null);
                    }
                });
    }


    public void updateRecipe(String title, String category, String time, String level, String inst, String imgUrl,String key, Model.Listener<Void> listener) {
        DocumentReference user_update = db.collection(Recipe.COLLECTION).document(key);
        user_update
                .update("avatar",imgUrl,"category", category,"difficulty",level,"duration",time,"title",title,"instructions",inst,"lastUpdated", FieldValue.serverTimestamp())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onComplete(null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onComplete(null);
                    }
                });
    }



    void uploadImage(String name, Bitmap bitmap, Model.Listener<String> listener){
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("images/" + name +".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.onComplete(uri.toString());
                    }
                });
            }
        });

    }

    public void getUserPropById(String id, Model.Listener<String[]>listener) {
        DocumentReference docRef = db.collection("users").document(id);
        String [] props=new String[2];
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        props[0]= (String) document.getData().get("name");
                        props[1]= (String) document.getData().get("avatar");
                        listener.onComplete(props);
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

    }


    public void deleteRecipe(String key, Model.Listener<Void> listener) {
        DocumentReference deleted = db.collection(Recipe.COLLECTION).document(key);
        deleted
                .update("isDeleted","true","lastUpdated", FieldValue.serverTimestamp())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        listener.onComplete(null);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onComplete(null);
                    }
                });
    }
}
