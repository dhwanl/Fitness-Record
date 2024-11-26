package ui;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import org.json.JSONArray;

import model.Exercise;
import model.Log;
import model.Muscles;
import model.PrintEventLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.util.List;

/*
* Represent application's main window frame
*/
public class FitnessRecordUI extends JFrame {
    private static final int WIDTH = 350;
    private static final int HEIGHT = 700;
    private static final String JSON_STORE = "./data/";
    private static final String IMAGE_STORE = "./image/background.png";
    
    private JFrame parentFrame;
    private JComboBox<Muscles> muscleComboBox;
    private JTextArea logDisplay;
    private JScrollPane scrollPane;
    private JTextField yearField;
    private JTextField monthField;
    private JTextField dayField;
    private JTextField nameField;
    private JTextField weightField;
    private JTextField setsField;
    private JTextField repsField;
    private String[] labels = {
        "Exercise Name", 
        "Muscle Type", 
        "Weight (kg)", 
        "Number of Reps", 
        "Number of Sets", 
        "Date yyyy/mm/dd"
    };
    

    /*
     * MODIFIES: this
     * EFFECTS: creates the main application window and initialize components
     */
    public FitnessRecordUI() {
        parentFrame = new JFrame("Fitness Record");
        parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        parentFrame.setSize(WIDTH, HEIGHT);
        parentFrame.setLayout(new BorderLayout());


        parentFrame.add(new ImagePanel(IMAGE_STORE), BorderLayout.CENTER);

        addButtonPanel();
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        parentFrame.setVisible(true);
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates and adds the option button panel to the main frame
     */
    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(8, 1));
        buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        buttonPanel.add(createButton("Add an exercise", e -> addExercise()));
        buttonPanel.add(createButton("Remove an exercise", e -> removeExercise()));
        buttonPanel.add(createButton("Update the log", e -> updateLog()));
        buttonPanel.add(createButton("Filter workout log", e -> filteredLog()));
        buttonPanel.add(createButton("View all exercises you added", 
                                            e -> displayAllLogs(new Log().getAllExercisesLog(), 
                                                    "Exercises in the list")));
        buttonPanel.add(createButton("Save logs to file", e -> saveLogsToFile()));
        buttonPanel.add(createButton("Load logs from file", e -> loadLogsFromFile()));
        buttonPanel.add(createButton("Exit", e -> {
            PrintEventLog.printEventLog();
            System.exit(0);
        }));
    
        parentFrame.add(buttonPanel, BorderLayout.SOUTH);
    }

    /*
     * REQUIRES: text != null, action != null
     * EFFECTS: creates and returns a button with the text and action listener
     */
    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    /*
     * MODIFIES: logDisplay, scrollPane, parentFrame
     * EFFECTS: if logDisplay is null, initializes a new JTextArea and wraps it in a JScrollPane.
     */
    private void createDisplayLog() {
        if (logDisplay == null) {
            logDisplay = new JTextArea();
            logDisplay.setEditable(false);
            scrollPane = new JScrollPane(logDisplay);
            scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
            parentFrame.add(scrollPane, BorderLayout.CENTER);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            centreOnScreen();
            parentFrame.setVisible(true);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: open a window to add a new exercise log
     */
    private void addExercise() {
        createDisplayLog();
        JDialog dialog = createDialog("Add Exercise", 400, 400);
        
        JPanel addExercisePanel = new JPanel();
        addExercisePanel.setLayout(new GridLayout(labels.length, 2));
        addExercisePanel.setBorder(new EmptyBorder(10, 5, 10, 5));
        
        addExerciseFormat(addExercisePanel);
        
        dialog.add(addExercisePanel, BorderLayout.CENTER);
        dialog.add(exerciseButtonPanel(dialog, "addEx"), BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    /*
     * REQUIRES: title != null, w > 0, h > 0
     * EFFECTS: creates a new window with the specified title, width, and height
     */
    private JDialog createDialog(String title, int w, int h) {
        JDialog dialog = new JDialog(parentFrame, title, true);
        dialog.setSize(w, h);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setLayout(new BorderLayout());
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        return dialog;
    }

    /*
     * REQUIRES: purpose is one of "addEx", "removeEx", "updateEx"
     * EFFECTS: creates a panel with buttons for saving and canceling
     */
    private JPanel exerciseButtonPanel(JDialog dialog, String purpose) {
        JPanel buttonPanel = new JPanel();
        JButton button = new JButton();
        JButton cancelButton = createCancelButton(dialog, "cancel");


        if (purpose.equals("addEx")) {
            button = createSaveButton(dialog);
        } else if (purpose.equals("removeEx")) {
            button = createSaveButtonForRemove(dialog);
        } else if (purpose.equals("updateEx")) {
            button = createSaveButtonForUpdate(dialog);
        }

        buttonPanel.add(button);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    /*
     * REQUIRES: dialog != null
     * MODIFIES: this, Log.exercises
     * EFFECTS: creates and adds a new exercise to the logs with the success message
     */
    private JButton createSaveButton(JDialog dialog) {
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            try {
                String exerciseName = nameField.getText();
                Muscles muscleType =  (Muscles) muscleComboBox.getSelectedItem();
                int weight = Integer.parseInt(weightField.getText());
                int reps = Integer.parseInt(repsField.getText());
                int sets = Integer.parseInt(setsField.getText());
                String date = String.format("%s/%s/%s", yearField.getText(), monthField.getText(), dayField.getText());

                Exercise exercise = new Exercise(exerciseName, muscleType, weight, reps, sets);
                Log newLog = new Log(exercise, date);
                newLog.addLogToExercisesList();

                displayLog(exercise, date, "Exercise Added");

                JOptionPane.showMessageDialog(dialog, "Exercise added successfully");
                dialog.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter vaild numbers for weight, reps, and sets:)");
            }
        });

        return saveButton;
    }

    /*
     * REQUIRES: dialog != null
     * MODIFIES: this
     * EFFECTS: creates and returns a cancel button that closes the window
     */
    private JButton createCancelButton(JDialog dialog, String title) {
        JButton cancelButton = new JButton(title);
        cancelButton.addActionListener(e -> dialog.dispose());
        return cancelButton;
    }

    /*
     * REQUIRES: e != null, date != null, title != null
     * MODIFIES: this
     * EFFECTS: appends exercise to the log display area
     */
    private void displayLog(Exercise e, String date, String title) {
        logDisplay.append(String.format(
                "\n" + title + ": %s\n Muscle: %s\n Weight: %d kg\n Reps: %d\n Sets: %d\n Date: %s\n", 
                e.getExerciseName(), 
                e.getMuscleType(), 
                e.getWeightLifted(), 
                e.getNumReps(), 
                e.getNumSets(), 
                date
            )
        );
    }

    /*
     * REQUIRES: addExercisePanel != null
     * MODIFIES: this
     * EFFECTS: adds input field to the specified panel for adding a new exercise
     */
    private void addExerciseFormat(JPanel addExercisePanel) {

        for (int i = 0; i < labels.length; i++) {
            addExercisePanel.add(new JLabel(labels[i]));
            
            if (i == 0) {
                nameField = new JTextField();
                addExercisePanel.add(nameField);
            } else if (i == 1) {
                muscleComboBox = createMuscleCombo();
                addExercisePanel.add(muscleComboBox);
            } else if (i == 2) {
                weightField = new JTextField();
                addExercisePanel.add(weightField);
            } else if (i == 3) {
                repsField = new JTextField();
                addExercisePanel.add(repsField);
            } else if (i == 4) {
                setsField = new JTextField();
                addExercisePanel.add(setsField);
            } else if (i == 5) {
                getDate(addExercisePanel);
            }
        }
    }

    /*
     * REQUIRES: addExercisePanel != null
     * MODIFIES: this
     * EFFECTS: initializes and adds input field for date to the panel
     */
    private void getDate(JPanel addExercisePanel) {
        yearField = new JTextField("YYYY");
        monthField = new JTextField("MM");
        dayField = new JTextField("DD");

        JPanel datePanel = new JPanel();
        datePanel.setLayout(new GridLayout(1, 3));
        datePanel.add(yearField);
        datePanel.add(monthField);
        datePanel.add(dayField);
        addExercisePanel.add(datePanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates a new window for user to remove a specific exercise from the log
     */
    private void removeExercise() {
        createDisplayLog();
        JDialog dialog = createDialog("Remove Exercise", 300, 150);

        JPanel removeExercisePanel = new JPanel();
        removeExercisePanel.setLayout(new GridLayout(2, 2));
        removeExercisePanel.setBorder(new EmptyBorder(10, 5, 10, 5));

        removeExericseFormat(removeExercisePanel);

        dialog.add(removeExercisePanel, BorderLayout.CENTER);
        dialog.add(exerciseButtonPanel(dialog, "removeEx"), BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    /*
     * REQUIRES: dialog != null
     * MODIFIES: this, Log.exercises
     * EFFECTS: removes an exercise from the log by clicking the remove button
     */
    private JButton createSaveButtonForRemove(JDialog dialog) {
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(e -> {
            try {
                String exerciseName = nameField.getText();
                String date = String.format("%s/%s/%s", yearField.getText(), monthField.getText(), dayField.getText());

                Log removedLog = new Log().removeLogExercises(exerciseName, date);

                if (removedLog != null) {
                    displayLog(removedLog.getExercise(), date, "Exercise Removed");
                    JOptionPane.showMessageDialog(dialog, "Exercise removed successfully");
                } else {
                    JOptionPane.showMessageDialog(dialog, "No matching exercise found");
                }
                
                dialog.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter vaild numbers for date:)");
            }
        });

        return removeButton;
    }

    /*
     * REQUIRES: removeExercisePanel != null
     * MODIFIES: this
     * EFFECTS: adds input fields for removing an exercise (exercise name, date)
     */
    private void removeExericseFormat(JPanel removeExercisePanel) {
        removeExercisePanel.add(new JLabel(labels[0]));
        nameField = new JTextField();
        removeExercisePanel.add(nameField);

        removeExercisePanel.add(new JLabel(labels[5]));
        getDate(removeExercisePanel);
    }

    /*
     * MODIFIES: this
     * EFFECTS: find exercise log for updating
     */
    private void updateLog() {
        createDisplayLog();
        JDialog dialog = createDialog("Update Exercise", 300, 150);
        
        JPanel updateExercisePanel = new JPanel();
        updateExercisePanel.setLayout(new GridLayout(2, 2));
        updateExercisePanel.setBorder(new EmptyBorder(10, 5, 10, 5));

        updateExericseFormat(updateExercisePanel);

        dialog.add(updateExercisePanel, BorderLayout.CENTER);
        dialog.add(exerciseButtonPanel(dialog, "updateEx"), BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    /*
     * REQUIRES: updateExercisePanel != null
     * MODIFIES: this
     * EFFECTS: adds input field for finding an exercise log to update
     */
    private void updateExericseFormat(JPanel updateExercisePanel) {
        updateExercisePanel.add(new JLabel(labels[0]));
        nameField = new JTextField();
        updateExercisePanel.add(nameField);

        updateExercisePanel.add(new JLabel(labels[5]));
        getDate(updateExercisePanel);
    }

    /*
     * REQURIES: dialog != null
     * MODIFIES: this
     * EFFECTS: finds a matching exercise log based on the input name and date, and open a new window.
     */
    private JButton createSaveButtonForUpdate(JDialog dialog) {
        JButton saveButton = new JButton("Find");
        saveButton.addActionListener(e -> {
            try {
                String exerciseName = nameField.getText();
                String date = String.format("%s/%s/%s", yearField.getText(), monthField.getText(), dayField.getText());

                List<Log> logs = new Log().getAllExercisesLog();
                
                int index = new Log().findMatchedLog(exerciseName, date);

                if (index != -1) {
                    updateOptions(logs.get(index));
                    displayLog(logs.get(index).getExercise(), logs.get(index).getDate(), "Exercise updated");
                } else {
                    JOptionPane.showMessageDialog(dialog, "No matching exercise found");
                }
                
                dialog.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Please enter vaild numbers for date:)");
            }
        });

        return saveButton;
    }
    
    /*
     * REQUIRES: log != null
     * MODIFIES: log, Exercise
     * EFFECTS: open a window for updating info by field
     */
    private void updateOptions(Log log) {
        JDialog dialog = createDialog("Update Exercise Options", 400, 400);

        Exercise curExercise = log.getExercise();
        JPanel updateExercisePanel = new JPanel();
        updateExercisePanel.setLayout(new GridLayout(0, 1));
        updateExercisePanel.setBorder(new EmptyBorder(10, 5, 10, 5));

        JComboBox<String> updateFieldComboBox = new JComboBox<>(labels);
        
        updateExercisePanelWithFields(updateExercisePanel, updateFieldComboBox, curExercise, log);

        JPanel datePanel = new JPanel(new GridLayout(1, 3));
        datePanel.add(yearField);
        datePanel.add(monthField);
        datePanel.add(dayField);
    
        updateExercisePanel.add(datePanel);

        updateFieldComboBoxEventHandler(updateFieldComboBox, dialog);

        JPanel buttonPanel = new JPanel();
        JButton updateButton = new JButton("update");
        JButton cancelButton = createCancelButton(dialog, "end");

        updateEventHandler(updateButton, updateFieldComboBox, curExercise, log, dialog);

        buttonPanel.add(updateButton);
        buttonPanel.add(cancelButton);

        dialog.add(updateExercisePanel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
    
    /*
     * REQUIRES: updateFieldComboBox != null, dialog != null
     * MODIFIES: this
     * EFFECTS: adds event handler to the combo box to visualize the input field by the option users choose
     */
    private void updateFieldComboBoxEventHandler(JComboBox<String> updateFieldComboBox, JDialog dialog) {
        updateFieldComboBox.addActionListener(e -> {
            String selected = (String) updateFieldComboBox.getSelectedItem();
            nameField.setVisible("Exercise Name".equals(selected));
            muscleComboBox.setVisible("Muscle Type".equals(selected));
            weightField.setVisible("Weight (kg)".equals(selected));
            repsField.setVisible("Number of Reps".equals(selected));
            setsField.setVisible("Number of Sets".equals(selected));
            boolean isDate = "Date yyyy/mm/dd".equals(selected);
            yearField.setVisible(isDate);
            monthField.setVisible(isDate);
            dayField.setVisible(isDate);
            dialog.pack();
        });
    }

    /*
     * REQUIRES: updateButton != null, updateFieldComboBox != null, curExercise != null, log != null, dialog != null
     * MODIFIES: curExercise, log
     * EFFECTS: updates the selected field in the exercise log with the new input value
     */
    private void updateEventHandler(JButton updateButton, JComboBox<String> updateFieldComboBox, 
                                            Exercise curExercise, Log log, JDialog dialog) {
        updateButton.addActionListener(e -> {
            try {
                String selected = (String) updateFieldComboBox.getSelectedItem();
                if ("Exercise Name".equals(selected)) {
                    curExercise.setExerciseName(nameField.getText());
                } else if ("Muscle Type".equals(selected)) {
                    curExercise.setMuscleType((Muscles) muscleComboBox.getSelectedItem());
                } else if ("Weight (kg)".equals(selected)) {
                    curExercise.setWeightLifted(Integer.parseInt(weightField.getText()));
                } else if ("Number of Reps".equals(selected)) {
                    curExercise.setNumReps(Integer.parseInt(repsField.getText()));
                } else if ("Number of Sets".equals(selected)) {
                    curExercise.setNumSets(Integer.parseInt(setsField.getText()));
                } else if ("Date yyyy/mm/dd".equals(selected)) {
                    String updatedDate = String.format("%s/%s/%s", 
                                            yearField.getText(), monthField.getText(), dayField.getText());
                    log.updateDate(updatedDate);
                }
                JOptionPane.showMessageDialog(dialog, "Exercise updated successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid input. Please check your values!");
            }
        });
    }

    /*
     * REQUIRES: updateExercisePanel != null, updateFieldComboBox != null, curExercise != null, log != null
     * MODIFIES: this
     * EFFECTS: adds fields to update an exercise and hide all input fields initially
     */
    private void updateExercisePanelWithFields(JPanel updateExercisePanel, JComboBox<String> updateFieldComboBox, 
                                                    Exercise curExercise, Log log) {
        nameField = new JTextField(curExercise.getExerciseName());
        muscleComboBox.setSelectedItem(curExercise.getMuscleType());
        weightField = new JTextField(String.valueOf(curExercise.getWeightLifted()));
        repsField = new JTextField(String.valueOf(curExercise.getNumReps()));
        setsField = new JTextField(String.valueOf(curExercise.getNumSets()));
        yearField = new JTextField(String.valueOf(log.getDate().split("/")[0]));
        monthField = new JTextField(String.valueOf(log.getDate().split("/")[1]));
        dayField = new JTextField(String.valueOf(log.getDate().split("/")[2]));

        updateExercisePanel.add(new Label("Choose Fields to Update:"));
        updateExercisePanel.add(updateFieldComboBox);
        updateExercisePanel.add(muscleComboBox);
        updateExercisePanel.add(nameField);
        updateExercisePanel.add(weightField);
        updateExercisePanel.add(repsField);
        updateExercisePanel.add(setsField);

        nameField.setVisible(false);
        muscleComboBox.setVisible(false);
        weightField.setVisible(false);
        repsField.setVisible(false);
        setsField.setVisible(false);
        yearField.setVisible(false);
        monthField.setVisible(false);
        dayField.setVisible(false);
    }

    /*
     * REQUIRES: logs != null
     * MODIFIES: this
     * EFFECTS: iterates through all logs and display the details in the main panel
     */
    private void displayAllLogs(List<Log> logs, String title) {
        createDisplayLog();
        logDisplay.setText("");
        if (logs.isEmpty()) {
            JDialog dialog = createDialog("Nothing saved yet", 200, 200);
            JOptionPane.showMessageDialog(dialog, "No exercise in this application now!!!!");
        }
        for (int i = 0; i < logs.size(); i++) {
            displayLog(logs.get(i).getExercise(), logs.get(i).getDate(), title);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: gives options to users for exercise to be filtered by date or muscle type
     *          and sets up event handlers for filtering
     */
    private void filteredLog() {
        createDisplayLog();
        JDialog dialog = createDialog("Filter log", 400, 75);

        JPanel buttonPanel = new JPanel();
        JButton dateButton = new JButton("Filtered by Date");
        JButton exerciseMuscleTypeButton = new JButton("Filtered by Muscle Type");

        buttonPanel.add(dateButton);
        buttonPanel.add(exerciseMuscleTypeButton);
        
        filteredByDateEventHandler(dateButton);
        filteredByMuscleTypeEventHandler(exerciseMuscleTypeButton);

        dialog.add(buttonPanel);

        centreOnScreen();
        dialog.setVisible(true);
        dialog.pack();
    }

    /*
     * REUQIRES: dataButton and dialog != null
     * MODIFIES: this
     * EFFECTS: pops up a window to filter exercises by date
     *          allows users to input a date in the format yyyy/mm/dd
     *          filters the log matching the date and display the result in main panel
     */
    private void filteredByDateEventHandler(JButton dateButton) {
        dateButton.addActionListener(e -> {
            JDialog subDialog = createDialog("Filtered By Date", 400, 150);
        
            JPanel datePanel = new JPanel();
            getDate(datePanel);

            JButton filterButton = new JButton("filter");
            JButton cancelButton = createCancelButton(subDialog, "cancel");

            filteredByDateEventHandlerHelper(filterButton, subDialog);

            JPanel buttonPanel = new JPanel();
            buttonPanel.add(filterButton);
            buttonPanel.add(cancelButton);

            subDialog.add(datePanel, BorderLayout.CENTER);
            subDialog.add(buttonPanel, BorderLayout.SOUTH);

            subDialog.pack();
            subDialog.setVisible(true);
        });

    }

    /*
     * REQURIES: filterButton and subDialog != null
     * MODIFIES: this
     * EFFECTS: filters and displays exercises by the inputted date
     */
    private void filteredByDateEventHandlerHelper(JButton filterButton, JDialog subDialog) {
        filterButton.addActionListener(event -> {
            try {
                String date = String.format("%s/%s/%s", 
                                        yearField.getText(), monthField.getText(), dayField.getText());

                List<Log> filteredLogs = new Log().filteredExercisesByDate(date);

                if (filteredLogs.isEmpty()) {
                    JOptionPane.showMessageDialog(subDialog, "No exercise founded for the given date!");
                } else {
                    displayAllLogs(filteredLogs, "Filtered Exercises");
                }

                subDialog.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(subDialog, "Invalid data format. Please try again.");
            }
        });
    }

    /*
     * REUQIRES: muscleTypeButton and dialog != null
     * MODIFIES: this
     * EFFECTS: pops up a window to filter exercises by muscle type
     *          allows users to input muscle type by choosing
     *          filters the log matching the muscle type and display the result in main panel
     */
    private void filteredByMuscleTypeEventHandler(JButton muscleTypeButton) {
        muscleTypeButton.addActionListener(e -> {
            JDialog subDialog = createDialog("Filtered By Exercise Name", 400, 150);

            JPanel muscleTypePanel = new JPanel();
            muscleTypePanel.setLayout(new GridLayout(2, 1));
            JLabel muscleTypeLabel = new JLabel("Select Muscle Type: ");
            muscleComboBox = createMuscleCombo();

            muscleTypePanel.add(muscleTypeLabel);
            muscleTypePanel.add(muscleComboBox);

            JButton filterButton = new JButton("filter");
            JButton cancelButton = createCancelButton(subDialog, "cancel");

            filteredByMuscleTypeEventHandlerHelper(filterButton, subDialog);
            
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(filterButton);
            buttonPanel.add(cancelButton);

            subDialog.add(muscleTypePanel, BorderLayout.CENTER);
            subDialog.add(buttonPanel, BorderLayout.SOUTH);

            subDialog.setVisible(true);
        });
    }

    /*
     * REQURIES: filterButton and subDialog != null
     * MODIFIES: this
     * EFFECTS: filteres and displays exercises by the inputted muscle type
     */
    private void filteredByMuscleTypeEventHandlerHelper(JButton filterButton, JDialog subDialog) {
        filterButton.addActionListener(event -> {
            Muscles selectedMuscleType = (Muscles) muscleComboBox.getSelectedItem();
            List<Log> filteredLogs = new Log().filteredExercisesByType(selectedMuscleType);

            if (filteredLogs.isEmpty()) {
                JOptionPane.showMessageDialog(subDialog, "No exercise founded for the given muscle type!");
            } else {
                displayAllLogs(filteredLogs, "Filtered Exercises");
            }

            subDialog.dispose();
        });
    }

    /*
     * MODIFIES: a file
     * EFFECTS: saves all logs to a file
     */
    private void saveLogsToFile() {
        createDisplayLog();
        String fileName = JOptionPane.showInputDialog(this, "Enter file name to save logs:");
        if (fileName == null) {
            JOptionPane.showMessageDialog(this, "File save canceled");
            return;
        }

        if (fileName != null && !fileName.trim().isEmpty()) {
            if (!fileName.equals(fileName + ".json")) {
                fileName += ".json";
            }

            saveLogsToFileHelper(fileName);

        } else if (fileName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "file name cannot be empty!!!!!");
        }
    
    }

    /*
     * REQUIRES: fileName != null
     * MODIFIES: this
     * EFFECTS: converts all logs to JSON format, and writes them to the specified file
     */
    private void saveLogsToFileHelper(String fileName) {
        JsonWriter jsonWriter = new JsonWriter(JSON_STORE + fileName);
            
        try {
            JSONArray jsonArray = new JSONArray();
            jsonWriter.open();
            List<Log> logs = new Log().getAllExercisesLog();

            if (logs.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No logs to save!");
            } else {
                jsonArray = new Log().saveLogsToJSonFile(fileName);
            }

            jsonWriter.write(jsonArray);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Logs saved successfully to " + JSON_STORE + fileName);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Unable to write logs to the file: " + JSON_STORE + fileName);
        }
    }
    
    /*
     * EFFECTS: loads logs from a file
     */
    private void loadLogsFromFile() {
        createDisplayLog();
        File f = new File("./data");
        String[] files = f.list();
        displayFileList(files);

        String fileName = JOptionPane.showInputDialog(this, "Enter file name to load logs:");
        
        if (fileName == null) {
            JOptionPane.showMessageDialog(this, "File save canceled");
            return;
        }

        if (fileName != null && !fileName.trim().isEmpty()) {
            if (!fileName.equals(fileName + ".json")) {
                fileName += ".json";
            }

            loadLogsFromFileHelper(fileName);

        }
    }

    /*
     * REQUIRES: fileName != null
     * MODIFIES: logDisplay
     * EFFECTS: reads logs from the specified file and adds them to the displayLog
     */
    private void loadLogsFromFileHelper(String fileName) {
        JsonReader jsonReader = new JsonReader(JSON_STORE + fileName);
        
        List<Log> newLogs = new Log().fromJson(jsonReader, fileName);
        
        if (newLogs != null) {
            JOptionPane.showMessageDialog(this, "Logs successfully loaded from " + JSON_STORE + fileName);
        } else {
            JOptionPane.showMessageDialog(this, "Unable to read from file: " + JSON_STORE + fileName);  
        }
    }

    /*
     * REQUIRES: files != null
     * MODIFIES: logDisplay
     * EFFECTS: if files is empty, shows a message dialog stating "No files available to load!!!"
     *          Otherwise, updates the logDisplay with the list of file names in files
     */
    private void displayFileList(String[] files) {

        if (files.length == 0) {
            JOptionPane.showMessageDialog(this, "No files available to load!!!");
        } else {
            logDisplay.setText("");
            logDisplay.append("\n**********The list of files in our database!!!!!***********\n");
            for (String file : files) {
                logDisplay.append(file + "\n");
            }
        }
    }

    /*
     * MODIFIES: muscleComboBox
     * EFFECTS: creates and returns a combo box with muscle types
     */
    private JComboBox<Muscles> createMuscleCombo() {
        muscleComboBox = new JComboBox<>();

        for (Muscles muscle : Muscles.values()) {
            muscleComboBox.addItem(muscle);
        }

        return muscleComboBox;
    }

    /*
     * MODIFIES: this
     * EFFECTS: centers the parent frame on the screen
     */
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        parentFrame.setLocation((width - parentFrame.getWidth()) / 2, (height - parentFrame.getHeight()) / 2);
    }

}