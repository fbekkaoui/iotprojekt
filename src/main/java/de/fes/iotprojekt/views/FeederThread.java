package de.fes.iotprojekt.views;

import java.util.List;

import de.fes.iotprojekt.mqttClient.MqttPublisher;
import de.fes.iotprojekt.mqttClient.MqttValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;

public class FeederThread extends Thread {
    
   

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