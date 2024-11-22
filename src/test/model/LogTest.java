package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LogTest {
    private Log log;
    private String date;
    private Exercise exercise;

    @BeforeEach
    public void runBefore() {
        exercise = new Exercise("Bench press", Muscles.CHEST, 60, 3, 10);
        date = "2024/10/08";
        log = new Log(exercise, date);
    }

    @Test
    public void testConstrutor() {
        Log emptyLog = new Log();
        assertEquals(null, emptyLog.getExercise());
        assertEquals(null, emptyLog.getDate());
        assertEquals("Bench press", log.getExercise().getExerciseName());
        assertEquals("2024/10/08", log.getDate());
    }

    @Test
    public void testAddLogExercises() {
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
        
        log = new Log(new Exercise("Squat", Muscles.LEGS, 70, 4, 10), "2024/10/20");
        log.addLogToExercisesList();
        assertEquals(2, logs.size());
        assertEquals("2024/10/20", logs.get(1).getDate());
        assertEquals("Squat", logs.get(1).getExercise().getExerciseName());
        assertEquals("LEGS", logs.get(1).getExercise().getMuscleType().toString());
        assertEquals(70.0, logs.get(1).getExercise().getWeightLifted());
        assertEquals(4, logs.get(1).getExercise().getNumSets());
        assertEquals(10, logs.get(1).getExercise().getNumReps());

        logs.clear();
        assertEquals(0, logs.size());

    }

    @Test
    public void testRemoveLogExercise() {
        log.addLogToExercisesList();

        List<Log> logs = log.getAllExercisesLog();
        assertEquals(1, logs.size());
        String str = "Bench press";
        Log removedOne = log.removeLogExercises(str, "2024/10/20");
        assertEquals(null, removedOne);

        Log savedOne = logs.get(0);
        removedOne = log.removeLogExercises(savedOne.getExercise().getExerciseName(), date);
        assertEquals(savedOne, removedOne);
        assertEquals(0, logs.size());


        assertTrue(logs.isEmpty());
    }

    @Test
    public void testFindMatchedLog() {
        log.addLogToExercisesList();
        log = new Log(new Exercise("Chest flies", Muscles.CHEST, 60, 3, 10), date);
        log.addLogToExercisesList();
        
        assertEquals(0, log.findMatchedLog("Bench press", "2024/10/08"));
        assertEquals(1, log.findMatchedLog("Chest flies", "2024/10/08"));
        assertEquals(-1, log.findMatchedLog("Shoulder press", date));
        assertEquals(-1, log.findMatchedLog("Bench press", "2024/10/20"));
        log.getAllExercisesLog().clear();
    }

    @Test
    public void testFilteredLog() {
        Log log4 = new Log(new Exercise("Lat pulldown", Muscles.BACK, 60, 3, 10), date);
        Log log5 = new Log(new Exercise("Barbell row", Muscles.BACK, 60, 3, 10), date);
        log4.addLogToExercisesList();
        log5.addLogToExercisesList();

        log.filteredLog(2, Muscles.BACK,  "2024/10/08");
        log.filteredLog(1, Muscles.BACK,  "2024/10/08");

        assertFalse(log.filteredLog(2, Muscles.BACK,  "2024/10/08").isEmpty());
        assertFalse(log.filteredLog(1, Muscles.BACK,  "2024/10/08").isEmpty());

        log.getAllExercisesLog().clear();
    }

    @Test
    public void testFilteredExercisesByType() {

        Log log1 = new Log(new Exercise("Incline Bench press", Muscles.CHEST, 60, 3, 10), date);
        Log log2 = new Log(new Exercise("Chest flies", Muscles.CHEST, 60, 3, 10), date);
        Log log3 = new Log(new Exercise("Dumbbell press", Muscles.CHEST, 60, 3, 10), date);
        Log log4 = new Log(new Exercise("Lat pulldown", Muscles.BACK, 60, 3, 10), date);
        Log log5 = new Log(new Exercise("Barbell row", Muscles.BACK, 60, 3, 10), date);

        log.addLogToExercisesList();
        log1.addLogToExercisesList();
        log2.addLogToExercisesList();
        log3.addLogToExercisesList();
        log4.addLogToExercisesList();
        log5.addLogToExercisesList();

        List<Log> logsChest = log.filteredExercisesByType(Muscles.CHEST);
        assertEquals(4, logsChest.size());
        assertEquals(logsChest.get(0).getExercise().getExerciseName(), "Bench press");

        List<Log> logsBack = log.filteredExercisesByType(Muscles.BACK);
        assertEquals(2, logsBack.size());
        assertEquals(logsBack.get(0).getExercise().getExerciseName(), "Lat pulldown");

        log.getAllExercisesLog().clear();
    }

    @Test
    public void testFilteredExercisesByDate() {

        Log log1 = new Log(new Exercise("Incline Bench press", Muscles.CHEST, 60, 3, 10), date);
        Log log2 = new Log(new Exercise("Chest flies", Muscles.CHEST, 60, 3, 10), date);

        date = "2024/10/20";
        Log log3 = new Log(new Exercise("Dumbbell press", Muscles.CHEST, 60, 3, 10), date);
        Log log4 = new Log(new Exercise("Lat pulldown", Muscles.BACK, 60, 3, 10), date);
        Log log5 = new Log(new Exercise("Barbell row", Muscles.BACK, 60, 3, 10), date);

        log.addLogToExercisesList();
        log1.addLogToExercisesList();
        log2.addLogToExercisesList();
        log3.addLogToExercisesList();
        log4.addLogToExercisesList();
        log5.addLogToExercisesList();

        List<Log> today = log.filteredExercisesByDate(date);
        assertEquals(3, today.size());
        assertEquals(today.get(0).getDate(), "2024/10/20");

        List<Log> yesterday = log.filteredExercisesByDate("2024/10/08");
        assertEquals(3, yesterday.size());
        assertEquals(yesterday.get(0).getDate(), "2024/10/08");

        log.getAllExercisesLog().clear();
    }

    @Test
    void testUpdate() {
        log.addLogToExercisesList();
        List<Log> logs = log.getAllExercisesLog();
        assertEquals(60.0, logs.get(0).getExercise().getWeightLifted());

        logs.get(0).updateWeight(0);
        assertEquals(0.0, logs.get(0).getExercise().getWeightLifted());

        logs.get(0).updateName("Bench");
        assertEquals("Bench", logs.get(0).getExercise().getExerciseName());

        Muscles type = Muscles.BACK;
        logs.get(0).updateMuscleType(type);
        assertEquals(type, logs.get(0).getExercise().getMuscleType());

        logs.get(0).updateReps(20);
        assertEquals(20, logs.get(0).getExercise().getNumReps());

        logs.get(0).updateSets(10);
        assertEquals(10, logs.get(0).getExercise().getNumSets());

        logs.get(0).updateWeight(200);
        assertEquals(200.0, logs.get(0).getExercise().getWeightLifted());

        logs.get(0).updateDate("2024/11/21");
        assertEquals("2024/11/21", logs.get(0).getDate());
        logs.clear();
    }

}
