package com.example.cookbook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.cookbook.databinding.FragmentHomeBinding;
import com.example.cookbook.databinding.FragmentRecipeListBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivityApp extends AppCompatActivity {
//    String userid;
//    FirebaseAuth aut;
   public String [] props;
   NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
       props = getIntent().getStringArrayExtra("props");
//
//
        NavHostFragment navHostFragment = (NavHostFragment)getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this,navController);


//       HomeDirections.ActionGlobalHomeFragment action = HomeDirections.actionGlobalHomeFragment(props[0],props[1]);
//        navController.navigate((NavDirections) action);

//        BottomNavigationView navView = findViewById(R.id.main_bottomNavigationView);
//        NavigationUI.setupWithNavController(navView,navController);
    }

    int fragmentMenuId = 0;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
//        if (fragmentMenuId != 0){
//            menu.removeItem(fragmentMenuId);
//        }
//        fragmentMenuId = 0;

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        userid=aut.getCurrentUser().getUid();
        if (item.getItemId() == android.R.id.home) {
            navController.popBackStack();
        }
        else if(item.getItemId() == R.id.logout_menu){

            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
        else{
            fragmentMenuId = item.getItemId();
            return NavigationUI.onNavDestinationSelected(item,navController);
        }
        return super.onOptionsItemSelected(item);
    }


    public String[] getProps() {
        return props;
    }
}