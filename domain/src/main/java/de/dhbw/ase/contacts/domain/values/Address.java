package de.dhbw.ase.contacts.domain.values;

import de.dhbw.ase.contacts.domain.values.enums.FieldType;
import jakarta.persistence.Embeddable;

import java.util.Objects;

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

    public boolean isInvalid() {
        return this.fieldType == FieldType.MOBILE;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return getFieldType() == address.getFieldType() && Objects.equals(getStreet(), address.getStreet()) && Objects.equals(getCity(), address.getCity()) && Objects.equals(getZipCode(), address.getZipCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFieldType(), getStreet(), getCity(), getZipCode());
    }
}