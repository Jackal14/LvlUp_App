package com.cs315.lvlup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.cs315.lvlup.fragments.HomeFragment;
import com.cs315.lvlup.fragments.ProfileFragment;
import com.cs315.lvlup.fragments.SettingsFragment;
import com.cs315.lvlup.login.StartScreen;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the auth instance for firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //Check login
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser == null)
        {
            //If there isn't a current user, load up the StartScreen activity
            Intent intent = new Intent(MainActivity.this, StartScreen.class);
            startActivity(intent);
        }
        else
        {
            //Else let the activity load the fragments
            //We can send necessary data to our fragments from here
            ProfileFragment fragment = ProfileFragment.newInstance("Example text");
            //Get a reference to the bottom navigation view and create a listener for clicks
            BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
            bottomNav.setOnNavigationItemSelectedListener(navListener);

            HomeFragment homeFragment = new HomeFragment();
            //When we first open the app, default to the home page
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    homeFragment).commit();

        }
        //Adapter listening will happen within their respective fragments
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    //Set our selected fragment based on what fragment was clicked from the bottom navigation view
                    switch(item.getItemId())
                    {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;

                        case R.id.nav_profile:
                            selectedFragment = ProfileFragment.newInstance("Example text");
                            break;

                        case R.id.nav_settings:
                            selectedFragment = new SettingsFragment();
                            break;

                    }

                    //Once selected fragment has been determined, replace the current fragment with selected fragment
                    assert selectedFragment != null;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

}