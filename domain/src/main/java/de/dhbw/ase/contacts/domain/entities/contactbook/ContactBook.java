package de.dhbw.ase.contacts.domain.entities.contactbook;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class ContactBook {
    @Id
    @GeneratedValue
    private UUID uuid;
    private String title;
    @ElementCollection
    private List<UUID> contacts;

    public ContactBook() {
        this.title = "";
        this.contacts = new ArrayList<>();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<UUID> getContacts() {
        return contacts;
    }

    public void setContacts(List<UUID> contacts) {
        this.contacts = contacts;
    }

    public void addContact(UUID uuid) {
        this.contacts.add(uuid);
    }

    public void removeContact(UUID uuid) {
        this.contacts.remove(uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactBook)) return false;
        ContactBook that = (ContactBook) o;
        return Objects.equals(getTitle(), that.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle());
    }
}
