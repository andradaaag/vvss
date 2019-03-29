package repository;

import domain.Assignment;
import exception.FileException;
import exception.InputException;
import validation.Validator;

import java.io.*;
import java.util.Scanner;

public class AssignmentFileRepo extends AssignmentRepo {
    private String filename;

    public AssignmentFileRepo(Validator<Assignment> validator, String filename) throws FileNotFoundException {
        super(validator);
        this.filename = filename;
        loadFromFile();
    }

    private Assignment readAssignment(Scanner scanner) {
        String assignmentIdLine = scanner.nextLine();
        String descriptionLine = scanner.nextLine();
        String startlineLine = scanner.nextLine();
        String deadlineLine = scanner.nextLine();

        validateInput(assignmentIdLine, descriptionLine, startlineLine, deadlineLine);

        return Assignment.builder().assignmentId(assignmentIdLine.substring(assignmentIdLine.indexOf(':') + 1))
                .description(descriptionLine.substring(descriptionLine.indexOf(':') + 1))
                .startline(Integer.parseInt(startlineLine.substring(startlineLine.indexOf(':') + 1)))
                .deadline(Integer.parseInt(deadlineLine.substring(deadlineLine.indexOf(':') + 1)))
                .build();

    }

    private void validateInput(String id, String description, String startline, String deadline) {
        if (!id.contains("assignmentId:")) {
            throw new InputException();
        }
        if (!description.contains("description:")) {
            throw new InputException();
        }
        if (!startline.contains("startline:")) {
            throw new InputException();
        }
        if (!deadline.contains("deadline:")) {
            throw new InputException();
        }
    }

    private void loadFromFile() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNext()) {
            Assignment student = readAssignment(scanner);
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
    public boolean save(Assignment entity) {
        if (super.save(entity.getID(), entity)) {
            writeToFile();
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Assignment entity) {
        if (super.update(entity.getID(), entity)) {
            writeToFile();
            return true;
        }
        return false;
    }

    private void writeToFile() {
        StringBuilder builder = new StringBuilder();

        for (Assignment assignment : findAll()) {
            writeAssignmentToFile(assignment, builder);
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

    private void writeAssignmentToFile(Assignment assignment, StringBuilder writer) {
        writer.append("assignmentId:").append(assignment.getID())
                .append("\ndescription:").append(assignment.getDescription())
                .append("\nstartline:").append(assignment.getStartline())
                .append("\ndeadline:").append(assignment.getDeadline());
    }
}
