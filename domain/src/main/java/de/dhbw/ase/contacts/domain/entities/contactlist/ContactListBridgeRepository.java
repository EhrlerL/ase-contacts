package de.dhbw.ase.contacts.domain.entities.contactlist;

import de.dhbw.ase.contacts.domain.entities.contact.Contact;

import java.util.List;
import java.util.UUID;

public interface ContactListBridgeRepository {
    void save(ContactList contactList);
    ContactList findById(UUID uuid);
    List<ContactList> findAll();
    void deleteById(UUID uuid);
}
