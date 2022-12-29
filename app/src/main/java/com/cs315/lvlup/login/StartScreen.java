package com.cs315.lvlup.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.cs315.lvlup.MainActivity;
import com.cs315.lvlup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        //Get the auth instance for firebase
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        //Check login
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null)
        {
            //If there isn't a current user, load up the StartScreen activity
            Intent intent = new Intent(StartScreen.this, MainActivity.class);
            startActivity(intent);
        }

        //Button for login
        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(StartScreen.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //Button for register
        Button registerButton = (Button) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(StartScreen.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}