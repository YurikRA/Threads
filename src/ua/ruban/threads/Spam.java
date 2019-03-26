package ua.ruban.threads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Spam {

    private static final int T1 = 333;
    private static final int T2 = 222;
    private static final int T3 = 2000;

    private static int n;
    private String[] mass;
    private int[] tim;
    private static List<Thread> threadList = new ArrayList<>();
    private static final String[] messageS = new String[] { "@@@", "bbbbbbb" };
    private static final int[] timeS = new int[] {T1, T2};

    Spam(String[] massage, int[] time){
        this.mass = massage.clone();
        this.tim = time.clone();
    }

    public static void main(String[] args) {
        String[] copy;
        int[] copyInt;
        copy = messageS.clone();
        copyInt = timeS.clone();

        n = copy.length;

        Spam p = new Spam(copy,copyInt);
        p.starT();

        MyInputStream ms = new MyInputStream(T3);
        ms.read();
        p.stop();
        try {
            ms.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void starT() {
        for (int i=0; i<n; i++){
            Thread t = new Worker(mass[i],tim[i]);
            threadList.add(t);
            threadList.get(i).start();
        }
    }

    public void stop() {
        for (int i=0; i<n; i++){
            threadList.get(i).interrupt();
        }
    }

    static class Worker extends Thread{

        private String mg;
        private int ts;

        Worker(String mg, int ts){
            this.mg = mg;
            this.ts = ts;
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                System.out.println(mg);
                try {
                    sleep(ts);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
}
