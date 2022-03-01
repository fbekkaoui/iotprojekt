package de.fes.iotprojekt.views;

import java.util.List;

import de.fes.iotprojekt.mqttClient.MqttPublisher;
import de.fes.iotprojekt.mqttClient.MqttValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;

public class FeederThread extends Thread {
    private final UI ui;
    private final Grid<MqttValue> view;
    private final MqttPublisher publisher;
    private List<MqttValue> mqttValues;

   

    public FeederThread(UI ui, Grid<MqttValue> view, MqttPublisher publisher, List<MqttValue> mqttValues) {
        this.mqttValues=mqttValues;
        this.publisher=publisher;
        this.ui = ui;
        this.view = view;
    }

    @Override
    public void run() {
        try {

            publisher.start("user", "passwd", mqttValues);
            publisher.subscribe("/test");
            

            // Update the data for a while
            while (true) {
                // Sleep to emulate background work
                Thread.sleep(100);
                ui.access(() -> view.getDataProvider().refreshAll());
            }

            // Inform that we are done
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}