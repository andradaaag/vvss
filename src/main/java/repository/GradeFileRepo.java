package repository;

import domain.Grade;
import exception.FileException;
import exception.InputException;
import validation.GradeValidator;
import validation.Validator;

import java.io.*;
import java.util.Scanner;

public class GradeFileRepo extends GradeRepo {
    private String filename;

    public GradeFileRepo(Validator<Grade> validator, StudentRepo studentRepo, AssignmentRepo assignmentRepo, String filename) {
        super(validator, studentRepo, assignmentRepo);
        this.filename = filename;
        loadFromFile();
    }

    private Grade readGrade(Scanner scanner) {
        Grade.GradeBuilder gradeBuilder = Grade.builder();

        String studentIdLine = scanner.nextLine();
        String assignmentIdLine = scanner.nextLine();
        String gradeLine = scanner.nextLine();
        String deadlineLine = scanner.nextLine();
        String deliveryLine = scanner.nextLine();
        String feedback = scanner.nextLine();

        validateInput(studentIdLine, assignmentIdLine, gradeLine, deadlineLine, deliveryLine, feedback);

        return gradeBuilder.studentId(studentIdLine.substring(studentIdLine.indexOf(':') + 1))
                .assignmentId(assignmentIdLine.substring(assignmentIdLine.indexOf(':') + 1))
                .grade(Double.parseDouble(gradeLine.substring(gradeLine.indexOf(':')+1)))
                .deadline(Integer.parseInt(deadlineLine.substring(deadlineLine.indexOf(':') + 1)))
                .delivery(Integer.parseInt(deliveryLine.substring(deliveryLine.indexOf(':') + 1)))
                .feedback(feedback.substring(feedback.indexOf(':') + 1))
                .build();

    }

    private void validateInput(String studentId, String assignmentId,String grade, String deadline, String delivery,
                               String feedback) {
        if (!studentId.contains("studentId:")) {
            throw new InputException();
        }
        if (!assignmentId.contains("assignmentId:")) {
            throw new InputException();
        }
        if (!grade.contains("grade:")) {
            throw new InputException();
        }

        if (!deadline.contains("deadline:")) {
            throw new InputException();
        }
        if (!delivery.contains("delivery:")) {
            throw new InputException();
        }
        if (!feedback.contains("feedback:")) {
            throw new InputException();
        }

    }

    private void loadFromFile() {
        try {
            Scanner scanner = new Scanner(new File(filename));
            while (scanner.hasNext()) {
                Grade grade = readGrade(scanner);
                super.addGrade(grade);
            }
        } catch (FileNotFoundException e) {
            throw new FileException(e);
        }
    }

    @Override
    public boolean addGrade(Grade entity) {
        if (super.addGrade(entity)) {
            writeToFile();
            return true;
        }
        return false;
    }

    private void writeToFile() {
        StringBuilder builder = new StringBuilder();

        for (Grade grade : findAll()) {
            writeGradeToFile(grade, builder);
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

    private void writeGradeToFile(Grade grade, StringBuilder writer) {
        writer.append("studentId:").append(grade.getGradeId().getKey())
                .append("\nassignmentId:").append(grade.getGradeId().getValue())
                .append("\ngrade:").append(grade.getGrade())
                .append("\ndeadline:").append(grade.getDeadline())
                .append("\ndelivery:").append(grade.getDelivery())
                .append("\nfeedback:").append(grade.getFeedback());
    }
}
