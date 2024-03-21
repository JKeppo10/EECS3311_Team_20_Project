package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import businessLogic.Misc.Course;
import database.MaintainCourses;

public class CoursePage extends JFrame {

	private JTextField courseCodeField;
	private JTextField courseNameField;
	private JTextField professorField;
	private JTextField emailField;
	private JTextField locationField;

	private JButton addButton;
	private JButton removeButton;
	private JButton cancelButton;

	private MaintainCourses maintainCourses;


    public CoursePage() {
        setTitle("Course Page");
        setSize(600, 360); // Enlarged window size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window
        getContentPane().setLayout(new GridLayout(7, 2, 10, 10)); // 7 rows, 2 columns with 10px vertical and horizontal gaps

        maintainCourses = new MaintainCourses();

        // Initialize text fields with preferred size
        courseCodeField = new JTextField();
        courseCodeField.setPreferredSize(new Dimension(200, 25));
        courseNameField = new JTextField();
        courseNameField.setPreferredSize(new Dimension(200, 25));
        professorField = new JTextField();
        professorField.setPreferredSize(new Dimension(200, 25));
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 25));
        locationField = new JTextField();
        locationField.setPreferredSize(new Dimension(200, 25));

        // Add labels and text fields to the frame
        getContentPane().add(new JLabel("Course Code:", SwingConstants.RIGHT));
        getContentPane().add(courseCodeField);
        getContentPane().add(new JLabel("Course Name:", SwingConstants.RIGHT));
        getContentPane().add(courseNameField);
        getContentPane().add(new JLabel("Professor:", SwingConstants.RIGHT));
        getContentPane().add(professorField);
        getContentPane().add(new JLabel("Email:", SwingConstants.RIGHT));
        getContentPane().add(emailField);
        getContentPane().add(new JLabel("Location:", SwingConstants.RIGHT));
        getContentPane().add(locationField);

        // Initialize buttons
        addButton = new JButton("Add Course");
        removeButton = new JButton("Remove Course");
        cancelButton = new JButton("Cancel");

        // Add action listeners to buttons
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCourse();
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCourse();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the CoursePage
            }
        });

        // Add buttons to the frame
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 10, 10)); // 1 row, 3 columns with 10px horizontal and vertical gaps
        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(cancelButton);
        getContentPane().add(new JLabel()); // Empty label to take space
        getContentPane().add(buttonPanel); // Add buttons panel to the frame

        setVisible(true);
    }
    
	private void addCourse() {
		String courseCode = courseCodeField.getText();
		String courseName = courseNameField.getText();
		String professor = professorField.getText();
		String email = emailField.getText();
		String location = locationField.getText();

		// Check if any field is empty
		if (courseCode.isEmpty() || courseName.isEmpty() || professor.isEmpty() || email.isEmpty()
				|| location.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Check if the course already exists
		for (Course course : maintainCourses.getCourses()) {
			if (course.getCourseCode().equals(courseCode)) {
				JOptionPane.showMessageDialog(this, "Course with the same course code already exists.", "Error",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		// Add the course to the ArrayList
		Course newCourse = new Course(courseCode, courseName, professor, email, location);
		maintainCourses.getCourses().add(newCourse);

		// Update the CSV file
		try {
			maintainCourses.update();
			JOptionPane.showMessageDialog(this, "Course added successfully.", "Success",
					JOptionPane.INFORMATION_MESSAGE);
			dispose(); // Close the CoursePage after adding the course
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error occurred while updating course data.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void removeCourse() {
		String courseCode = courseCodeField.getText();

		// Check if the course code is empty
		if (courseCode.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please enter a course code.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Find and remove the course from the ArrayList
		boolean found = false;
		for (Course course : maintainCourses.getCourses()) {
			if (course.getCourseCode().equals(courseCode)) {
				maintainCourses.getCourses().remove(course);
				found = true;
				break;
			}
		}

		// Show error if course not found
		if (!found) {
			JOptionPane.showMessageDialog(this, "Course with the provided course code does not exist.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Update the CSV file
		try {
			maintainCourses.update();
			JOptionPane.showMessageDialog(this, "Course removed successfully.", "Success",
					JOptionPane.INFORMATION_MESSAGE);
			dispose(); // Close the CoursePage after removing the course
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error occurred while updating course data.", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
