package de.fes.iotprojekt.views;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
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
import de.fes.iotprojekt.mqttClient.MyMqttCallback;

@PWA(name = "IoTProjekt", shortName = "IoTProjekt", enableInstallPrompt = false)
@Theme(themeFolder = "iotprojekt")
@PageTitle("Main")
@Route(value = "main")
@RouteAlias(value = "")
@Push
public class MainView extends VerticalLayout {

    private Grid<MqttValue> grid;
    private List<MqttValue> mqttValues;
    private MqttPublisher publisher;
    private MyMqttCallback myCallback;
    private MainViewController mainViewController;

    private String brokerAdress="tcp://localhost:1883";

    //Thread bsp, falls etwas im Hintergrund wiederholend durchgeführt werden soll
    //private FeederThread thread;


    @Override
    protected void onAttach(AttachEvent attachEvent) {

        //attach 
        mainViewController= new MainViewController(attachEvent.getUI(), grid);

        myCallback=new MyMqttCallback(mqttValues, mainViewController);
        publisher = new MqttPublisher(brokerAdress, "Client-01", myCallback);
        publisher.start("user", "passwd", mqttValues);
        publisher.subscribe("#");
        
        System.out.println("onAttach");

        //thread = new FeederThread(TODO thread was mitgeben?);
        //thread.run();
        
        
    }

    @Override
    protected void onDetach(DetachEvent detachEvent) {
      // TODO document why this method is empty
      //thread.interrupt();
      //thread = null;
    }
    

    public MainView() {
          
        mqttValues = new ArrayList<>();

       
        add(new Span("Mqtt Broker Adress: "+brokerAdress));
        
        setSizeFull();

        genExamplePublish();
        genExampleSubscribe();
    }

    public void genExampleSubscribe(){

        grid = new Grid<>(MqttValue.class, false);
        grid.setItems(mqttValues);

        grid.addColumn(MqttValue::getTopic).setHeader("Pfad").setSortable(true);
        grid.addColumn(MqttValue::getMessage).setHeader("Nachricht").setSortable(true);
        grid.addColumn(new LocalDateTimeRenderer<>(MqttValue::getTimeStamp,"dd.MM.YYYY hh:mm:ss"))
            .setHeader("Zeitstempel").setSortable(true).setComparator(MqttValue::getTimeStamp);
          
        grid.setSelectionMode(SelectionMode.SINGLE);
        grid.addItemClickListener(event -> Notification.show(event.getItem().toString()));
        
       add(grid);

    }

    public void genExamplePublish(){
        
        
        HorizontalLayout ePublish= new HorizontalLayout();

        Button btnPublish=new Button("Publish");
        TextField tfMessage = new TextField("Message");
        TextField tfTopic = new TextField("Topic");

        btnPublish.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        btnPublish.addClickListener(e-> {
            publisher.publish(tfTopic.getValue(), tfMessage.getValue());
        });

        ePublish.setVerticalComponentAlignment(FlexComponent.Alignment.END, btnPublish);
        ePublish.add(tfTopic, tfMessage, btnPublish);
   
        add(ePublish);

    }

}
