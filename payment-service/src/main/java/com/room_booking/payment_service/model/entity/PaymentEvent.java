package com.room_booking.payment_service.model.entity;

public class PaymentEvent {
    private String paymentId;
    private long bookingId;
    private double amount;
    private String paymentMethod;
    private String status;

    public String getPaymentId() {return paymentId;}
    public void setPaymentId(String paymentId) {this.paymentId = paymentId;}

    public long getBookingId() {return bookingId;}
    public void setBookingId(long bookingId) {this.bookingId = bookingId;}

    public double getAmount() {return amount;}
    public void setAmount(double amount) {this.amount = amount;}

    public String getPaymentMethod() {return paymentMethod;}
    public void setPaymentMethod(String paymentMethod) {this.paymentMethod = paymentMethod;}

    public String getStatus() {return status;}
    public void setStatus(String status) {this.status = status;}
}