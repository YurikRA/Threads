package ua.ruban.threads;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Part4 {
    private static int nAmt;
    private static int mAmt;
    private static int[][] mass;
    private static int max;
    private static boolean resultTh;

    public static void main(String[] args) throws InterruptedException {
        mass = readF("part4.txt");
        resultTh = true;
        long startTime = System.currentTimeMillis();
        sync();
        while (resultTh){
            Thread.sleep(1);
        }
        long timeSpent = System.currentTimeMillis() - startTime;
        System.out.println(max);
        System.out.println(timeSpent);
        long startTime2 = System.currentTimeMillis();
        PosWorker posWorker = new PosWorker();
        posWorker.start();
        posWorker.join();
        long timeSpent2 = System.currentTimeMillis() - startTime2;
        System.out.println(posWorker.getAsd());
        System.out.println(timeSpent2);
    }

    static class PosWorker extends Thread{
        private int asd;
        public int getAsd() {
            return asd;
        }
        @Override
        public void run() {
            int k = 0;
            int u = 0;
            while (!Thread.currentThread().isInterrupted()){
                try {
                    if (mass[k][u]>asd){
                        asd = mass[k][u];
                    }
                    u++;
                    if (u == nAmt){
                        k++;
                        u=0;
                    }
                    sleep(1);
                } catch (InterruptedException | IndexOutOfBoundsException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private static void sync() {
        max=0;
        for (int i=0; i<mAmt; i++){
            Worker worker = new Worker(i);
            worker.start();
        }
    }

    static class Worker extends Thread{
        private int nMass;
        private int num;

        Worker(int nMass){
            this.nMass = nMass;
            num = 0;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public int getnMass() {
            return nMass;
        }

        @Override
        public synchronized void run() {
            while (!Thread.currentThread().isInterrupted()){
                try {
                    Worker w = (Worker) Worker.currentThread();
                    int indM = w.getnMass();
                    int iN = w.getNum();
                    if (mass[indM][iN] > max){
                        max = mass[indM][iN];
                    }
                    w.setNum((iN+1));
                    sleep(1);
                } catch (InterruptedException | IndexOutOfBoundsException e) {
                    Thread.currentThread().interrupt();
                }
            }
            resultTh = false;
        }
    }

    private static int[][] readF(String fileName){
        nAmt=0;
        mAmt=0;
        List<Integer> integerList;
        int[][] massTime = null;
        StringBuilder sb = new StringBuilder();
        try (FileInputStream in = new FileInputStream(fileName)){
            int c=-1;
            while((c=in.read())!=-1){
                sb.append(String.valueOf((char)c));
            }
            String str = String.valueOf(sb);
            integerList = new ArrayList<>();
            Pattern p = Pattern.compile("(\\d+)|(\n)");
            Matcher m = p.matcher(str);
            while (m.find()){
                if (("\n").equals(m.group())){
                    mAmt++;
                }else {
                    nAmt++;
                    integerList.add(Integer.parseInt(m.group()));
                }
            }
            if (mAmt == 0){
                System.out.println("Error!");
            }else {
                nAmt = nAmt/mAmt;
            }
            massTime = new int[mAmt][nAmt];
            int allAmt=0;
            for (int i=0; i<mAmt; i++){
                for (int j=0; j<nAmt; j++){
                    massTime[i][j] = integerList.get(allAmt);
                    allAmt++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return massTime;
    }
}