package com.cs315.lvlup.creators_viewers;

import static com.cs315.lvlup.creators_viewers.RoutineViewer.ROUTINE_ID_2;
import static com.cs315.lvlup.fragments.HomeFragment.ROUTINE_ID;

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
import com.cs315.lvlup.models.WorkoutModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

//Activity for users to create workouts, connects to firebase and firestore to store the workouts added

public class WorkoutCreator extends AppCompatActivity {
    //Static strings to use for passing intentExtras to the exercise creator for data persistence
    public static final String BODY_FOCUS = "bodyFocus";
    public static final String WORKOUT_NAME = "workoutName";
    //Firebase variables to connect to firebase and firestore
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    String userID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_creator);
        String EXERCISE_MAP = "EXERCISE";

        //Get references to the different layout components
        EditText workoutName = findViewById(R.id.workout_name_edit_text);
        EditText bodyFocus = findViewById(R.id.workout_body_focus_edit_text);
        Button addExercise = findViewById(R.id.add_exercise_to_workout);
        Button saveWorkout = findViewById(R.id.save_workout_button);
        ListView exerciseList = findViewById(R.id.exercise_list_view);


        //Get our firebase/firestore instances and intent
        Intent intent = getIntent();
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        String routineId = intent.getStringExtra(ROUTINE_ID_2);



        //Get current exercises and display them in a list using an adapter
        HashMap<String, ExerciseModel> map = new HashMap<>();
        try
        {
            //If there was a workout name or body focus, retrieve it from intentExtras and set the text
            if(intent.getStringExtra(WORKOUT_NAME) != null)
            {
                workoutName.setText(intent.getStringExtra(WORKOUT_NAME));
            }
            if(intent.getStringExtra(BODY_FOCUS) != null)
            {
                bodyFocus.setText(intent.getStringExtra(BODY_FOCUS));
            }
            //If we have exercises in the map, retrieve the map and use our adapter to display them in our list view
            if((HashMap<String, ExerciseModel>) intent.getSerializableExtra(EXERCISE_MAP) != null)
            {
                map = (HashMap<String, ExerciseModel>) intent.getSerializableExtra(EXERCISE_MAP);
                // Getting Collection of values from HashMap
                Collection<ExerciseModel> values = map.values();
                // Creating an ArrayList of values
                ArrayList<ExerciseModel> listOfValues = new ArrayList<ExerciseModel>(values);
                ExerciseListAdapter adapter = new ExerciseListAdapter(this, R.layout.exercise, listOfValues);
                exerciseList.setAdapter(adapter);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        //Open the exercise creator activity, send our current exercises and strings to retrieve when we come back
        HashMap<String, ExerciseModel> finalMap = map;
        addExercise.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Open up an exercise creator activity, add that exercise to list and repopulate list view
                //Send the exercise map to be able to add to it
                Intent intent = new Intent(WorkoutCreator.this, ExerciseCreator.class);

                if(finalMap != null)
                {
                    intent.putExtra(WORKOUT_NAME, workoutName.getText().toString());
                    intent.putExtra(BODY_FOCUS, bodyFocus.getText().toString());
                    intent.putExtra(EXERCISE_MAP, finalMap);
                    intent.putExtra(ROUTINE_ID_2, routineId);
                }
                startActivity(intent);
            }
        });

        //Save the workout to document in firestore
        saveWorkout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Create a new workout model based on the data provided
                WorkoutModel workoutModel = new WorkoutModel(workoutName.getText().toString(), bodyFocus.getText().toString(), finalMap);

                try{
                    CollectionReference workoutsCollection = firestore.collection("users")
                            .document(userID).collection("routines").document(routineId).collection("workouts");
                    DocumentReference workoutsDocument = firestore.collection("users")
                            .document(userID).collection("routines").document(routineId);

                    workoutsCollection.add(workoutModel);
//                    if(workoutsCollection == null)
//                    {
//                        workoutsDocument.set(workoutModel);
//                    }
//                    else
//                    {
//
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }




                Intent intent = new Intent(WorkoutCreator.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}