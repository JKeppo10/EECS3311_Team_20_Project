package presentation;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;

import database.*;
import presentation.*;
import businessLogic.Users.*;
import businessLogic.Items.*;
import businessLogic.Misc.*;

public class LandingPage {

	private UserTypes userType;
	private User currentUser;
	private ArrayList<Course> allCourses;

	public LandingPage(User user) {
		this.currentUser = user;
		this.userType = user.getUserType();

		// Initialize the frame
		JFrame frame = new JFrame("Landing Page");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 900);
		frame.setResizable(false);
		
		populateCourseList();
		
		// Customize LandingPage based on user type
		customizeLandingPage(frame, currentUser);
		

		// Set frame visibility
		frame.setVisible(true);
	}
	
	private void populateCourseList() {
	    // Create an instance of MaintainCourses
	    MaintainCourses maintainCourses = new MaintainCourses();

	    // Load the courses
	    try {
	        maintainCourses.load();
	        // Assuming getCourses() returns the list of courses
	        allCourses = maintainCourses.getCourses();
	    } catch (Exception e) {
	        // Handle the exception appropriately
	        e.printStackTrace();
	    }
	}

	private void customizeLandingPage(JFrame frame, User currentUser) {
	    JPanel panel = new JPanel();
	    panel.setLayout(null); // Using absolute layout

	    // Add common components
	    JLabel welcomeLabel = new JLabel("Welcome to Landing Page");
	    welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
	    welcomeLabel.setBounds(50, 20, 300, 30);
	    panel.add(welcomeLabel);

	    //user's account info
	    JLabel currentUserInfoLabel = new JLabel("Your Account Information");
	    currentUserInfoLabel.setBounds(100, 40, 160, 25);
	    panel.add(currentUserInfoLabel);
	    
	    // Username label and field
	    JLabel usernameLabel = new JLabel("Username: ");
	    usernameLabel.setBounds(10, 60, 80, 25);
	    panel.add(usernameLabel);
	    
	    JLabel currentUsernameLabel = new JLabel(currentUser.getName());
	    currentUsernameLabel.setBounds(100, 60, 160, 25);
	    panel.add(currentUsernameLabel);

	    // Password label and field
	    JLabel passwordLabel = new JLabel("Password: ");
	    passwordLabel.setBounds(10, 90, 80, 25);
	    panel.add(passwordLabel);

	    JPasswordField passwordField = new JPasswordField(currentUser.getPW());
	    passwordField.setBounds(100, 90, 130, 25);
	    passwordField.setEditable(false);
	    panel.add(passwordField);

	    // Toggle button for password visibility
	    JToggleButton showPasswordButton = new JToggleButton("Show");
	    showPasswordButton.setBounds(235, 90, 70, 25);
	    panel.add(showPasswordButton);
	    showPasswordButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            if (showPasswordButton.isSelected()) {
	                passwordField.setEchoChar((char) 0); // Password will be visible
	                showPasswordButton.setText("Hide");
	            } else {
	                passwordField.setEchoChar('•'); // Password will be masked
	                showPasswordButton.setText("Show");
	            }
	        }
	    });

	    // Email label
	    JLabel emailLabel = new JLabel("Email: ");
	    emailLabel.setBounds(10, 120, 80, 25);
	    panel.add(emailLabel);
	    
	    // User's email label
	    JLabel userEmailLabel = new JLabel(currentUser.getEmail());
	    userEmailLabel.setBounds(100, 120, 160, 25);
	    panel.add(userEmailLabel);

	    // ID label and field
	    JLabel idLabel = new JLabel("ID: ");
	    idLabel.setBounds(10, 150, 80, 25);
	    panel.add(idLabel);

	    JLabel userIdLabel = new JLabel(String.valueOf(currentUser.getId()));
	    userIdLabel.setBounds(100, 150, 160, 25);
	    panel.add(userIdLabel);
	    
	    JLabel userTypeLabel = new JLabel("Type: ");
	    userTypeLabel.setBounds(10, 180, 80, 25);
	    panel.add(userTypeLabel);

	    JLabel currentUserTypeLabel = new JLabel(currentUser.getUserType().toString());
	    currentUserTypeLabel.setBounds(100, 180, 160, 25);
	    panel.add(currentUserTypeLabel);
	    
	    // Sign out button
	    JButton signOutButton = new JButton("Sign Out");
	    signOutButton.setBounds((frame.getWidth() - 100) / 2, frame.getHeight() - 80, 100, 30);
	    panel.add(signOutButton);
	    
	    // Add action listener to the signout button
	    signOutButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // Open the LoginPage
	            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
	            frame.dispose(); // Close the LandingPage
	            new LoginPage(); // Open the LoginPage
	        }
	    });
	    
	    // Add item search label
	    JLabel itemSearchLabel = new JLabel("Item Search:");
	    itemSearchLabel.setBounds(10, 210, 100, 25);
	    panel.add(itemSearchLabel);
	    
	    // Add search bar for inventory
        JTextField searchBar = new JTextField();
        searchBar.setBounds(100, 220, 200, 25);
        panel.add(searchBar);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(320, 220, 80, 25);
        panel.add(searchButton);
        
        // Add label for selected item
        JLabel selectedItemLabel = new JLabel("Selected Item:");
        selectedItemLabel.setBounds(100, 240, 200, 25);
        panel.add(selectedItemLabel);

        JTextArea searchResultTextArea = new JTextArea();
        searchResultTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(searchResultTextArea);
        scrollPane.setBounds(100, 260, 500, 200);
        panel.add(scrollPane);

        // Action listener for the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String itemName = searchBar.getText().trim();
                if (!itemName.isEmpty()) {
                    try {
                        ArrayList<String[]> searchResults = MaintainInventory.load();
                        ArrayList<String[]> matchingItems = new ArrayList<>();
                        for (String[] item : searchResults) {
                            if (item[0].toLowerCase().contains(itemName.toLowerCase())) {
                                matchingItems.add(item);
                            }
                        }
                        // Debugging line to print matching items
                        System.out.println("Matching Items: " + matchingItems);
                        
                        if (!matchingItems.isEmpty()) {
                            // Display selectable search results
                            String[] options = new String[matchingItems.size()];
                            for (int i = 0; i < matchingItems.size(); i++) {
                                options[i] = matchingItems.get(i)[0] + " (ID: " + matchingItems.get(i)[1] + ")";
                            }
                            // Debugging line to print selected option from JOptionPane
                            String selectedOption = (String) JOptionPane.showInputDialog(panel, "Select an item:", "Item Search Results", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                            System.out.println("Selected Option: " + selectedOption);
                            
                            if (selectedOption != null) {
                                // Get the selected item name from the selected option
                                String[] parts = selectedOption.split("\\(ID:|\\)");
                                String selectedItemName = parts[0].trim();
                                System.out.println("Selected Item Name: " + selectedItemName);
                                // Debugging output for matchingItems
                                System.out.println("Matching Items:");
                                for (String[] item : matchingItems) {
                                    System.out.println(Arrays.toString(item));
                                }
                                // Find the selected item in the matching items list
                                for (String[] item : matchingItems) {
                                    if (item[0].equals(selectedItemName)) {
                                        // Display all attributes of the selected item in the search result text area
                                        StringBuilder itemInfo = new StringBuilder();
                                        itemInfo.append("Title: ").append(item[0]).append("\n");
                                        itemInfo.append("ID: ").append(item[1]).append("\n");
                                        itemInfo.append("Quantity Available: ").append(item[2]).append("\n");
                                        itemInfo.append("Location: ").append(item[3]).append("\n");
                                        itemInfo.append("Available Online: ").append(item[4]).append("\n");
                                        itemInfo.append("Available for Purchase: ").append(item[5]).append("\n");
                                        searchResultTextArea.setText(itemInfo.toString());
                                        break;
                                    }
                                }
                            }
                            
                        } else {
                            // Item not found
                            JOptionPane.showMessageDialog(panel, "No matching items found.", "Item Search", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(panel, "Error loading inventory.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(panel, "Please enter an item name.", "Item Search", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
        // Add button to rent selected item
        JButton rentButton = new JButton("Rent Selected Item");
        rentButton.setBounds(420, 220, 150, 25);
        panel.add(rentButton);
        
     // Action listener for the rent button
        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected item name from the search result text area
                String selectedItemInfo = searchResultTextArea.getText();
                if (!selectedItemInfo.isEmpty()) {
                    // Extract item ID from selected item info
                    String[] lines = selectedItemInfo.split("\n");
                    String itemID = lines[1].split(": ")[1];

                    // Update UserItems.csv
                    try {
                        MaintainUserItems.addUserItem(currentUser.getId(), Integer.parseInt(itemID));
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(panel, "Error renting item.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    // Update the number of copies of the selected item
                    try {
                        MaintainInventory.(Integer.parseInt(itemID), -1);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(panel, "Error updating number of copies.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    // Notify the user
                    JOptionPane.showMessageDialog(panel, "Item rented successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(panel, "Please select an item to rent.", "Rent Item", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        
	    // Add components based on user type
	    switch (userType) {
	        case FACULTY:
	            addFacultyFeatures(panel);
	            break;
	        case NON_FACULTY:
	            addNonFacultyFeatures(panel);
	            break;
	        case STUDENT:
	            addStudentFeatures(panel);
	            break;
	        case GUEST:
	            addGuestFeatures(panel);
	            break;
	    }

	    // Add panel to frame
	    frame.add(panel);
	}

	private void addFacultyFeatures(JPanel panel) {

		// Faculty features label and field
		JLabel usernameLabel = new JLabel("Features");
		usernameLabel.setBounds(460, 40, 80, 25);
		panel.add(usernameLabel);
		// Create a button to open the CoursePage
		JButton courseButton = new JButton("Manage Courses");
		courseButton.setBounds(400, 60, 160, 30);
		panel.add(courseButton);

		// Add action listener to the course button
		courseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openCoursePage();
			}
		});
	}

	// Method to open the CoursePage
	private void openCoursePage() {
		new CoursePage();
	}

	private void addNonFacultyFeatures(JPanel panel) {

	}

	private void addStudentFeatures(JPanel panel) {
	    // Student features label and combo box for courses
	    JLabel coursesLabel = new JLabel("Current Courses:");
	    coursesLabel.setBounds(460, 40, 120, 25);
	    panel.add(coursesLabel);

	    // Get the list of course IDs connected to the current user
	    ArrayList<String[]> userCourseConnections;
	    try {
	        userCourseConnections = MaintainUserCourse.load();
	    } catch (IOException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(panel, "Error loading user-course connections.", "Error", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    // Filter the list of all courses to include only those connected to the current user
	    ArrayList<Course> connectedCourses = new ArrayList<>();
	    for (String[] connection : userCourseConnections) {
	        if (connection[0].equals(String.valueOf(currentUser.getId()))) {
	            // Find the course by ID
	            String courseId = connection[1];
	            for (Course course : allCourses) {
	                if (course.getCourseCode().equals(courseId)) {
	                    connectedCourses.add(course);
	                    break;
	                }
	            }
	        }
	    }

	    // Populate the combo box with course names
	    JComboBox<String> courseComboBox = new JComboBox<>();
	    for (Course course : connectedCourses) {
	        courseComboBox.addItem(course.getCourseName());
	    }
	    courseComboBox.setBounds(400, 60, 160, 25);
	    panel.add(courseComboBox);

	    // Button to get course information
	    JButton getInfoButton = new JButton("Get Course Info");
	    getInfoButton.setBounds(400, 100, 160, 30);
	    panel.add(getInfoButton);

	    // Text area to display course information
	    JTextArea courseInfoTextArea = new JTextArea();
	    courseInfoTextArea.setEditable(false);
	    JScrollPane scrollPane = new JScrollPane(courseInfoTextArea);
	    scrollPane.setBounds(600, 60, 400, 100);
	    panel.add(scrollPane);

	    // Action listener for the get info button
	    getInfoButton.addActionListener(new ActionListener() {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            // Get the selected course
	            String selectedCourseName = (String) courseComboBox.getSelectedItem();
	            if (selectedCourseName != null) {
	                Course selectedCourse = null;
	                // Find the selected course from the list of connected courses
	                for (Course course : connectedCourses) {
	                    if (course.getCourseName().equals(selectedCourseName)) {
	                        selectedCourse = course;
	                        break;
	                    }
	                }
	                // Display course information
	                if (selectedCourse != null) {
	                    StringBuilder courseInfo = new StringBuilder();
	                    courseInfo.append("Course Code: ").append(selectedCourse.getCourseCode()).append("\n");
	                    courseInfo.append("Course Name: ").append(selectedCourse.getCourseName()).append("\n");
	                    courseInfo.append("Professor: ").append(selectedCourse.getProfessor()).append("\n");
	                    courseInfo.append("Email: ").append(selectedCourse.getEmail()).append("\n");
	                    courseInfo.append("Location: ").append(selectedCourse.getLocation()).append("\n");
	                    courseInfoTextArea.setText(courseInfo.toString());
	                } else {
	                    courseInfoTextArea.setText("No information available for selected course.");
	                }
	            } else {
	                courseInfoTextArea.setText("Please select a course.");
	            }
	        }
	    });
	}


	private void addGuestFeatures(JPanel panel) {

	}
}
