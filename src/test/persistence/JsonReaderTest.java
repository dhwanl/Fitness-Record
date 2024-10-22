package persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import model.Log;
import model.Muscles;

// Referenced from JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/donghwan.json");
        try {
            Log log = reader.read();
            fail("IOExcetion expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyfile() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLog.json");
        try {
            Log log = reader.read();
            assertTrue(log.getDate().isEmpty());
            assertEquals(0, log.getAllExercisesLog().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneral() {
        JsonReader reader = new JsonReader("./data/testReaderGeneral.json");
        try {
            Log log = reader.read();
            assertNotNull(log.getDate());
            List<Log> logs = log.getAllExercisesLog();
            assertEquals(3, logs.size());
            checkExercise(logs.get(0), "Bench press", Muscles.CHEST, 100, 3, 12);
            checkExercise(logs.get(1), "Shoulder press", Muscles.SHOULDERS, 50, 4, 11);
            checkExercise(logs.get(2), "Squat", Muscles.LEGS, 90, 3, 10);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
