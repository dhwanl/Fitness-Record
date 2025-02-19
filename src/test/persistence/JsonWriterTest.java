package persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import model.Exercise;
import model.Log;
import model.Muscles;

// Referenced from JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest extends JsonTest {
    @Test
    public void testWriterInvalidFile() {
        
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            File file = new File("./data/my\\0illegal:fileName.json");
            assertFalse(file.exists());
        }
    }

    @Test
    public void testWriterEmptyLog() {
        try {
            JsonWriter writer = new JsonWriter("./data/testReaderEmptyLog.json");
            
            JSONArray jsonArray = new JSONArray();

            writer.open();
            writer.write(jsonArray);
            writer.close();

            JsonReader reader = new JsonReader("./data/testReaderEmptyLog.json");
            List<Log> logs = reader.read();
            assertTrue(logs.isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
    
    @Test
    public void testWriterWithNullExerciseAndDate() {
        Log log = new Log();
        JSONObject json = log.toJson();

        assertEquals(JSONObject.NULL, json.get("exercise"));
        assertEquals(JSONObject.NULL, json.get("date"));
    }

    @Test
    public void testWriterGenral() {
        try {
            Exercise exercise1 = new Exercise("Bench press", Muscles.CHEST, 100, 3, 12);
            Exercise exercise2 = new Exercise("Shoulder press", Muscles.SHOULDERS, 50, 4, 11);
            Log log1 = new Log(exercise1, "2024/10/22");
            Log log2 = new Log(exercise2, "2024/10/20");
            log1.addLogToExercisesList();
            log2.addLogToExercisesList();

            JsonWriter writer = new JsonWriter("./data/testWriterGeneral.json");
            writer.open();
            List<Log> logs = new Log().getAllExercisesLog();
            JSONArray jsonArray = new JSONArray();

            for (Log l: logs) {
                jsonArray.put(l.toJson());
            }

            writer.write(jsonArray);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneral.json");
            List<Log> logs2 = reader.read();
            checkExercise(logs2.get(0), "Bench press", Muscles.CHEST, 100, 3, 12);
            checkExercise(logs2.get(1), "Shoulder press", Muscles.SHOULDERS, 50, 4, 11);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
