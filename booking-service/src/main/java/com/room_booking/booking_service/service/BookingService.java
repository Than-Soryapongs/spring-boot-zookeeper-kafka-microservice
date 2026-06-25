package com.room_booking.booking_service.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.room_booking.booking_service.kafka.BookingEventProducer;
import com.room_booking.booking_service.mapper.BookingMapper;
import com.room_booking.booking_service.model.dto.request.BookingRequest;
import com.room_booking.booking_service.model.dto.response.BookingResponse;
import com.room_booking.booking_service.model.entity.Booking;
import com.room_booking.booking_service.model.entity.BookingEvent;
import com.room_booking.booking_service.model.enums.BookingStatus;

@Service
public class BookingService {

    private final BookingMapper bookingMapper;
    private final BookingEventProducer producer;

    public BookingService(BookingMapper bookingMapper, BookingEventProducer producer) {
        this.bookingMapper = bookingMapper;
        this.producer = producer;
    }

    public BookingResponse createBooking(BookingRequest request) {

        if (!request.getCheckOutDate().isAfter(request.getCheckInDate())) {
            throw new IllegalArgumentException("Check-out date must be after check-in date");
        }
        
        // Request -> Entity
        Booking booking = new Booking();
        booking.setRoomId(request.getRoomId());
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setAdults(request.getAdults());
        booking.setChildren(request.getChildren());
        booking.setTotalPrice(BigDecimal.ZERO);

        booking.setStatus(BookingStatus.PENDING);

        bookingMapper.insert(booking);

        // 3. Build Kafka event
        BookingEvent event = new BookingEvent();
        event.setBookingId(booking.getId());
        event.setRoomId(booking.getRoomId());
        event.setCheckInDate(booking.getCheckInDate());
        event.setCheckOutDate(booking.getCheckOutDate());
        event.setAdults(booking.getAdults());
        event.setChildren(booking.getChildren());
        event.setStatus(booking.getStatus());


        // 4. Publish event
        producer.publishBookingCreated(event);

        return BookingResponse.from(booking);
    }

    public BookingResponse getBookingById(Long id) {

        Booking booking = bookingMapper.findById(id);

        if (booking == null) {
            throw new IllegalArgumentException(
                    "Booking not found: " + id);
        }

        return BookingResponse.from(booking);
    }
}
