package com.room_booking.booking_service.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.room_booking.booking_service.model.entity.Booking;

@Mapper
public interface BookingMapper {
    void insert(Booking booking);
    Booking findById(long id);

    void updateStatus(
            @Param("bookingId") long bookingId,
            @Param("status") String status);

        void updateStatusAndTotalPrice(
            @Param("bookingId") long bookingId,
            @Param("status") String status,
            @Param("totalPrice") BigDecimal totalPrice);
}
