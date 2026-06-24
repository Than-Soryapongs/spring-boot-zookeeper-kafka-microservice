package com.room_booking.room_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.room_booking.room_service.mapper.RoomMapper;
import com.room_booking.room_service.model.db.BookingEvent;
import com.room_booking.room_service.model.db.Room;

@Component
public class RoomEventConsumer {

    private final RoomMapper roomMapper;

    public RoomEventConsumer(RoomMapper roomMapper) {
        this.roomMapper = roomMapper;
    }

    @KafkaListener(
            topics = "booking-created",
            groupId = "room-service-group"
    )
    public void consumeBookingCreated(BookingEvent event) {

        System.out.println(
                "[Room Service] Received booking event for room: "
                        + event.getRoomId());

        Room room = roomMapper.findByRoomId(event.getRoomId());

        if (room == null) {
            System.err.println(
                    "[Room Service] Room not found: "
                            + event.getRoomId());
            return;
        }

        roomMapper.updateRoomStatus(
                event.getRoomId(),
                "BOOKED");

        System.out.println(
                "[Room Service] Room marked as BOOKED: "
                        + event.getRoomId());
    }
}