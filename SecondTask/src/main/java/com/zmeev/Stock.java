package com.zmeev;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Stock {
    private static volatile Stock instance;
    private static int stockCount = 1000;
    private Lock lock = new ReentrantLock();

    private Stock() {}

    public static Stock getInstance() {
        if (instance == null) {
            synchronized (Stock.class) {
                if (instance == null) {
                    instance = new Stock();
                }
            }
        }
        return instance;
    }

    int sellUnits(int numberOfUnitsToSell) {
        lock.lock();
        numberOfUnitsToSell = Math.min(stockCount, numberOfUnitsToSell);
        stockCount -= numberOfUnitsToSell;
        lock.unlock();

        return numberOfUnitsToSell;
    }

    public static int getStockCount() {
        return stockCount;
    }
}
