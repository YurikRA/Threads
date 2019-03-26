package ua.ruban.threads;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Part5 {

    private static final int AMT = 10;
    private static final int SYM = 20;
    private static final int ALLTIME = 800;
    private static RandomAccessFile raf;
    private static boolean flag;
    private static File file;
    private static int kolD;

    public static void main(String[] args) throws IOException, InterruptedException {
        flag =  true;
        kolD =0;
        file = new File("part5.txt");
        raf = new RandomAccessFile(file, "rw");
        for (int i=0; i<AMT; i++){
            WorkerP5 worker = new WorkerP5(i);
            worker.start();
        }
        while (flag){
            Thread.sleep(ALLTIME);
        }
        raf.seek(0);
        for (int i=1; i<AMT; i++){
            raf.seek(SYM*i+(long)(i-1));
            raf.writeByte((byte)AMT);
        }
        raf.seek(0);
        for (int i=0; i<AMT; i++){
            System.out.println(raf.readLine());
        }
        raf.close();
    }

    static class WorkerP5 extends Thread{
        private int numTh;
        private int index;
        WorkerP5(int numTh){
            this.numTh = numTh;
            index=0;
        }
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                synchronized (file){
                    WorkerP5 w = (WorkerP5) WorkerP5.currentThread();
                    int n = w.getNumTh();
                    int iNd = w.getIndex();
                    int k = SYM*n+n+iNd;
                    w.setIndex(++iNd);
                    try {
                        raf.seek(k);
                        raf.write(('0'+n));
                        sleep(1);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (kolD == (AMT-1)){
                        flag = false;
                    }
                    if (w.getIndex() == SYM){
                        Thread.currentThread().interrupt();
                        kolD++;
                    }
                }
            }
        }
        public int getNumTh() { return numTh; }
        public int getIndex() { return index; }
        public void setIndex(int index) { this.index = index; }
    }
}
