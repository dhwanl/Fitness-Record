package model;


import java.util.ArrayList;
import java.util.List;

public class Log {
    private static List<Log> exercises = new ArrayList<Log>();           // store all exercises users did
    private String date;                                                 // date in the format "year/month/date"

    /*
     * EFFECTS: record exercise users did into the ArrayList
     */
    public Log(Exercise exercise, String date) {
        // stub
    }

    /*
     * REQUIRES: date must be in the format (year/month/date); The length of exerciseName is not zero
     * EFFECTS: return index if this finds the matched one in the list using the name of exercise and date
     */
    public int findMatchedLog(String exerciseName, String date) {
        return 0;
    }

    /*
     * REQUIRES: weight >= 0.0
     * MODIFIES: this
     * EFFECTS: update the weight for a specific exercise when users lifted more than the previous weight
     */
    public void updateWeightLifted(double weight) {
        // stub
    }

    /*
     * REQUIRES: reps >= 0
     * MODIFIES: this
     * EFFECTS: update the reps for a specific exercise when users did more reps than before
     */
    public void updateNumReps(int reps) {
        // stub
    }

    /*
     * REQUIRES: sets >= 0
     * MODIFIES: this
     * EFFECTS: update the sets for a specific exercise when users did more sets than before
     */
    public void updateNumSets(int sets) {
        // stub
    }

    /*
     * EFFECTS: filter exercises by muscle type
     *          i.e., CHEST, BACK, SHOULDERS etc.
     */
    public List<Log> filteredExercises(Muscles type) {
        // stub
        return null;
    }

    /*
     * MODIFIES: this
     * EFFECTS: add exercises users did into log list
     */
    public List<Log> addLogToExercisesList() {
        // stub
        return exercises;
    }

    /*
     * REQUIRES: exerciseName has a non-zero length
     * MODIFIES: this
     * EFFECTS: remove the log by name of the type of exercise and date       
     */
    public static List<Log> removeLogExercises(String exerciseName, String date) {
        // stub
        return exercises;
    }

    public static List<Log> getAllExercisesLog() {
        return exercises;
    }

    public Exercise getExercise() {
        return null;
    }
    
    public String getDate() {
        return date;
    }
}
