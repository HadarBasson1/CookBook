package com.example.cookbook;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookbook.databinding.FragmentAddRecipeBinding;
import com.example.cookbook.model.Model;
import com.example.cookbook.model.Recipe;


public class AddRecipe extends Fragment {
    FragmentAddRecipeBinding binding;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;


    Boolean isAvatarSelected = false;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        FragmentActivity parentActivity = getActivity();
//        parentActivity.addMenuProvider(new MenuProvider() {
//            @Override
//            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
//                menu.removeItem(R.id.addStudentFragment);
//            }
//
//            @Override
//            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
//                return false;
//            }
//        },this, Lifecycle.State.RESUMED);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    binding.addRecipeImg.setImageBitmap(result);
                    isAvatarSelected = true;
                }
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    binding.addRecipeImg.setImageURI(result);
                    isAvatarSelected = true;
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddRecipeBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        binding.addRecipeSavebtn.setOnClickListener(view1 -> {
            String title = binding.addRecipeRecipename.getText().toString();
            String category = binding.addRecipeCategory.getText().toString();
            String duration = binding.addRecipeTime.getText().toString();
            String level = binding.addRecipeLevel.getText().toString();
            String inst=binding.addRecipeInstructions.getText().toString();
            String key = RandomKeyGenerator.generateRandomKey();
            SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
            String id=sharedPref.getString("ID_USER", "user_id");
            Recipe recipe = new Recipe(title,category,level,duration,id,"",inst,key,"false");
            if (isAvatarSelected){
                binding.addRecipeImg.setDrawingCacheEnabled(true);
                binding.addRecipeImg.buildDrawingCache();
                Bitmap bitmap = ((BitmapDrawable) binding.addRecipeImg.getDrawable()).getBitmap();
                Model.instance().uploadImage(key, bitmap, url->{
                    if (url != null){
                        recipe.setImgUrl(url);
                    }
                    Model.instance().addRecipe(recipe, (unused) -> {
                        Navigation.findNavController(view1).popBackStack();
                    });
                });
            }else {
                Model.instance().addRecipe(recipe, (unused) -> {
                    Navigation.findNavController(view1).popBackStack();
                });
            }
        });

//        binding.cancellBtn.setOnClickListener(view1 -> Navigation.findNavController(view1).popBackStack(R.id.studentsListFragment,false));

        binding.addRecipeCamera.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });

        binding.addRecipeImg.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });
        return view;
    }
}