package model;

import java.util.ArrayList;
import java.util.List;

// Specify the exercise type, weight lifted, and number of sets and reps, as well as users can add and remove exercises
public class Exercise {
    private static List<Exercise> exerciseList = new ArrayList<Exercise>(); // store all exercises users added
    
    private String exerciseType;    // the type of exercise
    private int weightLifted;       // track the weight lifted in killogram
    private int numSets;            // track the number of sets of the exercise
    private int numReps;            // track the number of repetition of the exercise

    /*
     * REQUIRES: exerciseType has a non-zero length
     * EFFECTS: name of the type of exercise is set to exerciseType, and check if this exerciseType is already in exerciseList; 
     *          weightLifted, numSets, and numReps are initially set to zero
     */
    public Exercise(String exerciseType) {
        // stub
    }

    /*
     * REQUIRES: exerciseType has a non-zero length; weightLifted, numSets, and numReps have non-zero integer
     * EFFECTS: name of the type of exercise is set to exerciseType and check if this exerciseType is already in exerciseList; 
     *          weightLifted, numSets, and numReps are set to the given value
     */
    public Exercise(String exerciseType, int weightLifted, int numSets, int numReps) {
        // stub
    }

    public static List<Exercise> getExerciseList() {
        // stub
        return null;
    }

    public String getExerciseType() {
        // stub
        return "";
    }

    public int getWeightLifted() {
        // stub
        return 0;
    }

    public int getNumReps() {
        //stub
        return 0;
    }

    public void setWeightLifted() {
        // stub
    }

    public void setNumReps() {
        // stub
    }

    /*
     * EFFECTS: return true if there is the same type of exercise, otherwise false
     */
    public boolean isExerciseInList() {
        //stub
        return false;
    }

    /*
     * REQUIRES: exerciseType has a non-zero length
     * MODIFIES: this
     * EFFECTS: add the name of the type of exercise if it is not in exerciseList. Otherwise, print a user has added it already
     */
    public void add(String exerciseType) {
        // stub
    }

    /*
     * REQUIRES: exerciseType has a non-zero length
     * MODIFIES: this
     * EFFECTS: remove the name of the type of exercise if it is in exerciseList. Otherwise, print "there is no this type of exercise you would like to remove"
     */
    public void remove(String exerciseType) {
        // stub
    }


    


}
