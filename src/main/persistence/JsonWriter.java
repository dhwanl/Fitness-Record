package persistence;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import model.Log;

import java.io.*;

// Referenced from JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo

// Represents a writer that write all logs into JSON file
public class JsonWriter {
    private PrintWriter writer;
    private String directory;

    // EFFECTS: constructs writer to write a file to directory
    public JsonWriter(String directory) {
        this.directory = directory;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(directory));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of logs to file
    public void write(Log log) {
        // stub
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writies string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
