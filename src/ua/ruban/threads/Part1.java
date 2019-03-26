package ua.ruban.threads;

import static java.lang.Thread.sleep;

public class Part1 {

    private static final int COUNT = 4;
    private static final int TIME = 500;

    public static void main(String[] args) throws InterruptedException {

        Thread t = new MyThread();
        t.start();
        t.join();

        Thread t3 = new Thread(new MyThread2());
        t3.start();
        t3.join();
    }


    static class MyThread extends Thread {
        @Override
        public void run() {
            method();
        }
    }

    static class MyThread2 implements Runnable {
        @Override
        public void run() {
            method();
        }
    }

    private static void method(){
        int k=0;
        while (k<COUNT){
            try {
                sleep(TIME);
                System.out.println(Thread.currentThread().getName());
                k++;
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
