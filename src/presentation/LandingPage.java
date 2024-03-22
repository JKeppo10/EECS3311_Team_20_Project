package presentation;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
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
	private ArrayList<Item> inventory;
	private JPanel panel;
	private Integer dueDateWarnings = 0;
	private Integer numRentedItems;
	private JButton rentButton;

	public LandingPage(User user) {
		this.currentUser = user;
		this.userType = user.getUserType();
		
        try {
            MaintainUser maintainUser = new MaintainUser(); // Initialize MaintainUser instance
            MaintainInventory maintainInventory = new MaintainInventory(); //Initialize MaintainInventory instance
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error loading existing user or inventory data: " + ex.getMessage());
        }
        

		// Initialize the frame
		JFrame frame = new JFrame("Landing Page");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 900);
		frame.setResizable(false);

		populateCourseList();
	

		// Customize LandingPage based on user type
		customizeLandingPage(frame, currentUser);
		
		updateRented();

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

	private void updateRented() {
		int currUserID = currentUser.getId();
        ArrayList<String[]> itemIDs = new ArrayList<String[]>();
        ArrayList<String[]> inventory = new ArrayList<String[]>();
        ArrayList<String[]> users = new ArrayList<String[]>();
        try {
             users = MaintainUser.loadString();
			 itemIDs = MaintainUserItems.load();
			 inventory = MaintainInventory.loadString();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
        // Get All Items IDs for currently rented for the corresponding user
        ArrayList<String> currentUserItemsID = new ArrayList<String>();
        ArrayList<String> dueDates = new ArrayList<String>();
        for(int i = 0; i < itemIDs.size(); i++) {
        	if(itemIDs.get(i)[0].equals(Integer.toString(currUserID))) {
        		currentUserItemsID.add(itemIDs.get(i)[1]);
        		dueDates.add(itemIDs.get(i)[2]);
        	}
        }
        // Map All Item IDs to Names (ID, Name)
        HashMap<String, String> inventoryMap = new HashMap<String, String>();
        // Map Current User Items ID to Real Items in Inventory
        for(int i = 0; i < inventory.size(); i++) {
        	inventoryMap.put(inventory.get(i)[1], inventory.get(i)[0]);
        }
        // Use Map To List Current Users Items
        String[] currentlyRented = new String[currentUserItemsID.size()];
       for(int i = 0; i < currentUserItemsID.size(); i++) {
    	   currentlyRented[i] = inventoryMap.get(currentUserItemsID.get(i)) + ", Due: " + dueDates.get(i); // Using /t doesn't work in the GUI for some reason
    	   if(differenceDays(dueDates.get(i)) <= 0) {
    		   currentlyRented[i] += " (DUE DATE WARNING)";
    		   dueDateWarnings++;
    	   }
    	   
       }
       
        JLabel currentlyRentedLabel = new JLabel("Currently Rented: ");
	    currentlyRentedLabel.setBounds(800,200,120,25);
	    panel.add(currentlyRentedLabel);
	    
	    JList<String[]> currentlyRentedList = new JList(currentlyRented);
	    currentlyRentedList.setBounds(730,230,420,500);
	    panel.add(currentlyRentedList);
	    
	    //Penalty
	    JLabel penalty = new JLabel("Penalty:");
	    penalty.setBounds(680,230,125,25);
	    panel.add(penalty);
        
	    double currentBalance = 0;
	    for(int i = 0; i < dueDates.size(); i++) {
	    	if(differenceDays(dueDates.get(i)) < 0) {
	    		currentBalance += 0.5*(-1 * differenceDays(dueDates.get(i)));
	    	}
	    }
	    JLabel userPenalty = new JLabel(String.format("$%,.2f", currentBalance));
	    userPenalty.setBounds(680,240,50,50);
	    panel.add(userPenalty);
	    
	    int numRentedItems = currentUserItemsID.size();

	    // **Number of Rented Items Label**
	    JLabel numRentedLabel = new JLabel("Number of Rented Items: " + numRentedItems + "/10 (Max)");
	    numRentedLabel.setBounds(770, 180, 250, 25);
	    panel.add(numRentedLabel);
	    
	    if (numRentedItems >= 10) {
	        rentButton.setText("Max items rented. Please return an item.");
	        rentButton.setEnabled(false);
	    } else {
	        rentButton.setText("Rent Item");
	        rentButton.setEnabled(true);
	    }
	    
	    numRentedLabel.repaint();
	    currentlyRentedList.repaint();
	    
	    currentUser.setNumRent(currentUserItemsID.size());
	   
		// Update the rent button based on the number of due date warnings
	    if (dueDateWarnings >= 3) {
	        rentButton.setText("Please resolve due date warnings.");
	        rentButton.setEnabled(false);
	    } else if (currentUserItemsID.size() < 10) {
	        rentButton.setText("Rent Item");
	        rentButton.setEnabled(true);
	    } else {
	        rentButton.setText("Max items rented. Please return an item.");
	        rentButton.setEnabled(false);
	    }
	    
	    currentlyRentedList.repaint();
	}
	

	
	private void customizeLandingPage(JFrame frame, User currentUser) {
		this.panel = new JPanel();
		//JPanel panel = new JPanel();
		panel.setLayout(null); // Using absolute layout

		// Add common components
		JLabel welcomeLabel = new JLabel("Welcome to Landing Page");
		welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		welcomeLabel.setBounds(50, 20, 300, 30);
		panel.add(welcomeLabel);

		// user's account info
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
					passwordField.setEchoChar('*'); // Password will be masked
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
						inventory = MaintainInventory.load();
						ArrayList<Item> matchingItems = new ArrayList<>();
						for (Item item : inventory) {
							  if (item.getName().toLowerCase().contains(itemName.toLowerCase())) {
							    matchingItems.add(item);
							  }
							}
						
						// Debugging line to print matching items
						System.out.println("Matching Items: " + matchingItems);

						if (!matchingItems.isEmpty()) {
							// Display selectable search results
							String[] options = new String[matchingItems.size()]; // Create an array to hold options

							for (int i = 0; i < matchingItems.size(); i++) {
							  Item item = matchingItems.get(i);  // Get the Item object from the ArrayList
							  options[i] = item.getName() + " (ID: " + item.getUniqueId() + ")";  // Construct the option string
							}
							
							// Debugging line to print selected option from JOptionPane
							String selectedOption = (String) JOptionPane.showInputDialog(panel, "Select an item:",
									"Item Search Results", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
							System.out.println("Selected Option: " + selectedOption);

							if (selectedOption != null) {
								// Get the selected item name from the selected option
								String[] parts = selectedOption.split("\\(ID:|\\)");
								String selectedItemName = parts[0].trim();
								System.out.println("Selected Item Name: " + selectedItemName);
								// Debugging output for matchingItems
								System.out.println("Matching Items:");
								
								// Print information for each item (optional for debugging)
								// for (Item item : matchingItems) {
							    // System.out.println(item); // Output depends on Item's toString implementation
								// }

								// Find the selected item in the matching items list
								for (Item item : matchingItems) {
								    if (item.getName().equals(selectedItemName)) {
								        // Display all attributes of the selected item in the search result text area
								        StringBuilder itemInfo = new StringBuilder();
								        itemInfo.append("Title: ").append(item.getName()).append("\n");
								        itemInfo.append("ID: ").append(item.getUniqueId()).append("\n");
								        itemInfo.append("Quantity Available: ").append(item.getNumCopies()).append("\n");
								        itemInfo.append("Location: ").append(item.getLocation()).append("\n");
								        itemInfo.append("Available Online: ").append(item.getOnline()).append("\n");
								        itemInfo.append("Available for Purchase: ").append(item.getPurchasability()).append("\n");
								        searchResultTextArea.setText(itemInfo.toString());
								        break;
								    }
								}
							}

						} else {
							// Item not found
							JOptionPane.showMessageDialog(panel, "No matching items found.", "Item Search",
									JOptionPane.INFORMATION_MESSAGE);
						}
					} catch (IOException ex) {
						ex.printStackTrace();
						JOptionPane.showMessageDialog(panel, "Error loading inventory.", "Error",
								JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(panel, "Please enter an item name.", "Item Search",
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		// Add button to rent selected item
		this.rentButton = new JButton("Rent Selected Item");
		rentButton.setBounds(420, 220, 150, 25);
		panel.add(rentButton);

		
		// Action listener for the rent button
		rentButton.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        String selectedItemInfo = searchResultTextArea.getText();

		        if (!selectedItemInfo.isEmpty()) {
		            // Extract item ID from selected item info
		            String[] lines = selectedItemInfo.split("\n");
		            String itemID = lines[1].split(": ")[1]; // Assuming line 1 contains "ID: " followed by the ID

		            // Check if user already rented this item
		            boolean alreadyRented = MaintainUserItems.alreadyRented(currentUser.getId(), Integer.parseInt(itemID));

		            if (!alreadyRented) {
		                try {
		                    // Calculate due date (current date + 30 days)
		                    LocalDate today = LocalDate.now(); // Assuming you have access to LocalDate from java.time
		                    LocalDate dueDate = today.plusDays(30);

		                    MaintainUserItems.addUserItem(String.valueOf(currentUser.getId()), itemID, dueDate.toString()); // Add due date as third argument

		                    JOptionPane.showMessageDialog(panel, "Item rented successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

		                    // Update the currentlyRentedList in the GUI
		                    updateRented();

		                } catch (IOException ex) {
		                    ex.printStackTrace();
		                    JOptionPane.showMessageDialog(panel, "Error renting item.", "Error", JOptionPane.ERROR_MESSAGE);
		                }
		            } else {
		                JOptionPane.showMessageDialog(panel, "You have already rented this item.", "Rent Item", JOptionPane.INFORMATION_MESSAGE);
		            }
		        } else {
		            JOptionPane.showMessageDialog(panel, "Please select an item to rent.", "Rent Item", JOptionPane.INFORMATION_MESSAGE);
		        }
		    }
		});
		
		//Newsletter Button
		JButton newsletter = new JButton("Newsletter Subscriptions");
		newsletter.setBounds(10, 500, 200, 30);
		panel.add(newsletter);
		
		newsletter.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame newsFrame = new JFrame("Newsletter Subcriptions.");
				newsFrame.setVisible(true);
				newsFrame.setSize(500,500);
				
				JButton nyTimes = new JButton("Subscribe To NY Times");
				JButton globeAndMail = new JButton("Subscribe To The Globe And Mail");
				JButton theDailyMail = new JButton("Subscribe To The Daily Mail");
				
				nyTimes.setBounds(100,100,300,50);
				globeAndMail.setBounds(100,200,300,50);
				theDailyMail.setBounds(100,300,300,50);
				
				newsFrame.add(nyTimes);
				newsFrame.add(globeAndMail);
				newsFrame.add(theDailyMail);
				
				nyTimes.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							JEditorPane nyTimesWeb = new JEditorPane("https://www.nytimes.com/ca/");
							nyTimesWeb.setEditable(false);
							JFrame nFrame = new JFrame("NY Times");
							nFrame.add(new JScrollPane(nyTimesWeb));
							nFrame.setSize(600,600);
							nFrame.setVisible(true);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
				
				globeAndMail.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							JEditorPane globeWeb = new JEditorPane("https://www.theglobeandmail.com/canada/toronto/");
							globeWeb.setEditable(false);
							JFrame nFrame = new JFrame("Washington Post");
							nFrame.add(new JScrollPane(globeWeb));
							nFrame.setSize(600,600);
							nFrame.setVisible(true);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
				
				theDailyMail.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							JEditorPane wsWeb = new JEditorPane("https://www.dailymail.co.uk/home/index.html");
							wsWeb.setEditable(false);
							JFrame nFrame = new JFrame("The Daily Mail");
							nFrame.add(new JScrollPane(wsWeb));
							nFrame.setSize(600,600);
							nFrame.setVisible(true);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				});
				newsFrame.getContentPane().setLayout(null);
			}
		});
	// Add components based on user type
	switch(userType)
		{
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
			JOptionPane.showMessageDialog(panel, "Error loading user-course connections.", "Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		// Filter the list of all courses to include only those connected to the current
		// user
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
	private int differenceDays(String dueDate) {
		Date today = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date formatDueDate = new Date();
		try {
			formatDueDate = formatter.parse(dueDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long duration = formatDueDate.getTime() - today.getTime();
		return (int) TimeUnit.DAYS.convert(duration, TimeUnit.MILLISECONDS);
	}
}
