package com.room_booking.payment_service.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.room_booking.payment_service.model.entity.PaymentEvent;

@Component
public class PaymentEventProducer {

    private static final String PAYMENT_SUCCESS_TOPIC =
            "payment-success";

    private static final String PAYMENT_FAILED_TOPIC =
            "payment-failed";

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public PaymentEventProducer(
            KafkaTemplate<String, PaymentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishPaymentSuccess(PaymentEvent event) {

        kafkaTemplate.send(
                PAYMENT_SUCCESS_TOPIC,
                String.valueOf(event.getBookingId()),
                event);

        System.out.println(
                "[Payment Service] Published PAYMENT_SUCCESS for booking "
                        + event.getBookingId());
    }

    public void publishPaymentFailed(PaymentEvent event) {

        kafkaTemplate.send(
                PAYMENT_FAILED_TOPIC,
                String.valueOf(event.getBookingId()),
                event);

        System.out.println(
                "[Payment Service] Published PAYMENT_FAILED for booking "
                        + event.getBookingId());
    }
}