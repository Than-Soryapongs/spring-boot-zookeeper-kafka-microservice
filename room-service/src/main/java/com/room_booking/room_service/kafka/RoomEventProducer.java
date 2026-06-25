package com.room_booking.room_service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.room_booking.room_service.model.entity.RoomEvent;

@Component
public class RoomEventProducer {

    private static final String ROOM_RESERVED_TOPIC =
            "room-reserved";

    private static final String ROOM_RESERVATION_FAILED_TOPIC =
            "room-reservation-failed";

    private static final String ROOM_RELEASED_TOPIC =
            "room-released";

    private final KafkaTemplate<String, RoomEvent> kafkaTemplate;

    public RoomEventProducer(
            KafkaTemplate<String, RoomEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishRoomReserved(RoomEvent event) {

        kafkaTemplate.send(
                ROOM_RESERVED_TOPIC,
                event.getRoomId(),
                event);

        System.out.println(
                "[Room Service] Published ROOM_RESERVED for room: "
                        + event.getRoomId());
    }

    public void publishRoomReservationFailed(RoomEvent event) {

        kafkaTemplate.send(
                ROOM_RESERVATION_FAILED_TOPIC,
                event.getRoomId(),
                event);

        System.out.println(
                "[Room Service] Published ROOM_RESERVATION_FAILED for room: "
                        + event.getRoomId());
    }

    public void publishRoomReleased(RoomEvent event) {

        kafkaTemplate.send(
                ROOM_RELEASED_TOPIC,
                event.getRoomId(),
                event);

        System.out.println(
                "[Room Service] Published ROOM_RELEASED for room: "
                        + event.getRoomId());
    }
}