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
import model.Log;
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

    // EFFECTS: reads log from file and return it;
    // throws IOException if an error occurs reading data from file
    public List<Log> read() throws IOException {
        String jsonData = readFile(source);
        JSONArray jsonObj = new JSONArray(jsonData);
        
        return parseLog(jsonObj);
    }

    // EFFECTS: reads log from file and returns it;
    // throws IOException if an error occurs reading data from file
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses log from JSON object and return it
    private List<Log> parseLog(JSONArray jsonArray) {
        List<Log> historyLogs = new ArrayList<Log>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject objLog = jsonArray.getJSONObject(i);
            historyLogs.add(addExerciseToLog(objLog)); 
        }

        return historyLogs;
    }

    // MODIFIES: log
    // EFFECTS: parses logs from JSON object and adds them to log
    private Log addExerciseToLog(JSONObject objLog) {
        String date = objLog.getString("date");

        JSONObject exerciseJson = objLog.getJSONObject("exercise");

        String exerciseName = exerciseJson.getString("exercise name");
        Muscles muscleType = Muscles.valueOf(exerciseJson.getString("muscle Type"));
        int weightLifted = exerciseJson.getInt("weight");
        int numSets = exerciseJson.getInt("number of sets");
        int numReps = exerciseJson.getInt("number of Repetitions");

        Exercise newExercise = new Exercise(exerciseName, muscleType, weightLifted, numSets, numReps);

        Log newLog = new Log(newExercise, date);

        return newLog;
    }
}
