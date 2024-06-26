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

    public UUID saveContact(Contact contact) {
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
        return contact.getUuid();
    }

    public void deleteContact(UUID uuid) {
        getContact(uuid);
        List<ContactBook> contactBooks = this.contactBookBridgeRepository.findAll();
        contactBooks.forEach(book -> {
            book.removeContact(uuid);
            this.contactBookBridgeRepository.save(book);
        });

        this.contactBridgeRepository.deleteById(uuid);
    }

    public void renameContact(UUID uuid, Name newName) {
        Contact contact = getContact(uuid);
        contact.setName(new Name(newName));
        this.contactBridgeRepository.save(contact);
    }

    public void updateBirthday(UUID uuid, String newBirthday) {
        Contact contact = getContact(uuid);
        contact.setBirthday(newBirthday);
        this.contactBridgeRepository.save(contact);
    }

    public void addAddress(UUID uuid, Address address) {
        Contact contact = getContact(uuid);
        if (address.isInvalid()) {
            throw new IllegalArgumentException("FieldType \"MOBILE\" not allowed for address");
        }
        contact.addAddress(address);
        this.contactBridgeRepository.save(contact);
    }

    public void removeAddress(UUID uuid, Address address) {
        Contact contact = getContact(uuid);
        contact.removeAddress(address);
        this.contactBridgeRepository.save(contact);
    }

    public void addEmail(UUID uuid, Email email) {
        Contact contact = getContact(uuid);
        if (email.isInvalid()) {
            throw new IllegalArgumentException("FieldType \"MOBILE\" not allowed for email");
        }
        contact.addEmail(email);
        this.contactBridgeRepository.save(contact);
    }

    public void removeEmail(UUID uuid, Email email) {
        Contact contact = getContact(uuid);
        contact.removeEmail(email);
        this.contactBridgeRepository.save(contact);
    }

    public void addPhoneNumber(UUID uuid, PhoneNumber phoneNumber) {
        Contact contact = getContact(uuid);
        if (phoneNumber.isInvalid()) {
            throw new IllegalArgumentException("FieldType \"SCHOOL\" not allowed for phone number");
        }
        contact.addPhoneNumber(phoneNumber);
        this.contactBridgeRepository.save(contact);
    }

    public void removePhoneNumber(UUID uuid, PhoneNumber phoneNumber) {
        Contact contact = getContact(uuid);
        contact.removePhoneNumber(phoneNumber);
        this.contactBridgeRepository.save(contact);
    }

    public void linkContacts(UUID contactUuid, LinkedContact linkedContact) {
        Contact contact = getContact(contactUuid);
        getContact(linkedContact.getLinkedContactUuid());
        contact.addLinkedContact(linkedContact);
        this.contactBridgeRepository.save(contact);
    }

    public void unlinkContacts(UUID contactUuid, UUID linkedContactUuid) {
        Contact contact = getContact(contactUuid);
        getContact(linkedContactUuid);
        LinkedContact link = contact.getLinkedContacts().stream()
                .filter(lc -> lc.getLinkedContactUuid().equals(linkedContactUuid))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Link not found"));
        contact.removeLinkedContact(link);
        this.contactBridgeRepository.save(contact);
    }
}
