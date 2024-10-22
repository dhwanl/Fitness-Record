package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Exercise;
import model.Log;

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
    public Log read() throws IOException {
        return null; // stub
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
    private Log parseLog(JSONArray jsonArray) {
        return null; // stub
    }

    // MODIFIES: log
    // EFFECTS: parses logs from JSON object and adds them to log
    private void addExercises(Log log, JSONObject jsonObject) {
        // stub
    }

    // MODIFIES: log
    // EFFECTS: parses exercise from JSON object and adds it to log
    private void addExercise(Exercise ex, JSONObject jsonObject) {
        // stub
    }

}
