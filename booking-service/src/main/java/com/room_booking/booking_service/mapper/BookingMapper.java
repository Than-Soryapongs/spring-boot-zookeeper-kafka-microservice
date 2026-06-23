package com.room_booking.booking_service.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.room_booking.booking_service.model.db.Booking;

@Mapper
public interface BookingMapper {
    void insert(Booking booking);
    Booking findById(long id);
}
