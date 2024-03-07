package businessLogic.Users;

public class User {
  private String name;
  private String pw;
//basic constructor
  public User(String name, String pw){
    this.name=name;
    this.pw=pw;
  }
   //mutators and accessors
  public String getName{
    return name;
  }

  public String getPW(){
    return pw;
  }

  public setName(String name){
    this.name=name;
  }

  public setPW(String pw){
    this.pw=pw;
  }

  
}
