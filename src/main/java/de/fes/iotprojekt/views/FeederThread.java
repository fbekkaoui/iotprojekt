package de.fes.iotprojekt.views;


public class FeederThread extends Thread {
    
    //TODO Thread
    public FeederThread() {
     
    }

    @Override
    public void run() {
        try {
            // Update the data for a while
            while (true) {
                // Sleep to emulate background work
                Thread.sleep(1000);  
            }
            // Inform that we are done   
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}