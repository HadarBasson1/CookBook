package com.example.cookbook;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.cookbook.databinding.FragmentNutritionApiBinding;
import com.example.cookbook.databinding.FragmentRecipePageBinding;
import com.example.cookbook.model.NutrientInfo;
import com.example.cookbook.model.NutritionModel;


public class NutritionPage extends Fragment {
    FragmentNutritionApiBinding binding;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NutritionPage() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
//    public static NutritionPage newInstance(String param1, String param2) {
//        NutritionPage fragment = new NutritionPage();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentNutritionApiBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.nutritionEnergy.setVisibility(View.GONE);
        binding.nutritionProtein.setVisibility(View.GONE);
        binding.nutritionCarbs.setVisibility(View.GONE);
        binding.nutritionSuger.setVisibility(View.GONE);
        binding.nutritionFiber.setVisibility(View.GONE);
        binding.nutritionFat.setVisibility(View.GONE);
        binding.nutritionColesterol.setVisibility(View.GONE);
        binding.nutritionName.setVisibility(View.GONE);




        binding.nutritionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = String.valueOf(binding.nutritionSearchTV.getText());
                LiveData<NutrientInfo> data = NutritionModel.instance.searchInfoByTitle("100 gram "+value);
                data.observe(getViewLifecycleOwner(),info->{
                    if (info != null) {
                        binding.nutritionName.setText(value);
                        if(info.getEnergy()!=null) binding.nutritionEnergy.setText("Energy:  "+ info.getEnergy().getQuantity()+" "+info.getEnergy().getUnit());
                        if(info.getProtein()!=null) binding.nutritionProtein.setText("Protein:  "+ info.getProtein().getQuantity()+" "+info.getProtein().getUnit());
                        if(info.getCarbohydrates()!=null) binding.nutritionCarbs.setText("Carbs:  "+ info.getCarbohydrates().getQuantity()+" "+info.getCarbohydrates().getUnit());
                        if(info.getSugar()!=null) binding.nutritionSuger.setText("Sugar:  "+ info.getSugar().getQuantity()+" "+info.getSugar().getUnit());
                        if(info.getFiber()!=null) binding.nutritionFiber.setText("Fiber:  "+ info.getFiber().getQuantity()+" "+info.getFiber().getUnit());
                        if(info.getFat()!=null)binding.nutritionFat.setText("Fat:  "+ info.getFat().getQuantity()+" "+info.getFat().getUnit());
                        if(info.getCholesterol()!=null)binding.nutritionColesterol.setText("Cholesterol:  "+ info.getCholesterol().getQuantity()+" "+info.getCholesterol().getUnit());
                        binding.nutritionEnergy.setVisibility(View.VISIBLE);
                        binding.nutritionProtein.setVisibility(View.VISIBLE);
                        binding.nutritionCarbs.setVisibility(View.VISIBLE);
                        binding.nutritionSuger.setVisibility(View.VISIBLE);
                        binding.nutritionFiber.setVisibility(View.VISIBLE);
                        binding.nutritionFat.setVisibility(View.VISIBLE);
                        binding.nutritionColesterol.setVisibility(View.VISIBLE);
                        binding.nutritionName.setVisibility(View.VISIBLE);
                    }

                    else     Toast.makeText(MyApplication.getMyContext(),"not found information about this food,please search again!",Toast.LENGTH_SHORT).show();
//                    Log.d("TAG","*****************************************************" + info.getEnergy().getQuantity()+" "+info.getEnergy().getUnit() +"**********************************************************");


                });
            }
        });



        return view;
//        return inflater.inflate(R.layout.fragment_nutrition_api, container, false);
    }

}