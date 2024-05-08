package de.dhbw.ase.contacts.application.services;


import de.dhbw.ase.contacts.domain.entities.contact.Contact;
import de.dhbw.ase.contacts.domain.entities.contact.ContactBridgeRepository;
import de.dhbw.ase.contacts.domain.entities.contactbook.ContactBook;
import de.dhbw.ase.contacts.domain.entities.contactbook.ContactBookBridgeRepository;
import de.dhbw.ase.contacts.domain.values.enums.ContactConnection;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactService {
    private final ContactBridgeRepository contactBridgeRepository;
    private final ContactBookBridgeRepository contactBookBridgeRepository;

    @Autowired
    public ContactService(ContactBridgeRepository contactBridgeRepository, ContactBookBridgeRepository contactBookBridgeRepository) {
        this.contactBridgeRepository = contactBridgeRepository;
        this.contactBookBridgeRepository = contactBookBridgeRepository;
    }

    /**
     *
     *  /contact endpoint
     *
     */

    public Contact getContact(UUID uuid) {
        return this.contactBridgeRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("Contact not found")
        );
    }

    public List<Contact> getAllContacts() {
        return this.contactBridgeRepository.findAll();
    }

    public void saveContact(Contact contact) {
        this.contactBridgeRepository.save(contact);
    }

    public void deleteContact(UUID uuid) {
        this.contactBridgeRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("Contact not found")
        );
        List<ContactBook> contactBooks = this.contactBookBridgeRepository.findAll();
        contactBooks.forEach(book -> {
            book.removeContact(uuid);
            this.contactBookBridgeRepository.save(book);
        });

        this.contactBridgeRepository.deleteById(uuid);
    }

    public void linkContacts(UUID contactUuid, UUID linkedContactUuid, ContactConnection connection) {
        Contact contact = this.contactBridgeRepository.findById(contactUuid).orElseThrow(
                () -> new EntityNotFoundException("Contact not found")
        );
        Contact linkedContact = this.contactBridgeRepository.findById(linkedContactUuid).orElseThrow(
                () -> new EntityNotFoundException("Contact not found")
        );
        contact.addLinkedContact(connection, linkedContact);
    }

    /**
     *
     *  /contactbook endpoint
     *
     */

    public ContactBook getContactBook(UUID uuid) {
        return this.contactBookBridgeRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("ContactBook not found")
        );
    }

    public List<ContactBook> getAllContactBooks() {
        return this.contactBookBridgeRepository.findAll();
    }

    public void saveContactBook(ContactBook contactBook) {
        this.contactBookBridgeRepository.save(contactBook);
    }

    public void addContactToContactBook(UUID contactBookUuid, UUID contactUuid) {
        ContactBook contactBook = this.contactBookBridgeRepository.findById(contactBookUuid).orElseThrow(
                () -> new EntityNotFoundException("ContactBook not found")
        );
        this.contactBridgeRepository.findById(contactUuid).orElseThrow(
                () -> new EntityNotFoundException("Contact not found")
        );
        contactBook.addContact(contactUuid);
        this.contactBookBridgeRepository.save(contactBook);

    }

    public void removeContactFromContactBook(UUID contactBookUuid, UUID contactUuid) {
        ContactBook contactBook = this.contactBookBridgeRepository.findById(contactBookUuid).orElseThrow(
                () -> new EntityNotFoundException("ContactBook not found")
        );
        this.contactBridgeRepository.findById(contactUuid).orElseThrow(
                () -> new EntityNotFoundException("Contact not found")
        );
        contactBook.removeContact(contactUuid);
        this.contactBookBridgeRepository.save(contactBook);
    }

    public void deleteContactBook(UUID uuid) {
        this.contactBookBridgeRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("ContactBook not found")
        );
        this.contactBookBridgeRepository.deleteById(uuid);
    }

}
