package businessLogic.Users;

public abstract class User {

	private String name;
	private String pw;
	private String email;
	private Integer id;
	private String type;
	private UserTypes userType;
	private Double balance;
	private Integer numRent;
	public final static Integer maxRent = 10;

	// Empty constructor
	public User() {

	}

	// basic constructor
	public User(String name, String pw, Integer id, String email, UserTypes usertype) {
		this.name = name;
		this.id = id;
		this.pw = pw;
		this.email = email;
		this.userType = usertype;
		this.numRent = 0;
	}

	// mutators and accessors
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

	public void setId(Integer integer) {
		this.id = integer;
	}

	public UserTypes getUserType() {
		return userType;
	}

	public void setUserType(UserTypes userType) {
		this.userType = userType;
	}

	public Integer getNumRent() {
		return numRent;
	}

	public void setNumRent(Integer numRent) {
		this.numRent = numRent;
	}

	public double getBalance() {
		return balance;
	}
	
	public void setBalance(Double balance) {
		this.balance = balance;
	}
}
