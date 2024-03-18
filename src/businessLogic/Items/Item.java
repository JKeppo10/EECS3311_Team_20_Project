package businessLogic.Items;

public class Item {
	private String name;
	private int numCopies;
	private boolean online;
	private int uniqueIdNumber;
	private String location;
	boolean purchasable;
	
	public Item() {
		
	}
	
	public Item(String name, int numCopies, boolean online, int uniqueIdNumber, String location, boolean purchasable) {
		this.name = name;
		this.numCopies = numCopies;
		this.online = online;
		this.uniqueIdNumber = uniqueIdNumber;
		this.location = location;
		this.purchasable = purchasable;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setNumCopies(int numCopies) {
		this.numCopies = numCopies;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	public void setUniqueIdNumber(int uniqueIdNumber) {
		this.uniqueIdNumber = uniqueIdNumber;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public void setPurchasability(boolean purchasable) {
		this.purchasable = purchasable;
	}
	public String getName() {
		return this.name;
	}
	public int getNumCopies() {
		return this.numCopies;
	}
	public boolean getOnline() {
		return this.online;
	}
	public int getUniqueIdNumber() {
		return this.uniqueIdNumber;
	}
	public String getLocation() {
		return this.location;
	}
	public boolean getPurchasability() {
		return this.purchasable;
	}
}
