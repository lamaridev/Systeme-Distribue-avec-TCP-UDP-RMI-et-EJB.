package Client;

import java.util.Timer;
import java.util.TimerTask;

public class MonTimer {
    private Timer timer;
    private boolean isTimerExpired;

    public MonTimer() {
        this.timer = new Timer();
        this.isTimerExpired = false;
    }

    public void startTimer(int delay) {

        // Schedule a task to set isTimerExpired to true after the specified delay
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                isTimerExpired = true;
                System.out.println("Timer expired!");
            }
        }, delay);
    }

    public void stopTimer() {
        timer.cancel();
        isTimerExpired = false;
    }

    public boolean isTimerExpired() {
        return isTimerExpired;
    }
}
