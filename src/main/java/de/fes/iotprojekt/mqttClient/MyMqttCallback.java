package de.fes.iotprojekt.mqttClient;


import java.util.List;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MyMqttCallback implements MqttCallback  {

    List<MqttValue> al;
   

    public MyMqttCallback(List<MqttValue> al){
        this.al=al;

        System.out.println("Callback started!!");
    }
    @Override
    public void connectionLost(Throwable cause) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // TODO Auto-generated method stub


        System.out.println(message.toString());

        al.add(new MqttValue(message.toString(), topic));
        
      
        
    }
}

