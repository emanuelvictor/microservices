package com.emanuelvictor.api.functional.vehiclefinancingsimulatorsapi.domain.entities;

public class Customer {

    private String document;


    // TODO must be dynamic
    private String contact;

    public Customer(String document, String contact) {
        this.document = document;
        this.contact = contact;
    }

    public String getDocument() {
        return document;
    }

    public String getContact() {
        return contact;
    }
}
