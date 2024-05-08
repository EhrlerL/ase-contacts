package de.dhbw.ase.contacts.domain.entities.contactbook;

import de.dhbw.ase.contacts.domain.entities.contact.Contact;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class ContactBook {
    @Id
    @GeneratedValue
    private UUID uuid;
    private String title;
    @ElementCollection
    private List<UUID> contacts;
    public ContactBook(String title, List<UUID> contacts) {
        this.title = title;
        this.contacts = contacts;
    }

    public ContactBook() {
        this.title = "";
        this.contacts = new ArrayList<>();
    }

    public ContactBook(String title) {
        this.title = title;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public List<UUID> getContacts() {
        return contacts;
    }

    public void addContact(UUID uuid) {
        this.contacts.add(uuid);
    }

    public void removeContact(UUID uuid) {
        this.contacts.remove(uuid);
    }
}
