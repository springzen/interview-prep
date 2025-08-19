package com.imwsoftware.invoiceservice.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imwsoftware.invoiceservice.kafka.InvoiceKafkaProducer;
import com.imwsoftware.invoiceservice.model.Invoice;
import com.imwsoftware.invoiceservice.model.LineItem;
import com.imwsoftware.invoiceservice.repository.InvoiceRepository;

@WebMvcTest(InvoiceController.class)
class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceRepository repository;

    @MockBean
    private InvoiceKafkaProducer producer;

    @Autowired
    private ObjectMapper objectMapper;

    private Invoice sampleInvoice() {
        return new Invoice(
            1L,
            "Test Customer",
            LocalDate.of(2025, 8, 19),
            List.of(new LineItem(null, "Widget", 3, BigDecimal.valueOf(9.99).doubleValue()))
        );
    }

    @Test
    void shouldReturnAllInvoices() throws Exception {
        when(repository.findAll()).thenReturn(Collections.singletonList(sampleInvoice()));

        mockMvc.perform(get("/api/invoices"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].recipientName").value("Test Customer"))
            .andExpect(jsonPath("$[0].lineItems[0].description").value("Widget"));
    }

    @Test
    void shouldCreateInvoice() throws Exception {
        Invoice input = sampleInvoice();
        input.setId(null);

        Invoice saved = sampleInvoice();

        when(repository.save(any(Invoice.class))).thenReturn(saved);
        doNothing().when(producer).send(eq("invoice.created"), any(Invoice.class));

        mockMvc.perform(post("/api/invoices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.lineItems[0].description").value("Widget"));
    }

    @Test
    void shouldUpdateInvoice() throws Exception {
        Invoice input = sampleInvoice();
        input.setId(null);

        Invoice updated = sampleInvoice();
        updated.setId(2L);

        when(repository.save(any(Invoice.class))).thenReturn(updated);
        doNothing().when(producer).send(eq("invoice.updated"), any(Invoice.class));

        mockMvc.perform(put("/api/invoices/2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(2L));
    }

    @Test
    void shouldDeleteInvoice() throws Exception {
        doNothing().when(repository).deleteById(3L);
        doNothing().when(producer).send(eq("invoice.deleted"), any(Invoice.class));

        mockMvc.perform(delete("/api/invoices/3"))
            .andExpect(status().isOk());

        verify(repository).deleteById(3L);
        verify(producer).send(eq("invoice.deleted"), argThat(i -> i.getId() == 3L));
    }
}
