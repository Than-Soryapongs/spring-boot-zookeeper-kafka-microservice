package com.room_booking.booking_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.room_booking.booking_service.mapper.BookingMapper;
import com.room_booking.booking_service.model.entity.Booking;
import com.room_booking.booking_service.model.entity.PaymentEvent;
import com.room_booking.booking_service.model.entity.RoomEvent;
import com.room_booking.booking_service.model.enums.BookingStatus;

@Component
public class BookingEventConsumer {

    private final BookingMapper bookingMapper;
    private final BookingEventProducer producer;

    public BookingEventConsumer(BookingMapper bookingMapper, BookingEventProducer producer) {
        this.bookingMapper = bookingMapper;
        this.producer = producer;
    }

    @KafkaListener(
            topics = "room-reserved",
            groupId = "booking-service-group",
            containerFactory = "roomEventListenerFactory")
    public void consumeRoomReserved(RoomEvent event) {

        System.out.println(
                "[Booking Service] Room reserved for booking: "
                        + event.getBookingId());

        bookingMapper.updateStatusAndTotalPrice(
                event.getBookingId(),
                BookingStatus.WAITING_PAYMENT.name(),
                event.getTotalPrice());
    }

    @KafkaListener(
            topics = "room-reservation-failed",
            groupId = "booking-service-group",
            containerFactory = "roomEventListenerFactory")
    public void consumeRoomReservationFailed(RoomEvent event) {

        System.out.println(
                "[Booking Service] Room reservation failed for booking: "
                        + event.getBookingId());

        bookingMapper.updateStatus(
                event.getBookingId(),
                BookingStatus.REJECTED.name());
    }

    @KafkaListener(
            topics = "payment-success",
            groupId = "booking-service-group",
            containerFactory = "paymentEventListenerFactory")
    public void consumePaymentSuccess(PaymentEvent event) {

        System.out.println(
                "[Booking Service] Payment success for booking: "
                        + event.getBookingId());

        bookingMapper.updateStatus(
                event.getBookingId(),
                BookingStatus.CONFIRMED.name());
    }

    @KafkaListener(
            topics = "payment-failed",
            groupId = "booking-service-group",
            containerFactory = "paymentEventListenerFactory")
    public void consumePaymentFailed(PaymentEvent event) {

        System.out.println(
                "[Booking Service] Payment failed for booking: "
                        + event.getBookingId());

        bookingMapper.updateStatus(
                event.getBookingId(),
                BookingStatus.CANCELLED.name());

        Booking booking = bookingMapper.findById(event.getBookingId());

        if (booking == null) {
            System.err.println(
                    "[Booking Service] Booking not found for room release: "
                            + event.getBookingId());
            return;
        }

        RoomEvent roomEvent = new RoomEvent();
        roomEvent.setBookingId(booking.getId());
        roomEvent.setRoomId(booking.getRoomId());
        roomEvent.setStatus("RELEASE");

        producer.publishRoomRelease(roomEvent);
    }
}