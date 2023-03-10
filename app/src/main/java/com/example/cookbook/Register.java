package com.example.cookbook;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cookbook.model.Model;
import com.example.cookbook.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
        EditText editTextEmail,editTextPassword,editTextPhone,editTextAddress,editTextName;
        ImageView imageView;
        Button buttonReg;
        FirebaseAuth mAuth;
        ProgressBar progressBar;

    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;

    Boolean isAvatarSelected = false;

//    String userid;
//    FirebaseAuth aut;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail=findViewById(R.id.register_email);
        editTextPassword=findViewById(R.id.regidter_password);
        editTextPhone=findViewById(R.id.register_phone);
        editTextAddress=findViewById(R.id.register_address);
        editTextName=findViewById(R.id.register_username);
        imageView=findViewById(R.id.register_img);
        buttonReg=findViewById(R.id.register_btn);
        progressBar = findViewById(R.id.register_progressbar);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    imageView.setImageBitmap(result);
                    isAvatarSelected = true;
                }
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    imageView.setImageURI(result);
                    isAvatarSelected = true;
                }
            }
        });



        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email, password,name,id, phone,address,imageurl;

                email=String.valueOf(editTextEmail.getText());
                password=String.valueOf(editTextPassword.getText());
                name=String.valueOf(editTextName.getText());
                phone =String.valueOf(editTextPhone.getText());
                address=String.valueOf(editTextAddress.getText());
                imageurl=null;
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this,"Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this,"Enter Password",Toast.LENGTH_SHORT).show();
                    return;
                }



                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);

                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Account created.",
                                            Toast.LENGTH_SHORT).show();
                                    //////save in cloud store
                                    String user_id=mAuth.getUid();
                                    User user = new User(name,user_id, phone,address,imageurl);
                                    if (isAvatarSelected){
                                        imageView.setDrawingCacheEnabled(true);
                                        imageView.buildDrawingCache();
                                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                                        Model.instance().uploadImage(user_id, bitmap, url->{
                                            if (url != null){
                                                user.setImgUrl(url);
                                            }
                                            Model.instance().addUser(user, (unused) -> {
//                                                Navigation.findNavController(view1).popBackStack();

                                                Intent intent = new Intent(getApplicationContext(), MainActivityApp.class);
                                                intent.putExtra("props",new String[] {name,imageurl});
                                                startActivity(intent);
                                            });
                                        });
                                    }else {
                                        Model.instance().addUser(user, (unused) -> {
//                                            return;
                                        });
                                    }
//
                                    Intent intent = new Intent(getApplicationContext(), MainActivityApp.class);
                                    intent.putExtra("props",new String[] {user_id});
                                    startActivity(intent);

//                                    // Sign in success, update UI with the signed-in user's information
//                                    FirebaseUser user = mAuth.getCurrentUser();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });
    }



}