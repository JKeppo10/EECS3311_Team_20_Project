package businessLogic.Items;

import businessLogic.Items.*;

public class Book extends Item {
    private Book(String name, int uniqueId, int numCopies, String location, boolean online, boolean purchasable) {
        super(name, uniqueId, numCopies, location, online, purchasable);
    }

    // Inner builder class
    public static class Builder {
        private String title;
        private int uniqueId;
        private int numCopies;
        private String location;
        private boolean online;
        private boolean purchasable;

        public Builder title(String name) {
            this.title = name;
            return this;
        }

        public Builder uniqueId(int uniqueId) {
            this.uniqueId = uniqueId;
            return this;
        }

        public Builder numCopies(int numCopies) {
            this.numCopies = numCopies;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }
        
        public Builder online(Boolean online) {
        	this.online = online;
        	return this;
        }
        
        public Builder purchasable(Boolean purchasable) {
        	this.purchasable = purchasable;
        	return this;
        }

        public Book build() {
            return new Book(title, uniqueId, numCopies, location, online, purchasable);
        }
    }
}
