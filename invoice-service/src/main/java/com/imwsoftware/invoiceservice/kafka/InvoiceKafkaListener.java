package com.imwsoftware.invoiceservice.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.imwsoftware.invoiceservice.model.Invoice;

@Service
@Slf4j
public class InvoiceKafkaListener {

    @KafkaListener(
        topics = {"invoice.created", "invoice.updated", "invoice.deleted"},
        groupId = "invoice-log-group",
        containerFactory = "invoiceKafkaListenerContainerFactory"
    )
    public void listen(Invoice invoice) {
        log.info("Received invoice event: {}", invoice);
    }

    @KafkaListener(topics = "invoice.DLT", groupId = "invoice-dlt-group")
    public void handlePoisonMessage(ConsumerRecord<String, String> record) {
        log.error("Poison pill received: {}", record.value());
        // Save to DB / alerting / auditing, etc.
    }
}
