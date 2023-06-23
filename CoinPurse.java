package com.techelevator;
public class CoinPurse {

    private int pennyBalance;

    public CoinPurse() {
        this.pennyBalance = 0;
    }

    public void deposit(int amount) {
        // convert amount to pennies and add to balance
        pennyBalance += amount * 100d;
    }

    public void deduct(int amount) {
        // convert to pennies and subtract from balance
        pennyBalance -= amount;
    }

    public int getBalance() {
        // return current balance
        return this.pennyBalance;
    }

    public String getBalanceUser() {
        double dollarBalance = pennyBalance / 100.00;
        return String.format("%.2f", dollarBalance);
    }
//
//    public String amountToReturn() {
//        return pennyBalance / 100;
//    }

    public void returnChange() {

        // get a usable form of balance so we can subtract from it as we go
        int amountReturned = getBalance();
        int remainingBalance = getBalance();

        // calculate amount of each coin denomination then subtract from remainingBalance
        int numberOfQuarters = remainingBalance / 25;
        remainingBalance %= 25;

        int numberOfDimes = remainingBalance / 10;
        remainingBalance %= 10;

        int numberOfNickels = remainingBalance / 5;

        // sout() coin delegations
        System.out.println("Returning: $" + getBalanceUser());
        System.out.println("Dispensing: ");
        System.out.println("Quarters: " + numberOfQuarters);
        System.out.println("Dimes: " + numberOfDimes);
        System.out.println("Nickels: " + numberOfNickels);

        // set balance to 0
        pennyBalance = 0;
    }

}
