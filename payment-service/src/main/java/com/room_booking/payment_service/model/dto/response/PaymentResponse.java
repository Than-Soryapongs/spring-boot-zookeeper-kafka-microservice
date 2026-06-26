package com.room_booking.payment_service.model.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.room_booking.payment_service.model.entity.Payment;
import com.room_booking.payment_service.model.enums.PaymentMethod;
import com.room_booking.payment_service.model.enums.PaymentStatus;

public record PaymentResponse(
    long id,
    String paymentId,
    BigDecimal amount,
    PaymentMethod paymentMethod,
    String bookingId,
    PaymentStatus status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static PaymentResponse from(Payment payment) {
        return new PaymentResponse(
            payment.getId(),
            payment.getPaymentId(),
            payment.getAmount(),
            payment.getPaymentMethod(),
            payment.getBookingId(),
            payment.getStatus(),
            LocalDateTime.parse(payment.getCreatedAt()),
            LocalDateTime.parse(payment.getUpdatedAt())
        );
    }
}
