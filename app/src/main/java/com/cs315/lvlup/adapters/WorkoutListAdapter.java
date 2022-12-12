package com.cs315.lvlup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cs315.lvlup.R;
import com.cs315.lvlup.models.ExerciseModel;
import com.cs315.lvlup.models.WorkoutModel;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WorkoutListAdapter extends ArrayAdapter<WorkoutModel> {
    private static final String TAG2 = "WorkoutListAdapter";
    private Context mContext;

    int mResource;

    public WorkoutListAdapter(Context context, int resource, ArrayList<WorkoutModel> workoutModels) {
        super(context, resource, workoutModels);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String workoutName = getItem(position).getWorkoutName();
        String bodyFocus = getItem(position).getBodyFocus();
        HashMap<String, ExerciseModel> exercises= (HashMap<String, ExerciseModel>) getItem(position).getExercises();

        WorkoutModel workoutModel = new WorkoutModel(workoutName, bodyFocus, exercises);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView workoutNameTextView = (TextView) convertView.findViewById(R.id.workout_name);
        TextView bodyFocusTextView = (TextView) convertView.findViewById(R.id.body_focus);

        workoutNameTextView.setText(workoutName);
        bodyFocusTextView.setText(bodyFocus);

        return convertView;
    }
}
