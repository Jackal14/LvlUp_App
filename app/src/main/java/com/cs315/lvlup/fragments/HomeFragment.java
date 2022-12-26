package com.cs315.lvlup.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cs315.lvlup.R;
import com.cs315.lvlup.creators_viewers.WorkoutCreator;
import com.cs315.lvlup.creators_viewers.WorkoutViewer;
import com.cs315.lvlup.adapters.WorkoutListAdapter;
import com.cs315.lvlup.models.WorkoutModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

//Fragment class that will display user workouts/allow users to add more

public class HomeFragment extends Fragment implements View.OnClickListener {
    //Firebase variables to connect to firebase and firestore
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    String userID;
    CollectionReference workoutsRef;
    ListenerRegistration registration;

    public static final String WORKOUT_NAMES = "name";
    public static final String BODY_FOCUS = "bodyFocus";
    public static final String EXERCISE = "exercise";

    //When creating view, set up the entire layout
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        //Get our firebase and firestore instances
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        firestore = FirebaseFirestore.getInstance();

        //Get reference to our workouts collection
        workoutsRef = firestore.collection("users").document(userID).collection("workouts");
        getWorkouts(v);

        //Add on click listener for a button to add workouts to workout list
        Button addWorkout = (Button) v.findViewById(R.id.add_workout_button);
        addWorkout.setOnClickListener(this);
        return v;
    }

    //On click method for add workout button, starts workout creator activity
    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(HomeFragment.super.getContext(), WorkoutCreator.class);
        startActivity(intent);
    }

    //Method to display our workouts in the list using a custom adapter class
    private void getWorkouts(View v)
    {
        Query query = firestore.collection("users").document(userID).collection("workouts");
        registration = query.addSnapshotListener(
                new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null)
                        {
                            return;
                        }

                        HashMap<String, WorkoutModel> hashMap = new HashMap<>();
                        //Iterate through the documents we retrieved from the collection
                        for(QueryDocumentSnapshot documentSnapshot : value)
                        {
                            WorkoutModel model = documentSnapshot.toObject(WorkoutModel.class);
                            hashMap.put(model.getWorkoutName(), model);
                        }
                        displayWorkouts(hashMap, v);
                    }
                });
    }
    private void displayWorkouts(HashMap<String, WorkoutModel> hashMap, View v)
    {
        ListView listOfWorkouts = (ListView)v.findViewById(R.id.list_of_workouts);
        // Getting Collection of values from HashMap
        Collection<WorkoutModel> values = hashMap.values();
        // Creating an ArrayList of values
        ArrayList<WorkoutModel> listOfValues = new ArrayList<WorkoutModel>(values);
        WorkoutListAdapter adapter = new WorkoutListAdapter(getActivity(), R.layout.workout, listOfValues);

        listOfWorkouts.setAdapter(adapter);
        listOfWorkouts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HomeFragment.super.getContext(), WorkoutViewer.class);
                intent.putExtra(WORKOUT_NAMES, adapter.getItem(position).getWorkoutName());
                intent.putExtra(BODY_FOCUS, adapter.getItem(position).getBodyFocus());
                intent.putExtra(EXERCISE,  adapter.getItem(position).getExercises());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onPause() {

        super.onPause();
        registration.remove();
    }

}
