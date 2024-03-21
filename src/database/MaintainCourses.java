package database;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import businessLogic.Misc.Course;
import businessLogic.Users.User;
import businessLogic.Users.UserTypes;

public class MaintainCourses {
	
    private ArrayList<Course> courses = new ArrayList<>();
    private static final String path = "C:\\Users\\keppo\\Documents\\GitHub\\EECS3311_Team_20_Project\\CSVs\\courses.csv";

    public void load() throws Exception {
        CsvReader reader = new CsvReader(path);
        reader.readHeaders();

        while (reader.readRecord()) {
            String courseCode = reader.get("coursecode");
            String courseName = reader.get("coursename");
            String professor = reader.get("professor");
            String email = reader.get("email"); 
            String location = reader.get("location");

            Course course = new Course(courseCode, courseName, professor, email, location);
            courses.add(course);
        }
    }
    
    public void update() throws Exception {
        CsvWriter writer = null;
        try {
            writer = new CsvWriter(new FileWriter(path), ','); // Set append mode to false

            // Write headers
            writer.writeRecord(new String[]{"coursecode", "coursename", "professor", "email", "location"});

            // Write course data
            for (Course course : courses) {
                writer.write(course.getCourseCode());
                writer.write(course.getCourseName());
                writer.write(course.getProfessor());
                writer.write(course.getEmail()); // Ensure email field is written
                writer.write(course.getLocation());
                writer.endRecord();
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
    
    public ArrayList<Course> getCourses(){
    	return courses;
    }
    
    public String addCourse(Course newCourse) throws Exception {
        // Check if the course code already exists
        boolean courseExists = courses.stream().anyMatch(course -> course.getCourseCode().equals(newCourse.getCourseCode()));
        if (!courseExists) {
            courses.add(newCourse);
            update();
            return "Course added successfully.";
        } else {
        	return "Course with the same course code already exists. Could not add.";
        }
    }

    public String removeCourse(String courseCode) throws Exception {
        // Check if the course exists
        Course courseToRemove = null;
        for (Course course : courses) {
            if (course.getCourseCode().equals(courseCode)) {
                courseToRemove = course;
                break;
            }
        }

        if (courseToRemove != null) {
            courses.remove(courseToRemove);
            update(); // Update the CSV file
            return "Course removed successfully.";
        } else {
            return "Course with the provided course code does not exist.";
        }
    }
    
	/*
	 * // tests8 public static void main(String[] args) throws Exception {
	 * MaintainCourses maintain = new MaintainCourses();
	 * 
	 * maintain.load(); for (Course c : maintain.courses) {
	 * System.out.println(c.getCourseName()); }
	 * 
	 * Course newCourse = new Course("a", "b", "c", "d", "e");
	 * 
	 * String addResult = maintain.addCourse(newCourse);
	 * System.out.println(addResult);
	 * 
	 * 
	 * String removeResult = maintain.removeCourse("a");
	 * System.out.println(removeResult);
	 * 
	 * // maintain.update(path); }
	 */
}