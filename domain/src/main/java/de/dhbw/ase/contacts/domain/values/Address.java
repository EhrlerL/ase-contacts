package de.dhbw.ase.contacts.domain.values;

import de.dhbw.ase.contacts.domain.values.enums.FieldType;
import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private final FieldType fieldType;
    private final String street;
    private final String city;
    private final String zipCode;

    public Address(FieldType fieldType, String street, String city, String zipCode) {
        this.fieldType = fieldType;
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
    }

    public Address() {
        this.fieldType = null;
        this.street = "";
        this.city = "";
        this.zipCode = "";
    }

    private FieldType validateFieldType(FieldType fieldType) throws Exception {
        if (fieldType == FieldType.MOBILE) {
            throw new Exception("Address must not be from type \"mobile\"");
        } else {
            return fieldType;
        }
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getAddress() {
        return this.street + ", " + this.zipCode + " " + this.city;
    }
}
