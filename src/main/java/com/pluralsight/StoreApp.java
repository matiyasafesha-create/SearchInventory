package com.pluralsight;
import java.io.*;
import java .util.ArrayList;
import java.util.Scanner;





public class StoreApp {
    public static void main(String[] args) {


        ArrayList<Product> inventory = getInventory();
        Scanner scanner = new Scanner(System.in);
        System.out.println("We carry the following inventory: ");


        for (int i = 0; i < inventory.size(); i++) {
            Product p = inventory.get(i);
            System.out.printf("id: %d %s - Price: $%.2f\n",
                    p.getId(), p.getName(), p.getPrice());
        }
    }


    public static ArrayList<Product> getInventory(){
        ArrayList<Product> inventory = new ArrayList<Product>();
        //inventory.add(new Product(3434,"Hammer",14.49f ));

        //inventory.add(new Product(1234,"Bulb",12.23f  ));


        try {
            FileReader fileReader1 = new FileReader("src/main/resources/DataFiles/inventory.csv");
            BufferedReader bufReader = new BufferedReader(fileReader1);


            String input;

            Product[] storeList = new Product[15];

            int count = 0;

            bufReader.readLine();


            while ((input = bufReader.readLine()) != null) {
                String[] tokens = input.split("\\|");

                int c1id = Integer.parseInt(tokens[0]);
                String c2name = tokens[1];
                float c3price = Float.parseFloat(tokens[2]);


                storeList[count] = new Product(c1id, c2name, c3price);

                System.out.printf("Item ID: %d\n Name: %s\n Price: $%.2f\n\n",
                        storeList[count].getId(), storeList[count].getName(), storeList[count].getPrice());

                count++;

            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return inventory;

    }}






