package src;

import java.util.concurrent.Exchanger;

public class Demo {
    public static void main(String[] args) {
        Exchanger<String> stringExchanger = new Exchanger<>();

        Thread studentA = new Thread(() -> {
            try {
                String dataA = "A同学收藏多年的大片";
                String dataB = stringExchanger.exchange(dataA);
                System.out.println("A同学得到了" + dataB);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread studentB = new Thread(() -> {
            try {
                String dataB = "B同学收藏多年的大片";
                String dataA = stringExchanger.exchange(dataB);
                System.out.println("B同学得到了" + dataA);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        studentA.start();
        studentB.start();
    }
}