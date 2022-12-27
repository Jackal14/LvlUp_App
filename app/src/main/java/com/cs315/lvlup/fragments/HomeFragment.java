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
import com.cs315.lvlup.adapters.RoutineListAdapter;
import com.cs315.lvlup.creators_viewers.RoutineCreator;
import com.cs315.lvlup.creators_viewers.RoutineViewer;
import com.cs315.lvlup.models.RoutineModel;
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

    public static final String ROUTINE_NAME = "name";
    public static final String ROUTINE_ID = "id";
    public static final String WORKOUTS = "workouts";
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

        //Get workouts
        getRoutines(v);

        //Add on click listener for a button to add workouts to workout list
        Button addRoutine = (Button) v.findViewById(R.id.add_routine_button);
        addRoutine.setOnClickListener(this);
        return v;
    }

    //On click method for add workout button, starts workout creator activity
    @Override
    public void onClick(View v)
    {
        Intent intent = new Intent(HomeFragment.super.getContext(), RoutineCreator.class);
        startActivity(intent);
    }

    //Method to display our workouts in the list using a custom adapter class
    private void getRoutines(View v)
    {
        Query query = firestore.collection("users").document(userID).collection("routines");
        registration = query.addSnapshotListener(
                new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(error != null)
                        {
                            return;
                        }

                        HashMap<String, RoutineModel> hashMap = new HashMap<>();
                        //Iterate through the documents we retrieved from the collection
                        for(QueryDocumentSnapshot documentSnapshot : value)
                        {
                            RoutineModel model = documentSnapshot.toObject(RoutineModel.class);
                            model.setRoutineId(documentSnapshot.getId());
                            hashMap.put(model.getRoutineName(), model);
                        }
                        displayRoutines(hashMap, v);
                    }
                });
    }
    private void displayRoutines(HashMap<String, RoutineModel> hashMap, View v)
    {
        ListView listOfRoutines = (ListView)v.findViewById(R.id.list_of_routines);
        // Getting Collection of values from HashMap
        Collection<RoutineModel> values = hashMap.values();
        // Creating an ArrayList of values
        ArrayList<RoutineModel> listOfValues = new ArrayList<RoutineModel>(values);
        RoutineListAdapter adapter = new RoutineListAdapter(getActivity(), R.layout.routine, listOfValues);

        listOfRoutines.setAdapter(adapter);
        listOfRoutines.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HomeFragment.super.getContext(), RoutineViewer.class);
                intent.putExtra(ROUTINE_ID, adapter.getItem(position).getRoutineId());
                intent.putExtra(ROUTINE_NAME, adapter.getItem(position).getRoutineName());
                intent.putExtra(WORKOUTS,  adapter.getItem(position).getWorkouts());
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
