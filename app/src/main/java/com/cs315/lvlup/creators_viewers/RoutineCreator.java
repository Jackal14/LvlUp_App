package com.cs315.lvlup.creators_viewers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cs315.lvlup.MainActivity;
import com.cs315.lvlup.R;
import com.cs315.lvlup.adapters.ExerciseListAdapter;
import com.cs315.lvlup.models.ExerciseModel;
import com.cs315.lvlup.models.RoutineModel;
import com.cs315.lvlup.models.WorkoutModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class RoutineCreator extends AppCompatActivity {
    //Static strings to use for passing intentExtras to the exercise creator for data persistence
    public static final String ROUTINE_NAME = "routineName";

    //Firebase variables to connect to firebase and firestore
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    String userID;
    CollectionReference routinesCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_creator);

        String WORKOUT_MAP = "WORKOUT";

        //Get references to the different layout components
        EditText routineName = findViewById(R.id.routine_name_edit_text);
        Button saveWorkout = findViewById(R.id.save_routine_button);


        //Get our firebase/firestore instances and intent
        Intent intent = getIntent();
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        routinesCollection = firestore.collection("users").document(userID).collection("routines");

        //Get current exercises and display them in a list using an adapter
        HashMap<String, RoutineModel> map = new HashMap<>();
        try
        {
            //If there was a workout name or body focus, retrieve it from intentExtras and set the text
            if(intent.getStringExtra(ROUTINE_NAME) != null)
            {
                routineName.setText(intent.getStringExtra(ROUTINE_NAME));
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        HashMap<String, WorkoutModel> finalMap = new HashMap<>();
        //Save the workout to document in firestore
        saveWorkout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Create a new routine model based on the data provided
                RoutineModel routineModel = new RoutineModel(null, routineName.getText().toString(), finalMap);
                routinesCollection.add(routineModel);
                Intent intent = new Intent(RoutineCreator.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}