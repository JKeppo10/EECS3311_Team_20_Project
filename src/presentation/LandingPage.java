package presentation;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JButton;
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

public class LandingPage {

    private UserTypes userType;
    private User currentUser;

    public LandingPage(User user) {
    	this.currentUser = user;
        this.userType = user.getUserType();

        // Initialize the frame
        JFrame frame = new JFrame("Landing Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setResizable(false);

        // Customize LandingPage based on user type
        customizeLandingPage(frame, currentUser);

        // Set frame visibility
        frame.setVisible(true);
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

    }

    private void addNonFacultyFeatures(JPanel panel) {

    }

    private void addStudentFeatures(JPanel panel) {

    }

    private void addGuestFeatures(JPanel panel) {

    }

	/* testing
	 * public static void main(String[] args) { SwingUtilities.invokeLater(new
	 * Runnable() { public void run() { new LandingPage(user); } }); }
	 */
}
