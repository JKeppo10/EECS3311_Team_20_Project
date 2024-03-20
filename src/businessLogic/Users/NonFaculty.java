package businessLogic.Users;

public class NonFaculty extends User {
    private NonFaculty(String name, String pw, Integer id, String email, UserTypes userType) {
        super(name, pw, id, email, userType);
    }

    // Inner builder class
    public static class Builder {
        private String name;
        private String pw;
        private String email;
        private int id;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder pw(String pw) {
            this.pw = pw;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public NonFaculty build() {
            // Assuming UserTypes.NON_FACULTY is defined
            return new NonFaculty(name, pw, id, email, UserTypes.NON_FACULTY);
        }
    }
}
