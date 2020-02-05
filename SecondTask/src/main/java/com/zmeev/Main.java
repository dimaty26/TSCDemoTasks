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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        Stock stock = new Stock();
        for (int i = 0; i < 5; i++) {
            Runnable buyer = new Buyer(stock);
            executor.execute(buyer);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("All threads are finished.");
    }
}
