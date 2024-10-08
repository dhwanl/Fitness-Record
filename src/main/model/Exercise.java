package model;

import java.util.ArrayList;
import java.util.List;

// Specify the exercise type, weight lifted, and number of sets and reps, as well as users can add and remove exercises
public class Exercise {
    private String exerciseName;    // the type of exercise
    private double weightLifted;    // track the weight lifted in killogram
    private int numSets;            // track the number of sets of the exercise
    private int numReps;            // track the number of repetition of the exercise
    private Muscles muscleType;     // the type of muscle users use with exercise

    /*
     * REQUIRES: exerciseName has a non-zero length
     * EFFECTS: name of the type of exercise is set to exerciseType, and check if this exerciseType is already in exerciseList; 
     *          weightLifted, numSets, and numReps are initially set to zero; group exercises based on muscleType;
     */
    public Exercise(String exerciseName, Muscles muscleType) {
        this.exerciseName = exerciseName.substring(0, 1).toUpperCase() + exerciseName.substring(1).toLowerCase();
        this.muscleType = muscleType;
        this.weightLifted = 0.0;
        this.numSets = 0;
        this.numReps = 0;
    }

    /*
     * REQUIRES: exerciseType has a non-zero length; weightLifted >= 0.0; numSets and numReps >= 0
     * EFFECTS: name of the type of exercise is set to exerciseType and check if this exerciseType is already in exerciseList; 
     *          weightLifted, numSets, and numReps are set to the given value
     */
    public Exercise(String exerciseName, Muscles muscleType, double weightLifted, int numSets, int numReps) {
        this.exerciseName = exerciseName.substring(0, 1).toUpperCase() + exerciseName.substring(1).toLowerCase();
        this.muscleType = muscleType;
        this.weightLifted = weightLifted;
        this.numSets = numSets;
        this.numReps = numReps;
    }

    /*
     * EFFECTS: return false if the length of the name of exercise is not zero.
     *          Otherwise, return true with the error message
     */
    public boolean isLengthZero(String exerciseName) {
        if(exerciseName.length() > 0){
            return false;
        }

        return true;
    }

    // getters
    public String getExerciseName() {
        return this.exerciseName;
    }

    public double getWeightLifted() {
        return this.weightLifted;
    }

    public int getNumSets() {
        return this.numSets;
    }

    public int getNumReps() {
        return this.numReps;
    }

    public Muscles getMuscleType() {
        return this.muscleType;
    }
    
    // setters
    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public void setWeightLifted(int weight) {
        this.weightLifted = weight;
    }

    public void setNumReps(int reps) {
        this.numReps = reps;
    }

    public void setMuscleType(Muscles muscleType) {
        this.muscleType = muscleType;
    }

}
