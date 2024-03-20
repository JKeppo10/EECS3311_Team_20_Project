package businessLogic.Users;

public class Guest extends User {
    private Guest(String name, String pw, Integer id, String email, UserTypes userType) {
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

        public Guest build() {
            // Assuming UserTypes.GUEST is defined
            return new Guest(name, pw, id, email, UserTypes.GUEST);
        }
    }
}
