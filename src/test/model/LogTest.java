package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

public class LogTest {
    private Log log;
    private String date;

    @BeforeEach
    void runBefore() {
        date = "2024/10/08";
        log = new Log(new Exercise("Bench press", Muscles.CHEST, 60.0, 3, 10), date);
    }

    @Test
    void testConstrutor() {
        assertEquals("Bench press", log.getExercise().getExerciseName());
        assertEquals("2024/10/08", log.getDate());
    }

    @Test
    void testAddLogExercises() {
        log.addLogToExercisesList();
        List<Log> logs = log.getAllExercisesLog();
        
        assertFalse(logs.isEmpty());
        assertEquals(1, logs.size());
        assertEquals("2024/10/08", logs.get(0).getDate());
        assertEquals("Bench press", logs.get(0).getExercise().getExerciseName());
        assertEquals("CHEST", logs.get(0).getExercise().getMuscleType().toString());
        assertEquals(60.0, logs.get(0).getExercise().getWeightLifted());
        assertEquals(3, logs.get(0).getExercise().getNumSets());
        assertEquals(10, logs.get(0).getExercise().getNumReps());
        
        log = new Log(new Exercise("Squat", Muscles.LEGS, 70.0, 4, 10), "2024/10/20");
        assertEquals(2, logs.size());
        assertEquals("2024/10/20", logs.get(1).getDate());
        assertEquals("Squat", logs.get(1).getExercise().getExerciseName());
        assertEquals("CHEST", logs.get(1).getExercise().getMuscleType().toString());
        assertEquals(70.0, logs.get(1).getExercise().getWeightLifted());
        assertEquals(4, logs.get(1).getExercise().getNumSets());
        assertEquals(10, logs.get(1).getExercise().getNumReps());
    }

    @Test
    void testRemoveLogExercise() {
        List<Log> logs = log.getAllExercisesLog();
        assertEquals(1, logs.size());
        log.removeLogExercises(logs.get(0).getExercise().getExerciseName(), date);
        assertEquals(0, logs.size());
        assertTrue(logs.isEmpty());
    }

    @Test
    void testFindMatchedLog() {
        assertEquals(0, log.findMatchedLog("Bench press", "2024/10/08"));
    }

    @Test
    void testUpdateMethod() {

        assertEquals("2024/10/08", log.getDate());
        assertEquals("Bench press", log.getExercise().getExerciseName());
        assertEquals("CHEST", log.getExercise().getMuscleType().toString());
        assertEquals(60.0, log.getExercise().getWeightLifted());
        assertEquals(3, log.getExercise().getNumSets());
        assertEquals(10, log.getExercise().getNumReps());

        log.updateWeightLifted(100.0);
        assertEquals(100.0, log.getExercise().getWeightLifted());

        log.updateNumReps(30);
        assertEquals(30, log.getExercise().getNumReps());

        log.updateNumSets(10);
        assertEquals(10, log.getExercise().getNumSets());
    }

    @Test
    void testFilteredExercises() {
        Log log1 = new Log(new Exercise("Incline Bench press", Muscles.CHEST, 60.0, 3, 10), date);
        Log log2 = new Log(new Exercise("Chest flies", Muscles.CHEST, 60.0, 3, 10), date);
        Log log3 = new Log(new Exercise("Dumbbell press", Muscles.CHEST, 60.0, 3, 10), date);
        Log log4 = new Log(new Exercise("Lat pulldown", Muscles.BACK, 60.0, 3, 10), date);
        Log log5 = new Log(new Exercise("Barbell row", Muscles.BACK, 60.0, 3, 10), date);

        log1.addLogToExercisesList();
        log2.addLogToExercisesList();
        log3.addLogToExercisesList();
        log4.addLogToExercisesList();
        log5.addLogToExercisesList();

        List<Log> logsChest = log.filteredExercises(Muscles.CHEST);
        assertEquals(4, logsChest.size());
        assertEquals(logsChest.get(0).getExercise().getExerciseName(), "Bench press");

        List<Log> logsBack = log.filteredExercises(Muscles.BACK);
        assertEquals(2, logsBack.size());
        assertEquals(logsBack.get(0).getExercise().getExerciseName(), "Lat pulldown");
    }   

}
