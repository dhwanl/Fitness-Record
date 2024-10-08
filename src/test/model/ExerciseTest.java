package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ExerciseTest {
    private Exercise exercise;
    
    @Test
    void testConstrutor() {
        exercise = new Exercise("Leg extension");
        assertEquals("Leg extension", exercise.getExerciseName());

        exercise = new Exercise("Incline bench press", 60, 4, 10);
        assertEquals("Incline bench press", exercise.getExerciseName());
        assertEquals(60, exercise.getWeightLifted());
        assertEquals(4, exercise.getNumSets());
        assertEquals(10, exercise.getNumReps());
    }

}