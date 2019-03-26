package ua.ruban.threads;

import java.io.InputStream;

public class MyInputStream extends InputStream {

    private static final int U1 = 13;
    private static final int U2 = 10;
    private static final byte[] bytes = {U1,U2};
    private long millis;
    private boolean isFirstCall = true;
    private int current;

    public MyInputStream(long millis) {
        super();
        this.millis = millis;
    }

    @Override
    public int read() {
        try {
            if (isFirstCall) {
                Thread.sleep(millis);
            }
            isFirstCall = false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return -1;
        }
        if (current < bytes.length){
            return bytes[current++];
        }
        return -1;
    }
}