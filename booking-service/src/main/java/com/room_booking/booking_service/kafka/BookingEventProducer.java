package com.room_booking.booking_service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.room_booking.booking_service.model.entity.BookingEvent;

@Component
public class BookingEventProducer {

    private static final String TOPIC = "booking-created";

    private final KafkaTemplate<String, BookingEvent> kafkaTemplate;

    public BookingEventProducer(
            KafkaTemplate<String, BookingEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishBookingCreated(BookingEvent event) {
        kafkaTemplate.send(
                TOPIC,
                String.valueOf(event.getBookingId()),
                event);
        System.out.println(
                "[Booking Service] Published booking event for room: "
                        + event.getRoomId());
    }
}