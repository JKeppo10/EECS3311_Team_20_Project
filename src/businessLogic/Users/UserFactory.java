package businessLogic.Users;

public class UserFactory {
    public User createUser(String name, String pw, Integer id, String email, UserTypes userType) {
        if (userType == UserType.STUDENT) {
            return new Student.Builder().name(name).pw(pw).id(id).email(email).build();
        } 
        else if (userType == UserType.FACULTY) {
            return new Faculty.Builder().name(name).pw(pw).id(id).email(email).build();
        }
        else if (userType == UserType.NON_FACULTY) { 
             return new NonFaculty.Builder().name(name).pw(pw).id(id).email(email).build();
        } 
        else if (UserType == UserTypes.GUEST) {
            return new Guest.Builder().name(name).pw(pw).id(id).email(email).build();
        }
        else {
            return null; // Or throw an exception if invalid userType is provided
        }
    }
    
}
