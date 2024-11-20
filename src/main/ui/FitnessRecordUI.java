package ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.Exercise;
import model.Log;
import model.Muscles;

import java.util.List;

/*
 * Represent application's main window frame
 */
public class FitnessRecordUI extends JFrame {
    private static final int WIDTH = 350;
    private static final int HEIGHT = 700;
    
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
        "Exercise Name:", 
        "Muscle Type:", 
        "Weight (kg):", 
        "Number of Reps:", 
        "Number of Sets:", 
        "Date yyyy/mm/dd"
    };
    
    /*
     * Constructor calls a method to create main panel
     */
    public FitnessRecordUI() {
        parentFrame = new JFrame("Fitness Record");
        parentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        parentFrame.setSize(WIDTH, HEIGHT);
        parentFrame.setLayout(new BorderLayout());
        
        logDisplay = new JTextArea();
        logDisplay.setEditable(false);
        scrollPane = new JScrollPane(logDisplay);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        parentFrame.add(scrollPane, BorderLayout.CENTER);

        addButtonPanel();
        
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        parentFrame.setVisible(true);
    }

    /*
     * Create a button panel to be located at the bottom of the main panel
     */
    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(8, 1));
        buttonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        buttonPanel.add(createButton("Add an exercise", e -> addExercise()));
        buttonPanel.add(createButton("Remove an exercise", e -> removeExercise()));
        buttonPanel.add(createButton("Update the log", e -> updateLog()));
        buttonPanel.add(createButton("Filter workout log", e -> filteredLog()));
        buttonPanel.add(createButton("View all exercises you added", e -> displayAllLogs()));
        buttonPanel.add(createButton("Save logs to file", e -> saveLogsToFile()));
        buttonPanel.add(createButton("Load logs from file", e -> loadLogsFromFile()));
        buttonPanel.add(createButton("Exit", e -> System.exit(0)));
    
        parentFrame.add(buttonPanel, BorderLayout.SOUTH);
    }

    /*
     * Create a button
     */
    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    private JDialog createDialog(String title, int w, int h) {
        JDialog dialog = new JDialog(parentFrame, title, true);
        dialog.setSize(w, h);
        dialog.setLocationRelativeTo(parentFrame);
        dialog.setLayout(new BorderLayout());
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        return dialog;
    }

    private void addExercise() {
        JDialog dialog = createDialog("Add Exercise", 400, 400);
        
        JPanel addExercisePanel = new JPanel();
        addExercisePanel.setLayout(new GridLayout(labels.length, 2));
        addExercisePanel.setBorder(new EmptyBorder(10, 5, 10, 5));
        
        addExerciseFormat(addExercisePanel);
        
        dialog.add(addExercisePanel, BorderLayout.CENTER);
        dialog.add(exerciseButtonPanel(dialog, "addEx"), BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private JPanel exerciseButtonPanel(JDialog dialog, String purpose) {
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton();
        JButton cancelButton = createCancelButton(dialog);


        if (purpose.equals("addEx")) {
            saveButton = createSaveButton(dialog);
        } else if (purpose.equals("removeEx")) {
            saveButton = createSaveButtonForRemove(dialog);
        } else if (purpose.equals("updateEx")) {
            saveButton = createSaveButtonForUpdate(dialog);
        }

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

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

    private JButton createCancelButton(JDialog dialog) {
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> dialog.dispose());
        return cancelButton;
    }

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

    private void removeExercise() {
        JDialog dialog = createDialog("Remove Exercise", 300, 150);

        JPanel removeExercisePanel = new JPanel();
        removeExercisePanel.setLayout(new GridLayout(2, 2));
        removeExercisePanel.setBorder(new EmptyBorder(10, 5, 10, 5));

        removeExericseFormat(removeExercisePanel);

        dialog.add(removeExercisePanel, BorderLayout.CENTER);
        dialog.add(exerciseButtonPanel(dialog, "removeEx"), BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private JButton createSaveButtonForRemove(JDialog dialog) {
        JButton saveButton = new JButton("Remove");
        saveButton.addActionListener(e -> {
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

        return saveButton;
    }

    private void removeExericseFormat(JPanel removeExercisePanel) {
        removeExercisePanel.add(new JLabel(labels[0]));
        nameField = new JTextField();
        removeExercisePanel.add(nameField);

        removeExercisePanel.add(new JLabel(labels[5]));
        getDate(removeExercisePanel);
    }

    private void updateLog() {
        JDialog dialog = createDialog("Update Exercise", 300, 150);
        
        JPanel updateExercisePanel = new JPanel();
        updateExercisePanel.setLayout(new GridLayout(2, 2));
        updateExercisePanel.setBorder(new EmptyBorder(10, 5, 10, 5));

        updateExericseFormat(updateExercisePanel);

        dialog.add(updateExercisePanel, BorderLayout.CENTER);
        dialog.add(exerciseButtonPanel(dialog, "updateEx"), BorderLayout.SOUTH);
        dialog.setVisible(true);
    }

    private void updateExericseFormat(JPanel updateExercisePanel) {
        updateExercisePanel.add(new JLabel(labels[0]));
        nameField = new JTextField();
        updateExercisePanel.add(nameField);

        updateExercisePanel.add(new JLabel(labels[5]));
        getDate(updateExercisePanel);
    }

    private JButton createSaveButtonForUpdate(JDialog dialog) {
        JButton saveButton = new JButton("Find");
        saveButton.addActionListener(e -> {
            try {
                String exerciseName = nameField.getText();
                String date = String.format("%s/%s/%s", yearField.getText(), monthField.getText(), dayField.getText());

                List<Log> logs = new Log().getAllExercisesLog();
                displayAllLogs();
                
                int index = new Log().findMatchedLog(exerciseName, date);

                if (index != -1) {
                    displayLog(logs.get(index).getExercise(), date, "Exercise chosen");
                    JOptionPane.showMessageDialog(dialog, "Exercise removed successfully");
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

    private void displayAllLogs(List<Log> logs) {

        for (int i = 0; i < logs.size(); i++) {
            displayLog(logs.get(i).getExercise(), logs.get(i).getDate(), "Exercises in the list");
        }
    }

    private void filteredLog() {
        JOptionPane.showMessageDialog(this, "Add Exercise");
    }
    private void displayAllLogs() {
        JOptionPane.showMessageDialog(this, "Add Exercise");
    }
    private void saveLogsToFile() {
        JOptionPane.showMessageDialog(this, "Add Exercise");
    }

    private void loadLogsFromFile() {
        JOptionPane.showMessageDialog(this, "Add Exercise");
    }

    /**
	 * Helper to create print options combo box
	 * the combo box
	 */
    private JComboBox<Muscles> createMuscleCombo() {
        muscleComboBox = new JComboBox<>();

        for (Muscles muscle : Muscles.values()) {
            muscleComboBox.addItem(muscle);
        }

        return muscleComboBox;
    }

    /**
	 * Helper to centre main application window on desktop
	 */
    private void centreOnScreen() {
        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
        parentFrame.setLocation((width - parentFrame.getWidth()) / 2, (height - parentFrame.getHeight()) / 2);
    }

}
