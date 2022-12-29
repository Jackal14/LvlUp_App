package com.cs315.lvlup.creators_viewers;

import static com.cs315.lvlup.creators_viewers.RoutineViewer.EXERCISE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.cs315.lvlup.R;
import com.cs315.lvlup.adapters.ExerciseListAdapter;
import com.cs315.lvlup.models.ExerciseModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class WorkoutViewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_viewer);
        ListView listView = findViewById(R.id.list_of_exercises);
        Intent intent = getIntent();
        HashMap<String, ExerciseModel> map = (HashMap<String, ExerciseModel>) intent.getSerializableExtra(EXERCISE);
//        Set<String> keys = map.keySet();
//        ArrayList<String> listOfKeys
//                = new ArrayList<String>(keys);
        // Getting Collection of values from HashMap
        Collection<ExerciseModel> values = map.values();

        // Creating an ArrayList of values
        ArrayList<ExerciseModel> listOfValues = new ArrayList<ExerciseModel>(values);


        //ArrayList<ExerciseModel> temp = new ArrayList<ExerciseModel>();

        ExerciseListAdapter adapter = new ExerciseListAdapter(this, R.layout.exercise, listOfValues);
        listView.setAdapter(adapter);

        //Create delete button, on the button press, create a new hashmap with updated values and then save
    }


}