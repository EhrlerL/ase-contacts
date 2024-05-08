package de.dhbw.ase.contacts.application.services;


import de.dhbw.ase.contacts.domain.entities.contact.Contact;
import de.dhbw.ase.contacts.domain.entities.contact.ContactBridgeRepository;
import de.dhbw.ase.contacts.domain.entities.contactbook.ContactBook;
import de.dhbw.ase.contacts.domain.entities.contactbook.ContactBookBridgeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        try {
            return this.contactBridgeRepository.findAll();
        } catch (Exception e) {
            System.out.println("ERROR: Could not find any contacts");
            return null;
        }
    }

    public boolean saveContact(Contact contact) {
        try {
            this.contactBridgeRepository.save(contact);
            return true;
        } catch (Exception e) {
            System.out.println("ERROR: Could not save contact");
            return false;
        }
    }

    public boolean deleteContact(UUID uuid) {
        try {
            List<ContactBook> contactBooks = this.contactBookBridgeRepository.findAll();
            contactBooks.forEach(book -> book.removeContact(uuid));
            this.contactBridgeRepository.deleteById(uuid);
            return true;
        } catch (Exception e) {
            System.out.println("ERROR: Could not delete contact with UUID: " + uuid);
            return false;
        }
    }

    /*public boolean linkContacts(UUID contactUuid, UUID linkedContactUuid) {
        try {
            Contact contact = this.contactBridgeRepository.findById(contactUuid);
            Contact linkedContact = this.contactBridgeRepository.findById(linkedContactUuid);

        }
    }*/

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

    public boolean deleteContactBook(UUID uuid) {
        try {
            this.contactBookBridgeRepository.deleteById(uuid);
            return true;
        } catch (Exception e) {
            System.out.println("ERROR: Could not delete contact book with UUID: " + uuid);
            return false;
        }
    }

}
