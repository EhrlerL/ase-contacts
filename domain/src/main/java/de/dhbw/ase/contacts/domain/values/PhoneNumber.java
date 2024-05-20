package de.dhbw.ase.contacts.domain.values;

import de.dhbw.ase.contacts.domain.values.enums.FieldType;
import jakarta.persistence.Embeddable;

import java.util.Objects;

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

    public boolean isValid() {
        if (this.fieldType == FieldType.SCHOOL) {
            return false;
        } else {
            return true;
        }
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneNumber)) return false;
        PhoneNumber that = (PhoneNumber) o;
        return getFieldType() == that.getFieldType() && Objects.equals(getPhoneNumber(), that.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFieldType(), getPhoneNumber());
    }
}
