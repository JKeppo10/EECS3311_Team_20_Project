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

    public static void main(String[] args) throws Exception {
        String path = "C:\\Users\\keppo\\git\\EECS3311_Team_20_Project\\CSVs\\user.csv";
        MaintainUser maintain = new MaintainUser();

        maintain.load(path);
        for (User u : maintain.users) {
            System.out.println(u.toString());
        }

        // Assuming you have a UserType enum with values: FACULTY, STUDENT, NONFACULTY
        User newUser = new User("t4", "t4t4", 4, "t4@yorku.ca", UserTypes.FACULTY);
        maintain.users.add(newUser);

        maintain.update(path);
    }
}
