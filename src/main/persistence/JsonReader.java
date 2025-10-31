package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Exercise;
import model.WorkoutSession;
import model.Muscles;

// Referenced from JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
// Represents a reader that reads logs from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads logbook from file and return it as a list of sessions;
    // throws IOException if an error occurs reading data from file
    public List<WorkoutSession> read() throws IOException {
        String jsonData = readFile(source);

        JSONArray jsonArray = new JSONArray(jsonData);
        
        return parseLogbook(jsonArray);
    }

    // EFFECTS: reads source file as string and returns it;
    // throws IOException if an error occurs reading data from file
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses logbook from JSON array and return it as a list of sessions
    private List<WorkoutSession> parseLogbook(JSONArray jsonArray) {
        List<WorkoutSession> sessions = new ArrayList<>();

        for (Object json : jsonArray) {
            JSONObject sessionJson = (JSONObject) json;
            WorkoutSession session = parseWorkoutSession(sessionJson);
            sessions.add(session);
        }

        return sessions;
    }

    // EFFECTS: parses a single WorkoutSession from JSONObject
    private WorkoutSession parseWorkoutSession(JSONObject sessionJson) {
        String date = sessionJson.getString("date");
        WorkoutSession session = new WorkoutSession(date);

        JSONArray exerciseArray = sessionJson.getJSONArray("exercises");
        addExerciseToSession(session, exerciseArray);

        return session;
    }
    
    // MODIFIES: session
    // EFFECTS: parses exercieses from JSONArray and adds them to the workout session
    private void addExerciseToSession(WorkoutSession session, JSONArray exercisesArray) {
        for (Object exJson : exercisesArray) {
            JSONObject exerciseJson = (JSONObject) exJson;
            Exercise exercise = parseExercise(exerciseJson);
            session.addExercise(exercise);
        }
    }

    // EFFECTS: parses a single Exercise from a JSONObject
    private Exercise parseExercise(JSONObject exerciseJson) {
        String exerciseName = exerciseJson.getString("exercise name");
        Muscles muscleType = Muscles.valueOf(exerciseJson.getString("muscle Type"));
        int weightLifted = exerciseJson.getInt("weight");
        int numSets = exerciseJson.getInt("number of Sets");
        int numReps = exerciseJson.getInt("number of Repetitions");

        return new Exercise(exerciseName, muscleType, weightLifted, numSets, numReps);
    }
}
