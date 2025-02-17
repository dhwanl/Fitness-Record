# My Personal Project: Fitness Record

## Record Workouts, Track Progress, Stay Fit

- **What will the application do?**

> ***Fitness Record*** is an application that helps users plan and track their workouts. Users can save exercises, log the weights they use, and monitor their progress over time. Features like workout logs, and progress tracking empower users to stay motivated and push toward their fitness goals.

- **Who will use it?**

> ***Fitness Record*** is for users who want to track their workout progress, systematically increase weights over time, view all the workouts they saved and plan daily workout routines.

- **Why is this project of interest to you?**

> In the gym, I observed people taking notes after every set, recording how much weight they lifted to ensure they gradually increased their load. This inspired me to create an application that would simplify this process, making it easier for users to track their workouts and progress.

## User Stories

- As a user, I want to be able to add and remove an exercise to my workout log
- As a user, I want to be able to delete my workout log if I no longer wish to track it 
- As a user, I want to be able to view all exercises and my workout log to track my progress
- As a user, I want to be able to specify the exercise type, weight lifted, and number of sets and reps
- As a user, I want to be able to update log information to accurately correct if a user put the wrong value
- As a user, I want to be able to filter my workout log by date or exercise type to analyze my progrss more easily
- As a user, I want to be able to save my workout log to a file and name the file
- As a user, I want to be able to load my workout log from a file by entering the file name

## Phase 4: Task 2
****************Event Log****************
Tue Nov 26 13:26:41 PST 2024
Created a new exercise: B with details: 1kg, 1 sets, 1 reps, LEGS
Tue Nov 26 13:26:41 PST 2024
Created a new log for exercise: B on 1111/11/11
Tue Nov 26 13:26:41 PST 2024
Added log for exercise: B on 1111/11/11
Tue Nov 26 13:26:51 PST 2024
View all exercise logs
Tue Nov 26 13:26:51 PST 2024
Found log for exercise: B on 1111/11/11
Tue Nov 26 13:26:59 PST 2024
Updated exercise name to: Bench press
Tue Nov 26 13:27:05 PST 2024
Updated muscle type to: CHEST for exercise: Bench press
Tue Nov 26 13:27:10 PST 2024
Updated weight to: 100kg for exercise: Bench press
Tue Nov 26 13:27:15 PST 2024
Updated repetitions to: 10 for exercise: Bench press
Tue Nov 26 13:27:20 PST 2024
Updated sets to 3 for exercise: Bench press
Tue Nov 26 13:27:27 PST 2024
Updated date to: 2024/11/26 for exercise: Bench press
Tue Nov 26 13:27:32 PST 2024
View all exercise logs
Tue Nov 26 13:27:58 PST 2024
Found log for exercise: Bench press on 2024/11/26
Tue Nov 26 13:27:58 PST 2024
Removed log for exercise: Bench press on 2024/11/26
Tue Nov 26 13:28:01 PST 2024
View all exercise logs
Tue Nov 26 13:28:06 PST 2024
View all exercise logs
Tue Nov 26 13:28:06 PST 2024
Created a new exercise: Bench press with details: 100kg, 3 sets, 12 reps, CHEST
Tue Nov 26 13:28:06 PST 2024
Created a new log for exercise: Bench press on 2024/10/10
Tue Nov 26 13:28:06 PST 2024
Created a new exercise: Shoulder press with details: 50kg, 4 sets, 11 reps, SHOULDERS
Tue Nov 26 13:28:06 PST 2024
Created a new log for exercise: Shoulder press on 2024/10/10
Tue Nov 26 13:28:06 PST 2024
Created a new exercise: Squat with details: 90kg, 3 sets, 10 reps, LEGS
Tue Nov 26 13:28:06 PST 2024
Created a new log for exercise: Squat on 2024/10/10
Tue Nov 26 13:28:06 PST 2024
Created a new exercise: Test with details: 100kg, 10 sets, 3 reps, LEGS
Tue Nov 26 13:28:06 PST 2024
Created a new log for exercise: Test on 2024/11/21
Tue Nov 26 13:28:06 PST 2024
Added log for exercise: Bench press on 2024/10/10
Tue Nov 26 13:28:06 PST 2024
Added log for exercise: Shoulder press on 2024/10/10
Tue Nov 26 13:28:06 PST 2024
Added log for exercise: Squat on 2024/10/10
Tue Nov 26 13:28:06 PST 2024
Added log for exercise: Test on 2024/11/21
Tue Nov 26 13:28:06 PST 2024
Loaded logs to json file: logfile.json
Tue Nov 26 13:28:10 PST 2024
View all exercise logs
Tue Nov 26 13:28:18 PST 2024
Exercises are filtered by Date
Tue Nov 26 13:28:29 PST 2024
Exercises are filtered by muscle type
Tue Nov 26 13:28:33 PST 2024
Exercises are filtered by muscle type
Tue Nov 26 13:28:41 PST 2024
View all exercise logs
Tue Nov 26 13:28:41 PST 2024
Saved all exercise logs to json file: logFile.json
*****************************************

## Phase 4: Task 3
If I had more time, I would refactor the code by separating methods in the FitnessRecordUI class, which currently exceeds 800 lines, into purpose-specific classes. Organizing methods by their functionality would make the application easier to maintain, improve readability, and adhere to the single responsibility principle.