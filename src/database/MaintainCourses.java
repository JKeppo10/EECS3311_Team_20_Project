package database;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

import businessLogic.Misc.Course;

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
            writer = new CsvWriter(new FileWriter(path), ',');
            writer.writeRecord(new String[]{"coursecode", "coursename", "professor", "email", "location"});
            for (Course course : courses) {
                writer.write(course.getCourseCode());
                writer.write(course.getCourseName());
                writer.write(course.getProfessor());
                writer.write(course.getEmail());
                writer.write(course.getLocation());
                writer.endRecord();
            }
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
    
    public void addCourse(Course newCourse) throws Exception {
        // Check if the course code already exists
        boolean courseExists = courses.stream().anyMatch(course -> course.getCourseCode().equals(newCourse.getCourseCode()));
        if (!courseExists) {
            courses.add(newCourse);
            update();
            System.out.println("Course added successfully.");
        } else {
            System.out.println("Course with the same course code already exists. Could not add.");
        }
    }

    public void removeCourse(String courseCode) throws Exception {
        // Check if the course exists
        boolean courseFound = courses.removeIf(course -> course.getCourseCode().equals(courseCode));
        if (courseFound) {
            update();
            System.out.println("Course removed successfully.");
        } else {
            System.out.println("Course with the provided course code does not exist. Could not remove.");
        }
    }
}
