package de.dhbw.ase.contacts.domain.values;

import de.dhbw.ase.contacts.domain.values.enums.FieldType;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Email {
    private FieldType fieldType;
    private String email;

    public Email(FieldType fieldType, String email) {
        this.fieldType = fieldType;
        this.email = email;
    }

    public Email() {
        this.fieldType = null;
        this.email = "";
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

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email)) return false;
        Email email1 = (Email) o;
        return getFieldType() == email1.getFieldType() && Objects.equals(getEmail(), email1.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFieldType(), getEmail());
    }
}
