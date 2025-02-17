package persistence;

import static org.junit.Assert.assertEquals;

import model.Exercise;
import model.Log;
import model.Muscles;

// Referenced from JsonSerialization Demo
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
public class JsonTest {
    protected void checkExercise(Log log, String exerciseName, Muscles muscleType, 
                                            int weightLifted, int numSets, int numReps) {
        Exercise temp = log.getExercise();
        
        assertEquals(temp.getExerciseName(), exerciseName);
        assertEquals(temp.getMuscleType(), muscleType);
        assertEquals(temp.getWeightLifted(), weightLifted);
        assertEquals(temp.getNumSets(), numSets);
        assertEquals(temp.getNumReps(), numReps);
    }
}
