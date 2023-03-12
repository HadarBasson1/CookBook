package com.example.cookbook;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookbook.model.FirebaseModel;
import com.example.cookbook.model.Model;
import com.example.cookbook.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link Home#newInstance} factory method to
// * create an instance of this fragment.

public class Home extends Fragment {

//    FirebaseAuth mAuth;
//    String [] props;
    TextView UserName;
    String id;
    ImageView imageUser;
            //recipe_card_row_user_img

    public Home() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mAuth = FirebaseAuth.getInstance();
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        id= sharedPref.getString("ID_USER", "user_name");

//        Model.instance().getPropsById(mAuth.getUid(),props->{
////            setProps(props);
////            UserName.setText(props[0]);
////        });


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_home, container, false);
        UserName=view.findViewById(R.id.home_user_name);
        imageUser=view.findViewById(R.id.home_user_img);
//        props = getActivity().getIntent().getStringArrayExtra("props");


//        String [] arg = getActivity().getIntent().getStringArrayExtra("props");
//        String userId=arg[0];
        Model.instance().getPropsById(id, User->{
            if (User != null) {
           UserName.setText(User.name);
           if (User.getImgUrl()  != null && User.getImgUrl().length() > 5) {
               Picasso.get().load(User.getImgUrl()).placeholder(R.drawable.avatar).into(imageUser);
           }else{
               imageUser.setImageResource(R.drawable.avatar);
           }
                   }
        else UserName.setText("noam"); });
//        });
//        if (props != null) {
//            UserName.setText(props[0]);
//        }
        ImageView edit_btn = view.findViewById(R.id.home_edit_profile_btn);
        ImageView my_recipes = view.findViewById(R.id.home_my_recipes_btn);




        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_home_fragment_to_edit_user_fragment);
            }
        });


        my_recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_home_fragment_to_recipesList);
            }
        });

        return view;

    }




    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

//    public void setProps(String[] props) {
//        this.props = props;
//    }
}
