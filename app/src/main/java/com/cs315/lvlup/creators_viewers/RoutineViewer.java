package com.cs315.lvlup.creators_viewers;

import static com.cs315.lvlup.fragments.HomeFragment.ROUTINE_ID;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.cs315.lvlup.R;
import com.cs315.lvlup.adapters.RoutineListAdapter;
import com.cs315.lvlup.adapters.WorkoutListAdapter;
import com.cs315.lvlup.fragments.HomeFragment;
import com.cs315.lvlup.login.LoginActivity;
import com.cs315.lvlup.login.StartScreen;
import com.cs315.lvlup.models.RoutineModel;
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

public class RoutineViewer extends AppCompatActivity {
    //Firebase variables to connect to firebase and firestore
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    String userID;
    CollectionReference workoutsRef;
    ListenerRegistration registration;
    String routineId;

    public static final String WORKOUT_NAMES = "name";
    public static final String BODY_FOCUS = "bodyFocus";
    public static final String EXERCISE = "exercise";
    public static final String ROUTINE_ID_2 = "routineId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_viewer);

        //Get our firebase and firestore instances
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        firestore = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        routineId = intent.getStringExtra(ROUTINE_ID);
        CollectionReference workoutsRef = firestore.collection("users")
                .document(userID).collection("routines").document(routineId).collection("workouts");

        getWorkouts();

        //Button for adding workouts
        Button addWorkout = (Button) findViewById(R.id.add_workout_button);
        addWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RoutineViewer.this, WorkoutCreator.class);
                intent.putExtra(ROUTINE_ID_2, routineId);
                startActivity(intent);
            }
        });
    }

    //Method to display our workouts in the list using a custom adapter class
    private void getWorkouts()
    {
        Query query = firestore.collection("users")
                .document(userID).collection("routines").document(routineId).collection("workouts");
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
                        displayWorkouts(hashMap);
                    }
                });
    }
    private void displayWorkouts(HashMap<String, WorkoutModel> hashMap)
    {
        ListView listOfWorkouts = (ListView)findViewById(R.id.list_of_workouts);
        // Getting Collection of values from HashMap
        Collection<WorkoutModel> values = hashMap.values();
        // Creating an ArrayList of values
        ArrayList<WorkoutModel> listOfValues = new ArrayList<WorkoutModel>(values);
        WorkoutListAdapter adapter = new WorkoutListAdapter(this, R.layout.workout, listOfValues);

        listOfWorkouts.setAdapter(adapter);
        listOfWorkouts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RoutineViewer.this, WorkoutViewer.class);
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