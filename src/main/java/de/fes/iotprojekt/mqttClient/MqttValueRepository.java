package de.fes.iotprojekt.mqttClient;



import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MqttValueRepository extends JpaRepository<MqttValue, UUID>{
    
}