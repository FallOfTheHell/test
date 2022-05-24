package race;

import java.util.concurrent.Semaphore;

public class Tunnel extends Stage{
    static Semaphore semaphore;
    public Tunnel(int lenght) {
        this.length = lenght;
        this.description = "Тоннель " + length + " метров";
    }
    static {
        semaphore = new Semaphore(MainClass.CARS_COUNT/2);
    }
    @Override
    public void go(Car c) {
        try {
            try {
                System.out.println(c.getName() + " готовится к этапу(ждет): " + description);
                semaphore.acquire();
                System.out.println(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
                System.out.println(c.getName() + " закончил этап: " +
                        description);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
