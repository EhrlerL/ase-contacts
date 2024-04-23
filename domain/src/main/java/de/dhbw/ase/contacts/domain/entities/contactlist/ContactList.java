package de.dhbw.ase.contacts.domain.entities.contactlist;

import de.dhbw.ase.contacts.domain.entities.contact.Contact;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.List;
import java.util.UUID;

public class ContactList {
    @Id
    @GeneratedValue
    private UUID uuid;
    private String title;
    @ManyToMany
    private List<Contact> contacts;

    public ContactList(String title, List<Contact> contacts) {
        this.title = title;
        this.contacts = contacts;
    }

    public ContactList(String title) {
        this.title = title;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public List<Contact> getContacts() {
        return contacts;
    }

    public void addContact(Contact contact) {
        this.contacts.add(contact);
    }

    public void removeContact(Contact contact) {
        this.contacts.remove(contact);
    }
}
