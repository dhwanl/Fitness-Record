package ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import model.Exercise;
import model.Log;
import model.Muscles;

/*
 * Represent application's main window frame
 */
public class FitnessRecordUI extends JFrame {
    private static final int WIDTH = 700;
	private static final int HEIGHT = 700;

    private JDesktopPane desktop;
    private JComboBox<Muscles> muscleComboBox;
    
    /*
     * Constructor calls a method to create main panel
     */
    public FitnessRecordUI() {
        createMainPanel();
    }

    /*
     * Create main panel with the menu buttons for user to choose
     */
    private void createMainPanel() {
        desktop = new JDesktopPane();
        desktop.addMouseListener(new DesktopFocusAction());
        desktop.setLayout(new BorderLayout());


        setContentPane(desktop);
        setTitle("Fitness Record");
        setSize(WIDTH, HEIGHT);

        addButtonPanel();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        centreOnScreen();
        setVisible(true);
    }

    /*
     * Create a button panel to be located at the bottom of the main panel
     */
    private void addButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setSize(new Dimension(0, 0));
        buttonPanel.setLayout(new GridLayout(8, 1));
        buttonPanel.add(createButton("Add an exercise", e -> addExercise()));
        buttonPanel.add(createButton("Remove an exercise", e -> removeExercise()));
        buttonPanel.add(createButton("Update the log", e -> updateLog()));
        buttonPanel.add(createButton("Filter workout log", e -> filteredLog()));
        buttonPanel.add(createButton("View all exercises you added", e -> displayAllLogs()));
        buttonPanel.add(createButton("Save logs to file", e -> saveLogsToFile()));
        buttonPanel.add(createButton("Load logs from file", e -> loadLogsFromFile()));
        buttonPanel.add(createButton("Exit", e -> System.exit(0)));
    
        desktop.add(buttonPanel, BorderLayout.SOUTH);
    }

    /*
     * Create a button
     */
    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    private void addExercise() {
        JDialog dialog = new JDialog(this, "Add a New Exercise", true);
        dialog.setSize(450, 450);
        dialog.setLocationRelativeTo(this);

        // JPanel dialogPanel = new JPanel();
        // dialogPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        JTextField yearField = new JTextField();
        JTextField monthField = new JTextField();
        JTextField dayField = new JTextField();
        JTextField nameField = new JTextField();
        JComboBox<Muscles> muscleComboBox = createMuscleCombo();
        JTextField weightField = new JTextField();
        JTextField setsField = new JTextField();
        JTextField repsField = new JTextField();

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {

        });

        dialog.setLayout(new GridLayout(10, 2, 10, 10));
        dialog.add(new JLabel("----------Date-------"));
        dialog.add(new JLabel());
        dialog.add(new JLabel("Year"));
        dialog.add(yearField);
        dialog.add(new JLabel("Month"));
        dialog.add(monthField);
        dialog.add(new JLabel("Day"));
        dialog.add(dayField);
        dialog.add(new JLabel("Exercise Name: "));
        dialog.add(nameField);
        dialog.add(new JLabel("Muscle Group: "));
        dialog.add(muscleComboBox);
        dialog.add(new JLabel("Weight Lifted (kg): "));
        dialog.add(weightField);
        dialog.add(new JLabel("Number of Sets: "));
        dialog.add(setsField);
        dialog.add(new JLabel("Number of Reps: "));
        dialog.add(repsField);
        dialog.add(new JLabel());
        dialog.add(saveButton);
        
        // dialogPanel.add(dialog, BorderLayout.CENTER);
        // dialogPanel.add(saveButton, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    private void removeExercise() {
        JOptionPane.showMessageDialog(this, "Remove Exercise");
    }
    private void updateLog() {
        JOptionPane.showMessageDialog(this, "Add Exercise");
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
	 * @return  the combo box
	 */
	private JComboBox<Muscles> createMuscleCombo() {
		muscleComboBox = new JComboBox<Muscles>();

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
		setLocation((width - getWidth()) / 2, (height - getHeight()) / 2);
	}

    /**
	 * Represents action to be taken when user clicks desktop
	 * to switch focus. (Needed for key handling.)
	 */
	private class DesktopFocusAction extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			FitnessRecordUI.this.requestFocusInWindow();
		}
	}
}
