package com.cs315.lvlup.creators_viewers;

import static com.cs315.lvlup.creators_viewers.RoutineViewer.ROUTINE_ID_2;
import static com.cs315.lvlup.creators_viewers.WorkoutCreator.BODY_FOCUS;
import static com.cs315.lvlup.creators_viewers.WorkoutCreator.WORKOUT_NAME;
import static com.cs315.lvlup.fragments.HomeFragment.ROUTINE_ID;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cs315.lvlup.R;
import com.cs315.lvlup.models.ExerciseModel;

import java.util.HashMap;

public class ExerciseCreator extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_creator);
        String EXERCISE_MAP = "EXERCISE";
        Intent intent = getIntent();
        String routineId = intent.getStringExtra(ROUTINE_ID_2);
        HashMap<String, ExerciseModel> map = new HashMap<>();
        String workoutName = intent.getStringExtra(WORKOUT_NAME);
        String bodyFocus = intent.getStringExtra(BODY_FOCUS);
        if(intent.getSerializableExtra(EXERCISE_MAP) != null)
        {
            map = (HashMap<String, ExerciseModel>) intent.getSerializableExtra(EXERCISE_MAP);
        }

        Button saveExercise = findViewById(R.id.save_exercise_button);
        EditText exerciseName = findViewById(R.id.exercise_name_edit_text);
        EditText exerciseSets = findViewById(R.id.exercise_sets_edit_text);
        EditText exerciseReps = findViewById(R.id.exercise_reps_edit_text);
        EditText exerciseLoad = findViewById(R.id.exercise_load_edit_text);
        EditText exerciseRPE = findViewById(R.id.exercise_rpe_edit_text);
        EditText exerciseRest = findViewById(R.id.exercise_rest_edit_text);
        EditText exerciseNotes = findViewById(R.id.exercise_notes_edit_text);

        HashMap<String, ExerciseModel> finalMap = map;
        saveExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open up an exercise creator activity, add that exercise to list and repopulate list view
                //Send the exercise map to be able to add to it
                ExerciseModel model = new ExerciseModel(
                        exerciseName.getText().toString(),
                        Integer.valueOf(exerciseSets.getText().toString()),
                        Integer.valueOf(exerciseReps.getText().toString()),
                        Integer.valueOf(exerciseLoad.getText().toString()),
                        Integer.valueOf(exerciseRPE.getText().toString()),
                        Integer.valueOf(exerciseRest.getText().toString()),
                        exerciseNotes.getText().toString());
                finalMap.put(model.getExerciseName(),model);
                Intent intent = new Intent(ExerciseCreator.this, WorkoutCreator.class);
//                intent.putExtra(, model.getWorkoutName());
//                intent.putExtra(, model.getBodyFocus());
                intent.putExtra(WORKOUT_NAME, workoutName);
                intent.putExtra(BODY_FOCUS, bodyFocus);
                intent.putExtra(EXERCISE_MAP, finalMap);
                intent.putExtra(ROUTINE_ID_2, routineId);
                startActivity(intent);
            }
        });
    }
}