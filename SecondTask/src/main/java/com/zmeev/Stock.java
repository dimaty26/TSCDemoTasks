package com.zmeev;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Stock {
    private static int stockCount = 1000;
    private Lock lock = new ReentrantLock();

    private int sellRandomUnits() {
        int randomNumber;
        lock.lock();
        try {
            randomNumber = (int) (Math.random() * 10) + 1;
            stockCount -= randomNumber;
        } finally {
            lock.unlock();
        }
        return randomNumber;
    }

    int sellUnitsFromStock() {
        return sellRandomUnits();
    }

    public static int getStockCount() {
        return stockCount;
    }
}
