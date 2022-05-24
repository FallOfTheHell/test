package Book;

public class Test {
    static volatile char c = 'A';
    static Object object = new Object();

    static class WaitNotifyClass implements Runnable{
        private char currentLetter;
        private char nextLetter;

        public WaitNotifyClass(char currentLetter, char nextLetter){
            this.currentLetter = currentLetter;
            this.nextLetter = nextLetter;
        }
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                synchronized (object){
                    {
                        try {
                            while (c != currentLetter)
                            object.wait();
                            System.out.print(currentLetter + " ");
                            c = nextLetter;
                            object.notifyAll();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
    public static void main(String[] args) {
        new Thread(new WaitNotifyClass('A', 'B')).start();
        new Thread(new WaitNotifyClass('B', 'C')).start();
        new Thread(new WaitNotifyClass('C', 'A')).start();
    }
}
