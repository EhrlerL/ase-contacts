package de.dhbw.ase.contacts.application.services;

import de.dhbw.ase.contacts.domain.entities.contact.ContactBridgeRepository;
import de.dhbw.ase.contacts.domain.entities.contactbook.ContactBook;
import de.dhbw.ase.contacts.domain.entities.contactbook.ContactBookBridgeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactBookService {
    private final ContactBridgeRepository contactBridgeRepository;
    private final ContactBookBridgeRepository contactBookBridgeRepository;

    @Autowired
    public ContactBookService(ContactBridgeRepository contactBridgeRepository, ContactBookBridgeRepository contactBookBridgeRepository) {
        this.contactBridgeRepository = contactBridgeRepository;
        this.contactBookBridgeRepository = contactBookBridgeRepository;
    }

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

    public void renameContactBook(UUID uuid, String newTitle) {
        ContactBook contactBook = this.contactBookBridgeRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("ContactBook not found")
        );
        contactBook.setTitle(newTitle);
        this.contactBookBridgeRepository.save(contactBook);
    }

    public void deleteContactBook(UUID uuid) {
        this.contactBookBridgeRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("ContactBook not found")
        );
        this.contactBookBridgeRepository.deleteById(uuid);
    }

}