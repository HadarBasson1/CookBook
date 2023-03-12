package com.example.cookbook;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookbook.databinding.FragmentEditUserBinding;
import com.example.cookbook.model.Model;
import com.example.cookbook.model.User;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditUser#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditUser extends Fragment {
    FragmentEditUserBinding binding;
    EditUserViewModel viewModel;
    User user;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditUser() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditUser.
     */
    // TODO: Rename and change types and number of parameters
    public static EditUser newInstance(String param1, String param2) {
        EditUser fragment = new EditUser();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
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
                Model.instance().updateUser(user.id, name, phone, address, new Model.Listener<Void>() {
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