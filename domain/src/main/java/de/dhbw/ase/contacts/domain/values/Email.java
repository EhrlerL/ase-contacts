package de.dhbw.ase.contacts.domain.values;

import de.dhbw.ase.contacts.domain.values.enums.FieldType;
import jakarta.persistence.Embeddable;

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
}
