package persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Log;
import model.Muscles;


// Referenced from JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonReaderTest extends JsonTest {

    @Test
    public void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/donghwan.json");
        List<Log> logs = new ArrayList<Log>();

        try {
            logs = reader.read();
            fail("IOExcetion expected");
        } catch (IOException e) {
            assertTrue(logs.isEmpty());
        }
    }

    @Test
    public void testReaderEmptyfile() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyLog.json");
        try {
            List<Log> logs = reader.read();
            System.out.println(logs.isEmpty());
            assertTrue(logs.isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    public void testReaderGeneral() {
        JsonReader reader = new JsonReader("./data/testReaderGeneral.json");
        try {
            List<Log> logs = reader.read();
            assertNotNull(logs);
            assertEquals(3, logs.size());
            checkExercise(logs.get(0), "Bench press", Muscles.CHEST, 100, 3, 12);
            checkExercise(logs.get(1), "Shoulder press", Muscles.SHOULDERS, 50, 4, 11);
            checkExercise(logs.get(2), "Squat", Muscles.LEGS, 90, 3, 10);
        } catch (IOException e) {
            // fail("Couldn't read from file");
        }
    }
}
