package com.imwsoftware.invoiceservice.config;

import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import com.imwsoftware.invoiceservice.model.Invoice;

@Configuration
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, Invoice> invoiceConsumerFactory() {
        // Construct JsonDeserializer with "false" to disable Spring Boot property-based config
        JsonDeserializer<Invoice> deserializer = new JsonDeserializer<>(Invoice.class, false);
        deserializer.addTrustedPackages("*");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "invoice-log-group");
        // Do not configure TRUSTED_PACKAGES here to avoid dual config path

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Invoice> invoiceKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Invoice> factory =
            new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(invoiceConsumerFactory());
        return factory;
    }
}
