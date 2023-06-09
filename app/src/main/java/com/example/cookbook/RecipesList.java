package com.example.cookbook;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cookbook.databinding.FragmentRecipeListBinding;
import com.example.cookbook.model.Model;
import com.example.cookbook.model.NutrientInfo;
import com.example.cookbook.model.NutritionModel;
import com.example.cookbook.model.NutritionResponse;
import com.example.cookbook.model.Recipe;

import java.util.List;
import java.util.stream.Collectors;


public class RecipesList extends Fragment {

    FragmentRecipeListBinding binding;
    RecipeRecyclerAdapter adapter;
    RecipesListViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentRecipeListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecipeRecyclerAdapter(getLayoutInflater(),viewModel.getData().getValue());
        adapter.setIfEditable(false);
        binding.recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new  RecipeRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "Row was clicked " + pos);
                Recipe recipe = viewModel.getData().getValue().get(pos);
                    RecipesListDirections.ActionRecipesListToRecipePage action = RecipesListDirections.actionRecipesListToRecipePage(recipe.title,recipe.imgUrl, recipe.difficulty, recipe.category, recipe.duration,recipe.instructions,false);
                    Navigation.findNavController(view).navigate(action);
            }
        });

        View addButton = view.findViewById(R.id.btnAdd);
        NavDirections action = RecipesListDirections.actionGlobalAddRecipe();
        addButton.setOnClickListener(Navigation.createNavigateOnClickListener(action));

        binding.progressBar.setVisibility(View.GONE);

        viewModel.getData().observe(getViewLifecycleOwner(),list->{
//            List<Recipe> noDeleted = list.stream().filter(recipe -> recipe.isDeleted!="true").collect(Collectors.toList());
            adapter.setData(list);
        });

//        viewModel.getUsers().observe(getViewLifecycleOwner(),list->{
//            adapter.setUsers(list);
//        });

        Model.instance().EventRecipesListLoadingState.observe(getViewLifecycleOwner(), status->{
            binding.swipeRefresh.setRefreshing(status == Model.LoadingState.LOADING);
        });

        binding.swipeRefresh.setOnRefreshListener(()->{
            reloadData();
        });

//        LiveData<List<Movie>> data = MovieModel.instance.searchMoviesByTitle("avatar");
//        data.observe(getViewLifecycleOwner(),list->{
//            list.forEach(item->{
//                Log.d("TAG","got movie: " + item.getTitle() + " " + item.getPoster());
//            });
//        });

//        LiveData<NutrientInfo> data = NutritionModel.instance.searchInfoByTitle("100 gram banana");
//        data.observe(getViewLifecycleOwner(),info->{
//                Log.d("TAG","*****************************************************" + info.getEnergy().getQuantity()+" "+info.getEnergy().getUnit() +"**********************************************************");
//        });

        return view;

        //https://api.edamam.com/api/nutrition-data?app_id=73c41008&app_key=05e00d422cee8579657eb67ec11d4d06%09&nutrition-type=cooking&ingr=100%20gram%20pasta
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get( RecipesListViewModel.class);
    }


    void reloadData(){
        Model.instance().refreshAllRecipes();
        Model.instance().refreshAllUsers();
        //        refreshAllUsers();
    }

}