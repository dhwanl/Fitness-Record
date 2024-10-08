package model;

// Specify the exercise type, weight lifted, and number of sets and reps, as well as users can add and remove exercises
public class Exercise {
    private String exerciseName;    // the type of exercise
    private double weightLifted;    // track the weight lifted in killogram
    private int numSets;            // track the number of sets of the exercise
    private int numReps;            // track the number of repetition of the exercise
    private String muscleType;      // the type of muscle users use with exercise
    /*
     * REQUIRES: exerciseType has a non-zero length
     * EFFECTS: name of the type of exercise is set to exerciseType, and check if this exerciseType is already in exerciseList; 
     *          weightLifted, numSets, and numReps are initially set to zero
     */
    public Exercise(String exerciseType) {
        // stub
    }

    /*
     * REQUIRES: exerciseType has a non-zero length; weightLifted >= 0.0; numSets and numReps >= 0
     * EFFECTS: name of the type of exercise is set to exerciseType and check if this exerciseType is already in exerciseList; 
     *          weightLifted, numSets, and numReps are set to the given value
     */
    public Exercise(String exerciseType, double weightLifted, int numSets, int numReps) {
        // stub
    }

    public String getExerciseName() {
        // stub
        return "";
    }

    public int getWeightLifted() {
        // stub
        return 0;
    }

    public int getNumSets() {
        // stub
        return 0;
    }

    public int getNumReps() {
        // stub
        return 0;
    }

    public String getMuscleType() {
        // stub
        return null;
    }
    
    public void setExerciseName(String exerciseName) {
        // stub
    }

    public void setWeightLifted(int weight) {
        // stub
    }

    public void setNumReps(int reps) {
        // stub
    }

    public void setMuscleType(int muscleType) {
        // stub
    }

}
