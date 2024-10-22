package model;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import persistence.Writable;

/*
 * Stores information about exercises and the date performed by users, as well as the exercise details.
 * 
 * In addition, all logs are stored in a list, allowing Log class to provide functional management for exercise logs.
 * i.e., filtering logs by muscle types or dates, 
 *       finding a specific log by exercise name and date.
 *       updating exercise details
 */
public class Log implements Writable{
    private static List<Log> exercises = new ArrayList<Log>();  // store all exercises users did

    private Exercise exercise;     // data type Exercise variable
    private String date;           // date in the format "year/month/date"

    
    /*
     * EFFECTS: create an empty Log instance with no exercise or date
     */
    public Log() {
        this.exercise = null;
        this.date = null;
    }
    
    /*
     * REQUIRES: date must follow the format (yyyy/mm/dd)
     * EFFECTS: Initialize the Log instance with the provided Exercise object
     *          and set the date
     */
    public Log(Exercise exercise, String date) {
        this.exercise = exercise;
        this.date = date;
    }

    /*
     * REQUIRES: date must be in the format (year/month/date); 
     *           the length of exerciseName is not zero
     * EFFECTS: return index if this finds the matched one in the list using the name of exercise and date
     */
    public int findMatchedLog(String exerciseName, String date) {

        for (int i = 0; i < exercises.size(); i++) {
            String tempName = exercises.get(i).getExercise().getExerciseName();
            String tempDate = exercises.get(i).getDate();

            if (tempName.equalsIgnoreCase(exerciseName) && tempDate.equals(date)) {
                return i;
            }
        }
        
        return -1;
    }

    /*
     * REQUIRES: newWeight >= 0
     * MODIFIES: this
     * EFFECTS: update weight lifted
     */
    public void updateWeight(double newWeight) {
        this.getExercise().setWeightLifted(newWeight);
    }

    /*
     * REQUIRES: the length of newExerciseName must be greater than zero
     * MODIFIES: this
     * EFFECTS: update exercise name
     */
    public void updateName(String newExerciseName) {
        this.getExercise().setExerciseName(newExerciseName);
    }

    /*
     * MODIFIES: this
     * EFFECTS: update muscle type
     */
    public void updateMuscleType(Muscles newType) {
        this.getExercise().setMuscleType(newType);
    }

    /*
     * REQUIRES: newSets >= 0
     * MODIFIES: this
     * EFFECTS: update the number of set
     */
    public void updateSets(int newSets) {
        this.getExercise().setNumSets(newSets);
    }

    /*
     * REQUIRES: newReps >= 0
     * MODIFIES: this
     * EFFECTS: update the number of repetition
     */
    public void updateReps(int newReps) {
        this.getExercise().setNumReps(newReps);
    }

    /*
     * REQUIRES: date in format (yyyy/mm/dd)
     * MODIFIES: this
     * EFFECTS: update the date
     */
    public void updateDate(String date) {
        this.date = date;
    }

    /*
     * REQUIRES: response == 1 || response == 2;
     *           date must be in the format (yyyy/mm/dd)
     * EFFECTS: when response = 1, create a new list filtered by a specific muscle type
     *          otherwise, create a new list filtered by a specific date.
     *          return the new filtered list
     */
    public List<Log> filteredLog(int response, Muscles type, String date) {
        List<Log> filteredLog = new ArrayList<Log>();

        if (response == 1) {
            filteredLog = new Log().filteredExercisesByType(type);
        }

        if (response == 2) {
            filteredLog = new Log().filteredExercisesByDate(date);
        }

        return filteredLog;
    }

    /*
     * EFFECTS: filter exercises by a specific muscle type
     *          i.e., CHEST, BACK, SHOULDERS etc.
     *          then, return a filtered list
     */
    public List<Log> filteredExercisesByType(Muscles type) {
        List<Log> filteredByTypeLogs = new ArrayList<Log>();

        for (int i = 0; i < exercises.size(); i++) {
            if (exercises.get(i).getExercise().getMuscleType() == type) {
                filteredByTypeLogs.add(exercises.get(i));
            }
        }

        return filteredByTypeLogs;
    }

    /* 
     * REQUIRES: the length of date > 0 and in the format (year/month/date)
     * EFFECTS: filter exercises by date.
     *          then, return a filtered list
     *          
     */
    public List<Log> filteredExercisesByDate(String date) {
        List<Log> filteredByDateLogs = new ArrayList<Log>();

        for (int i = 0; i < exercises.size(); i++) {
            if (exercises.get(i).getDate().equals(date)) {
                filteredByDateLogs.add(exercises.get(i));
            }
        }

        return filteredByDateLogs;
    }

    /*
     * MODIFIES: this
     * EFFECTS: add the registered exercise into the exercises list
     */
    public void addLogToExercisesList() {
        exercises.add(this);
    }

    /*
     * REQUIRES: exerciseName has a non-zero length;
     *           date must be in the format (yyyy/mm/dd)
     * MODIFIES: this
     * EFFECTS: remove the log that matches the given exercise name and date from the list.
     *          then, return the removed log if a match is found, otherwise return null       
     */
    public Log removeLogExercises(String exerciseName, String date) {
        int matchedIndex = findMatchedLog(exerciseName, date);

        if (matchedIndex != -1) {
            Log log = exercises.get(matchedIndex);
            exercises.remove(matchedIndex);
            return log;
        }

        return null;
    }

    // getters
    public List<Log> getAllExercisesLog() {
        return exercises;
    }

    public Exercise getExercise() {
        return exercise;
    }
    
    public String getDate() {
        return date;
    }

    @Override
    public JSONObject toJson() {
        // stub
        return null;
    }
}
