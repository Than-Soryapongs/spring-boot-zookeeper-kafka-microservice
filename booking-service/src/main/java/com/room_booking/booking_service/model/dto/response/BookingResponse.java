package com.room_booking.booking_service.model.dto.response;

import com.room_booking.booking_service.model.db.Booking;

public record BookingResponse (
    long id,
    String roomId,
    String checkInDate,
    String checkOutDate,
    int adults,
    int children,
    String totalPrice,
    String status
) {
    public static BookingResponse from(Booking booking) {
        return new BookingResponse(
            booking.getId(),
            booking.getRoomId(),
            booking.getCheckInDate().toString(),
            booking.getCheckOutDate().toString(),
            booking.getAdults(),
            booking.getChildren(),
            booking.getTotalPrice().toString(),
            booking.getStatus()
        );
    }
}
