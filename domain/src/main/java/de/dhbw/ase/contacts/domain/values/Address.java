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

    public boolean isValid() {
        if (this.fieldType == FieldType.MOBILE) {
            return false;
        } else {
            return true;
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

}