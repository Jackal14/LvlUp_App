package com.cs315.lvlup.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;

public class RoutineModel implements Serializable {
    @SerializedName("routineId")
    String routineId;
    @SerializedName("routineName")
    String routineName;
    @SerializedName("workouts")
    HashMap<String, WorkoutModel> WORKOUTS;

    public RoutineModel()
    {

    }

    public RoutineModel(String routineId, String name, HashMap<String, WorkoutModel> workouts)
    {
        this.routineId = routineId;
        this.routineName = name;
        this.WORKOUTS = workouts;
    }

    public String getRoutineId() {return routineId; }
    public String getRoutineName() {
        return routineName;
    }
    public HashMap<String, WorkoutModel> getWorkouts() {return WORKOUTS;}

    public void setRoutineId(String routineId) {this.routineId = routineId; }
    public void setRoutineName(String routineName) {
        this.routineName = routineName;
    }
    public void setWORKOUTS(HashMap<String, WorkoutModel> workouts) {this.WORKOUTS = workouts;}
}
