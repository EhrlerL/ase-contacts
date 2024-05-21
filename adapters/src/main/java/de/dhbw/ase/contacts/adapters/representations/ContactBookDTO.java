package de.dhbw.ase.contacts.adapters.representations;

import java.util.Objects;

public class ContactBookDTO {
    private final String title;

    public ContactBookDTO(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactBookDTO that)) return false;
        return Objects.equals(getTitle(), that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }
}
