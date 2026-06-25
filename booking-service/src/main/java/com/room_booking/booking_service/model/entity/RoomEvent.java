package com.room_booking.booking_service.model.entity;

import java.math.BigDecimal;

public class RoomEvent {
    private long bookingId;
    private String roomId;
    private String roomType;
    private int capacity;
    private BigDecimal pricePerNight;
    private BigDecimal totalPrice;
    private String description;
    private String amenities;
    private String status;

    public long getBookingId() {return bookingId;}
    public void setBookingId(long bookingId) {this.bookingId = bookingId;}

    public String getRoomId() {return roomId;}
    public void setRoomId(String roomId) {this.roomId = roomId;}

    public String getRoomType() {return roomType;}
    public void setRoomType(String roomType) {this.roomType = roomType;}

    public int getCapacity() {return capacity;}
    public void setCapacity(int capacity) {this.capacity = capacity;}

    public BigDecimal getPricePerNight() {return pricePerNight;}
    public void setPricePerNight(BigDecimal pricePerNight) {this.pricePerNight = pricePerNight;}

    public BigDecimal getTotalPrice() {return totalPrice;}
    public void setTotalPrice(BigDecimal totalPrice) {this.totalPrice = totalPrice;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public String getAmenities() {return amenities;}
    public void setAmenities(String amenities) {this.amenities = amenities;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
}
