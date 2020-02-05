package com.zmeev;

public class Buyer implements Runnable{
    private static int countOfPurchases = 0;
    private static int countOfUnits = 0;
    private Stock stock;

    public Buyer(Stock stock) {
        this.stock = stock;
    }

    private String buyRandomUnits() {
        StringBuilder buffer = new StringBuilder();

        countOfUnits = stock.sellUnitsFromStock();
        buffer.append(countOfUnits).append(" ");
        buffer.append(++countOfPurchases);

        return buffer.toString();
    }

    @Override
    public void run() {
        while (Stock.getStockCount() >= 0) {
            System.out.println(Thread.currentThread().getName() + " started " + buyRandomUnits() + " " + Stock.getStockCount());
        }
    }
}
