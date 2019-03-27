package domain;

import javafx.util.Pair;

import java.util.Objects;

public class Grade {
    private Pair<String, String> gradeId; // student id, assignment id (IN THIS ORDER)
    private double grade;
    private int deadline;
    private int delivery;
    private String feedback;

    public Grade() {
    }

    public Grade(Pair<String, String> gradeId, double grade,int delivery, int deadline, String feedback) {
        this.gradeId = gradeId;
        this.grade = grade;
        this.deadline = deadline;
        this.feedback = feedback;
        this.delivery = delivery;
    }

    public int getDelivery() {
        return delivery;
    }

    public void setDelivery(int delivery) {
        this.delivery = delivery;
    }

    public Pair<String, String> getGradeId() {
        return gradeId;
    }

    public void setGradeId(Pair<String, String> gradeId) {
        this.gradeId = gradeId;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "studentId=" + gradeId.getKey() +
                ", assignmentId=" + gradeId.getValue() +
                ", grade=" + grade +
                ", deadline=" + deadline +
                ", delivery=" + delivery +
                ", feedback='" + feedback + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade = (Grade) o;
        return Objects.equals(gradeId, grade.gradeId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(gradeId);
    }

    public static GradeBuilder builder() {
        return new GradeBuilder();
    }

    public static class GradeBuilder {
        private Grade grade = new Grade();


        public GradeBuilder studentId(String studentId) {
            if (grade.getGradeId() == null) {
                grade.setGradeId(new Pair<>(studentId, null));
                return this;
            }
            grade.setGradeId(new Pair<>(studentId, grade.getGradeId().getValue()));
            return this;
        }

        public GradeBuilder assignmentId(String assignmentId) {
            if (grade.getGradeId() == null) {
                grade.setGradeId(new Pair<>(null, assignmentId));
                return this;
            }
            grade.setGradeId(new Pair<>(grade.getGradeId().getKey(), assignmentId));
            return this;
        }

        public GradeBuilder grade(Double grade) {
            this.grade.setGrade(grade);
            return this;
        }

        public GradeBuilder deadline(Integer deadline) {
            grade.deadline = deadline;
            return this;
        }
        public GradeBuilder delivery(Integer delivery) {
            grade.delivery = delivery;
            return this;
        }

        public GradeBuilder feedback(String  feedback) {
            grade.feedback = feedback;
            return this;
        }

        public Grade build() {
            return grade;
        }

    }
}
