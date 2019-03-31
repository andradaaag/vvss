package console;

import service.Service;

import java.util.Scanner;

public class UI {
    private Service service;

    public UI(Service service) {
        this.service = service;
    }

    private void printMenu() {
        System.out.println("11. Show all students.");
        System.out.println("12. Show all assignments.");
        System.out.println("13. Show all grades.");

        System.out.println("21. Add new student.");
        System.out.println("22. Add new assignment.");
        System.out.println("23. Give grade.");

        System.out.println("31. Delete student.");
        System.out.println("32. Delete assignment.");

        System.out.println("4. Edit student details.");

        System.out.println("5. Change deadline for assignment.");

        System.out.println("0. EXIT \n");
    }

    private void printAllStudents() {
        service.getAllStudents().forEach(System.out::println);
    }

    private void printAllAssignments() {
        service.getAllAssignments().forEach(System.out::println);
    }

    private void printAllGrades() {
        service.getAllGrades().forEach(System.out::println);
    }

    private void addStudent() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Student id: ");
        String id = scanner.nextLine();

        System.out.println("Student name: ");
        String name = scanner.nextLine();

        System.out.println("Student group: ");
        int group = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Student email: ");
        String email = scanner.nextLine();

        System.out.println("Student professor: ");
        String professor = scanner.nextLine();

        if (service.saveStudent(id, name, group, email, professor)) {
            System.out.println("Student added successfully \n");
        } else {
            System.out.println("Student invalid or duplicated \n");
        }
    }

    private void addAssignment() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Assignment ID: ");
        String id = scanner.nextLine();

        System.out.println("Assignment description: ");
        String description = scanner.nextLine();

        System.out.println("Assignment deadline week: ");
        int deadline = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Assignment startline week: ");
        int startline = scanner.nextInt();
        scanner.nextLine();

        if (service.saveAssignment(id, description, deadline, startline)) {
            System.out.println("Assignment added successfully \n");
        } else {
            System.out.println("Assignment invalid or duplicated! \n");
        }
    }

    private void giveGrade() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Student id: ");
        String studentId = scanner.nextLine();

        System.out.println("Assignment id: ");
        String assignmentId = scanner.nextLine();

        System.out.println("Grade value: ");
        double gradeValue = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Assignment delivery week");
        int delivery = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Feedback: ");
        String feedback = scanner.nextLine();

        if (service.saveGrade(studentId, assignmentId, gradeValue, delivery, feedback)) {
            System.out.println("Grade added successfully \n");
        } else {
            System.out.println("Grade invalid or existent \n");
        }

    }

    private void deleteStudent() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Student id: ");
        String id = scanner.nextLine();

        if (service.deleteStudent(id)) {
            System.out.println("Student deleted successfully \n");
        } else {
            System.out.println("Student does not exist \n");
        }
    }

    private void deleteAssignment() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Assignment id: ");
        String id = scanner.nextLine();

        if (service.deleteAssignment(id)) {
            System.out.println("Assignment deleted successfully \n");
        } else {
            System.out.println("Assignment does not exist \n");
        }
    }

    private void updateStudent() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Student id: ");
        String id = scanner.nextLine();

        System.out.println("New student name: ");
        String newName = scanner.nextLine();

        System.out.println("New student group: ");
        int newGroup = scanner.nextInt();
        scanner.nextLine();

        System.out.println("New student email: ");
        String newEmail = scanner.nextLine();

        System.out.println("New student professor: ");
        String newProfessor = scanner.nextLine();

        System.out.println("Keep subscription? (True/False): ");
        Boolean subscribed = scanner.nextBoolean();

        if (service.updateStudent(id, newName, newGroup, newEmail, newProfessor, subscribed)) {
            System.out.println("Student updated successfully \n");
        } else {
            System.out.println("Student does not exist \n");
        }
    }

    private void extendDeadline() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Assignment id: ");
        String id = scanner.nextLine();

        System.out.println("Number of weeks delayed: ");
        int noWeeks = scanner.nextInt();

        if (service.extendDeadline(id, noWeeks)) {
            System.out.println("Deadline delayed successfully \n");
        } else {
            System.out.println("Assignment does not exist \n");
        }
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        int cmd = -1;

        while (cmd != 0) {
            printMenu();
            System.out.println("Insert option: ");
            String input = scanner.nextLine();
            cmd = Integer.parseInt(input);
            try {
                switch (cmd) {
                    case 11:
                        printAllStudents();
                        break;
                    case 12:
                        printAllAssignments();
                        break;
                    case 13:
                        printAllGrades();
                        break;
                    case 21:
                        addStudent();
                        break;
                    case 22:
                        addAssignment();
                        break;
                    case 23:
                        giveGrade();
                        break;
                    case 31:
                        deleteStudent();
                        break;
                    case 32:
                        deleteAssignment();
                        break;
                    case 4:
                        updateStudent();
                        break;
                    case 5:
                        extendDeadline();
                        break;
                }
            } catch (RuntimeException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
