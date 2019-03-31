package repository;

import domain.Student;
import exception.FileException;
import exception.InputException;
import validation.Validator;

import java.io.*;
import java.util.Scanner;

public class StudentFileRepo extends StudentRepo {
    private String filename;

    public StudentFileRepo(Validator<Student> validator, String filename) throws FileNotFoundException {
        super(validator);
        this.filename = filename;
        loadFromFile();
    }

    private Student readStudent(Scanner scanner) {
        Student.StudentBuilder studentBuilder = new Student.StudentBuilder();

        String studentIdLine = scanner.nextLine();
        String nameLine = scanner.nextLine();
        String groupLine = scanner.nextLine();
        String emailLine = scanner.nextLine();
        String professorLine = scanner.nextLine();
        String subscribedLine = scanner.nextLine();

        validateInput(studentIdLine, nameLine, groupLine, emailLine, professorLine, subscribedLine);

        return studentBuilder.idStudent(studentIdLine.substring(studentIdLine.indexOf(':') + 1))
                .name(nameLine.substring(nameLine.indexOf(':') + 1))
                .group(Integer.parseInt(groupLine.substring(groupLine.indexOf(':') + 1)))
                .email(emailLine.substring(emailLine.indexOf(':') + 1))
                .professor(professorLine.substring(professorLine.indexOf(':') + 1))
                .subscribed(Boolean.parseBoolean(subscribedLine.substring(subscribedLine.indexOf(':') + 1)))
                .build();

    }

    private void validateInput(String id, String name, String group, String email,
                               String professor, String subscribed) {
        if (!id.contains("studentId:")) {
            throw new InputException();
        }
        if (!name.contains("name:")) {
            throw new InputException();
        }
        if (!group.contains("group:")) {
            throw new InputException();
        }
        if (!email.contains("email:")) {
            throw new InputException();
        }
        if (!professor.contains("professor:")) {
            throw new InputException();
        }
        if (!subscribed.contains("subscribe")) {
            throw new InputException();
        }
    }

    private void loadFromFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNext()) {
            Student student = readStudent(scanner);
            super.save(student.getID(), student);
        }
    }

    @Override
    public boolean delete(String s) {
        if (super.delete(s)) {
            writeToFile();
            return true;
        }
        return false;
    }

    @Override
    public boolean save(Student entity) {
        if (super.save(entity)) {
            writeToFile();
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Student entity) {
        if (super.update(entity)) {
            writeToFile();
            return true;
        }
        return false;
    }

    private void writeToFile() {
        StringBuilder builder = new StringBuilder();

        for (Student student : findAll()) {
            writeStudentToFile(student, builder);
            builder.append("\n");
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(builder.toString().trim());
            writer.close();
        } catch (IOException e) {
            throw new FileException(e);
        }
    }

    private void writeStudentToFile(Student student, StringBuilder writer) {
        writer.append("studentId:").append(student.getID())
                .append("\nname:").append(student.getName())
                .append("\ngroup:").append(student.getGroup().toString())
                .append("\nemail:").append(student.getEmail())
                .append("\nprofessor:").append(student.getProfessor())
                .append("\nsubscribed:").append(student.isSubscribed().toString());
    }
}
