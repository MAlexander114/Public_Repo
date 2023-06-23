package com.techelevator;

public abstract class Item {

    // properties/variables
    private final String itemCode;

    private final String itemName;

    private final int pennyPrice;

    private final String itemType;

    private int itemQuantity;

    // Getters
    public int getItemQuantity() {
        return itemQuantity;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public int getPennyPrice() {
        return pennyPrice;
    }

    // Constructor
    public Item(String itemCode, String itemName, int pennyPrice, String itemType) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.pennyPrice = pennyPrice;
        this.itemType = itemType;
        this.itemQuantity = 5;
    }

    // Methods

    public String getUserPrice() {
        double dollarPrice = pennyPrice / 100.00;
        return String.format("%.2f", dollarPrice);
    }

    public String getDisplayedQuantity() {
        if (this.itemQuantity > 0) {
            return String.valueOf(this.itemQuantity);
        }
        return "SOLD OUT!";
    }

    @Override
    public String toString() {
        return String.format("[%1$2s] %2$-20s$%3$6.2f  ", itemCode, itemName, pennyPrice / 100.00, getDisplayedQuantity());
    }

    public void dispense() {

        // decrement quantity
        this.itemQuantity--;

        // use itemType to determine which phrase is displayed when dispensing
        switch (itemType.toLowerCase()) {
            case "chip":
                System.out.println("Crunch Crunch, Yum!");
                break;
            case "candy":
                System.out.println("Munch Munch, Yum!");
                break;
            case "drink":
                System.out.println("Glug Glug, Yum!");
                break;
            case "gum":
                System.out.println("Chew Chew, Yum!");
                break;
            default:
                break;
        }
    }
}