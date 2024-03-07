package businessLogic.Users;

public class User {
	
	private String name;
	private String pw;
	private String email;
	private Integer id;
	
	//Empty constructor
	public User() {
		this.name = null;
		this.id = null;
		this.email = null;
		this.pw = null;
	}
	
	// basic constructor
	public User(String name, Integer id, String pw, String email) {
		this.name = name;
		this.id = id;
		this.pw = pw;
		this.email = email;
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
}
