package com.imwsoftware.invoiceservice.kafka;

import lombok.extern.slf4j.Slf4j;
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
}
