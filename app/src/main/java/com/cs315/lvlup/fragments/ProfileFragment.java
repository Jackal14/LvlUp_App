package com.cs315.lvlup.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cs315.lvlup.R;

public class ProfileFragment extends Fragment {
    //Static string for our key to use when retrieving bundle
    private static final String ARG_TEXT = "argText";
    private TextView textView;
    private String text;

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

        //If arguments isn't null, get values from the bundle
        if(getArguments() != null)
        {
            text = getArguments().getString(ARG_TEXT);
        }

        textView.setText(text);
        return v;

    }
}
