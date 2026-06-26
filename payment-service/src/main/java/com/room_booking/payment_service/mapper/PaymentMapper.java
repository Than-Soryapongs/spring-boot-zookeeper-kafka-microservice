package com.room_booking.payment_service.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.room_booking.payment_service.model.entity.Payment;

@Mapper
public interface PaymentMapper {

    void insert(Payment payment);

    Payment findByPaymentId(String paymentId);

    Payment findByBookingId(String bookingId);

    void updateStatus(
            @Param("paymentId") String paymentId,
            @Param("status") String status);
}