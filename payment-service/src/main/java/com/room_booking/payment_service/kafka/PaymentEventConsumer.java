package com.room_booking.payment_service.kafka;

import java.util.UUID;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.room_booking.payment_service.model.entity.BookingEvent;
import com.room_booking.payment_service.model.entity.Payment;
import com.room_booking.payment_service.model.entity.PaymentEvent;
import com.room_booking.payment_service.model.enums.PaymentMethod;
import com.room_booking.payment_service.model.enums.PaymentStatus;
import com.room_booking.payment_service.mapper.PaymentMapper;

@Component
public class PaymentEventConsumer {

    private final PaymentMapper paymentMapper;
    private final PaymentEventProducer producer;

    public PaymentEventConsumer(
            PaymentMapper paymentMapper,
            PaymentEventProducer producer) {

        this.paymentMapper = paymentMapper;
        this.producer = producer;
    }

    @KafkaListener(
            topics = "room-reserved",
            groupId = "payment-service-group")
    public void consumeRoomReserved(BookingEvent event) {

        System.out.println(
                "[Payment Service] Received room reservation for booking "
                        + event.getBookingId());

        Payment payment = new Payment();

        payment.setPaymentId(UUID.randomUUID().toString());
        payment.setBookingId(String.valueOf(event.getBookingId()));
        payment.setAmount(event.getTotalPrice());
        payment.setPaymentMethod(PaymentMethod.BANK_TRANSFER);

        /*
         * Simulate payment
         */
        boolean paymentSuccess = true; // Simulate payment success or failure

        PaymentEvent paymentEvent = new PaymentEvent();

        paymentEvent.setPaymentId(payment.getPaymentId());
        paymentEvent.setBookingId(event.getBookingId());
        paymentEvent.setAmount(event.getTotalPrice().doubleValue());
        paymentEvent.setPaymentMethod(payment.getPaymentMethod().name());

        if (paymentSuccess) {

            payment.setStatus(PaymentStatus.SUCCESS);
            paymentEvent.setStatus(PaymentStatus.SUCCESS.name());

            paymentMapper.insert(payment);

            producer.publishPaymentSuccess(paymentEvent);

            System.out.println(
                    "[Payment Service] Payment completed.");
        } else {

            payment.setStatus(PaymentStatus.FAILED);
            paymentEvent.setStatus(PaymentStatus.FAILED.name());

            paymentMapper.insert(payment);

            producer.publishPaymentFailed(paymentEvent);

            System.out.println(
                    "[Payment Service] Payment failed.");
        }
    }
}