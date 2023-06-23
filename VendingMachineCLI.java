package com.techelevator;

import com.techelevator.utilities.ErrorLog;
import com.techelevator.utilities.TransactionLog;

import java.util.Scanner;

public class VendingMachineCLI {

    private static final String BANNER = "\n-----------------------------------------------------\n";
    private static final String BANNER_1 = "\n#############################\n";
    private static final String BANNER_2 = "\n*****************************\n";

    private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
    private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
    private static final String MAIN_MENU_OPTION_EXIT = "Exit M & J's Vending Machine";

    private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
    private static final String PURCHASE_MENU_OPTION_SELECT_ITEM = "Select Item";
    private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";

    private static final String[] MAIN_MENU = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};
    private static final String[] PURCHASE_MENU = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_ITEM, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};

    private final ErrorLog mainLog = new ErrorLog();
    private final TransactionLog salesLog = new TransactionLog();
    private final CoinPurse wallet = new CoinPurse();
    private final Inventory inv = new Inventory();

    private Scanner userInput;

    public static void main(String[] args) {

        VendingMachineCLI app = new VendingMachineCLI();

        app.userInput = new Scanner(System.in); // instantiate userInput scanner

        app.run();

    }

    public void run() {

        System.out.println(BANNER_1 + "    Hello and Welcome to: \n            M & J\n       Vending Machine!" + BANNER_1);

        boolean runMenu = true;
        String[] activeMenu = MAIN_MENU;

        while (runMenu) {

            displayMenuOptions(activeMenu);
            if (activeMenu == MAIN_MENU) {
                System.out.println("\nPlease Make A Selection: ");
            }

            String userSelection = userInput.nextLine();

            try {

                int userSelectionIndex = Integer.parseInt(userSelection) - 1;

                String menuSelection = activeMenu[userSelectionIndex];

                System.out.println(BANNER);

                System.out.println("You've Selected: " + menuSelection);

                System.out.println(BANNER);
                System.out.println(BANNER);

                switch (menuSelection) {

                    case MAIN_MENU_OPTION_DISPLAY_ITEMS:
                        System.out.println("We Have ALL KINDS Of Items!");
                        for (Item invItem : inv.getItemList()) {

                            System.out.println(invItem.toString() + "Quantity Left: " + invItem.getDisplayedQuantity());

                        }
                        break;

                    case MAIN_MENU_OPTION_PURCHASE:
                        activeMenu = PURCHASE_MENU;
                        System.out.println("Here You Can Add Funds Or Select An Item To Purchase");
                        System.out.println("Current Balance: $" + wallet.getBalanceUser());
                        break;

                    case MAIN_MENU_OPTION_EXIT:
                        runMenu = false;
                        break;

                    case PURCHASE_MENU_OPTION_FEED_MONEY:
                        System.out.println("Current Balance: $" + wallet.getBalanceUser() + ".");
                        System.out.println("How Much Money (in whole dollar increments) Would You Like To Deposit?");
                        String lastDeposit = userInput.nextLine();
                        wallet.deposit(Integer.parseInt(lastDeposit));
                        System.out.println("You Deposited: $" + lastDeposit + ".00." + "\nNew Balance: $" + wallet.getBalanceUser() + ".");
                        salesLog.write(PURCHASE_MENU_OPTION_FEED_MONEY, lastDeposit + ".00", wallet.getBalanceUser());
                        break;

                    case PURCHASE_MENU_OPTION_SELECT_ITEM:
                        for (Item invItem : inv.getItemList()) {

                            System.out.println(invItem.toString());

                        }
                        System.out.println(BANNER);
                        System.out.println("Current Balance: $" + wallet.getBalanceUser() + ".");
                        System.out.println("Please Enter The Alpha-Numeric Code of the Item You Want To Purchase");
                        String code = userInput.nextLine();
                        Item selectedItem = inv.getItemByCode(code);
                        if (selectedItem == null) {
                            System.out.println("Invalid Item. How About Another Try, Champ?");
                        } else if (selectedItem.getPennyPrice() > wallet.getBalance()) {
                            System.out.println("INSUFFICIENT FUNDS! This Isn't A Charity!");
                        } else if (selectedItem.getItemQuantity() <= 0) {
                            System.out.println("SOLD OUT! How About A Different Item?");
                        } else {
                            selectedItem.dispense();
                            wallet.deduct(selectedItem.getPennyPrice());
                            System.out.println("You've Purchased " + selectedItem.getItemName() + " For $" + selectedItem.getUserPrice() + ".");
                            System.out.println("Current Balance: $" + wallet.getBalanceUser() + ".");
                            salesLog.write(selectedItem.getItemName(), selectedItem.getUserPrice(), wallet.getBalanceUser());
                        }
                        break;

                    case PURCHASE_MENU_OPTION_FINISH_TRANSACTION:
                        salesLog.write("Dispense Change", wallet.getBalanceUser(), "0.00");
                        wallet.returnChange();
                        System.out.println("Current Balance: $" + wallet.getBalanceUser() + ".");
                        activeMenu = MAIN_MENU;
                        break;

                    default:
                        System.out.println("YOU BROKE OUR VENDING MACHINE, THANKS");
                        break;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("ERROR: Please Only Enter Menu Option Numbers");
                mainLog.write("MENU ERROR: " + nfe.getClass() + nfe.getMessage());
            } catch (Exception ex) {
                System.out.println("ERROR: Please Enter A Valid Menu Number");
                mainLog.write("MENU ERROR: " + ex.getClass() + " " + ex.getMessage());
            }
        }
    }

    public void displayMenuOptions(String[] menu) {

        System.out.print(BANNER);

        for (int i = 0; i < menu.length; i++) {
            int menuNum = i + 1;
            System.out.println("(" + menuNum + ") " + menu[i]);
        }

        System.out.print(BANNER);

    }
}
