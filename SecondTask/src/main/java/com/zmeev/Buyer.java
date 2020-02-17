package com.zmeev;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Buyer implements Runnable {
    private static final int MAX_NUMBER_OF_UNITS_PER_ONE_PURCHASE = 10;
    private int countOfPurchases = 0;
    private int countOfUnits = 0;
    private int id;
    private CyclicBarrier barrier;

    static {
        Stock.getInstance();
    }

    public Buyer(int id, CyclicBarrier barrier) {
        this.id = id;
        this.barrier = barrier;
    }

    private void buyUnits() {
        countOfUnits += Stock.sellUnits(getRandomNumberOfPurchases());
        countOfPurchases++;
    }

    private int getRandomNumberOfPurchases() {
        return (int) (Math.random() * MAX_NUMBER_OF_UNITS_PER_ONE_PURCHASE) + 1; //add 1 to avoid zero
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
