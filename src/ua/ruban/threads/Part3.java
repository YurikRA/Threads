package ua.ruban.threads;

public class Part3 {

    private static int k1;
    private static int k2;
    private static Counter counter;
    private static final int TEN = 10;
    private static final int TIME = 70;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("First");
        counter = new Counter();
        Worker[] th = new Worker[TEN];
        for (int i=0; i<TEN; i++){
            th[i] = new Worker();
            th[i].start();
        }
        Thread.sleep(TIME);
        for (int i=0; i<TEN; i++){
            th[i].interrupt();
        }
        Thread.sleep(TIME*(long)TEN);
        System.out.println("Second");
        counter = new Counter();
        Worker2[] th2 = new Worker2[TEN];
        for (int i=0; i<TEN; i++){
            th2[i] = new Worker2();
            th2[i].start();
        }
        Thread.sleep(TIME);
        for (int i=0; i<TEN; i++){
            th2[i].interrupt();
        }
        Thread.sleep(TIME*(long)TEN);
    }

    public static void result(){
        if (k1>k2){
            System.out.println(k1+" > "+k2);
        }
        if (k1<k2){
            System.out.println(k1+" < "+k2);
        }
        if (k1 == k2){
            System.out.println(k1+" == "+k2);
        }
        count1();
        try {
            Thread.sleep(TEN);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        count2();
    }
    private static void count1(){
        k1++;
    }
    private static void count2(){
        k2++;
    }


    static class Counter {
        Counter(){
        }
    }

    static class Worker extends Thread{
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                result();
            }
        }
    }

    static class Worker2 extends Thread{
        @Override
        public void run() {
            synchronized (counter){
                result();
            }
        }
    }
}
