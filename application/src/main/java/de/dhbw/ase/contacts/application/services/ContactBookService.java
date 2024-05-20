package de.dhbw.ase.contacts.application.services;

import de.dhbw.ase.contacts.domain.entities.contact.Contact;
import de.dhbw.ase.contacts.domain.entities.contact.ContactBridgeRepository;
import de.dhbw.ase.contacts.domain.entities.contactbook.ContactBook;
import de.dhbw.ase.contacts.domain.entities.contactbook.ContactBookBridgeRepository;
import de.dhbw.ase.contacts.domain.services.ContactSorting;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        if (contactBook.getTitle().isBlank()) {
            throw new IllegalArgumentException("Title is required");
        }
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
        this.contactBookBridgeRepository.save(sortContactBook(contactBook));
    }

    public void removeContactFromContactBook(UUID contactBookUuid, UUID contactUuid) {
        ContactBook contactBook = this.contactBookBridgeRepository.findById(contactBookUuid).orElseThrow(
                () -> new EntityNotFoundException("ContactBook not found")
        );
        this.contactBridgeRepository.findById(contactUuid).orElseThrow(
                () -> new EntityNotFoundException("Contact not found")
        );
        contactBook.removeContact(contactUuid);
        this.contactBookBridgeRepository.save(sortContactBook(contactBook));
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

    private ContactBook sortContactBook(ContactBook contactBook) {
        List<Contact> contacts = new ArrayList<>();
        contactBook.getContacts().forEach(uuid -> this.contactBridgeRepository.findById(uuid).ifPresent(contacts::add));
        List<Contact> sorted = ContactSorting.sortByNameAsc(contacts);
        contactBook.setContacts(sorted.stream().map(Contact::getUuid).collect(Collectors.toList()));
        return contactBook;
    }

}
