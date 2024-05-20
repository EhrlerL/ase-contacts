package de.dhbw.ase.contacts.application.services;

import de.dhbw.ase.contacts.domain.entities.contact.Contact;
import de.dhbw.ase.contacts.domain.entities.contact.ContactBridgeRepository;
import de.dhbw.ase.contacts.domain.entities.contactbook.ContactBook;
import de.dhbw.ase.contacts.domain.entities.contactbook.ContactBookBridgeRepository;
import de.dhbw.ase.contacts.domain.values.LinkedContact;
import de.dhbw.ase.contacts.domain.values.Address;
import de.dhbw.ase.contacts.domain.values.Email;
import de.dhbw.ase.contacts.domain.values.Name;
import de.dhbw.ase.contacts.domain.values.PhoneNumber;
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

    public Contact getContact(UUID uuid) {
        return this.contactBridgeRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("Contact not found")
        );
    }

    public List<Contact> getAllContacts() {
        return this.contactBridgeRepository.findAll();
    }

    public void saveContact(Contact contact) {
        if(contact.getName().toString().isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (contact.getAddresses().stream().anyMatch(Address::isInvalid)) {
            throw new IllegalArgumentException("FieldType \"MOBILE\" not allowed for address");
        }
        if (contact.getEmails().stream().anyMatch(Email::isInvalid)) {
            throw new IllegalArgumentException("FieldType \"MOBILE\" not allowed for email");
        }
        if (contact.getPhoneNumbers().stream().anyMatch(PhoneNumber::isInvalid)) {
            throw new IllegalArgumentException("FieldType \"SCHOOL\" not allowed for phone number");
        }

        for(Contact c : this.contactBridgeRepository.findAll()) {
            if(c.equals(contact)) {
                throw new IllegalArgumentException("Contact already exists");
            }
        }

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

    public void renameContact(UUID uuid, Name newName) {
        Contact contact = this.contactBridgeRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("Contact not found")
        );
        contact.setName(new Name(newName));
        this.contactBridgeRepository.save(contact);
    }

    public void updateBirthday(UUID uuid, String newBirthday) {
        Contact contact = this.contactBridgeRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("Contact not found")
        );
        contact.setBirthday(newBirthday);
        this.contactBridgeRepository.save(contact);
    }

    public void addAddress(UUID uuid, Address address) {
        Contact contact = this.contactBridgeRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("Contact not found")
        );
        if (address.isInvalid()) {
            throw new IllegalArgumentException("FieldType \"MOBILE\" not allowed for address");
        }
        contact.addAddress(address);
        this.contactBridgeRepository.save(contact);
    }

    public void removeAddress(UUID uuid, Address address) {
        Contact contact = this.contactBridgeRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("Contact not found")
        );
        contact.removeAddress(address);
        this.contactBridgeRepository.save(contact);
    }

    public void addEmail(UUID uuid, Email email) {
        Contact contact = this.contactBridgeRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("Contact not found")
        );
        if (email.isInvalid()) {
            throw new IllegalArgumentException("FieldType \"MOBILE\" not allowed for email");
        }
        contact.addEmail(email);
        this.contactBridgeRepository.save(contact);
    }

    public void removeEmail(UUID uuid, Email email) {
        Contact contact = this.contactBridgeRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("Contact not found")
        );
        contact.removeEmail(email);
        this.contactBridgeRepository.save(contact);
    }

    public void addPhoneNumber(UUID uuid, PhoneNumber phoneNumber) {
        Contact contact = this.contactBridgeRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("Contact not found")
        );
        if (phoneNumber.isInvalid()) {
            throw new IllegalArgumentException("FieldType \"SCHOOL\" not allowed for phone number");
        }
        contact.addPhoneNumber(phoneNumber);
        this.contactBridgeRepository.save(contact);
    }

    public void removePhoneNumber(UUID uuid, PhoneNumber phoneNumber) {
        Contact contact = this.contactBridgeRepository.findById(uuid).orElseThrow(
                () -> new EntityNotFoundException("Contact not found")
        );
        contact.removePhoneNumber(phoneNumber);
        this.contactBridgeRepository.save(contact);
    }

    public void linkContacts(UUID contactUuid, UUID linkedContactUuid, ContactConnection connection) {
        Contact contact = this.contactBridgeRepository.findById(contactUuid).orElseThrow(
                () -> new EntityNotFoundException("Contact not found")
        );
        Contact linkedContact = this.contactBridgeRepository.findById(linkedContactUuid).orElseThrow(
                () -> new EntityNotFoundException("Contact not found")
        );
        LinkedContact link = new LinkedContact(linkedContactUuid, connection);
        contact.addLinkedContact(link);
        this.contactBridgeRepository.save(contact);
    }

    public void unlinkContacts(UUID contactUuid, UUID linkedContactUuid) {
        Contact contact = this.contactBridgeRepository.findById(contactUuid).orElseThrow(
                () -> new EntityNotFoundException("Contact not found")
        );
        Contact linkedContact = this.contactBridgeRepository.findById(linkedContactUuid).orElseThrow(
                () -> new EntityNotFoundException("Contact not found")
        );
        LinkedContact link = contact.getLinkedContacts().stream()
                .filter(lc -> lc.getLinkedContactUuid().equals(linkedContactUuid))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Link not found"));
        contact.removeLinkedContact(link);
        this.contactBridgeRepository.save(contact);
    }
}
