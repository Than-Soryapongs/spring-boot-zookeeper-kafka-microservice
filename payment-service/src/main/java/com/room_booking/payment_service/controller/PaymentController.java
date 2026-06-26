package com.room_booking.payment_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.room_booking.payment_service.model.dto.request.PaymentRequest;
import com.room_booking.payment_service.model.dto.response.PaymentResponse;
import com.room_booking.payment_service.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/payments")
@Validated
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentResponse> createPayment(
            @Valid @RequestBody PaymentRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.createPayment(request));
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponse> getPaymentById(
            @PathVariable String paymentId) {

        return ResponseEntity.ok(
                paymentService.getPaymentById(paymentId));
    }
}