package com.cs315.lvlup.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkoutModel implements Serializable {
    @SerializedName("workoutName")
    String workoutName;
    @SerializedName("bodyFocus")
    String bodyFocus;
    @SerializedName("exercises")
    HashMap<String, ExerciseModel> EXERCISES;

    public WorkoutModel()
    {

    }

    public WorkoutModel(String name, String focus, HashMap<String, ExerciseModel> exercise)
    {
        this.workoutName = name;
        this.bodyFocus = focus;
        this.EXERCISES = exercise;
    }

    public String getWorkoutName() {
        return workoutName;
    }
    public String getBodyFocus(){
        return bodyFocus;
    }
    public HashMap<String, ExerciseModel> getExercises() {return EXERCISES;}

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }

    public void setBodyFocus(String bodyFocus) {
        this.bodyFocus = bodyFocus;
    }
    public void setEXERCISES(HashMap<String, ExerciseModel> exercises) {this.EXERCISES = exercises;}
}
