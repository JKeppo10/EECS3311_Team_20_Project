package businessLogic.Items;

import businessLogic.Users.UserTypes;

public class Item {
	private String name;
	private int numCopies;
	private boolean online;
	private int uniqueId;
	private String location;
	boolean purchasable;

	public Item() {

	}

	public Item(String name, int uniqueId, int numCopies, String location, boolean online, boolean purchasable) {
		this.name = name;
		this.numCopies = numCopies;
		this.online = online;
		this.uniqueId = uniqueId;
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

	public void setUniqueId(int uniqueIdNumber) {
		this.uniqueId = uniqueIdNumber;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public void setPurchasability(boolean purchasable, UserTypes type) throws Exception {
		if(type == UserTypes.FACULTY) {
			this.purchasable = purchasable;
		}
		else {
			throw new Exception("Unauthorized User.");
		}
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

	public int getUniqueId() {
		return this.uniqueId;
	}

	public String getLocation() {
		return this.location;
	}

	public boolean getPurchasability() {
		return this.purchasable;
	}
}
