package de.fes.iotprojekt.views;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;

import de.fes.iotprojekt.mqttClient.MqttValue;

public class MainViewController {
    private UI ui;
    private Grid<MqttValue> grid;

    MainViewController(UI ui, Grid<MqttValue> grid){

        this.ui=ui;
        this.grid=grid;
    }

    public void updateGrid(){
        
        ui.access(() -> grid.getDataProvider().refreshAll());
    }
    
}
