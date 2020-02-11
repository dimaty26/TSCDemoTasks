package com.zmeev;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Buyer implements Runnable {
    private int countOfPurchases = 0;
    private int countOfUnits = 0;
    private int id;
    private Stock stock;
    private CyclicBarrier barrier;

    public Buyer(Stock stock, int id, CyclicBarrier barrier) {
        this.stock = stock;
        this.id = id;
        this.barrier = barrier;
    }

    private int buyUnits() {
        countOfUnits += stock.sellUnits(getRandomNumberOfPurchases());
        countOfPurchases++;
        return countOfUnits;
    }

    private int getRandomNumberOfPurchases() {
        return (int) (Math.random() * 10) + 1;
    }

    @Override
    public void run() {

        while (Stock.getStockCount() > 0) {
            buyUnits();
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                //It's empty and it's ok
            }
        }
        if (Stock.getStockCount() == 0) {
            barrier.reset();
        }

        System.out.println("As a result: Buyer " + id + " bought " + countOfUnits
                + " and performed " + countOfPurchases + " purchases.");
    }
}
