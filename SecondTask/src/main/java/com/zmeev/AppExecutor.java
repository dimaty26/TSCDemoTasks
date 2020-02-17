package com.zmeev;
/*
Требуется реализовать систему склад - покупатели, работающую по следующим правилам:
1. На складе учитывается остаток товара. Начальное количество = 1000
2. Количество покупателей указывается в параметрах запуска системы.
Все покупатели стартуют одновременно и совершают покупки параллельно.
3. Класс покупатель может за одну покупку "купить" (уменьшить остаток на складе) случайное
количество единиц товара от 1 до 10.
Если на складе осталось меньше требуемого товара, покупатель забирает остаток.
4. Когда товар на складе заканчивается, каждый покупатель должен вывести в консоль
общее количество купленного товара, количество покупок и завершить работу.
5. Требуется обеспечить равномерное количество покупок, т.е. количество покупок,
сделанных каждым покупателем не должно отличаться больше, чем на 1.
 */

import java.util.concurrent.*;

public class AppExecutor {
    public static void main(String[] args) {

        long time = timer(() -> {
                    if (args.length == 1) {
                        try {
                            int numberOfBuyers = Integer.parseInt(args[0]);
                            ExecutorService executor = Executors.newFixedThreadPool(numberOfBuyers);
                            CyclicBarrier barrier = new CyclicBarrier(numberOfBuyers);

                            for (int i = 1; i <= numberOfBuyers; i++) {
                                Runnable buyer = new Buyer(i, barrier);
                                executor.execute(buyer);
                            }
                            executor.shutdown();
                            while (!executor.isTerminated()) {
                            }

                        } catch (NumberFormatException e) {
                            System.out.println("Incorrect data. Integer number is expected.");
                        } catch (IllegalArgumentException e) {
                            System.out.println("Integer number should be more than zero.");
                        }
                    } else {
                        System.out.println("Incorrect number of arguments. Should be 1 integer number.");
                    }
                }, TimeUnit.MILLISECONDS);

        System.out.println(time);

        long time2 = timer( () -> {
            sellUnitsInMain(1000);

        }, TimeUnit.MILLISECONDS);
        System.out.println(time2);
    }

    private static long timer(Runnable method, TimeUnit timeUnit) {
        long time = System.nanoTime();
        method.run();
        time = System.nanoTime() - time;
        return TimeUnit.MILLISECONDS.convert(time, timeUnit);
    }

    private static void sellUnitsInMain(int stockCount) {

        while (stockCount > 0) {
            int numberOfUnitsToSell = (int) (Math.random() * 10) + 1;
            numberOfUnitsToSell = Math.min(stockCount, numberOfUnitsToSell);
            stockCount -= numberOfUnitsToSell;
        }
    }
}
