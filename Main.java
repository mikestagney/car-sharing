package carsharing;

import java.util.List;
import java.util.Scanner;

public class Main {
    static CarDAO dao;
    static Scanner input;

    public static void main(String[] args) {
        input = new Scanner(System.in);
        String filename = "test";
        for (int i = 0; i < args.length; i++) {
            if ("-databaseFileName".equals(args[i]) && i + 1 < args.length) {
                filename = args[i + 1];
            }
        }
        dao = new H2CarCompDAO(filename);
        mainMenu();
    }
    public static void mainMenu() {
        while (true) {
            System.out.println("1. Log in as a manager");
            System.out.println("0. Exit");
            String choice = input.nextLine();

            switch (choice.charAt(0)) {
                case ('1'):
                    managerMenu();
                    break;
                case ('0'):
                    System.exit(0);
                    break;
                default:
                    System.out.println("Not a valid option");
                    break;
            }
        }
    }
    public static void managerMenu() {
        while (true) {
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("0. Back");
            String choice = input.nextLine();
            switch (choice.charAt(0)) {
                case ('1'):
                    listAllCompanies();
                    break;
                case ('2'):
                    addCarCompany();
                    break;
                case ('0'):
                    return;
                default:
                    System.out.println("Not a valid option");
                    break;
            }
        }
    }
    public static void listAllCompanies() {
        List<CarCompany> companies = dao.getAllCompanies();

        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            System.out.println("Company list:");
            companies.forEach(com -> System.out.printf("%d. %s \n", com.getId(), com.getName()));
        }
        System.out.println();
    }
    public static void addCarCompany() {
        System.out.println("Enter the company name:");
        String choice = input.nextLine();
        dao.addCompany(choice);
        System.out.println("The company was created!");
        System.out.println();
    }
}