package presentation;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
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

public class LoginPage {

    private JPanel loginPanel;

    private JLabel logoLabel;
    private JLabel userNameLabel;
    private JLabel passwordLabel;
    private JTextField userNameField;
    private JPasswordField passwordField;

    private JButton logInButton;
    private JButton signUpButton;

    public LoginPage() {
    	// Initialize the frame
        JFrame frame = new JFrame("Login Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setResizable(false);

        // Initialize the login panel
        loginPanel = new JPanel();
        loginPanel.setLayout(null); // Using absolute layout

        // Logo label
        logoLabel = new JLabel("Your Logo");
        logoLabel.setBounds(150, 20, 100, 100); // Set bounds (x, y, width, height)
        loginPanel.add(logoLabel);

        // Username label and field
        userNameLabel = new JLabel("Username:");
        userNameLabel.setBounds(50, 120, 80, 25);
        loginPanel.add(userNameLabel);

        userNameField = new JTextField();
        userNameField.setBounds(140, 120, 200, 25);
        loginPanel.add(userNameField);

        // Password label and field
        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 150, 80, 25);
        loginPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(140, 150, 200, 25);
        loginPanel.add(passwordField);

        // Login button
        logInButton = new JButton("Login");
        logInButton.setBounds(140, 190, 100, 30);
        loginPanel.add(logInButton);

        // Sign up button
        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(250, 190, 100, 30);
        loginPanel.add(signUpButton);
        
        // Add signup button listener
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Dispose the current login frame
                JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor((JButton) e.getSource());
                loginFrame.dispose();
                
                // Open the SignUpPage
                new SignUpPage();
            }
        });

        // Add login panel to the frame
        frame.add(loginPanel);

        // Set frame visibility
        frame.setVisible(true);
        
        //check 
        logInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userNameField.getText();
                String password = new String(passwordField.getPassword());

                // Check credentials
                MaintainUser maintainUser = new MaintainUser();
                try {
                    maintainUser.load();
                    for (User user : maintainUser.users) {
                        if (user.getName().equalsIgnoreCase(username) && user.getPW().equals(password)) {
                            JOptionPane.showMessageDialog(null, "Login Successful!");
                            // Open LandingPage upon successful login
                            new LandingPage();
                            // Close the current login frame
                            JFrame loginFrame = (JFrame) SwingUtilities.getWindowAncestor(userNameField);
                            loginFrame.dispose();
                            return;
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Invalid username or password.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error loading user data: " + ex.getMessage());
                }
            }
        });
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginPage();
            }
        });
    }
}
