package ua.ruban.threads;

import java.io.InputStream;

public class Part2 {

    private static final int DELAY = 2000;
    private static final InputStream STD_IN = System.in;

    public static void main(String[] args) throws  InterruptedException {
        // create the mock input
        MyInputStream mockIn = new MyInputStream(DELAY);
        // set the mock input
        System.setIn(mockIn);
        // start the thread for delayed output
        Thread t = new Thread() {
            @Override
            public void run() {
                Spam.main(null);
            }
        };
        t.start();
        t.join();
        // restore the standard input
        System.setIn(STD_IN);

    }
}