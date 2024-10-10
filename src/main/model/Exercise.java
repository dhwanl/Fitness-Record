package model;

// Specify the exercise type, weight lifted, and number of sets and reps, as well as users can add and remove exercises
public class Exercise {
    private String exerciseName;    // the type of exercise
    private double weightLifted;    // track the weight lifted in killogram
    private int numSets;            // track the number of sets of the exercise
    private int numReps;            // track the number of repetition of the exercise
    private Muscles muscleType;     // the type of muscle users use with exercise

    /*
     * REQUIRES: exerciseName has a non-zero length
     * EFFECTS: name of the type of exercise is set to exerciseName. 
     *          WeightLifted, numSets, and numReps are initially set to zero; group exercises based on muscleType;
     */
    public Exercise(String exerciseName, Muscles muscleType) {
        this.exerciseName = capitalizationForFirstLetter(exerciseName);
        this.muscleType = muscleType;
        this.weightLifted = 0.0;
        this.numSets = 0;
        this.numReps = 0;
    }

    /*
     * REQUIRES: exerciseName has a non-zero length; weightLifted >= 0.0; numSets and numReps >= 0
     * EFFECTS: name of the type of exercise is set to exerciseName.
     *          weightLifted, numSets, and numReps are set by the given value
     */
    public Exercise(String exerciseName, Muscles muscleType, double weightLifted, int numSets, int numReps) {
        this.exerciseName = capitalizationForFirstLetter(exerciseName);
        this.muscleType = muscleType;
        this.weightLifted = weightLifted;
        this.numSets = numSets;
        this.numReps = numReps;
    }

    /*
     * REQUIRES: exerciseName is not empty or null
     * EFFECTS: convert case; 
     *          return a string that has the first letter in the upper case \
     *          and the rest of letters in the lower case
     */
    public String capitalizationForFirstLetter(String exerciseName) {
        return exerciseName.substring(0, 1).toUpperCase() + exerciseName.substring(1).toLowerCase();
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
        this.exerciseName = capitalizationForFirstLetter(exerciseName);
    }

    public void setWeightLifted(double weight) {
        this.weightLifted = weight;
    }

    public void setNumReps(int reps) {
        this.numReps = reps;
    }

    public void setNumSets(int sets) {
        this.numSets = sets;
    }

    public void setMuscleType(Muscles muscleType) {
        this.muscleType = muscleType;
    }

}
