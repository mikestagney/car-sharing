package carsharing;

import java.util.List;
import java.util.Scanner;

public class Main {
    static CarDAO dao;
    static Scanner input;
    static CarCompany company;

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
                    companyMenu();
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
    public static void companyMenu() {
        while(true) {
            List<CarCompany> companies = dao.getAllCompanies();

            if (companies.isEmpty()) {
                System.out.println("The company list is empty!");
                break;
            } else {
                System.out.println("Choose the Company:");
                companies.forEach(com -> System.out.printf("%d. %s \n", com.getId(), com.getName()));
            }
            int selection = Integer.parseInt(input.nextLine());
            company = companies.get(selection - 1);


            System.out.println();
        }
    }
    public static void carMenu() {
        while (true) {
            System.out.printf("'%s' company\n", company.getName());
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("0. Back");
            String choice = input.nextLine();
            switch (choice.charAt(0)) {
                case ('1'):

                    break;
                case ('2'):
                    addCar();
                    break;
                case ('0'):
                    return;
                default:
                    System.out.println("Not a valid option");
                    break;
            }

        }

    }


    public static void addCarCompany() {
        System.out.println("Enter the company name:");
        String choice = input.nextLine();
        dao.addCompany(choice);
        System.out.println("The company was created!");
        System.out.println();
    }
    public static void addCar() {
            System.out.println("Enter the car name:");
            String choice = input.nextLine();
            dao.addCar(choice, company.getId());
            System.out.println("The car was added!");
            System.out.println();
    }
}