package model;


import java.util.ArrayList;
import java.util.List;

public class Log {
    public static List<Log> exercises = new ArrayList<Log>();           // store all exercises users did

    private Exercise exercise;
    private String date;                                                 // date in the format "year/month/date"

    /*
     * EFFECTS: Data type Exercise is set to exercse in log class
     *          and String date (year/month/date) is set to date in log class
     */
    public Log(Exercise exercise, String date) {
        this.exercise = exercise;
        this.date = date;
    }

    /*
     * REQUIRES: date must be in the format (year/month/date); The length of exerciseName is not zero
     * EFFECTS: return index if this finds the matched one in the list using the name of exercise and date
     */
    public int findMatchedLog(String exerciseName, String date) {
    for(int i = 0; i < exercises.size(); i++) {
        String tempName = exercises.get(i).getExercise().getExerciseName();
        String tempDate = exercises.get(i).getDate();

            if(tempName.equals(exerciseName) && tempDate.equals(date)) {
                return i;
            }
        }
        
        return -1;
    }

    /*
     * EFFECTS: filter exercises by muscle type
     *          i.e., CHEST, BACK, SHOULDERS etc.
     */
    public List<Log> filteredExercises(Muscles type) {
        List<Log> filteredLogs = new ArrayList<Log>();

        for(int i = 0; i < exercises.size(); i++) {
            if(exercises.get(i).getExercise().getMuscleType() == type) {
                filteredLogs.add(exercises.get(i));
            }
        }

        return filteredLogs;
    }

    /*
     * MODIFIES: this
     * EFFECTS: add exercises users did into log list
     */
    public void addLogToExercisesList() {
        exercises.add(this);
    }

    /*
     * REQUIRES: exerciseName has a non-zero length
     * MODIFIES: this
     * EFFECTS: remove the log by name of the type of exercise and date       
     */
    public void removeLogExercises(String exerciseName, String date) {
        int matchedIndex = findMatchedLog(exerciseName, date);
        exercises.remove(matchedIndex);
    }

    public List<Log> getAllExercisesLog() {
        return exercises;
    }

    public Exercise getExercise() {
        return exercise;
    }
    
    public String getDate() {
        return date;
    }
}
