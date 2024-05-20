package de.dhbw.ase.contacts.domain.values;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Name {
    private String firstName;
    private String lastName;

    public Name(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Name(Name name) {
        this.firstName = name.getFirstName();
        this.lastName = name.getLastName();
    }

    public Name() {
        this.firstName = "";
        this.lastName = "";
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Name)) return false;
        Name name = (Name) o;
        return Objects.equals(getFirstName(), name.getFirstName()) && Objects.equals(getLastName(), name.getLastName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFirstName(), getLastName());
    }

    public String toString() {
        return this.firstName + " " + this.lastName;
    }
}
