package domain;

import java.util.Objects;

public class Assignment {
    private String assignmentId;
    private String description;
    private int deadline;
    private int startline;

    public Assignment() {
    }

    public Assignment(String assignmentId, String description, int deadline, int startline) {
        this.assignmentId = assignmentId;
        this.description = description;
        this.deadline = deadline;
        this.startline = startline;
    }

    public String getID() {
        return assignmentId;
    }

    public void setID(String assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public int getStartline() {
        return startline;
    }

    public void setStartline(int startline) {
        this.startline = startline;
    }

    @Override
    public String toString() {
        return "Assignment{" +
                "assignmentId='" + assignmentId + '\'' +
                ", description='" + description + '\'' +
                ", deadline=" + deadline +
                ", startline=" + startline +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assignment assignment = (Assignment) o;
        return Objects.equals(assignmentId, assignment.assignmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assignmentId);
    }

    public static AssignmentBuilder builder() {
        return new AssignmentBuilder();
    }

    public static class AssignmentBuilder {
        private Assignment assignment = new Assignment();

        public AssignmentBuilder assignmentId(String assignmentId) {
            assignment.assignmentId = assignmentId;
            return this;
        }

        public AssignmentBuilder description(String description) {
            assignment.description = description;
            return this;
        }

        public AssignmentBuilder deadline(Integer deadline) {
            assignment.deadline = deadline;
            return this;
        }

        public AssignmentBuilder startline(Integer startline) {
            assignment.startline = startline;
            return this;
        }

        public Assignment build() {
            return assignment;
        }
    }
}
