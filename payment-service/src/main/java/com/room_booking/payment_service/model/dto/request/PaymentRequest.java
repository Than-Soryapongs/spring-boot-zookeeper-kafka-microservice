package com.room_booking.payment_service.model.dto.request;

import java.math.BigDecimal;

import com.room_booking.payment_service.model.enums.PaymentMethod;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PaymentRequest {
    @NotBlank(message = "Room ID is required")
    private String paymentId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    private BigDecimal amount;

    @NotBlank(message = "Payment method is required")
    private PaymentMethod paymentMethod;

    @NotBlank(message = "Booking ID is required")
    private String bookingId;

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }

    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }
}
