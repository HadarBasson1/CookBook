package com.example.cookbook;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cookbook.databinding.FragmentMyRecipeListBinding;
import com.example.cookbook.databinding.FragmentRecipeListBinding;
import com.example.cookbook.model.Model;
import com.example.cookbook.model.Recipe;
import com.google.firebase.auth.FirebaseAuth;


public class MyRecipeList extends Fragment {

    FragmentMyRecipeListBinding binding;
    RecipeRecyclerAdapter adapter;
    MyRecipeListViewModel viewModel;
    FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  FragmentMyRecipeListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        mAuth = FirebaseAuth.getInstance();
        String id=mAuth.getUid();
        binding.myRecipeListRecyclerView.setHasFixedSize(true);
        binding.myRecipeListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecipeRecyclerAdapter(getLayoutInflater(),viewModel.getData(id).getValue());
        adapter.setIfEditable(true);
        binding.myRecipeListRecyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new  RecipeRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "Row was clicked " + pos);
                Recipe recipe = viewModel.getData(id).getValue().get(pos);
                MyRecipeListDirections.ActionMyRecipeListToRecipePage action = MyRecipeListDirections.actionMyRecipeListToRecipePage(recipe.title,recipe.imgUrl, recipe.difficulty, recipe.category, recipe.duration,recipe.editor,true);
                Navigation.findNavController(view).navigate(action);

            }
        });

        View addButton = view.findViewById(R.id.my_recipe_list_btnAdd);
        NavDirections action = MyRecipeListDirections.actionGlobalAddRecipe();
        addButton.setOnClickListener(Navigation.createNavigateOnClickListener(action));

        binding.progressBar.setVisibility(View.GONE);

        viewModel.getData(id).observe(getViewLifecycleOwner(),list->{
            adapter.setData(list);
        });

//        viewModel.getUsers().observe(getViewLifecycleOwner(),list->{
//            adapter.setUsers(list);
//        });

        Model.instance().EventRecipesListLoadingState.observe(getViewLifecycleOwner(), status->{
            binding.myRecipeListSwipeRefresh.setRefreshing(status == Model.LoadingState.LOADING);
        });

        binding.myRecipeListSwipeRefresh.setOnRefreshListener(()->{
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
        viewModel = new ViewModelProvider(this).get(MyRecipeListViewModel.class);
    }

    void reloadData(){
        Model.instance().refreshAllRecipes();
        Model.instance().refreshAllUsers();
        //        refreshAllUsers();
    }
}