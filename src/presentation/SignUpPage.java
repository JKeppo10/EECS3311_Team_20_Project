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


public class SignUpPage {

    private JPanel signUpPanel;
    private JLabel logoLabel;
    private JLabel userNameLabel;
    private JLabel passwordLabel;
    private JLabel emailLabel;
    private JTextField userNameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private JButton signUpButton;
    private JButton logInButton;

    public SignUpPage() {
        JFrame frame = new JFrame("Sign Up Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setResizable(false);

        signUpPanel = new JPanel();
        signUpPanel.setLayout(null);

        logoLabel = new JLabel("Your Logo");
        logoLabel.setBounds(150, 20, 100, 100);
        signUpPanel.add(logoLabel);

        userNameLabel = new JLabel("Username:");
        userNameLabel.setBounds(50, 120, 80, 25);
        signUpPanel.add(userNameLabel);

        userNameField = new JTextField();
        userNameField.setBounds(140, 120, 200, 25);
        signUpPanel.add(userNameField);

        passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 150, 80, 25);
        signUpPanel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(140, 150, 200, 25);
        signUpPanel.add(passwordField);

        emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 180, 80, 25);
        signUpPanel.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(140, 180, 200, 25);
        signUpPanel.add(emailField);

        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(140, 220, 100, 30);
        signUpPanel.add(signUpButton);

        logInButton = new JButton("Log In");
        logInButton.setBounds(250, 220, 100, 30);
        signUpPanel.add(logInButton);

        // Sign up button action listener
        signUpButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userNameField.getText();
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();

                // Check if username or email already exists
                MaintainUser maintainUser = new MaintainUser();
                try {
                    maintainUser.load();
                    for (User user : maintainUser.users) {
                        if (user.getName().equalsIgnoreCase(username) || user.getEmail().equalsIgnoreCase(email)) {
                            JOptionPane.showMessageDialog(null, "Username or Email already in use.");
                            return;
                        }
                    }
                    // If username and email are unique, add the new user
                    User newUser = new User(username, password, email);
                    maintainUser.addUser(newUser);
                    JOptionPane.showMessageDialog(null, "Sign Up Successful!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error loading user data: " + ex.getMessage());
                }
            }
        });

        // Log in button action listener
        logInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Close the current sign up frame and open the login page
                frame.dispose();
                new LoginPage();
            }
        });

        frame.add(signUpPanel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SignUpPage();
            }
        });
    }
}
