package de.dhbw.ase.contacts.domain.values;

import de.dhbw.ase.contacts.domain.values.enums.FieldType;
import jakarta.persistence.Embeddable;

@Embeddable
public class Email {
    private FieldType fieldType;
    private String email;

    public Email(FieldType fieldType, String email) {
        this.fieldType = validateFieldType(fieldType);
        this.email = email;
    }

    public Email() {
        this.fieldType = null;
        this.email = "";
    }

    private FieldType validateFieldType(FieldType fieldType) {
        try{
            if (fieldType == FieldType.MOBILE) {
                throw new Exception("Email must not be from type \"mobile\"");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return fieldType;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public String getEmail() {
        return email;
    }
}
