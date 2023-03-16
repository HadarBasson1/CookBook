package com.example.cookbook;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookbook.databinding.FragmentHomeBinding;
import com.example.cookbook.model.Model;
import com.example.cookbook.model.User;
import com.google.firebase.firestore.FieldValue;
import com.squareup.picasso.Picasso;

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link Home#newInstance} factory method to
// * create an instance of this fragment.

public class Home extends Fragment {
    FragmentHomeBinding binding;
    HomeViewModel viewModel;
    User user;
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
        user=Model.instance().getExsitUser().getValue();
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
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
//        binding.homeUserName.setText(viewModel.getUser().getValue().name);
//        if (viewModel.getUser().getValue().imgUrl  != null && viewModel.getUser().getValue().imgUrl.length() > 5) {
//                    Picasso.get().load(viewModel.getUser().getValue().imgUrl ).placeholder(R.drawable.avatar).into(binding.homeUserImg);
//                }else{
//                    binding.homeUserImg.setImageResource(R.drawable.avatar);
//                }

        viewModel.getUser().observe(getViewLifecycleOwner(), exist_user -> {
            user = exist_user;
            if (user != null) {
                binding.homeUserName.setText(user.name);
                String imageUrl = user.getImgUrl();
//                Picasso.get().invalidate(user.getImgUrl()); // clear the cache for the old URL
//                String imageUrl = user.getImgUrl() + System.currentTimeMillis();
                if (imageUrl  != null && imageUrl.length() > 5) {
                    Picasso.get().load(imageUrl).placeholder(R.drawable.avatar).into(binding.homeUserImg);
                }else{
                    binding.homeUserImg.setImageResource(R.drawable.avatar);
                }
            }

        });

//        viewModel.getUser().observe(getViewLifecycleOwner(), exist_user -> {
//            user = exist_user;
//            if (user != null) {
//                binding.homeUserName.setText(user.name);
//                if (user.getImgUrl()  != null && user.getImgUrl().length() > 5) {
//                    Picasso.get().load(user.getImgUrl()).placeholder(R.drawable.avatar).into(binding.homeUserImg);
//                }else{
//                    binding.homeUserImg.setImageResource(R.drawable.avatar);
//                }
//            }
//        });

//        UserName=view.findViewById(R.id.home_user_name);
//        imageUser=view.findViewById(R.id.home_user_img);
//        props = getActivity().getIntent().getStringArrayExtra("props");


//        String [] arg = getActivity().getIntent().getStringArrayExtra("props");
//        String userId=arg[0];

//        Model.instance().getPropsById(id, User->{
//            if (User != null) {
//           UserName.setText(User.name);
//           if (User.getImgUrl()  != null && User.getImgUrl().length() > 5) {
//               Picasso.get().load(User.getImgUrl()).placeholder(R.drawable.avatar).into(imageUser);
//           }else{
//               imageUser.setImageResource(R.drawable.avatar);
//           }
//                   }
//        else UserName.setText("noam"); });
//        });
//        if (props != null) {
//            UserName.setText(props[0]);
//        }
        ImageView edit_btn = view.findViewById(R.id.home_edit_profile_btn);
        ImageView my_recipes = view.findViewById(R.id.home_my_recipes_btn);
        ImageView all_recipes = view.findViewById(R.id.home_all_recipes_btn);
        ImageView nutrition = view.findViewById(R.id.home_nutrition_btn);




        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_home_fragment_to_edit_user_fragment);
            }
        });


        my_recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_myRecipeList);
            }
        });

        all_recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_home_fragment_to_recipesList);
            }
        });

        nutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_nutritionPage);
            }
        });



        return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        Model.instance().refreshAllUsers();
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
