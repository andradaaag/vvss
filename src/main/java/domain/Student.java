package domain;

import java.util.Objects;

public class Student {
    private String studentId;
    private String name;
    private int group;
    private String email;
    private String professor;
    private boolean subscribed;

    public Student() {
    }

    public Student(String studentId, String name, int group, String email, String professor, boolean subscribed) {
        this.studentId = studentId;
        this.name = name;
        this.group = group;
        this.email = email;
        this.professor = professor;
        this.subscribed = subscribed;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public Boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }

    public String getID() {
        return studentId;
    }

    public void setID(String idStudent) {
        this.studentId = idStudent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Student{" +
                "studentId='" + studentId + '\'' +
                ", name='" + name + '\'' +
                ", group=" + group +
                ", email='" + email + '\'' +
                ", professor='" + professor + '\'' +
                ", subscribed=" + subscribed +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studentId, student.studentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId);
    }

    public static StudentBuilder builder() {
        return new StudentBuilder();
    }

    public static class StudentBuilder {
        private Student student = new Student();


        public StudentBuilder idStudent(String idStudent) {
            student.studentId = idStudent;
            return this;
        }

        public StudentBuilder name(String name) {
            student.name = name;
            return this;
        }

        public StudentBuilder group(Integer group) {
            student.group = group;
            return this;
        }

        public StudentBuilder email(String email) {
            student.email = email;
            return this;
        }

        public StudentBuilder professor(String professor) {
            student.professor = professor;
            return this;
        }

        public StudentBuilder subscribed(Boolean subscribed) {
            student.subscribed = subscribed;
            return this;
        }

        public Student build() {
            return student;
        }

    }
}

