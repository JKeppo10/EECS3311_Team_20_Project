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

public class LandingPage {

    private UserTypes userType;

    public LandingPage(UserTypes userType) {
        this.userType = userType;

        // Initialize the frame
        JFrame frame = new JFrame("Landing Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setResizable(false);

        // Customize LandingPage based on user type
        customizeLandingPage(frame);

        // Set frame visibility
        frame.setVisible(true);
    }

    private void customizeLandingPage(JFrame frame) {
        JPanel panel = new JPanel();
        panel.setLayout(null); // Using absolute layout

        // Add common components
        JLabel welcomeLabel = new JLabel("Welcome to Landing Page");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setBounds(50, 20, 300, 30);
        panel.add(welcomeLabel);

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
        // Add components specific to FACULTY user type
        // Example: JButton, JLabel, etc.
    }

    private void addNonFacultyFeatures(JPanel panel) {
        // Add components specific to NON_FACULTY user type
        // Example: JButton, JLabel, etc.
    }

    private void addStudentFeatures(JPanel panel) {
        // Add components specific to STUDENT user type
        // Example: JButton, JLabel, etc.
    }

    private void addGuestFeatures(JPanel panel) {
        // Add components specific to GUEST user type
        // Example: JButton, JLabel, etc.
    }

    public static void main(String[] args) {
        // Simulate login with user type
        UserTypes userType = UserTypes.FACULTY; // Change to actual user type after login
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LandingPage(userType);
            }
        });
    }
}
