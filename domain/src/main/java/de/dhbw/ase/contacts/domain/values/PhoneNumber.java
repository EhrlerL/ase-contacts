package de.dhbw.ase.contacts.domain.values;

import de.dhbw.ase.contacts.domain.values.enums.FieldType;
import jakarta.persistence.Embeddable;

@Embeddable
public class PhoneNumber {
    private final FieldType fieldType;
    private final String phoneNumber;

    public PhoneNumber(FieldType fieldType, String phoneNumber) {
        this.fieldType = fieldType;
        this.phoneNumber = phoneNumber;
    }

    public PhoneNumber() {
        this.fieldType = null;
        this.phoneNumber = "";
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

}
