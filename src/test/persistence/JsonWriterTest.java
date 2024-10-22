package persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import model.Exercise;
import model.Log;
import model.Muscles;

// Referenced from JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonWriterTest extends JsonTest{
    @Test
    void testWriterInvalidFile() {
        try {
            Log log = new Log();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyLog() {
        try {
            Log log = new Log();
            JsonWriter writer = new JsonWriter("./data/testReaderEmptyLog.json");
            writer.open();
            writer.write(log);
            writer.close();

            JsonReader reader = new JsonReader("./data/testReaderEmptyLog.json");
            log = reader.read();
            assertNull(log.getDate());
            assertEquals(0, log.getAllExercisesLog().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGenral() {
        try{
            Exercise exercise1 = new Exercise( "Bench press", Muscles.CHEST, 100, 3, 12);
            Exercise exercise2 = new Exercise( "Shoulder press", Muscles.SHOULDERS, 50, 4, 11);
            Exercise exercise3 = new Exercise( "Squat", Muscles.LEGS, 90, 3, 10);
            Log log = new Log();
            Log log1 = new Log(exercise1, "2024/10/22");
            Log log2 = new Log(exercise2, "2024/10/20");
            Log log3 = new Log(exercise3, "2024/10/23");
            log1.addLogToExercisesList();
            log2.addLogToExercisesList();
            log3.addLogToExercisesList();

            JsonWriter writer = new JsonWriter("./data/testWriterGeneral.json");
            writer.open();
            writer.write(log);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneral.json");
            log = reader.read();
            List<Log> logs = log.getAllExercisesLog();
            assertEquals(3, logs.size());
            checkExercise(logs.get(0), "Bench press", Muscles.CHEST, 100, 3, 12);
            checkExercise(logs.get(1), "Shoulder press", Muscles.SHOULDERS, 50, 4, 11);
            checkExercise(logs.get(2), "Squat", Muscles.LEGS, 90, 3, 10);
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
