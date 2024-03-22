package presentation;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JPasswordField;
import javax.swing.SwingUtilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.net.URL;
import java.awt.event.ActionEvent;

import database.*;
import businessLogic.Users.*;
import presentation.*;

public class SignUpPage {

	private JPanel signUpPanel;
	private JLabel logoLabel;
	private JTextField userNameField;
	private JPasswordField passwordField;
	private JTextField emailField;
	private JButton signUpButton;
	private JButton logInButton;
	private JComboBox<UserTypes> userTypeComboBox;
	
	private MaintainUser maintainUser;

	public SignUpPage() {
		

        try {
    		//intialize database
            maintainUser = new MaintainUser(); // Initialize MaintainUser instance
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error loading existing user data: " + ex.getMessage());
        }
        
		// Initialize the frame
		JFrame frame = new JFrame("Sign Up Page");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 300);
		frame.setResizable(false);

		// Initialize the signup panel
		signUpPanel = new JPanel();
		signUpPanel.setLayout(null); // Using absolute layout

		// Logo label
		logoLabel = new JLabel("Your Logo");
		logoLabel.setBounds(150, 20, 100, 100);
		signUpPanel.add(logoLabel);

		// Username label and field
		JLabel userNameLabel = new JLabel("Username:");
		userNameLabel.setBounds(50, 120, 80, 25);
		signUpPanel.add(userNameLabel);
		userNameField = new JTextField();
		userNameField.setBounds(140, 120, 200, 25);
		signUpPanel.add(userNameField);

		// Password label and field
		JLabel passwordLabel = new JLabel("Password:");
		passwordLabel.setBounds(50, 150, 80, 25);
		signUpPanel.add(passwordLabel);
		passwordField = new JPasswordField();
		passwordField.setBounds(140, 150, 200, 25);
		signUpPanel.add(passwordField);

		// Email label and field
		JLabel emailLabel = new JLabel("Email:");
		emailLabel.setBounds(50, 180, 80, 25);
		signUpPanel.add(emailLabel);
		emailField = new JTextField();
		emailField.setBounds(140, 180, 200, 25);
		signUpPanel.add(emailField);

		// User type label and dropdown menu
		JLabel userTypeLabel = new JLabel("User Type:");
		userTypeLabel.setBounds(50, 210, 80, 25);
		signUpPanel.add(userTypeLabel);
		userTypeComboBox = new JComboBox<>(UserTypes.values());
		userTypeComboBox.setBounds(140, 210, 200, 25);
		signUpPanel.add(userTypeComboBox);

		// Sign up button
		signUpButton = new JButton("Sign Up");
		signUpButton.setBounds(140, 240, 100, 30);
		signUpPanel.add(signUpButton);

		// Log in button
		logInButton = new JButton("Log In");
		logInButton.setBounds(250, 240, 100, 30);
		signUpPanel.add(logInButton);

		// Add signup panel to the frame
		frame.add(signUpPanel);

		// Set frame visibility
		frame.setVisible(true);

		// Action listener for signup button
	    signUpButton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	            String username = userNameField.getText();
	            String password = new String(passwordField.getPassword());
	            String email = emailField.getText();
	            UserTypes userType = (UserTypes) userTypeComboBox.getSelectedItem();

	            // Validate password strength using MaintainUser method
	            if (!maintainUser.isStrongPassword(password)) {
	                JOptionPane.showMessageDialog(null, "Password is not strong enough. It must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
	                return; // Stop signup process
	            }

	            // Check if the email is a university email for Faculty, Student, Non-Faculty types
	            if (userType == UserTypes.FACULTY || userType == UserTypes.STUDENT || userType == UserTypes.NON_FACULTY) {
	                if (!maintainUser.isUniversityEmailAndType(email, userType.toString())) {
	                    JOptionPane.showMessageDialog(null, "You are not registering with an email registered in the university database with the same type.");
	                    return; // Stop signup process
	                }
	            }

	            // Try to add user to database
	            try {
	                String addUserResult = maintainUser.addUser(username, password, email, userType);
	                JOptionPane.showMessageDialog(null, addUserResult);
	                // Close the current signup frame
	                frame.dispose();
	                // Open the login frame
	                new LoginPage();
	            } catch (Exception ex) {
	                JOptionPane.showMessageDialog(null, "Error adding user to the database: " + ex.getMessage());
	            }
	        }
	    });

		// Action listener for login button
		logInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Close the current signup frame
				frame.dispose();
				// Open the login frame
				new LoginPage();
			}
		});
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new SignUpPage();
			}
		});
	}
}
