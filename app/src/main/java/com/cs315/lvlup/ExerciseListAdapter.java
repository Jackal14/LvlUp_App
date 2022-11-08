package com.cs315.lvlup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.cs315.lvlup.models.ExerciseModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ExerciseListAdapter extends ArrayAdapter<ExerciseModel> {
    private static final String TAG = "ExerciseListAdapter";
    private Context mContext;

    int mResource;

    public ExerciseListAdapter(Context context, int resource, ArrayList<ExerciseModel> exercises)
    {
        super(context, resource, exercises);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        String exerciseName = getItem(position).getExerciseName();
        Integer exerciseSets = getItem(position).getExerciseSets();
        Integer exerciseReps = getItem(position).getExerciseReps();
        Integer exerciseLoad = getItem(position).getExerciseLoad();
        Integer exerciseRPE = getItem(position).getExerciseRPE();
        Integer exerciseRest = getItem(position).getExerciseRest();
        String exerciseNotes = getItem(position).getExerciseNotes();

        ExerciseModel exerciseModel =
                new ExerciseModel(exerciseName,exerciseSets,exerciseReps,exerciseLoad,exerciseRPE,exerciseRest,exerciseNotes);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView exName = (TextView) convertView.findViewById(R.id.exercise_name);
        TextView exSets = (TextView) convertView.findViewById(R.id.exercise_sets);
        TextView exReps = (TextView) convertView.findViewById(R.id.exercise_reps);
        TextView exLoad = (TextView) convertView.findViewById(R.id.exercise_load);
        TextView exRPE = (TextView) convertView.findViewById(R.id.exercise_RPE);
        TextView exRest = (TextView) convertView.findViewById(R.id.exercise_rest);

        exName.setText(exerciseName);
        exSets.setText(exerciseSets.toString());
        exReps.setText(exerciseReps.toString());
        exLoad.setText(exerciseLoad.toString());
        //exRPE.setText(exerciseRPE.toString());
        exRest.setText(exerciseRest.toString());

        return convertView;
    }

}
