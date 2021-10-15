package carsharing;

import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Main {
    static CarDAO dao;
    static Scanner input;
    static CarCompany company;
    static Customer customer;
    static Car carRented;
    static List<Customer> customersList;
    static boolean loggedInAsManager;

    public static void main(String[] args) {
        input = new Scanner(System.in);
        String filename = "test";
        for (int i = 0; i < args.length; i++) {
            if ("-databaseFileName".equals(args[i]) && i + 1 < args.length) {
                filename = args[i + 1];
            }
        }
        dao = new H2CarCompDAO(filename);
        loggedInAsManager = false;
        mainMenu();
    }
    public static void mainMenu() {
        while (true) {
            System.out.println("1. Log in as a manager");
            System.out.println("2. Log in as a customer");
            System.out.println("3. Create a customer");
            System.out.println("0. Exit");
            String choice = input.nextLine();

            switch (choice.charAt(0)) {
                case ('1'):
                    managerMenu();
                    break;
                case ('2'):
                    customerList();
                    break;
                case ('3'):
                    addCustomer();
                    break;
                case ('0'):
                    dao.closeAndExit();
                    break;
                default:
                    System.out.println("Not a valid option");
                    break;
            }
        }
    }
    public static void managerMenu() {
        loggedInAsManager = true;
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
                    loggedInAsManager = false;
                    return;
                default:
                    System.out.println("Not a valid option");
                    break;
            }
        }
    }
    public static void customerList() {
        customersList = dao.getAllCustomers();

        if (customersList.isEmpty()) {
            System.out.println("The customer list is empty!");
        } else {
            System.out.println("Customer list:");
            customersList.forEach(com -> System.out.printf("%d. %s \n", com.getId(), com.getName()));
            System.out.println("0. Back");

            int selection = Integer.parseInt(input.nextLine());
            if (selection >= 1) {
                customer = customersList.get(selection - 1);
                customerMenu();
                System.out.println();
            }
        }
    }
    public static void customerMenu() {
        while (true) {
            System.out.println("1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented car");
            System.out.println("0. Back");
            String choice = input.nextLine();
            switch (choice.charAt(0)) {
                case ('1'):
                    rentCar();
                    break;
                case ('2'):
                    returnCar();
                    break;
                case ('3'):
                    displayCar();
                case ('0'):
                    return;
                default:
                    System.out.println("Not a valid option");
                    break;
            }
        }
    }
    public static void rentCar() {
        System.out.println(customer.getRentedCarId() + " car id" );
        if (customer.getRentedCarId() != null) {
            System.out.println("You've already rented a car!");
        } else {
            companyMenu();
            List<Car> carsCompany = dao.getAllCars(company.getId());
            Set<Integer> rentalIdList = customersList
                    .stream()
                    .map(Customer::getRentedCarId)
                    .collect(Collectors.toSet());
            List<Car> carsAvailable = carsCompany
                    .stream()
                    .filter(car -> !rentalIdList.contains(car.getId()))
                    .collect(Collectors.toList());
            if (carsAvailable.isEmpty()) {
                System.out.printf("No available cars in the %s company\n", company.getName());
            } else {
                System.out.print("Choose a car:\n");
                AtomicInteger counter = new AtomicInteger();
                carsAvailable.forEach(car -> {
                    counter.getAndIncrement();
                    System.out.printf(" %s. %s\n", counter, car.getName());
                });
                System.out.print("0. Exit\n");
                int selection = Integer.parseInt(input.nextLine());
                if (selection >= 1) {
                    carRented = carsAvailable.get(selection - 1);
                    dao.rentCar(customer, carRented.getId());
                    System.out.printf("You rented %s\n", carRented.getName());

                }
            }
        }
        System.out.println();
    }
    public static void returnCar() {
        Integer rentedCarId = customer.getRentedCarId();
        if (rentedCarId == null) {
            System.out.println("You didn't rent a car!");
        } else {
            dao.returnCar(customer, customer.getRentedCarId());
            System.out.println("You've returned a rented car!");
        }
        System.out.println();
    }
    public static void displayCar() {
        Integer carId = customer.getRentedCarId();

        if (carId == null) {
            System.out.println("You didn't rent a car!");
        } else {


        }


    }
    public static void companyMenu() {
        List<CarCompany> companies = dao.getAllCompanies();

        if (companies.isEmpty()) {
            System.out.println("The company list is empty!");
        } else {
            System.out.println("Choose the Company:");
            companies.forEach(com -> System.out.printf("%d. %s \n", com.getId(), com.getName()));
            System.out.println("0. Back");

            int selection = Integer.parseInt(input.nextLine());
            if (selection >= 1) {
                company = companies.get(selection - 1);
                if (loggedInAsManager) {
                    carMenu();
                }
                System.out.println();
            }
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
                    carList();
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
    public static void carList() {
        List<Car> carList = dao.getAllCars(company.getId());

        if (carList.isEmpty()) {
            System.out.println("The car list is empty!");
        } else {
            System.out.println("Car list:");
            AtomicInteger counter = new AtomicInteger();
            carList.forEach(car -> {
                counter.getAndIncrement();
                System.out.printf("%s. %s \n", counter, car.getName());
            });
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
    public static void addCar() {
            System.out.println("Enter the car name:");
            String choice = input.nextLine();
            dao.addCar(choice, company.getId());
            System.out.println("The car was added!");
            System.out.println();
    }
    public static void addCustomer() {
        System.out.println("Enter the customer name:");
        String choice = input.nextLine();
        dao.addCustomer(choice);
        System.out.println("The customer was added!");
        System.out.println();
    }
}