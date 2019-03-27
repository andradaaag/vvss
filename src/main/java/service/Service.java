package service;

import domain.Assignment;
import domain.Grade;
import domain.Student;
import javafx.util.Pair;
import repository.AssignmentRepo;
import repository.GradeRepo;
import repository.StudentRepo;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Locale;

public class Service {
    private StudentRepo studentRepo;
    private AssignmentRepo assignmentRepo;
    private GradeRepo gradeRepo;

    public Service(StudentRepo studentRepo, AssignmentRepo assignmentRepo, GradeRepo gradeRepo) {
        this.studentRepo = studentRepo;
        this.assignmentRepo = assignmentRepo;
        this.gradeRepo = gradeRepo;
    }

    public Iterable<Student> getAllStudents() {
        return studentRepo.findAll();
    }

    public Iterable<Assignment> getAllAssignments() {
        return assignmentRepo.findAll();
    }

    public Iterable<Grade> getAllGrades() {
        return gradeRepo.findAll();
    }

    public boolean saveStudent(String id, String name, int group, String email, String professor) {
        Student student = new Student(id, name, group, email, professor, true);

        return studentRepo.save(student);
    }

    public boolean saveAssignment(String id, String description, int deadline, int startline) {
        Assignment assignment = new Assignment(id, description, deadline, startline);

        return assignmentRepo.save(assignment);
    }

    public boolean saveGrade(String studentId, String assignmentId, double gradeValue, int delivery, String feedback) {
        if (studentRepo.find(studentId) == null || assignmentRepo.find(assignmentId) == null) {
            throw new RuntimeException("Invalid student or assignment id");
        }
        int deadline = assignmentRepo.find(assignmentId).getDeadline();

        if (delivery - deadline > 2) {
            gradeValue = 1;
        } else if (delivery > deadline){
            gradeValue = gradeValue - 2.5 * (delivery - deadline);
        }
        Grade grade = new Grade(new Pair<>(studentId, assignmentId), gradeValue, delivery,
                deadline, feedback);
        return gradeRepo.addGrade(grade);
    }

    public boolean deleteStudent(String id) {
        return studentRepo.delete(id);
    }

    public boolean deleteAssignment(String id) {
        return assignmentRepo.delete(id);
    }

    public boolean updateStudent(String id, String newName, int newGroup, String newEmail, String newProfessor, Boolean newSubscribed) {
        Student newStudent = new Student(id, newName, newGroup, newEmail, newProfessor, newSubscribed);
        return studentRepo.update(newStudent);
    }

    public boolean updateAssignment(String id, String newDescription, int newDeadline, int newStartLine) {
        Assignment newAssignment = new Assignment(id, newDescription, newDeadline, newStartLine);
        return assignmentRepo.update(newAssignment);
    }

    public boolean extendDeadline(String id, int noWeeks) {
        Assignment assignment = assignmentRepo.find(id);

        if (assignment != null) {
            LocalDate date = LocalDate.now();
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int currentWeek = date.get(weekFields.weekOfWeekBasedYear());

            if (currentWeek >= 39) {
                currentWeek = currentWeek - 39;
            } else {
                currentWeek = currentWeek + 12;
            }

            if (currentWeek <= assignment.getDeadline()) {
                int deadlineNou = assignment.getDeadline() + noWeeks;
                return updateAssignment(assignment.getID(), assignment.getDescription(), deadlineNou, assignment.getStartline());
            }
        }
        return true;
    }
}
