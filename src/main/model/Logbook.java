package model;

import org.json.JSONArray;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Logbook {
    private List<WorkoutSession> sessions;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private String storeFile;

    public Logbook(String storeFile) {
        this.storeFile = storeFile;
        this.sessions = new ArrayList<>();
        this.jsonWriter = new JsonWriter(storeFile);
        this.jsonReader = new JsonReader(storeFile);
    }

    public void addSession(WorkoutSession session) {
        this.sessions.add(session);
        EventLog.getInstance().logEvent(new Event("Added new session for " + session.getDate() + " to logbook."));
    }

    public WorkoutSession getSessionByDate(String date) {
        for (WorkoutSession session : sessions) {
            if (session.getDate().equals(date)) {
                return session;
            }
        }
        return null;
    }

    public void clearLogbook() {
        sessions.clear();
        EventLog.getInstance().logEvent(new Event("LogBook cleared."));
    }

    public List<WorkoutSession> filterSessionsByDate(String date) {
        List<WorkoutSession> filtered = sessions.stream()
            .filter(session -> session.getDate()
            .equals(date)).collect(Collectors.toList());

        EventLog.getInstance().logEvent(new Event("Filtered logbook by date: " + date));
        return filtered;
    }


    /*
     * Finds all workout sessions that contain at least one exercise of the specified muscle type
     * @param type the muscle type to filter by
     * @return A new list of matching WorkoutSession objects
     */
    public List<WorkoutSession> filterSessionsByMuscle(Muscles type) {
        List<WorkoutSession> filtered = sessions.stream()
            .filter(session -> session.getExercises().stream()
                .anyMatch(exercise -> exercise.getMuscleType() == type))
            .collect(Collectors.toList());

        EventLog.getInstance().logEvent(new Event("Filtered logbook by muscle: " + type.toString()));
        return filtered;
    }

    public List<Exercise> getAllExercisesByMuscle(Muscles type) {
        List<Exercise> filtered = sessions.stream()
            .flatMap(session -> session.getExercises().stream())
            .filter(exercise -> exercise.getMuscleType() == type)
            .collect(Collectors.toList());

        EventLog.getInstance().logEvent(new Event("Retrieved all exercises for muscle: " +  type.toString()));
        return filtered;
    }

    public void saveLogBook() throws FileNotFoundException {
        jsonWriter.open();
        JSONArray jsonArray = new JSONArray();
        for (WorkoutSession session : sessions) {
            jsonArray.put(session.toJson());
        }
        jsonWriter.write(jsonArray);
        jsonWriter.close();

        EventLog.getInstance().logEvent(new Event("Logbook saved to file: " + storeFile));
    }

    public void loadLogBook() throws IOException {
        this.sessions = jsonReader.read();
        EventLog.getInstance().logEvent(new Event("Logbook loaded from file: " + storeFile));
    }

    public List<WorkoutSession> getAllSessions() {
        return new ArrayList<>(sessions);
    }
}
