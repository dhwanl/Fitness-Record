package ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Exercise;
import model.Log;
import model.Muscles;

/*
 * Fitness tracker application
 *  
 * This helps users easily view all the exercises they have recorded
 * and track their workout progress over time.
 */
public class FitnessRecordApp {
    private List<Log> logs;

    private Scanner input;
    private String exerciseName;
    private Muscles type;
    private String date;
    private double weightLifted;
    private int numSets;
    private int numReps;
    boolean isStop;

    /*
     * EFFECTS: initialize Scanner and ArrayList for logs;
     * call a greeting message to welcome users
     * and start this application by calling runRecord method
     */
    public FitnessRecordApp() {
        input = new Scanner(System.in);
        logs = new ArrayList<Log>();
        greeting();
        runRecord();
    }

    /*
     * EFFECTS: display main menu on the console, allowing users to choose from menu options.
     *          get the users' response to the menu option
     *          , and then call menuOption method based on their selection.
     */
    private void runRecord() {
        isStop = false;
        int response;

        do {
            menuDisplay();              // display main menu
            response = input.nextInt(); // get users's response
            menuOption(response);       // call this method and move onto the next menu based on their selection
        } while (!isStop);
    }

    /*
     * EFFECTS: Based on users' response, this code executes different actions:
     *              - case 1: add an exercise
     *              - case 2: remove a specific exercise
     *              - case 3: update a log (in case when users enter the wrong info)
     *              - case 4: filter logs by muscle type or date as users want
     *              - case 5: display all logs
     *              - case 6: terminate this application
     *              - otherwise, display an error message
     */
    private void menuOption(int response) {
        switch (response) {
            case 1:
                addExercise();
                break;
            case 2:
                removeExercise();
                break;
            case 3:
                updateLog();
                break;
            case 4:
                filterWorkoutLog();
                break;
            case 5:
                viewAllLogs(logs);
                break;
            case 6:
                isStop = true;
                break;

            default:
                System.out.println("\nUnfortunately, the number you entered is not correct!");
                break;
        }
    }

    /*
     * EFFECTS: display all logs in the nicely formatting way
     *              - date
     *              - exercise name
     *              - muscle type
     *              - weight lifted
     *              - the number of sets
     *              - the number of repetitions
     * 
     *          otherwise, display a message that logs is empty
     */
    private void viewAllLogs(List<Log> logs) {
        for (Log log : logs) {
            Exercise ex = log.getExercise();
            System.out.printf("\n-Date: %2s%n", log.getDate());
            System.out.printf("Name of Exercise: %2s%n", ex.getExerciseName());
            System.out.printf("Type of muscles: %2s%n", ex.getMuscleType().toString());
            System.out.printf("Weight you lifted: %.2f%n", ex.getWeightLifted());
            System.out.printf("Number of sets: %2d%n", ex.getNumSets());
            System.out.printf("Number of reps: %2d%n", ex.getNumReps());
        }

        if (logs.size() == 0) {
            System.out.println("You haven't added any log in this application:)");
        } else {
            System.out.println("-----------------All Logs--------------------");
        }
    }

    /*
     * EFFECTS: prompt users to enter either muscle type (1) or date (2) to filter logs by.
     *          then, get a new muscle type or date based on the selection.
     *          
     *          call filteredLog method to get a new filtered list and display it
     */
    private void filterWorkoutLog() {
        boolean isValid = false;
        Log log = new Log();
        int responseFilter;

        do {
            System.out.println("---Would you like to filter logs out by name or date?---");
            System.out.println("1. Muscle type\n2. Date");
            System.out.print("Please enter the option number");
            responseFilter = input.nextInt();

            if (responseFilter == 1 || responseFilter == 2) {
                isValid = true;
            } else {
                System.out.println("You entered the wrong number, please try this again!");
            }

        } while (!isValid); // determine if users' response is valid.

        if (responseFilter == 1) {          // muscle type
            type = promptMuscleType();
        }

        if (responseFilter == 2) {          // date
            getDateStringFromUser();
        }

        viewAllLogs(log.filteredLog(responseFilter, type, date)); // display a new filtered list
    }

    /*
     * EFFECTS: prompt users to enter an exercise name and date to update.
     *          then display update options and update based on the users' selection
     */
    private void updateLog() {
        Log tempLog = new Log();
        input.nextLine(); // Clear the leftover newline
        System.out.print("What kind of the name of exercise the log has would you like to update: ");
        String tempName = input.nextLine(); // get the exercise name to find and update

        getDateStringFromUser();            // get the date to find and update

        int index = tempLog.findMatchedLog(tempName, date); // find the existing exercise in the log

        if (index == -1) {
            System.out.println("\nThere is no matching log in our database!!!");
        } else {
            updateOptions();                                // display update options
            System.out.print("\nPlease enter the number 1 to 6: ");
            int response = input.nextInt();
            input.nextLine();
            menuUpdate(response, index);                    // update based on the selection
        }

    }

    // EFFECTS: display update options
    private void updateOptions() {
        System.out.println("\n---------Update--------");
        System.out.println("1. Exercise name");
        System.out.println("2. Muscle type");
        System.out.println("3. Weight");
        System.out.println("4. Sets");
        System.out.println("5. Reps");
    }

    /*
     * EFFECTS: get the exercise name and the date to find and remove.
     *          then, remove it and print the removed log
     */
    private void removeExercise() {
        System.out.println("---------Remove an exercise into our log---------");
        input.nextLine(); // Clear the leftover newline
        System.out.print("Enter the name of the exercise to remove: ");
        exerciseName = input.nextLine().trim();         // get the exercise name

        getDateStringFromUser();                        // get the date

        Log removedLog = new Log().removeLogExercises(exerciseName, date); // save the removed log

        if (removedLog == null) {
            System.out.println("\nNo matching workout in our log!");
        } else {
            System.out.println("\n" + removedLog + " is deleted!!!!");
        }
    }

    /*
     * EFFECTS: add a new exercise into the log list by creating Exercise object.
     *          and create a log to put Exercise object and date together.
     *          then, the log is saved into the static list in Log class
     */
    private void addExercise() {
        System.out.println("---------Add an exercise into our log---------");

        getUserInputs();
        Exercise exercise = new Exercise(exerciseName, type, weightLifted, numSets, numReps);
        Log log = new Log(exercise, date);
        log.addLogToExercisesList();            // add a log into the static list in Log class

        logs = log.getAllExercisesLog();        // connect a list here to the static list in Log class

        System.out.println("\n--------------Successfully added!-------------\n");
    }

    /*
     * EFFECTS: display a welcome message
     */
    private void greeting() {
        System.out.println("*******************************************");
        System.out.println("*      Welcome to the Exercise Tracker    *");
        System.out.println("*******************************************");

        System.out.println("Hello! We're thrilled to have you here!");
        System.out.println("This application is designed to help you track and improve your workouts");
        System.out.print("Whether you're just starting out or you're an experienced athlete, ");
        System.out.println(" we're here to support your progress");

        System.out.println("\n\tHere's how we can help you");
        System.out.println("\t- Track your exercise with ease");
        System.out.println("\t- Monitor your progress over time");

        System.out.println("\nLet's get started! Your journey to better fitness begins now.");
        System.out.println("-------------------------------------------------------------");
    }

    /*
     * EFFECTS: get date in the specific format (yyyy/mm/dd).
     *          this method determines if the date is in the right format
     */
    private void getDateStringFromUser() {
        boolean isValid = true;

        do {
            if (!isValid) {
                System.out.println("Unfortunately, you entered the date in the wrong format");
                System.out.println("Please try this again");
            }
            System.out.print("-Please Enter the date of your workout (yyyy/mm/dd): ");
            date = input.next();
            isValid = isValidDateFormat(date);

        } while (!isValid);
    }

    /*
     * EFFECTS: get all information to register a new exercise in the list
     */
    private void getUserInputs() {
        getDateStringFromUser();

        input.nextLine(); // Clear the leftover newline

        System.out.print("\n-What kind of workout would you like to register here: ");
        exerciseName = input.nextLine();

        System.out.print("\n-How much weight (in kg) did you lift or plan to lift: ");
        weightLifted = input.nextDouble();

        System.out.print("\n-How many sets did you do or plan to do : ");
        numSets = input.nextInt();

        System.out.print("\n-How many reps per set: ");
        numReps = input.nextInt();

        type = promptMuscleType();
    }

    /*
     * EFFECTS: return ture if the date is in the right format (yyyy/mm/dd).
     *          otherwise, return false.
     */
    private boolean isValidDateFormat(String date) {

        if (date.length() != 10) {
            return false;
        }

        for (int i = 0; i < date.length(); i++) {

            if (i < 4) {

                if (!Character.isDigit(date.charAt(i))) {
                    return false;
                }

            } else if (i == 4 || i == 7) {

                if (!(date.charAt(i) == '/')) {
                    return false;
                }

            } else {

                if (!Character.isDigit(date.charAt(i))) {
                    return false;
                }
            }
        }

        return true;
    }

    /*
     * EFFECTS: allows users to select the muscle type by providing a menu
     */
    private Muscles promptMuscleType() {
        System.out.println("\n-------------------Muscle type options---------------");
        System.out.println("1. Legs\n2. Biceps\n3. Triceps\n4. Shoulders\n5. Back\n6. Chest");
        System.out.print("\nPlease Enter the number corresponding to the muscle group this workout targets: ");
        int response = input.nextInt();
        setMuscleType(response);
        return type;
    }

    /*
     * EFFECTS: based on users' selection, type is determined
     */
    private void setMuscleType(int response) {
        switch (response) {
            case 1:
                type = Muscles.LEGS;
                break;
            case 2:
                type = Muscles.BICEPS;
                break;
            case 3:
                type = Muscles.TRICEPS;
                break;
            case 4:
                type = Muscles.SHOULDERS;
                break;
            case 5:
                type = Muscles.BACK;
                break;
            case 6:
                type = Muscles.CHEST;
                break;

            default:
                System.out.println("Invalid option. Please enter a vaild number between 1 and 6");
                promptMuscleType();
        }
    }

    /*
     * REQUIRES: response must be between 1 and 6, inclusively
     * EFFECTS: based on the response, update information about workout
     */
    private void menuUpdate(int response, int index) {
        if (response == 1) {
            System.out.print("\nWhat is the new name of workout : ");
            exerciseName = input.nextLine();
            logs.get(index).updateName(exerciseName);
        } else if (response == 2) {
            type = promptMuscleType();
            logs.get(index).updateMuscleType(type);
        } else if (response == 3) {
            System.out.print("\nHow much weight (in kg) is it: ");
            weightLifted = input.nextDouble();
            logs.get(index).updateWeight(weightLifted);
        } else if (response == 4) {
            System.out.print("\nHow many sets is it : ");
            numSets = input.nextInt();
            logs.get(index).updateSets(numSets);
        } else if (response == 5) {
            System.out.print("\nHow many reps per set: ");
            numReps = input.nextInt();
            logs.get(index).updateReps(numReps);
        } else {
            System.out.println("\nUnfortunately, the number you entered is not correct!");
        }

    }

    /*
     * EFFECTS: display main menu on the console
     */
    private void menuDisplay() {
        System.out.println("--------------------------------------");
        System.out.println("|         Fitness Tracker Menu       |");
        System.out.println("--------------------------------------");
        System.out.println("1. Add an exercies");
        System.out.println("2. Remove an exercise");
        System.out.println("3. Update the log");
        System.out.println("4. Filter workout log");
        System.out.println("5. View all exercises you added");
        System.out.println("6. Exit");
        System.out.print("Please enter a number between 1 and 6: ");
    }
}
