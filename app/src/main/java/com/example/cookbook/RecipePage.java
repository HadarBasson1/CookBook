package com.example.cookbook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cookbook.databinding.FragmentHomeBinding;
import com.example.cookbook.databinding.FragmentRecipePageBinding;


public class RecipePage extends Fragment {
    FragmentRecipePageBinding binding;
    TextView titleTv,durationTv,difficultyTv,categoryTv,instructionsTv;
    ImageView imgUrl;
    String title,difficulty,category,duration,instructions;


    public RecipePage() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bundle bundle = getArguments();
//        if (getArguments() != null) {
//            this.title = bundle.getString("TITLE");
////            this.img_url = bundle.getString("I");
//            this.difficulty= bundle.getString("DIFFICULTY");
//            this.category = bundle.getString("CATEGORY");
//            this.duration = bundle.getString("DURATION");
//            this.instructions = bundle.getString("INSTRUCTIONS");
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


//            String arg1 = getArguments().getString("arg1");
//            int arg2 = getArguments().getInt("arg2");
            // Do something with the arguments...

        // Inflate the layout for this fragment
//        View view= inflater.inflate(R.layout.fragment_recipe_page, container, false);
        binding = FragmentRecipePageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.recipePageTitle.setText(RecipePageArgs.fromBundle(getArguments()).getTitle());
        binding.recipePageLevel.setText(RecipePageArgs.fromBundle(getArguments()).getLevel());
        binding.recipePageCategory.setText(RecipePageArgs.fromBundle(getArguments()).getCategory());
        binding.recipePageDuraion.setText(RecipePageArgs.fromBundle(getArguments()).getDuration());
        binding.recipePageInstructions.setText(RecipePageArgs.fromBundle(getArguments()).getInstructions());



//        difficulty = RecipePageArgs.fromBundle(getArguments()).getLevel();
//        category= RecipePageArgs.fromBundle(getArguments()).getCategory();
//        duration = RecipePageArgs.fromBundle(getArguments()).getDuration();
//        instructions = RecipePageArgs.fromBundle(getArguments()).getInstructions();

        return view;
    }

}