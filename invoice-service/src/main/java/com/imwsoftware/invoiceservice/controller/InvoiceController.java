package com.imwsoftware.invoiceservice.controller;

import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.imwsoftware.invoiceservice.kafka.InvoiceKafkaProducer;
import com.imwsoftware.invoiceservice.model.Invoice;
import com.imwsoftware.invoiceservice.repository.InvoiceRepository;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceRepository repository;
    private final InvoiceKafkaProducer producer;

    @GetMapping
    public List<Invoice> all() {
        return repository.findAll();
    }

    @PostMapping
    public Invoice create(@RequestBody Invoice invoice) {
        Invoice saved = repository.save(invoice);
        producer.send("invoice.created", saved);
        return saved;
    }

    @PutMapping("/{id}")
    public Invoice update(@PathVariable Long id, @RequestBody Invoice invoice) {
        invoice.setId(id);
        Invoice updated = repository.save(invoice);
        producer.send("invoice.updated", updated);
        return updated;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
        producer.send("invoice.deleted", new Invoice(id, null, null, null));
    }
}
