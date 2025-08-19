package com.imwsoftware.invoiceservice.repository;

import com.imwsoftware.invoiceservice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}