package src;
import org.junit.Test;
import static org.junit.Assert.*;

import businessLogic.Users.User;
import businessLogic.Users.Faculty;
import businessLogic.Users.Student;
import businessLogic.Users.Guest;
import businessLogic.Users.NonFaculty;
import businessLogic.Users.UserTypes;
import businessLogic.Users.Faculty.Builder;
public class unitTests{

//unit Tests for Users

  //Faculty Testing
@Test
public void test1(){
  //test accessors
User a1 = new Faculty();
Builder name("FacultyName");
String name1 = a1.getName();
AssertEquals("FacultyName", name1);
}

  @Test
  public void test2(){
   //general object creation for Faculty user; dynamic polymorphism 
  User a1a = new Faculty("Bob", "password1", 1111, "faculty1@yorku.ca", FACULTY, 2);
    //test Factory design pattern implementation
  
  }
@Test
  //test Builder design pattern
public void testFacultyBuilder() {
Faculty faculty = new Faculty.Builder()
.name("John Doe")
.pw("password123")
.email("john.doe@example.com")
.id(12345)
.numRent(5)
.build();

assertEquals("John Doe", faculty.getName());
assertEquals("password123", faculty.getPw());
assertEquals("john.doe@example.com", faculty.getEmail());
assertEquals(12345, faculty.getId());
assertEquals(UserTypes.FACULTY, faculty.getUserType());
assertEquals(5, faculty.getNumRent().intValue());
}


 @Test
  //Test builder for default fill-in values
public void testFacultyMissingVal() {
Faculty faculty = new Faculty.Builder()
.name("Jane Smith")
.pw("password456")
.id(54321)
.build();

assertEquals("Jane Smith", faculty.getName());
assertEquals("password456", faculty.getPw());
assertEquals(null, faculty.getEmail()); // Assuming email can be null
assertEquals(54321, faculty.getId());
assertEquals(UserTypes.FACULTY, faculty.getUserType());
assertEquals(null, faculty.getNumRent()); // Assuming numRent can be null
}
//test the overall Faculty creation and the factory user type. eg. FACULTY enum
@Test
public void testUserType() {
Faculty faculty = new Faculty.Builder()
.name("John Doe")
.pw("password123")
.id(1001)
.email("john.doe@example.com")
.numRent(5)
.build();

assertEquals(UserTypes.FACULTY, faculty.getUserType());
}
//testing creation of multiple Faculty users with unique IDs
  @Test
public void testMultipleFacultyMembers() {
Faculty faculty1 = new Faculty.Builder()
.name("John Doe")
.pw("password123")
.id(1001)
.email("john.doe@example.com")
.numRent(5)
.build();
                                
Faculty faculty2 = new Faculty.Builder()
.name("Jane Doe")
.pw("password456")
.id(1002)
.email("jane.doe@example.com")
.numRent(3)
.build();
assertNotEquals(faculty1.getId(), faculty2.getId());
}

//Error Testing
  @Test
  //test for unexpected numerical values. Eg. negative number of rented items
public void testFacultyRentError() {
new Faculty.Builder()
.name("John Doe")
.pw("password123")
.id(1001)
.email("john.doe@example.com")
.numRent(-5)
.build();
} //should throw IllegalArgumentException

  //testing for if user does not enter anything for email (null is placed instead). Expecting NullPointerException
@Test
public void testNullEmail() {
new Faculty.Builder()
.name("John Doe")
.pw("password123")
.id(1001)
.email(null)
.numRent(5)
.build();
}
  //Student Testing
@Test
public void test3(){
  //test accessors
User a2 = new Student();
Builder name("studentName");
String name2 = a2.getName();
AssertEquals("studentName", name2);
}

@Test
//test Builder design pattern
public void testStudentBuilder() {
Student student = new Student.Builder()
.name("Alice Johnson")
.pw("student123")
.email("alice@example.com")
.id(67890)
.numRent(3)
.build();

assertEquals("Alice Johnson", student.getName());
assertEquals("student123", student.getPw());
assertEquals("alice@example.com", student.getEmail());
assertEquals(67890, student.getId());
assertEquals(UserTypes.STUDENT, student.getUserType());
assertEquals(3, student.getNumRent().intValue());
}

@Test
    //Test builder for default fill-in values
public void testStudentMissingVal() {
Student student = new Student.Builder()
.name("Bob Smith")
.pw("student456")
.id(98765)
.build();

assertEquals("Bob Smith", student.getName());
assertEquals("student456", student.getPw());
assertEquals(null, student.getEmail()); // Assuming email can be null
assertEquals(98765, student.getId());
assertEquals(UserTypes.STUDENT, student.getUserType());
assertEquals(null, student.getNumRent()); // Assuming numRent can be null
}
//Guest Testing
@Test
public void test4(){
  //test accessors
User a3 = new Guest();
Builder name("guestName");
String name3 = a3.getName();
AssertEquals("guestName", name3);
}
    @Test
  //test builder design pattern
public void testGuestBuilder() {
Guest guest = new Guest.Builder()
.name("Alice Johnson")
.pw("guest123")
.email("alice@example.com")
.id(12345)
.numRent(2)
.build();

assertEquals("Alice Johnson", guest.getName());
assertEquals("guest123", guest.getPw());
assertEquals("alice@example.com", guest.getEmail());
assertEquals(12345, guest.getId());
assertEquals(UserTypes.GUEST, guest.getUserType());
assertEquals(2, guest.getNumRent().intValue());
}

@Test
  //for default/missing values
public void testGuestMissingVal() {
Guest guest = new Guest.Builder()
.name("Bob Smith")
.pw("guest456")
.id(54321)
.build();

assertEquals("Bob Smith", guest.getName());
assertEquals("guest456", guest.getPw());
assertEquals(null, guest.getEmail()); // Assuming email can be null
assertEquals(54321, guest.getId());
assertEquals(UserTypes.GUEST, guest.getUserType());
assertEquals(null, guest.getNumRent()); // Assuming numRent can be null
}

  //NonFac Testing
  @Test
public void test5(){
  //test accessors
User a4 = new NonFaculty();
Builder name("nonFacName");
String name4 = a4.getName();
AssertEquals("nonFacName", name4);
}
  
@Test
  //test builder design pattern
public void testNonFacultyBuilder() {
 NonFaculty nonFaculty = new NonFaculty.Builder()
.name("Alice Johnson")
.pw("nonfaculty123")
.email("alice@example.com")
.id(12345)
.numRent(2)
.build();

assertEquals("Alice Johnson", nonFaculty.getName());
assertEquals("nonfaculty123", nonFaculty.getPw());
assertEquals("alice@example.com", nonFaculty.getEmail());
assertEquals(12345, nonFaculty.getId());
assertEquals(UserTypes.NON_FACULTY, nonFaculty.getUserType());
assertEquals(2, nonFaculty.getNumRent().intValue());
}

@Test
  //for missing/default values
public void testNonFacultyMissingVal() {
NonFaculty nonFaculty = new NonFaculty.Builder()
.name("Bob Smith")
.pw("nonfaculty456")
.id(54321)
.build();

assertEquals("Bob Smith", nonFaculty.getName());
assertEquals("nonfaculty456", nonFaculty.getPw());
assertEquals(null, nonFaculty.getEmail()); // Assuming email can be null
assertEquals(54321, nonFaculty.getId());
assertEquals(UserTypes.NON_FACULTY, nonFaculty.getUserType());
assertEquals(null, nonFaculty.getNumRent()); // Assuming numRent can be null
}
  
}
