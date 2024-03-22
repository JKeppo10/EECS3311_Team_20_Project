package database;

import java.io.IOException;
import java.util.ArrayList;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import java.io.FileWriter;

import businessLogic.Items.*;

public class MaintainInventory {

    private static final String path = "C:\\Users\\keppo\\Documents\\GitHub\\EECS3311_Team_20_Project\\CSVs\\inventory.csv";
    
    public static ArrayList<String[]> loadString() throws IOException {
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
    
    public static ArrayList<Item> load() throws IOException {
        ArrayList<Item> inventory = new ArrayList<>();
        CsvReader reader = new CsvReader(path);
        reader.readHeaders();
        while (reader.readRecord()) {
        	Item item = new Item(reader.get("itemname"), Integer.parseInt(reader.get("itemID")),Integer.parseInt(reader.get("numCopies")), reader.get("location"), Boolean.parseBoolean(reader.get("online")), Boolean.parseBoolean(reader.get("purchasable")));
        	inventory.add(item);
        }
        reader.close();
        return inventory;
    }

    public static void update(ArrayList<Item> inventory) throws IOException {
    	  CsvWriter writer = new CsvWriter(new FileWriter(path), ',');

    	  // Write the header row
    	  writer.writeRecord(new String[] { "itemname", "itemID", "numCopies", "location", "online", "purchasable" });

    	  // Write each item's attributes to a CSV record
    	  for (Item item : inventory) {
    	    String[] itemAttributes = new String[] {
    	      item.getName(),
    	      String.valueOf(item.getUniqueId()),
    	      String.valueOf(item.getNumCopies()),
    	      item.getLocation(),
    	      String.valueOf(item.getOnline()),
    	      String.valueOf(item.getPurchasability())
    	    };
    	    writer.writeRecord(itemAttributes);
    	  }

    	  writer.close();
    	}

    public static void addItem(Item newItem) throws IOException {
    	  ArrayList<Item> inventory = load();

    	  // Check for duplicate ID
    	  boolean exists = inventory.stream().anyMatch(i -> i.getUniqueId() == newItem.getUniqueId());
    	  if (exists) {
    	    System.out.println("Item with the same ID already exists. Could not add.");
    	  } else {
    	    inventory.add(newItem);
    	    update(inventory);
    	    System.out.println("Item added successfully.");
    	  }
    	}

    public static void removeItem(Integer itemID) throws IOException {
    	  ArrayList<Item> inventory = load();

    	  // Find and remove the item with the matching ID
    	  boolean removed = inventory.removeIf(i -> i.getUniqueId() == itemID);
    	  if (removed) {
    	    update(inventory);
    	    System.out.println("Item removed successfully.");
    	  } else {
    	    System.out.println("Item with the provided ID does not exist.");
    	  }
    	}

    // test
    public static void main(String[] args) throws IOException {
        // Load inventory
        ArrayList<Item> inventory = load();

        // Print loaded inventory
        for (Item item : inventory) {
        	  System.out.println("Item Name: " + item.getName() + ", Item ID: " + item.getUniqueId());
        	}

        // Add a new item
        addItem(new Item("New Item", 101, 10, "Library", Boolean.parseBoolean("true"), Boolean.parseBoolean("false")));

        // Attempt to add existing item
        addItem(new Item("History of Canada", 1, 20, "1001", Boolean.parseBoolean("true"),Boolean.parseBoolean("true")));

        // Remove an existing item
        removeItem(1);

        // Attempt to remove non-existing item
        removeItem(101);
    }
}

