package businessLogic.Users;

public class User {
    private String name;
    private String pw;
    private String email;
    private Integer id;
    private UserTypes userType;

    // Empty constructor
    public User() {
    	
    }

    public User(String name, String pw, String email, UserTypes userType) {
        this.name = name;
        this.pw = pw;
        this.email = email;
        this.userType = userType;
    }

    // Basic constructor
    public User(String name, String pw, Integer id, String email, UserTypes userType) {
        this.name = name;
        this.id = id;
        this.pw = pw;
        this.email = email;
        this.userType = userType;
    }

    // Mutators and accessors
    public String getName() {
        return name;
    }

    public String getPW() {
        return pw;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPW(String pw) {
        this.pw = pw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserTypes getUserType() {
        return userType;
    }

    public void setUserType(UserTypes userType) {
        this.userType = userType;
    }
}
