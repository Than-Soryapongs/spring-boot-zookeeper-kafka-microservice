package com.room_booking.payment_service.model.entity;

import java.math.BigDecimal;

import com.room_booking.payment_service.model.enums.PaymentMethod;
import com.room_booking.payment_service.model.enums.PaymentStatus;

public class Payment {
    private long id;
    private String paymentId;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private String bookingId;
    private PaymentStatus status;
    private String createdAt;
    private String updatedAt;

    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public String getPaymentId() {return paymentId;}
    public void setPaymentId(String paymentId) {this.paymentId = paymentId;}

    public BigDecimal getAmount() {return amount;}
    public void setAmount(BigDecimal amount) {this.amount = amount;}

    public PaymentMethod getPaymentMethod() {return paymentMethod;}
    public void setPaymentMethod(PaymentMethod paymentMethod) {this.paymentMethod = paymentMethod;}

    public String getBookingId() {return bookingId;}
    public void setBookingId(String bookingId) {this.bookingId = bookingId;}

    public PaymentStatus getStatus() {return status;}
    public void setStatus(PaymentStatus status) {this.status = status;}

    public String getCreatedAt() {return createdAt;}
    public void setCreatedAt(String createdAt) {this.createdAt = createdAt;}

    public String getUpdatedAt() {return updatedAt;}
    public void setUpdatedAt(String updatedAt) {this.updatedAt = updatedAt;}
}