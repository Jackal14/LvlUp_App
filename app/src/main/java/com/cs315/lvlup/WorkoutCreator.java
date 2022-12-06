package com.cs315.lvlup;

import static com.cs315.lvlup.fragments.HomeFragment.EXERCISE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cs315.lvlup.adapters.ExerciseListAdapter;
import com.cs315.lvlup.login.LoginActivity;
import com.cs315.lvlup.login.StartScreen;
import com.cs315.lvlup.models.ExerciseModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class WorkoutCreator extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_creator);
        EditText workoutName = findViewById(R.id.workout_name_edit_text);
        EditText bodyFocus = findViewById(R.id.workout_body_focus_edit_text);
        Button addExercise = findViewById(R.id.add_exercise_to_workout);
        Button saveWorkout = findViewById(R.id.save_workout_button);
        ListView exerciseList = findViewById(R.id.exercise_list_view);
        HashMap<String, ExerciseModel> exercises = new HashMap<String, ExerciseModel>();

        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open up an exercise creator fragment, add that exercise to list and repopulate list view
            }
        });
        saveWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save the workout to firebase

            }
        });



        Collection<ExerciseModel> values = exercises.values();
        ArrayList<ExerciseModel> listOfValues = new ArrayList<ExerciseModel>(values);
        ExerciseListAdapter adapter = new ExerciseListAdapter(this, R.layout.exercise, listOfValues);
        exerciseList.setAdapter(adapter);

    }
}