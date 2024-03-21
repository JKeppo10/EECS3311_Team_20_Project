package businessLogic.Misc;

public class Course { 
	
	private String courseName;
	private String courseCode;
	private String professor;
	private String email;
	private String location;
	
	public Course(String coursecode, String coursename, String professor, String eamil, String locationCode) {
		this.courseCode = coursecode;
		this.courseName = coursename;
		this.professor = professor;
		this.email = email;
		this.location = locationCode;
	}
	
	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public String getProfessor() {
		return professor;
	}

	public void setProfessor(String professor) {
		this.professor = professor;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
	

}
