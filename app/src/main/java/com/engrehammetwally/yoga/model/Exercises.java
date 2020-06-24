package com.engrehammetwally.yoga.model;



public class Exercises {
    private int exercisesImageId;
    private String exercisesName;

    public Exercises(int exercisesImageId, String exercisesName) {
        this.exercisesImageId = exercisesImageId;
        this.exercisesName = exercisesName;
    }

    public int getExercisesImageId() {
        return exercisesImageId;
    }

    public void setExercisesImageId(int exercisesImageId) {
        this.exercisesImageId = exercisesImageId;
    }

    public String getExercisesName() {
        return exercisesName;
    }

    public void setExercisesName(String exercisesName) {
        this.exercisesName = exercisesName;
    }
}
