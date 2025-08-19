package com.imwsoftware.invoiceservice.kafka;

import com.imwsoftware.invoiceservice.model.Invoice;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceKafkaProducer {
    private final KafkaTemplate<String, Invoice> kafkaTemplate;

    public void send(String topic, Invoice invoice) {
        kafkaTemplate.send(topic, invoice);
    }
}
