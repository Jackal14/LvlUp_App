package com.cs315.lvlup.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cs315.lvlup.R;
import com.cs315.lvlup.login.LoginActivity;
import com.cs315.lvlup.login.RegistrationActivity;
import com.cs315.lvlup.login.StartScreen;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.Objects;

public class ProfileFragment extends Fragment {
    //Static string for our key to use when retrieving bundle
    private static final String ARG_TEXT = "argText";
    private TextView textView;
    private String text;
    private Button logout;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    String userID;
    ListenerRegistration registration;

    //Whenever we call new instance from the outside, it will do all the data retrieval here
    public static ProfileFragment newInstance(String text)
    {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TEXT, text);
        fragment.setArguments(args);
        return fragment;
    }

    //On create view is essentially like the onCreate method, this is where we'll want to get our data and set it
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        textView = v.findViewById(R.id.profile_text);
        logout = (Button) v.findViewById(R.id.logout_button);
        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firestore.collection("users").document(userID);
        registration = documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                textView.setText(documentSnapshot.getString("username"));
            }
        });
        //Button for logout
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileFragment.super.getContext(), StartScreen.class);
                //firebaseAuth.signOut();
                startActivity(intent);
            }
        });

        return v;

    }
    @Override
    public void onStop() {
        super.onStop();
        registration.remove();
    }
}
