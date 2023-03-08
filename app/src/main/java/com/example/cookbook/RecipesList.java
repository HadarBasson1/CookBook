package com.example.cookbook;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
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
import com.example.cookbook.model.Recipe;


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
        binding.recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new  RecipeRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int pos) {
                Log.d("TAG", "Row was clicked " + pos);
                Recipe recipe = viewModel.getData().getValue().get(pos);
                RecipesListDirections.ActionRecipesListToRecipePage action = RecipesListDirections.actionRecipesListToRecipePage(recipe.title);
                Navigation.findNavController(view).navigate((NavDirections) action);
            }
        });

        View addButton = view.findViewById(R.id.btnAdd);
        NavDirections action = RecipesListDirections.actionGlobalAddRecipe();
        addButton.setOnClickListener(Navigation.createNavigateOnClickListener(action));

        binding.progressBar.setVisibility(View.GONE);

        viewModel.getData().observe(getViewLifecycleOwner(),list->{
            adapter.setData(list);
        });

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

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get( RecipesListViewModel.class);
    }

    void reloadData(){
        Model.instance().refreshAllRecipes();
    }

}