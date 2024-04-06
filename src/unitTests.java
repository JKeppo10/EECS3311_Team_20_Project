package src;

public class unitTests{

//unit Tests for Users

  //Faculty Testing
@Test
public void test(){
  //test accessors
User a1 = new Faculty();
Builder name(FacultyName);
String name1 = a1.getName();
AssertEquals("FacultyName", name1);
}

  //Student Testing
@Test
public void test(){
  //test accessors
User a2 = new Student();
Builder name(studentName);
String name2 = a2.getName();
AssertEquals("studentName", name2);
}

  //Guest Testing
@Test
public void test(){
  //test accessors
User a3 = new Guest();
Builder name(guestName);
String name3 = a3.getName();
AssertEquals("guestName", name3);
}

  //NonFac Testing
  @Test
public void test(){
  //test accessors
User a4 = new NonFaculty();
Builder name(nonFacName);
String name4 = a4.getName();
AssertEquals("nonFacName", name4);
}
  
}
