import java.time.LocalTime; 
import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

public class StopWatch extends JLabel {

    private int second0;
    private int second1;
    private int minute0;
    private int minute1;
    private int hour0;
    private int hour1; 
    private boolean stop; 
    public static int[] time; 
    JLabel timer;

    public StopWatch(JLabel timer) {
        second0 = 0; //range 0-9
        second1 = 0; //range 0-5
        minute0 = 0; //range 0-9
        minute1 = 0; //range 0-5
        hour0 = 0; 
        hour1 = 0;
        stop = false;
        this.timer = timer;
        timer.setText("Timer: " + hour1 + hour0 + ":" + minute1 + minute0 + ":" + second1 + second0); 
        add(timer, SwingConstants.CENTER);
    }

    public void start() {
        
        while(true) {
            
            long now = System.currentTimeMillis(); //get milliseconds
            now = now/1000; //convert to second
            int x = 1;

            while (stop == false) {
                long xSecondsLater = now + x;
                long after = System.currentTimeMillis();
                after = after/1000;
                //System.out.println(after);

                if (after == xSecondsLater) { 
                    second0++;
                    x++;

                    if (second0 == 10) {
                        second0 = 0;
                        second1++;

                        if (second1 == 6) {
                            second1 = 0;
                            minute0++;

                            if (minute0 == 10) {
                                minute0 = 0;
                                minute1++;

                                if (minute1 == 6) {
                                    minute1 = 0;
                                    hour0++;

                                    if (hour0 == 10) {
                                        hour0 = 0;
                                        hour1++;

                                        if (hour1 == 24) {
                                            timer.setText("Give it a break, you've been playing for the entire day, you won't win!"); 
                                        }
                                    }
                                }
                            }
                        }
                    }
                timer.setText("Timer: " + hour1 + hour0 + ":" + minute1 + minute0 + ":" + second1 + second0);
                }
            }
            timer.setText("Timer: " + hour1 + hour0 + ":" + minute1 + minute0 + ":" + second1 + second0);
        }
    }

    public void stop() {
        stop = true; 
    }

    public void reset() {
        second0 = 0;
        second1 = 0;
        minute0 = 0;
        minute1 = 0;
        hour0 = 0;
        hour1 = 0;
        timer.setText("Timer: " + hour1 + hour0 + ":" + minute1 + minute0 + ":" + second1 + second0);
        stop = false; 
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame(); 
        JLabel timer = new JLabel("", SwingConstants.CENTER);
        StopWatch s = new StopWatch(timer);
        s.start();
    }
}