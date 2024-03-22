package database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import businessLogic.Users.User;
import businessLogic.Users.UserTypes;

public class MaintainUser {

	public ArrayList<User> users = new ArrayList<User>();
	private static final String path = "Path";
	private int idCounter;
	
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
			User user = new User();
			// name,id,email,password
			user.setName(reader.get("name"));
			user.setPW(reader.get("password"));
			user.setId(Integer.valueOf(reader.get("id")));
			user.setEmail(reader.get("email"));
			user.setUserType(UserTypes.valueOf(reader.get("type"))); // Set user type
			users.add(user);
		}
		initializeIdCounter();
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

	public String addUser(User newUser) {
		if (emailExists(newUser.getEmail())) {
			return "A user with this email already exists.";
		}
		if (usernameExists(newUser.getName())) {
			return "A user with this name already exists.";
		} else {
			// Set the ID for the new user
			newUser.setId(idCounter++);
			users.add(newUser);
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

	private void initializeIdCounter() {
		int maxId = 0;
		for (User user : users) {
			if (user.getId() > maxId) {
				maxId = user.getId();
			}
		}
		idCounter = users.isEmpty() ? 1 : maxId + 1;
	}

	// tests8
	public static void main(String[] args) throws Exception {
		MaintainUser maintain = new MaintainUser();

		maintain.load();
		for (User u : maintain.users) {
			System.out.println(u.getName());
		}

		// Assuming you have a UserType enum with values: FACULTY, STUDENT, NON_FACULTY
		User newUser = new User("t5", "t5t5", "t5@yorku.ca", UserTypes.STUDENT);

		String addResult = maintain.addUser(newUser);
		System.out.println(addResult);

	
		String removeResult = maintain.removeUser("t5@yorku.ca");
		System.out.println(removeResult);

		// maintain.update(path);
	}
}
