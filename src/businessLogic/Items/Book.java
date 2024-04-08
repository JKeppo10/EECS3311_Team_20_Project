package businessLogic.Items;

public class Book extends Item {

  private String priority;

  private Book(String name, int uniqueId, int numCopies, String location, boolean online, boolean purchasable, String requestType, String priority) {
    super(name, uniqueId, numCopies, location, online, purchasable, requestType);
    this.priority = priority;
  }

  // Accessor methods (optional)

  public String getPriority() {
    return priority;
  }
  public String getTitle(){
    return name;
  }
  public int getuniqueId(){
    return uniqueId;
  }
  public int getnumCopies(){
    return numCopies;
  }
  public String getlocation(){
    return location;
  }
  public Boolean getonline(){
    return online;
  }
  public Boolean getpurchasable(){
    return purchasable;
  }
  public String getrequestType(){
    return requestType;
  }
  
  // Inner builder class (updated)
  public static class Builder {
    private String title;
    private int uniqueId;
    private int numCopies;
    private String location;
    private boolean online;
    private boolean purchasable;
    private String requestType;
    private String priority;

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

    public Builder requestType(String requestType) {
      this.requestType = requestType;
      return this;
    }

    public Builder priority(String priority) {
      this.priority = priority;
      return this;
    }

    public Book build() {
      return new Book(title, uniqueId, numCopies, location, online, purchasable, requestType, priority);
    }
  }
}