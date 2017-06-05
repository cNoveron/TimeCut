package net.sf.timecut.ui.swt.notifications;

import net.sf.timecut.ui.swt.SWTWindow;

public class NotificationManager {
    
    private NotificationWindow notificationWindow;
    private Thread notificationThread;
    private String message;
    
    public NotificationManager(SWTWindow Window) {
        this.notificationWindow = new NotificationWindow(Window);
        this.notificationThread = new Thread(new Notifier());
    }
    

    public void sendMessage(String message) {
        synchronized (message) {
            if (!this.notificationThread.isAlive()) {
                this.notificationThread.start();
            }
            this.message = message;
        }
    }
    
    private class Notifier implements Runnable {

        public void run() {
            while (true) {
                if (message != null) {
                    synchronized (message) {
                        notificationWindow.showMessage(message);
                        for (int i = 0; i <= 255; i += 10) {
                            notificationWindow.setAlpha(i);
                            doWait(100);
                        }
                        doWait(1000);
                        for (int i = 255; i >= 0; i -= 10) {
                            notificationWindow.setAlpha(i);
                            doWait(100);
                        }
                        notificationWindow.close();
                        message = null;
                    }
                }
                doWait(1000);
            }
        }
        
    }
    
    private void doWait(long ms) {
        try {
            Thread.sleep(ms);
        }
        catch (InterruptedException e) {
            // ignore
        }
    }

}
