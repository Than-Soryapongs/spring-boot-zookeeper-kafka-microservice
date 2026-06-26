package com.room_booking.room_service.kafka;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.room_booking.room_service.mapper.RoomMapper;
import com.room_booking.room_service.model.entity.BookingEvent;
import com.room_booking.room_service.model.entity.Room;
import com.room_booking.room_service.model.entity.RoomEvent;
import com.room_booking.room_service.model.enums.RoomStatus;

@Component
public class RoomEventConsumer {

    private final RoomMapper roomMapper;
    private final RoomEventProducer roomEventProducer;

    public RoomEventConsumer(RoomMapper roomMapper, RoomEventProducer roomEventProducer) {
        this.roomMapper = roomMapper;
        this.roomEventProducer = roomEventProducer;
    }

    @KafkaListener(
            topics = "booking-created",
            groupId = "room-service-group",
            containerFactory = "bookingEventListenerFactory"
    )
    public void consumeBookingCreated(BookingEvent event) {

        System.out.println(
                "[Room Service] Received booking event for room: "
                        + event.getRoomId());

        Room room = roomMapper.findByRoomId(event.getRoomId());

        if (room == null) {
            System.err.println("[Room Service] Room not found: " + event.getRoomId());
            roomEventProducer.publishRoomReservationFailed(
                    buildFailedEvent(event, "Room not found"));
            return;
        }

        if (room.getStatus() == RoomStatus.RESERVED) {
            System.out.println("[Room Service] Room already reserved: " + room.getRoomId());
            roomEventProducer.publishRoomReservationFailed(
                    buildFailedEvent(event, "Room already reserved"));
            return;
        }

        if (room.getStatus() == RoomStatus.MAINTENANCE
                || room.getStatus() == RoomStatus.OCCUPIED) {
            System.out.println("[Room Service] Room unavailable: " + room.getRoomId());
            roomEventProducer.publishRoomReservationFailed(
                    buildFailedEvent(event, "Room unavailable"));
            return;
        }

        roomMapper.updateRoomStatus(event.getRoomId(), RoomStatus.RESERVED);

        RoomEvent reservedEvent = new RoomEvent();
        reservedEvent.setBookingId(event.getBookingId());
        reservedEvent.setRoomId(event.getRoomId());
        reservedEvent.setRoomType(room.getRoomType());
        reservedEvent.setCapacity(room.getCapacity());
        reservedEvent.setPricePerNight(room.getPricePerNight());
        reservedEvent.setTotalPrice(calculateTotalPrice(event, room.getPricePerNight()));
        reservedEvent.setDescription(room.getDescription());
        reservedEvent.setAmenities(room.getAmenities());
        reservedEvent.setStatus(RoomStatus.RESERVED);

        roomEventProducer.publishRoomReserved(reservedEvent);

        System.out.println("[Room Service] Room marked as RESERVED: " + event.getRoomId());
    }

    @KafkaListener(
        topics = "room-release",
        groupId = "room-service-group",
        containerFactory = "roomEventListenerFactory")
        public void consumeRoomRelease(RoomEvent event) {

            roomMapper.updateRoomStatus(
                    event.getRoomId(),
                    RoomStatus.AVAILABLE);

            System.out.println(
                    "[Room Service] Room released: "
                            + event.getRoomId());
        }

    // Helper to avoid repeating the same 2-field pattern for every failure branch
    private RoomEvent buildFailedEvent(BookingEvent source, String reason) {
        RoomEvent event = new RoomEvent();
        event.setBookingId(source.getBookingId());
        event.setRoomId(source.getRoomId());
        return event;
    }

    private BigDecimal calculateTotalPrice(BookingEvent bookingEvent, BigDecimal pricePerNight) {
        long nights = ChronoUnit.DAYS.between(
                bookingEvent.getCheckInDate(),
                bookingEvent.getCheckOutDate());

        if (nights <= 0) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }

        return pricePerNight.multiply(BigDecimal.valueOf(nights));
    }
}