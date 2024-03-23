package database;

import java.io.IOException;
import java.util.ArrayList;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class MaintainCourseItem {

    private static final String path = "C:\\Users\\keppo\\Documents\\GitHub\\EECS3311_Team_20_Project\\CSVs\\courseitems.csv";

    public static ArrayList<String[]> load() throws IOException {
        ArrayList<String[]> courseItems = new ArrayList<>();
        CsvReader reader = new CsvReader(path);
        reader.readHeaders();
        while (reader.readRecord()) {
            String[] userItem = { reader.get("courseCode"), reader.get("itemID") };
            courseItems.add(userItem);
        }
        reader.close();
        
        // Print loaded data for debugging
        for (String[] item : courseItems) {
            System.out.println("Loaded Course-Item Connection: Course Code: " + item[0] + ", Item ID: " + item[1]);
        }
        
        return courseItems;
    }

    public static void update(ArrayList<String[]> courseItems) throws IOException {
        CsvWriter writer = new CsvWriter(new FileWriter(path), ',');
        writer.writeRecord(new String[] { "courseCode", "itemID"});
        for (String[] courseItem : courseItems) {
            writer.writeRecord(courseItem);
        }
        writer.close();
    }

    public static void addCourseItem(String courseCode, String itemID) throws IOException {
        ArrayList<String[]> courseItems = load();
        boolean exists = courseItems.stream().anyMatch(ui -> ui[0].equals(courseCode) && ui[1].equals(itemID));
        if (exists) {
            System.out.println("User-item connection already exists.");
        } else {
            courseItems.add(new String[] { courseCode, itemID });
            update(courseItems);
            System.out.println("User-item connection added successfully.");
        }
    }

    public static void removeCourseItem(String courseCode, String itemID) throws IOException {
        ArrayList<String[]> courseItems = load();
        boolean exists = courseItems.removeIf(ui -> ui[0].equals(courseCode) && ui[1].equals(itemID));
        if (exists) {
            update(courseItems);
            System.out.println("Course-item connection removed successfully.");
        } else {
            System.out.println("Course-item connection does not exist.");
        }
    }
    
    
    // Example usage
    public static void main(String[] args) throws IOException {
        // Load user-item connections
        ArrayList<String[]> courseItems = load();

        // Print loaded connections
        for (String[] courseItem : courseItems) {
            System.out.println("User ID: " + courseItem[0] + ", Item ID: " + courseItem[1]);
        }

        // Add a new user-item connection
        addCourseItem("4", "101");

        // Attempt to remove non-existing user-item connection
        removeCourseItem("4", "101");
    }
}