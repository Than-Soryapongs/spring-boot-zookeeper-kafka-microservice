package com.room_booking.booking_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.room_booking.booking_service.mapper.BookingMapper;
import com.room_booking.booking_service.model.entity.RoomEvent;
import com.room_booking.booking_service.model.enums.BookingStatus;

@Component
public class BookingEventConsumer {

    private final BookingMapper bookingMapper;

    public BookingEventConsumer(BookingMapper bookingMapper) {
        this.bookingMapper = bookingMapper;
    }

    @KafkaListener(
            topics = "room-reserved",
            groupId = "booking-service-group")
    public void consumeRoomReserved(RoomEvent event) {

        System.out.println(
                "[Room Service] Room reserved for booking: "
                        + event.getBookingId());

        bookingMapper.updateStatusAndTotalPrice(
                event.getBookingId(),
                BookingStatus.CONFIRMED.name(),
                event.getTotalPrice());
    }

    @KafkaListener(
            topics = "room-reservation-failed",
            groupId = "booking-service-group")
    public void consumeRoomReservationFailed(RoomEvent event) {

        System.out.println(
                "[Room Service] Room reservation failed for booking: "
                        + event.getBookingId());

        bookingMapper.updateStatus(
                event.getBookingId(),
                BookingStatus.REJECTED.name());
    }
}