package com.room_booking.payment_service.service;

import org.springframework.stereotype.Service;

import com.room_booking.payment_service.mapper.PaymentMapper;
import com.room_booking.payment_service.model.entity.Payment;
import com.room_booking.payment_service.model.enums.PaymentStatus;
import com.room_booking.payment_service.model.dto.request.PaymentRequest;
import com.room_booking.payment_service.model.dto.response.PaymentResponse;

@Service
public class PaymentService {

    private final PaymentMapper paymentMapper;

    public PaymentService(PaymentMapper paymentMapper) {
        this.paymentMapper = paymentMapper;
    }

    public PaymentResponse createPayment(PaymentRequest request) {

        Payment payment = new Payment();

        payment.setPaymentId(request.getPaymentId());
        payment.setBookingId(request.getBookingId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setStatus(PaymentStatus.PENDING);

        paymentMapper.insert(payment);

        Payment savedPayment =
                paymentMapper.findByPaymentId(payment.getPaymentId());

        return PaymentResponse.from(savedPayment);
    }

    public PaymentResponse getPaymentById(String paymentId) {

        Payment payment = paymentMapper.findByPaymentId(paymentId);

        if (payment == null) {
            throw new IllegalArgumentException(
                    "Payment not found: " + paymentId);
        }

        return PaymentResponse.from(payment);
    }

    public void updatePaymentStatus(String paymentId, String status) {

        Payment payment = paymentMapper.findByPaymentId(paymentId);

        if (payment == null) {
            throw new IllegalArgumentException(
                    "Payment not found: " + paymentId);
        }

        paymentMapper.updateStatus(paymentId, status);
    }
}