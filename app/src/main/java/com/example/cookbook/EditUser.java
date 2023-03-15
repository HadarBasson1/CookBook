package com.example.cookbook;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.cookbook.databinding.FragmentEditUserBinding;
import com.example.cookbook.model.Model;
import com.example.cookbook.model.User;
import com.squareup.picasso.Picasso;


public class EditUser extends Fragment {
    FragmentEditUserBinding binding;
    EditUserViewModel viewModel;
    User user;
    ImageView imagegallery,imagecamera;
    ActivityResultLauncher<Void> cameraLauncher;
    ActivityResultLauncher<String> galleryLauncher;

    Boolean isAvatarSelected = false;



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
        binding = FragmentEditUserBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
//        Model.instance().exist_user.observe(getViewLifecycleOwner(),exist_user->{
//            user=exist_user;
//    });
        imagegallery=binding.editPageImg;
        imagecamera=binding.editPageCamera;
        imagecamera.setOnClickListener(view1->{
            cameraLauncher.launch(null);
        });

        imagegallery.setOnClickListener(view1->{
            galleryLauncher.launch("image/*");
        });

        viewModel.getUser().observe(getViewLifecycleOwner(), exist_user -> {
            user = exist_user;
            if (user != null) {
                binding.editPageName.setText(user.name);
                binding.editPagePhone.setText(user.phone);
                binding.editPageAddress.setText(user.address);
                if (user.getImgUrl() != null && user.getImgUrl().length() > 5) {
                    Picasso.get().load(user.getImgUrl()).placeholder(R.drawable.man).into(binding.editPageImg);
                } else {
                    binding.editPageImg.setImageResource(R.drawable.man);
                }
            }

        });


        binding.editPageSaveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String name = binding.editPageName.getText().toString();
                String phone = binding.editPagePhone.getText().toString();
                String address = binding.editPageAddress.getText().toString();
                if (isAvatarSelected) {
                    binding.editPageImg.setDrawingCacheEnabled(true);
                    binding.editPageImg.buildDrawingCache();
                    Bitmap bitmap = ((BitmapDrawable) binding.editPageImg.getDrawable()).getBitmap();
                    Model.instance().uploadImage(user.id, bitmap, url -> {
                        if (url != null) {
                            user.setImgUrl(url);
                        }
                    });
                }
                Model.instance().updateUser(user.id, name, phone, address,"", new Model.Listener<Void>() {
                    @Override
                    public void onComplete(Void data) {
                        Navigation.findNavController(v).navigate(R.id.action_global_home_fragment);
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(EditUserViewModel.class);
    }

}