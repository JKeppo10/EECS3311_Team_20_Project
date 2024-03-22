package businessLogic.Users;

public class UserFactory {
    public User createUser(String name, String pw, Integer id, String email, UserTypes userType) {
        if (userType == UserTypes.STUDENT) {
            return new Student.Builder().name(name).pw(pw).id(id).email(email).build();
        } 
        else if (userType == UserTypes.FACULTY) {
            return new Faculty.Builder().name(name).pw(pw).id(id).email(email).build();
        }
        else if (userType == UserTypes.NON_FACULTY) { 
             return new NonFaculty.Builder().name(name).pw(pw).id(id).email(email).build();
        } 
        else if (userType == UserTypes.GUEST) {
            return new Guest.Builder().name(name).pw(pw).id(id).email(email).build();
        }
        else {
            return null; // Or throw an exception if invalid userType is provided
        }
    }   
}
