package com.room_booking.booking_service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.room_booking.booking_service.model.entity.BookingEvent;
import com.room_booking.booking_service.model.entity.RoomEvent;

@Component
public class BookingEventProducer {

    private static final String BOOKING_TOPIC = "booking-created";
    private static final String ROOM_RELEASE_TOPIC = "room-release";

    private final KafkaTemplate<String, BookingEvent> bookingTemplate;
    private final KafkaTemplate<String, RoomEvent> roomTemplate;

    public BookingEventProducer(
            KafkaTemplate<String, BookingEvent> bookingTemplate,
            KafkaTemplate<String, RoomEvent> roomTemplate) {

        this.bookingTemplate = bookingTemplate;
        this.roomTemplate = roomTemplate;
    }

    public void publishBookingCreated(BookingEvent event) {

        bookingTemplate.send(
                BOOKING_TOPIC,
                String.valueOf(event.getBookingId()),
                event);
    }

    public void publishRoomRelease(RoomEvent event) {

        roomTemplate.send(
                ROOM_RELEASE_TOPIC,
                event.getRoomId(),
                event);
    }
}