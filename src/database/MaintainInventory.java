package database;

import java.io.IOException;
import java.util.ArrayList;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import java.io.FileWriter;

public class MaintainInventory {

    private static final String path = "C:\\Users\\keppo\\Documents\\GitHub\\EECS3311_Team_20_Project\\CSVs\\inventory.csv";

    public static ArrayList<String[]> load() throws IOException {
        ArrayList<String[]> inventory = new ArrayList<>();
        CsvReader reader = new CsvReader(path);
        reader.readHeaders();
        while (reader.readRecord()) {
            String[] item = { reader.get("itemname"), reader.get("itemID"), reader.get("numCopies"), reader.get("location"), reader.get("online"), reader.get("purchasable") };
            inventory.add(item);
        }
        reader.close();
        return inventory;
    }

    public static void update(ArrayList<String[]> inventory) throws IOException {
        CsvWriter writer = new CsvWriter(new FileWriter(path), ',');
        writer.writeRecord(new String[] { "itemname", "itemID", "numCopies", "location", "online", "purchasable" });
        for (String[] item : inventory) {
            writer.writeRecord(item);
        }
        writer.close();
    }

    public static void addItem(String[] newItem) throws IOException {
        ArrayList<String[]> inventory = load();
        boolean exists = inventory.stream().anyMatch(i -> i[1].equals(newItem[1]));
        if (exists) {
            System.out.println("Item with the same ID already exists. Could not add.");
        } else {
            inventory.add(newItem);
            update(inventory);
            System.out.println("Item added successfully.");
        }
    }

    public static void removeItem(String itemID) throws IOException {
        ArrayList<String[]> inventory = load();
        boolean exists = inventory.removeIf(i -> i[1].equals(itemID));
        if (exists) {
            update(inventory);
            System.out.println("Item removed successfully.");
        } else {
            System.out.println("Item with the provided ID does not exist.");
        }
    }

    // Example usage
    public static void main(String[] args) throws IOException {
        // Load inventory
        ArrayList<String[]> inventory = load();

        // Print loaded inventory
        for (String[] item : inventory) {
            System.out.println("Item Name: " + item[0] + ", Item ID: " + item[1]);
        }

        // Add a new item
        addItem(new String[] { "New Item", "101", "10", "Library", "true", "false" });

        // Attempt to add existing item
        addItem(new String[] { "History of Canada", "1", "20", "1001", "true", "true" });

        // Remove an existing item
        removeItem("1");

        // Attempt to remove non-existing item
        removeItem("101");
    }
}

