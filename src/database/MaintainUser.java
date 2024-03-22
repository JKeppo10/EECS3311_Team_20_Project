package database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import businessLogic.Users.*;


public class MaintainUser {

	public ArrayList<User> users = new ArrayList<User>();
	private static final String path = "C:\\Users\\keppo\\Documents\\GitHub\\EECS3311_Team_20_Project\\CSVs\\user.csv";
	private static final String path2 = "C:\\Users\\keppo\\Documents\\GitHub\\EECS3311_Team_20_Project\\CSVs\\university.csv";
	private int idCounter;
	
	private UserFactory userFactory;

    private Map<String, String> universityEmailsAndTypes;
	
	public MaintainUser() throws Exception {
		this.userFactory = new UserFactory();
        universityEmailsAndTypes = new HashMap<>();
		load();
	}
	public static ArrayList<String[]> loadString() throws IOException {
        ArrayList<String[]> users = new ArrayList<>();
        CsvReader reader = new CsvReader(path);
        reader.readHeaders();
        while (reader.readRecord()) {
            String[] userItem = {reader.get("name"), reader.get("password"), reader.get("id"), reader.get("email"), reader.get("type"), reader.get("Balance")};
            users.add(userItem);
        }
        reader.close();
        return users;
    }
	public void load() throws Exception {
		CsvReader reader = new CsvReader(path);
		reader.readHeaders();

		int maxId = 0;

        while (reader.readRecord()) {
            String name = reader.get("name");
            String pw = reader.get("password");
            Integer id = Integer.valueOf(reader.get("id"));
            String email = reader.get("email");
            UserTypes userType = UserTypes.valueOf(reader.get("type"));

            // Create user object using UserFactory
            User user = userFactory.createUser(name, pw, id, email, userType);
            if (user != null) {
                users.add(user);
                maxId = Math.max(maxId, id);
            }
        }
        idCounter = maxId + 1;
        loadUniversityEmails();
	}

	public void update(String path) throws Exception {
		try {
			CsvWriter csvOutput = new CsvWriter(new FileWriter(path, false), ',');
			// name,password,id,email,type
			csvOutput.write("name");
			csvOutput.write("password");
			csvOutput.write("id");
			csvOutput.write("email");
			csvOutput.write("type");
			csvOutput.endRecord();

			// Write out the records
			for (User u : users) {
				csvOutput.write(u.getName());
				csvOutput.write(u.getPW());
				csvOutput.write(String.valueOf(u.getId()));
				csvOutput.write(u.getEmail());
				csvOutput.write(u.getUserType().toString()); // Write user type
				csvOutput.endRecord();
			}
			csvOutput.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String addUser(String name, String pw, String email, UserTypes userType) {

	    // Use UserFactory to create a new user
	    UserFactory userFactory = new UserFactory();
	    User newUser = userFactory.createUser(name, pw, idCounter, email, userType);
	    
	    if (newUser == null) {
	        return "Invalid user type provided.";
	    }

	    if (emailExists(newUser.getEmail())) {
	        return "A user with this email already exists.";
	    }
	    if (usernameExists(newUser.getName())) {
	        return "A user with this name already exists.";
	    } else {
	        users.add(newUser);
	        idCounter++;
	        try {
	            update(path);
	            return "New user added successfully.";
	        } catch (Exception e) {
	            e.printStackTrace();
	            return "Error occurred while updating the CSV file.";
	        }
	    }
	}
	

	private boolean usernameExists(String username) {
		for (User user : users) {
			if (user.getName().equalsIgnoreCase(username)) {
				return true;
			}
		}
		return false;
	}

	private boolean emailExists(String email) {
		for (User user : users) {
			if (user.getEmail().equalsIgnoreCase(email)) {
				return true;
			}
		}
		return false;
	}

	public String removeUser(String emailRemove) {
		boolean userFound = false;
		for (User user : users) {
			if (user.getEmail().equalsIgnoreCase(emailRemove)) {
				users.remove(user);
				userFound = true;
				break;
			}
		}
		if (userFound) {
			try {
				update(path);
				return "User removed successfully.";
			} catch (Exception e) {
				e.printStackTrace();
				return "Error occurred while updating the CSV file.";
			}
		} else {
			return "User not found.";
		}
	}
	
    public boolean isStrongPassword(String password) {
        // Define regex patterns for password validation
        String uppercaseRegex = ".*[A-Z].*";
        String lowercaseRegex = ".*[a-z].*";
        String digitRegex = ".*\\d.*";
        String specialCharRegex = ".*[!@#$%^&*()\\[\\]{};:,.<>?~_+-=|].*";

        // Check if password meets all requirements
        return password.matches(uppercaseRegex) &&
               password.matches(lowercaseRegex) &&
               password.matches(digitRegex) &&
               password.matches(specialCharRegex);
    }
    
    private void loadUniversityEmails() {
        try (BufferedReader reader = new BufferedReader(new FileReader(path2))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String email = parts[0].trim(); // Assuming email is in the first column
                    String type = parts[1].trim(); // Assuming type is in the second column
                    universityEmailsAndTypes.put(email.toLowerCase(), type.toUpperCase()); // Store email and type in map, email in lowercase for case-insensitive comparison
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isUniversityEmailAndType(String email, String userType) {
        String registeredType = universityEmailsAndTypes.get(email.toLowerCase());
        return registeredType != null && registeredType.equals(userType.toUpperCase());
    }

	// tests
    public static void main(String[] args) throws Exception {
        MaintainUser maintain = new MaintainUser();
        maintain.load();
        for (User u : maintain.users) {
            System.out.println(u.getName());
        }

        // Assuming you have a UserType enum with values: FACULTY, STUDENT, NON_FACULTY
        String addUserResult = maintain.addUser( "t5", "t5t5", "t5@yorku.ca",UserTypes.STUDENT);
        System.out.println(addUserResult);

        String removeResult = maintain.removeUser("t5@yorku.ca");
        System.out.println(removeResult);
    }
}
