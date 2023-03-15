package com.example.cookbook;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cookbook.databinding.FragmentEditRecipePageBinding;
import com.example.cookbook.databinding.FragmentRecipePageBinding;
import com.example.cookbook.model.Model;
import com.squareup.picasso.Picasso;


public class EditRecipePage extends Fragment {

    FragmentEditRecipePageBinding binding;
    ImageView imagegallery,imagecamera;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;
    String imgUrl;
    Boolean isAvatarSelected = false;


    public EditRecipePage() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        cameraLauncher = registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), new ActivityResultCallback<Bitmap>() {
            @Override
            public void onActivityResult(Bitmap result) {
                if (result != null) {
                    imagegallery.setImageBitmap(result);
                    isAvatarSelected = true;
                }
            }
        });
        galleryLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                if (result != null){
                    imagegallery.setImageURI(result);
                    isAvatarSelected = true;
                }
            }
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentEditRecipePageBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        imagegallery=binding.editRecipeImg;
        imagecamera=binding.editRecipeCamera;

        imagecamera.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });

        imagegallery.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });


        binding.editRecipeRecipename.setText(EditRecipePageArgs.fromBundle(getArguments()).getTitle());
        binding.editRecipeCategory.setText(EditRecipePageArgs.fromBundle(getArguments()).getCategory());
        binding.editRecipeTime.setText(EditRecipePageArgs.fromBundle(getArguments()).getTime());
        binding.editRecipeLevel.setText(EditRecipePageArgs.fromBundle(getArguments()).getLevel());
        binding.editRecipeInstructions.setText(EditRecipePageArgs.fromBundle(getArguments()).getInstruction());
        binding.editRecipeRecipename.setText(EditRecipePageArgs.fromBundle(getArguments()).getTitle());
        imgUrl = EditRecipePageArgs.fromBundle(getArguments()).getImgUrl();
        if (imgUrl != null && imgUrl.length() > 5) {
            Picasso.get().load(imgUrl).placeholder(R.drawable.salmon).into(binding.editRecipeImg);
        } else {
            binding.editRecipeImg.setImageResource(R.drawable.salmon);
        }

        binding.editRecipeSavebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String title = binding.editRecipeRecipename.getText().toString();
                String category = binding.editRecipeCategory.getText().toString();
                String time = binding.editRecipeTime.getText().toString();
                String level = binding.editRecipeLevel.getText().toString();
                String inst = binding.editRecipeInstructions.getText().toString();

                if (isAvatarSelected) {
                    binding.editRecipeImg.setDrawingCacheEnabled(true);
                    binding.editRecipeImg.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) binding.editRecipeImg.getDrawable()).getBitmap();
                    Model.instance().uploadImage(title, bitmap, url -> {
                        if (url != null) {
                            imgUrl=url;
                        }
                    });
                }
                Model.instance().updateRecipe(title, category, time, level,inst, imgUrl, new Model.Listener<Void>() {
                    @Override
                    public void onComplete(Void data) {
                        Navigation.findNavController(v).navigate(R.id.myRecipeList);
                    }
                });
            }
        });



        return view;
    }
}

