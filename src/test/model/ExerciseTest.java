package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExerciseTest {
    private Exercise exercise;
    
    @BeforeEach
    void beforeTest() {
        exercise = new Exercise("Leg extension", Muscles.LEGS);
    }

    @Test
    void testConstrutor() {
        assertEquals("Leg extension", exercise.getExerciseName());
        assertEquals("LEGS", exercise.getMuscleType().toString());

        exercise = new Exercise("Incline bench press", Muscles.CHEST, 60, 4, 10);
        assertEquals("Incline bench press", exercise.getExerciseName());
        assertEquals(Muscles.CHEST, exercise.getMuscleType());
        assertEquals(60, exercise.getWeightLifted());
        assertEquals(4, exercise.getNumSets());
        assertEquals(10, exercise.getNumReps());
    }

    @Test
    void testSetmethods() {
        exercise.setExerciseName("shoulder press");
        assertEquals("Shoulder press", exercise.getExerciseName());
        exercise.setMuscleType(Muscles.SHOULDERS);
        assertEquals("SHOULDERS", Muscles.SHOULDERS.toString());
        exercise.setNumReps(10);
        assertEquals(10, exercise.getNumReps());
        exercise.setNumSets(3);
        assertEquals(3, exercise.getNumSets());
        exercise.setWeightLifted(40);
        assertEquals(40, exercise.getWeightLifted());
    }

    @Test
    void testIsLengthZero() {
        assertFalse(exercise.isLengthZero(exercise.getExerciseName()));
        exercise.setExerciseName("");
        assertTrue(exercise.isLengthZero(exercise.getExerciseName()));
    }

}