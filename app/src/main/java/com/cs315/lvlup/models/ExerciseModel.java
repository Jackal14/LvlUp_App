package com.cs315.lvlup.models;

import com.google.gson.annotations.SerializedName;

public class ExerciseModel {
    @SerializedName("exerciseName")
    String exerciseName;
    @SerializedName("exerciseSets")
    Integer exerciseSets;
    @SerializedName("exerciseReps")
    Integer exerciseReps;
    @SerializedName("exerciseLoad")
    Integer exerciseLoad;
    @SerializedName("exerciseRPE")
    Integer exerciseRPE;
    @SerializedName("exerciseRest")
    Integer exerciseRest;
    @SerializedName("exerciseNotes")
    String exerciseNotes;

    public ExerciseModel()
    {

    }

    public ExerciseModel(String name, Integer sets, Integer reps, Integer load, Integer RPE, Integer rest, String notes)
    {
        this.exerciseName = name;
        this.exerciseSets = sets;
        this.exerciseReps = reps;
        this.exerciseLoad = load;
        this.exerciseRPE = RPE;
        this.exerciseRest = rest;
        this.exerciseNotes = notes;
    }

    public String getExerciseName() {return exerciseName;}
    public Integer getExerciseSets() {return exerciseSets;}
    public Integer getExerciseReps() {return exerciseReps;}
    public Integer getExerciseLoad() {return exerciseLoad;}
    public Integer getExerciseRPE() {return exerciseRPE;}
    public Integer getExerciseRest() {return exerciseRest;}
    public String getExerciseNotes() {return exerciseNotes;}

    public void setExerciseName(String name) {this.exerciseName = name;}
    public void setExerciseSets(Integer sets) {this.exerciseSets = sets;}
    public void setExerciseReps(Integer reps) {this.exerciseReps = reps;}
    public void setExerciseLoad(Integer load) {this.exerciseLoad = load;}
    public void setExerciseRPE(Integer RPE) {this.exerciseSets = RPE;}
    public void setExerciseRest(Integer rest) {this.exerciseRest = rest;}
    public void setExerciseNotes(String notes) {this.exerciseNotes = notes;}
}
