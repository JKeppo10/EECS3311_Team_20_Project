package database;

import java.io.IOException;
import java.util.ArrayList;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class MaintainUserItems {

    private static final String path = "C:\\Users\\keppo\\Documents\\GitHub\\EECS3311_Team_20_Project\\CSVs\\useritems.csv";

    public static ArrayList<String[]> load() throws IOException {
        ArrayList<String[]> userItems = new ArrayList<>();
        CsvReader reader = new CsvReader(path);
        reader.readHeaders();
        while (reader.readRecord()) {
        	String[] userItem = { reader.get("userID"), reader.get("itemID"), reader.get("dueDate") };
            userItems.add(userItem);
        }
        reader.close();
        return userItems;
    }

    public static void update(ArrayList<String[]> userItems) throws IOException {
        CsvWriter writer = new CsvWriter(new FileWriter(path), ',');
        writer.writeRecord(new String[] { "userID", "itemID", "dueDate" });
        for (String[] userItem : userItems) {
            writer.writeRecord(userItem);
        }
        writer.close();
    }

    public static void addUserItem(String userID, String itemID, String dueDate) throws IOException {
        ArrayList<String[]> userItems = load();
        boolean exists = userItems.stream().anyMatch(ui -> ui[0].equals(userID) && ui[1].equals(itemID));
        if (exists) {
            System.out.println("User-item connection already exists.");
        } else {
            userItems.add(new String[] { userID, itemID, dueDate });
            update(userItems);
            System.out.println("User-item connection added successfully.");
        }
    }

    public static void removeUserItem(String userID, String itemID) throws IOException {
        ArrayList<String[]> userItems = load();
        boolean exists = userItems.removeIf(ui -> ui[0].equals(userID) && ui[1].equals(itemID));
        if (exists) {
            update(userItems);
            System.out.println("User-item connection removed successfully.");
        } else {
            System.out.println("User-item connection does not exist.");
        }
    }
    
    public static boolean alreadyRented(Integer userId, int itemId) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            // Skip the header line
            reader.readLine(); // Assuming only one header line

            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(","); // Assuming CSV uses a comma delimiter
                if (values.length >= 2) {
                    int parsedUserId = Integer.parseInt(values[0]);
                    int parsedItemId = Integer.parseInt(values[1]);
                    if (parsedUserId == userId && parsedItemId == itemId) {
                        return true; // Found a matching entry
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();  // Handle potential file reading errors
        }
        return false; // No matching entry found
    }
    
    // Example usage
    public static void main(String[] args) throws IOException {
        // Load user-item connections
        ArrayList<String[]> userItems = load();

        // Print loaded connections
        for (String[] userItem : userItems) {
            System.out.println("User ID: " + userItem[0] + ", Item ID: " + userItem[1]);
        }

        // Add a new user-item connection
        addUserItem("4", "101", "01/01/2024");

        // Attempt to add existing user-item connection
        addUserItem("1", "101", "02/02/2024");

        // Remove an existing user-item connection
        removeUserItem("1", "101");

        // Attempt to remove non-existing user-item connection
        removeUserItem("5", "105");
    }


}

