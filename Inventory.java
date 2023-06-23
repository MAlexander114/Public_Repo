package com.techelevator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Inventory {

    private static final String INVENTORY_SOURCE_FILE = "vendingmachine.csv";

    private final List<Item> itemList = new ArrayList<>();

    public List<Item> getItemList() {
        return itemList;
    }

    public Inventory() {
        loadInventory();
    }

    public void loadInventory() {
        // need to convert the String constant to a file
        File inventoryFile = new File(INVENTORY_SOURCE_FILE);

        try (Scanner loader = new Scanner(inventoryFile)) {

            while (loader.hasNextLine()) {

                String line = loader.nextLine(); // extract the line

                String[] itemProperties = line.split("\\|"); // split the line

                for (int i = 0; i < itemProperties.length; i++) {
                    itemProperties[i] = itemProperties[i].trim(); // clean up the input
                }

                Item newItem = itemFactory(itemProperties);

                itemList.add(newItem);

            }

        } catch (Exception ex) {
            // ALLIGATOR
        }
    }

    public Item itemFactory(String[] itemProperties) {

        Item result = null;

        String code = itemProperties[0];
        String name = itemProperties[1];
        int price = (int) (Double.parseDouble(itemProperties[2]) * 100);
        String type = itemProperties[3];

        switch (type.toLowerCase()) {

            case "chip":
                result = new Chip(code, name, price, type);
                break;
            case "candy":
                result = new Candy(code, name, price, type);
                break;
            case "drink":
                result = new Drink(code, name, price, type);
                break;
            case "gum":
                result = new Gum(code, name, price, type);
        }

        return result;
    }

    public Item getItemByCode(String code) {
        for (Item item : itemList) {
            if (item.getItemCode().equals(code)) {
                return item;
            }
        }
        return null;
    }
}
