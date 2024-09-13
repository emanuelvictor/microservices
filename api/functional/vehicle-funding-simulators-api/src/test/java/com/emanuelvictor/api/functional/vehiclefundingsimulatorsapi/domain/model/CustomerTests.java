package com.emanuelvictor.api.functional.vehiclefundingsimulatorsapi.domain.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomerTests {

    @Test
    void mustCreateAInstanceOfCustomer() {
        final String document = "07124762944";
        final String contact = "45998261540";

        final Customer customer = new Customer(document, contact);

        Assertions.assertThat(customer.getDocument()).isEqualTo(document);
        Assertions.assertThat(customer.getContact()).isEqualTo(contact);
    }
}
