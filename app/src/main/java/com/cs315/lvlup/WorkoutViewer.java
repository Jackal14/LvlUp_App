package com.cs315.lvlup;

import static com.cs315.lvlup.fragments.HomeFragment.EXERCISE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cs315.lvlup.models.ExerciseModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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
    }


}