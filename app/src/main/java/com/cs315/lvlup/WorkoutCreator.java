package com.cs315.lvlup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.cs315.lvlup.adapters.ExerciseListAdapter;
import com.cs315.lvlup.fragments.HomeFragment;
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
        String EXERCISE_MAP = "EXERCISE";
        EditText workoutName = findViewById(R.id.workout_name_edit_text);
        EditText bodyFocus = findViewById(R.id.workout_body_focus_edit_text);
        Button addExercise = findViewById(R.id.add_exercise_to_workout);
        Button saveWorkout = findViewById(R.id.save_workout_button);
        ListView exerciseList = findViewById(R.id.exercise_list_view);
        Intent intent = getIntent();
        HashMap<String, ExerciseModel> map = new HashMap<>();
        try{
            map = (HashMap<String, ExerciseModel>) intent.getSerializableExtra(EXERCISE_MAP);
            // Getting Collection of values from HashMap
            Collection<ExerciseModel> values = map.values();
            // Creating an ArrayList of values
            ArrayList<ExerciseModel> listOfValues = new ArrayList<ExerciseModel>(values);
            ExerciseListAdapter adapter = new ExerciseListAdapter(this, R.layout.exercise, listOfValues);
            exerciseList.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HashMap<String, ExerciseModel> finalMap = map;
        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open up an exercise creator activity, add that exercise to list and repopulate list view
                //Send the exercise map to be able to add to it
                Intent intent = new Intent(WorkoutCreator.this, ExerciseCreator.class);
//                intent.putExtra(, model.getWorkoutName());
//                intent.putExtra(, model.getBodyFocus());
                if(finalMap != null)
                {
                    intent.putExtra(EXERCISE_MAP, finalMap);
                }
                startActivity(intent);
            }
        });
        saveWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Save the workout to firebase

            }
        });
    }
}