package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;
import java.util.List;

public class WorkoutSession implements Writable {
    private String date;
    private List<Exercise> exercises;

    public WorkoutSession(String date) {
        this.date = date;
        this.exercises = new ArrayList<>();
    
        EventLog.getInstance().logEvent(new Event("Started new workout session for date: " + date));
    }

    public void addExercise(Exercise exercise) {
        this.exercises.add(exercise);

        EventLog.getInstance().logEvent(new Event("Added " + exercise.getExerciseName() + " to session on " + date));
    }

    public boolean removeExercise(String exerciseName) {
        Exercise toRemove = null;
        for (Exercise e : exercises) {
            if (e.getExerciseName().equalsIgnoreCase(exerciseName)) {
                toRemove = e;
                break;
            }
        }

        if (toRemove != null) {
            exercises.remove(toRemove);
            EventLog.getInstance().logEvent(new Event("Removed " + exerciseName + " from session on " + date));
            
            return true;
        }

        return false;
    }

    public String getDate() {
        return date;
    }

    public List<Exercise> getExercises() {
        return new ArrayList<>(exercises);
    }

    public void setDate(String date) {
        this.date = date;
        EventLog.getInstance().logEvent(new Event("Updated session date to: " + date));
    }
    
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("date", date);

        JSONArray jsonArray = new JSONArray();
        for (Exercise e : exercises) {
            jsonArray.put(e.toJson());
        }
        json.put("exercises", jsonArray);

        return json;
    }
}