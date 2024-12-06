package design.patterns.builder;

public class Student {

    private String name;
    private int rollNumber;
    private String email;
    private String ph;

    private Student(Builder builder) {
        this.name = builder.name;
        this.rollNumber = builder.rollNumber;
        this.email = builder.email;
        this.ph = builder.ph;
    }


    static class Builder {
        private String name;
        private int rollNumber;
        private String email;
        private String ph;

        public Builder(String name, int rollNumber) {
            this.name = name;
            this.rollNumber = rollNumber;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setPh(String ph) {
            this.ph = ph;
            return this;
        }

        public Student create() {
            return new Student(this);
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", rollNumber=" + rollNumber +
                ", email='" + email + '\'' +
                ", ph='" + ph + '\'' +
                '}';
    }
}
