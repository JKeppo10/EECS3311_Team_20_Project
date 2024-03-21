package database;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

public class MaintainUserCourse {

    private static final String path = "C:\\Users\\keppo\\Documents\\GitHub\\EECS3311_Team_20_Project\\CSVs\\usercourse.csv";

    public static ArrayList<String[]> load() throws IOException {
        ArrayList<String[]> userCourses = new ArrayList<>();
        CsvReader reader = new CsvReader(path);
        reader.readHeaders();
        while (reader.readRecord()) {
            String[] userCourse = { reader.get("userID"), reader.get("courseCode") };
            userCourses.add(userCourse);
        }
        reader.close();
        return userCourses;
    }

    public static void update(ArrayList<String[]> userCourses) throws IOException {
        CsvWriter writer = new CsvWriter(new FileWriter(path), ',');
        writer.writeRecord(new String[] { "userID", "courseCode" });
        for (String[] userCourse : userCourses) {
            writer.writeRecord(userCourse);
        }
        writer.close();
    }

    public static void addUserCourse(String userID, String courseID) throws IOException {
        ArrayList<String[]> userCourses = load();
        boolean exists = userCourses.stream().anyMatch(uc -> uc[0].equals(userID) && uc[1].equals(courseID));
        if (exists) {
            System.out.println("User-course connection already exists.");
        } else {
            userCourses.add(new String[] { userID, courseID });
            update(userCourses);
            System.out.println("User-course connection added successfully.");
        }
    }

    public static void removeUserCourse(String userID, String courseID) throws IOException {
        ArrayList<String[]> userCourses = load();
        boolean exists = userCourses.removeIf(uc -> uc[0].equals(userID) && uc[1].equals(courseID));
        if (exists) {
            update(userCourses);
            System.out.println("User-course connection removed successfully.");
        } else {
            System.out.println("User-course connection does not exist.");
        }
    }

	
	 // Example usage 
    public static void main(String[] args) throws IOException {
	 // Load user-course connections 
    ArrayList<String[]> userCourses = load();
    }
}
