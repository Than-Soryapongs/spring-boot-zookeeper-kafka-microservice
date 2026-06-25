package com.room_booking.booking_service.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.room_booking.booking_service.model.enums.BookingStatus;

public class BookingEvent {
    private long bookingId;
    private String roomId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int adults;
    private int children;
    private BigDecimal totalPrice;
    private BookingStatus status;

    public long getBookingId() { return bookingId; }
    public void setBookingId(long bookingId) { this.bookingId = bookingId; }

    public String getRoomId() { return roomId; }
    public void setRoomId(String roomId) { this.roomId = roomId; }

    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }

    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }

    public int getAdults() { return adults; }
    public void setAdults(int adults) { this.adults = adults; }

    public int getChildren() { return children; }
    public void setChildren(int children) { this.children = children; }

    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }

    public BookingStatus getStatus() { return status; }
    public void setStatus(BookingStatus status) { this.status = status; }

}
