package businessLogic.Users;

public class Faculty extends User {
private int fID;
  //simple constructor with super(), keep private for builder pattern to function correctly
 Faculty(String name, String pw){
    super(name, pw);
    this.fID=fID;
  }


  
}

//Implementing builder pattern
public class FacBuilder{
  private String name;
  private String pw;
  private int fID;

  //builder setters
  public FacBuilder name(String name){
    this.name=name;
  }

  public FacBuilder pw(String pw){
    this.pw=pw;
  }

  public FacBuilder fID(int fID){
    this.fID=fID;
  }

  //build method
  public Faculty build(){
    return new Faculty(name, pw, fID);
  }
  
}
