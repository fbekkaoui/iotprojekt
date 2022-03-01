package de.fes.iotprojekt.mqttClient;


import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttPublisher {

    private MqttClient client;
    private MyMqttCallback myCallback;
   


    public MqttPublisher(String mqttBroker, String clientName)  {
 
        try {
             client=new MqttClient(mqttBroker, clientName);
        
        } catch (MqttException e) {
            e.printStackTrace();
            //System.exit(1);
        }
    }

    public void start(String user, String pwd, List<MqttValue> al){

        myCallback=new MyMqttCallback(al);
        client.setCallback(myCallback);

        MqttConnectOptions connOpts = new MqttConnectOptions();
	    connOpts.setCleanSession(true);//??
	    connOpts.setUserName(user);
        connOpts.setPassword(pwd.toCharArray());
    
        try {

            client.connect(connOpts);

        } catch (MqttException e){
            e.printStackTrace();
            //System.exit(1);
        }
        

    }

    public void subscribe(String topicFilter) {
        try {
            client.subscribe(topicFilter, 0);
        } catch (MqttException e){
            e.printStackTrace();
           //System.exit(1);
        }
    }

    public void publish(){
        //client.publish(topic, message);
    }
}