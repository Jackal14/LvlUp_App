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
import com.cs315.lvlup.models.RoutineModel;
import com.cs315.lvlup.models.WorkoutModel;

import java.util.ArrayList;
import java.util.HashMap;

public class RoutineListAdapter extends ArrayAdapter<RoutineModel> {
    private static final String TAG2 = "RoutineListAdapter";
    private Context mContext;

    int mResource;

    public RoutineListAdapter(Context context, int resource, ArrayList<RoutineModel> routineModels) {
        super(context, resource, routineModels);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String routineId = getItem(position).getRoutineId();
        String routineName = getItem(position).getRoutineName();
        HashMap<String, WorkoutModel> workouts = (HashMap<String, WorkoutModel>) getItem(position).getWorkouts();

        RoutineModel routineModel = new RoutineModel(routineId, routineName, workouts);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView routineNameTextView = (TextView) convertView.findViewById(R.id.routine_name);

        routineNameTextView.setText(routineName);
        return convertView;
    }
}