package database;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import businessLogic.Users.User;
import businessLogic.Users.UserTypes;

public class MaintainUser {
	
    public ArrayList<User> users = new ArrayList<User>();
    public String path;

    public void load(String path) throws Exception {
        this.path = path; // Assigning the path parameter to the class-level variable
        CsvReader reader = new CsvReader(path);
        reader.readHeaders();

        while (reader.readRecord()) {
            User user = new User();
            // name,id,email,password
            user.setName(reader.get("name"));
            user.setId(Integer.valueOf(reader.get("id")));
            user.setEmail(reader.get("email"));
            user.setPW(reader.get("password"));
            user.setUserType(UserTypes.valueOf(reader.get("type"))); // Set user type
            users.add(user);
        }
    }

    public void update(String path) throws Exception {
        try {
            CsvWriter csvOutput = new CsvWriter(new FileWriter(path, false), ',');
            // name,id,email,password,type
            csvOutput.write("name");
            csvOutput.write("id");
            csvOutput.write("email");
            csvOutput.write("password");
            csvOutput.write("type");
            csvOutput.endRecord();

            // else assume that the file already has the correct header line
            // write out a few records
            for (User u : users) {
                csvOutput.write(u.getName());
                csvOutput.write(String.valueOf(u.getId()));
                csvOutput.write(u.getEmail());
                csvOutput.write(u.getPW());
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
        if (usernameExists(newUser.getName())){
        	return "A user with this name already exists.";
        }
        	else {
        		users.add(newUser);
    	        try {
    	            update(path);
    	            return "New user added successfully.";
    	        } 
    	        catch (Exception e) {
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

    public static void main(String[] args) throws Exception {
        String path = "C:\\Users\\keppo\\git\\EECS3311_Team_20_Project\\CSVs\\user.csv";
        MaintainUser maintain = new MaintainUser();

        maintain.load(path);
        for (User u : maintain.users) {
            System.out.println(u.getName());
        }

        // Assuming you have a UserType enum with values: FACULTY, STUDENT, NON_FACULTY
        User newUser = new User("t4", "t4t4", 4, "t4@yorku.ca", UserTypes.FACULTY);
        
        String addResult = maintain.addUser(newUser);
        System.out.println(addResult);
        
        String removeResult = maintain.removeUser("t5@yorku.ca");
        System.out.println(removeResult);
        
        //maintain.update(path);
    }
}
