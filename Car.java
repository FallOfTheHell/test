package race;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Car implements Runnable{
    private static int CARS_COUNT;
    private Race race;
    private int speed;
    private String name;
    private static CyclicBarrier cyclicBarrier;
    private static CountDownLatch countDownLatchReady;
    private static CountDownLatch countDownLatchFinish;

    static {
        countDownLatchFinish = MainClass.countDownLatchFinish;
        countDownLatchReady = MainClass.countDownLatchReady;
        cyclicBarrier = MainClass.startBarrier;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            shortSleep();
            countDownLatchReady.countDown();
            System.out.println(this.name + " готов");
            cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            ArrayList<Stage> stages = race.getStages();
            for (Stage stage :stages) {
                stage.go(this);
            }
            countDownLatchFinish.countDown();
        }
    }
    public static void shortSleep() {
        try {
            Thread.sleep(500 + (int)(Math.random() * 800));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
