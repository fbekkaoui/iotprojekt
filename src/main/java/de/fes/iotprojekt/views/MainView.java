package de.fes.iotprojekt.views;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.LocalDateTimeRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;

import de.fes.iotprojekt.mqttClient.MqttPublisher;
import de.fes.iotprojekt.mqttClient.MqttValue;

@PWA(name = "IoTProjekt", shortName = "IoTProjekt", enableInstallPrompt = false)
@Theme(themeFolder = "iotprojekt")
@PageTitle("Main")
@Route(value = "main")
@RouteAlias(value = "")
@Push
public class MainView extends VerticalLayout {

    private Grid<MqttValue> grid;
    private List<MqttValue> mqttValues;
    private String mqTTBrokerAdress = "tcp://192.168.178.53:1883";

    private FeederThread thread;

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        //add(new Span("Waiting for updates"));

        // Start the data feed thread
        thread = new FeederThread(attachEvent.getUI(), grid, new MqttPublisher(mqTTBrokerAdress, "Client-01"), mqttValues);
        thread.start();

    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
        thread.interrupt();
        thread = null;
    }
    

    public MainView() {
        mqttValues = new ArrayList<>();
      
        grid = new Grid<>(MqttValue.class, false);
        grid.setItems(mqttValues);

        grid.addColumn(MqttValue::getTopic).setHeader("Pfad").setSortable(true);
        grid.addColumn(MqttValue::getMessage).setHeader("Nachricht").setSortable(true);
        grid.addColumn(new LocalDateTimeRenderer<>(MqttValue::getTimeStamp,
        "dd.MM.YYYY hh:mm:ss")).setHeader("Zeitstempel").setSortable(true)
        .setComparator(MqttValue::getTimeStamp);

       
       
        Span title= new Span("Mqtt Broker: "+mqTTBrokerAdress);
        setSizeFull();

        TextField text= new TextField("Hallo Jungs");
       add(title, text, grid);
       
    }

}
