package com.room_booking.booking_service.config;

import com.room_booking.booking_service.model.entity.PaymentEvent;
import com.room_booking.booking_service.model.entity.RoomEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    private Map<String, Object> baseConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "booking-service-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<String, RoomEvent> roomEventConsumerFactory() {
        Map<String, Object> props = baseConfig();
        JsonDeserializer<RoomEvent> deserializer = new JsonDeserializer<>(RoomEvent.class);
        deserializer.setUseTypeHeaders(false); // ignore publisher's class header
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, RoomEvent> roomEventListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, RoomEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(roomEventConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, PaymentEvent> paymentEventConsumerFactory() {
        Map<String, Object> props = baseConfig();
        JsonDeserializer<PaymentEvent> deserializer = new JsonDeserializer<>(PaymentEvent.class);
        deserializer.setUseTypeHeaders(false); // ignore publisher's class header
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentEvent> paymentEventListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, PaymentEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(paymentEventConsumerFactory());
        return factory;
    }
}
