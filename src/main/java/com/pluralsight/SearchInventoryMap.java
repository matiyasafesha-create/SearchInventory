package com.pluralsight;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;


public class SearchInventoryMap {
    public static void main(String[] args) {
        run();
    }

    public static void run() {
        System.out.println("---Welcome to Super Duper's Product Inventory System---\n");
        while(true) {
            menu();
            menuSelector();
        }
    }

    public static void menu() {
        System.out.println("What do you want to do?\n" +
                "  1- List all products\n" +
                "  2- Lookup a product by its id\n" +
                "  3- Find all products within a price range\n" +
                "  4- Find a product by name\n" +
                "  5- Add a new product\n" +
                "  0- Quit the application\n");
    }

    public static void menuSelector() {
        HashMap<String, Product> inventory = loadinventory();

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                System.out.println();
                viewAll(inventory);
                break;
            case 2:
                System.out.print("Enter an id to search: ");
                int id = scanner.nextInt();
                scanner.nextLine();

                searchProductByID(inventory, id);
                break;
            case 3:
                System.out.print("Enter a starting value: ");
                int startRange = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Enter a ending value: ");
                int endRange = scanner.nextInt();
                scanner.nextLine();

                searchProductByPriceRange(inventory, startRange, endRange);
                break;
            case 4:
                System.out.print("Enter an name to search: ");
                String name = scanner.nextLine().trim();

                searchProductByName(inventory, name);
                break;
            case 5:
                addProduct(scanner);
                break;
            case 0:
                scanner.close();
                System.exit(0);
                break;
            default:
                System.out.println("Incorrect option entered, try again");
        }

        System.out.println("\nPress ENTER to continue...\n");
        scanner.nextLine();

    }

    public static HashMap<String,Product> loadinventory() {
        HashMap<String, Product> inventory = new HashMap<>();

        // Unused Manual Product Creation
        //            inventory.add(new Product(1234, "Cabin Air Filter", 17.00f));
        //            inventory.add(new Product(1235, "Engine Oil 5W-30", 24.99f));
        //            inventory.add(new Product(1236, "Brake Pads Set", 45.50f));
        //            inventory.add(new Product(1237, "Windshield Wiper Blades", 19.95f));
        //            inventory.add(new Product(1238, "Car Battery", 89.99f));
        //            inventory.add(new Product(1239, "Spark Plugs 4-Pack", 32.75f));

        try {
            BufferedReader bufReader = new BufferedReader(new FileReader("src/main/resources/DataFiles/inventory.csv"));

            String input;

            while((input = bufReader.readLine()) != null) {
                String[] tokens = input.split("\\|");

                int id = Integer.parseInt(tokens[0]);
                String name = tokens[1];
                float price = Float.parseFloat(tokens[2]);

                inventory.put(name, new Product(id, name, price));

            }

            bufReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inventory;
    }

    public static void addProduct(Scanner scanner) {
        System.out.println("--Welcome to the Product Admin Page--\n");
        System.out.println("Please begin by entering the Product info");

        System.out.print("  Enter the ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("  Enter the Name: ");
        String name = scanner.nextLine();

        System.out.print("  Enter the Price (numbers/decimal only): ");
        float price = scanner.nextFloat();
        scanner.nextLine();

        try {
            BufferedWriter bufWriter = new BufferedWriter(new FileWriter("src/main/resources/inventory.csv", true));
            bufWriter.newLine();
            bufWriter.write(String.format("%d|%s|%.2f", id, name, price));
            bufWriter.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void searchProductByPriceRange( HashMap<String, Product> inventory, int startRange, int endRange) {
        for (Product p : inventory.values()) {
            if (startRange <= p.getPrice() && endRange >= p.getPrice()) {
                displayProduct(p);
            }
        }
    }

    public static void searchProductByID( HashMap<String, Product> inventory, int id) {
        for (Product p : inventory.values()) {
            if (p.getId() == id) {
                displayProduct(p);
            }
        }
    }

    public static void searchProductByName( HashMap<String, Product> inventory, String name) {
        for (Product p : inventory.values()) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                displayProduct(p);
            }
        }
    }

    public static void viewAll( HashMap<String, Product> inventory) {
        System.out.println(); //empty line

        for (Product inventoryItem : inventory.values()) {
            displayProduct(inventoryItem);
        }
    }

    public static void displayProduct(Product p) {
        System.out.printf("Product: %d\n  Name: %s\n  Price: $%.2f\n\n", p.getId(), p.getName(), p.getPrice());
    }
}
