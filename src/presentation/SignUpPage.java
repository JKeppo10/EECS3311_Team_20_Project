package presentation;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
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


public class SignUpPage {
	
	//Initialize UI components
	private JPanel signUpPanel;

	private JLabel logoLabel;

	private JTextField userNameField;
	private JPasswordField passwordField;

	private JButton signUpButton;
	private JButton logInButton;
	
	public static void main(String[] args) {
		
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                startGUI();
            }
        });
	}
	
	static void startGUI() {
		
        JFrame frame = new JFrame("Simple GUI"); // Create a frame with a title
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when the window is closed

        JLabel label = new JLabel("test"); // Create a label with text
        frame.getContentPane().add(label); // Add the label to the frame's content pane

        frame.pack(); // Size the frame so that all its contents are at or above their preferred sizes
        frame.setVisible(true); // Display the window
	}
}