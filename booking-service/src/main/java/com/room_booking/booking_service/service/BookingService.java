package com.room_booking.booking_service.service;

import org.springframework.stereotype.Service;

import com.room_booking.booking_service.kafka.BookingEventProducer;
import com.room_booking.booking_service.mapper.BookingMapper;
import com.room_booking.booking_service.model.db.Booking;
import com.room_booking.booking_service.model.db.BookingEvent;
import com.room_booking.booking_service.model.dto.request.BookingRequest;
import com.room_booking.booking_service.model.dto.response.BookingResponse;

@Service
public class BookingService {

    private final BookingMapper bookingMapper;
    private final BookingEventProducer producer;

    public BookingService(BookingMapper bookingMapper, BookingEventProducer producer) {
        this.bookingMapper = bookingMapper;
        this.producer = producer;
    }

    public BookingResponse createBooking(BookingRequest request) {
        
        // Request -> Entity
        Booking booking = new Booking();
        booking.setRoomId(request.getRoomId());
        booking.setCheckInDate(request.getCheckInDate());
        booking.setCheckOutDate(request.getCheckOutDate());
        booking.setAdults(request.getAdults());
        booking.setChildren(request.getChildren());

        booking.setStatus("PENDING");

        bookingMapper.insert(booking);

        // 3. Build Kafka event
        BookingEvent event = new BookingEvent();
        event.setBookingId(booking.getId());
        event.setRoomId(booking.getRoomId());
        event.setCheckInDate(booking.getCheckInDate());
        event.setCheckOutDate(booking.getCheckOutDate());
        event.setAdults(booking.getAdults());
        event.setChildren(booking.getChildren());
        event.setTotalPrice(booking.getTotalPrice());
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
