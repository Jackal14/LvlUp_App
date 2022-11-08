package com.cs315.lvlup.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cs315.lvlup.MainActivity;
import com.cs315.lvlup.R;
import com.cs315.lvlup.WorkoutCreator;
import com.cs315.lvlup.WorkoutViewer;
import com.cs315.lvlup.models.ExerciseModel;
import com.cs315.lvlup.models.WorkoutModel;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class HomeFragment extends Fragment implements View.OnClickListener {
    private FirebaseListAdapter<WorkoutModel> adapter;
    private FirebaseUser currentUser;

    public static final String WORKOUT_NAMES = "name";
    public static final String BODY_FOCUS = "bodyFocus";
    public static final String EXERCISE = "exercise";

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        displayWorkouts(v);
        Button addWorkout = (Button) v.findViewById(R.id.add_workout_button);
        addWorkout.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v)
    {
//        FirebaseDatabase.getInstance()
//                .getReference()
//                .push()
//                .setValue(new WorkoutModel(input.getText().toString(),
//                        FirebaseAuth.getInstance()
//                                .getCurrentUser().getEmail())
//                );
    }

    private void displayWorkouts(View v)
    {
        ListView listOfWorkouts = (ListView)v.findViewById(R.id.list_of_workouts);

        FirebaseListOptions<WorkoutModel> options = new FirebaseListOptions.Builder<WorkoutModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference(), WorkoutModel.class).setLayout(R.layout.workout).build();

        adapter = new FirebaseListAdapter<WorkoutModel>(options) {
            @Override
            protected void populateView(View v, WorkoutModel model, int position) {
                // Get references to the views of message.xml
                TextView workoutName = (TextView)v.findViewById(R.id.workout_name);
                TextView bodyFocus = (TextView)v.findViewById(R.id.body_focus);

                // Set their text
                workoutName.setText(model.getWorkoutName());
                bodyFocus.setText(model.getBodyFocus());
                HashMap<String, ExerciseModel> test = model.getExercises();

                //Setting item click listener for list view item
                v.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View v) {
                                             Intent intent = new Intent(HomeFragment.super.getContext(), WorkoutViewer.class);
                                             intent.putExtra(WORKOUT_NAMES, model.getWorkoutName());
                                             intent.putExtra(BODY_FOCUS, model.getBodyFocus());
                                             intent.putExtra(EXERCISE, model.getExercises());
                                             startActivity(intent);

                                         }
                                     });
//                // Format the date before showing it
//                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
//                        model.getMessageTime()));
            }
        };

        listOfWorkouts.setAdapter(adapter);
    }

    //Start and stop the adapter whenever this fragment is started
    @Override
    public void onStart() {
        super.onStart();
        if (currentUser != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (currentUser != null) {
            adapter.stopListening();
        }
    }
}
