package de.dhbw.ase.contacts.plugins.rest;

import de.dhbw.ase.contacts.adapters.representations.ContactDTO;
import de.dhbw.ase.contacts.application.services.ContactService;
import de.dhbw.ase.contacts.domain.entities.contact.Contact;
import de.dhbw.ase.contacts.domain.entities.contact.ContactBuilder;
import de.dhbw.ase.contacts.domain.values.*;
import de.dhbw.ase.contacts.domain.values.enums.ContactConnection;
import de.dhbw.ase.contacts.domain.values.enums.FieldType;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactControllerTest {

    @InjectMocks
    private ContactController contactController;

    @Mock
    private ContactService contactService;

    @Test
    void getContactValid() {
        UUID contactUuid = UUID.randomUUID();
        Contact contact = ContactBuilder.named(new Name("Max", "Mustermann"))
                .identifiedBy(contactUuid)
                .born("1.1.2001")
                .withPhoneNumbers(new ArrayList<>(Collections.singleton(new PhoneNumber(FieldType.MOBILE, "1234567890"))))
                .withEmails(new ArrayList<>(Collections.singleton(new Email(FieldType.WORK, "max@mustermann"))))
                .withAddresses(new ArrayList<>(Collections.singleton(new Address(FieldType.PRIVATE, "Musterstraße 1", "Musterstadt", "12345"))))
                .withLinkedContacts(new ArrayList<>(Collections.singleton(new LinkedContact(UUID.randomUUID(), ContactConnection.COLLEAGUE))))
                .build();

        when(contactService.getContact(contactUuid)).thenReturn(contact);

        ResponseEntity<Contact> response = contactController.getContact(contactUuid);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contact, response.getBody());
    }

    @Test
    void getContactNotFound() {
        UUID contactUuid = UUID.randomUUID();
        when(contactService.getContact(contactUuid)).thenThrow(new EntityNotFoundException("Contact not found"));

        ResponseEntity<Contact> response = contactController.getContact(contactUuid);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void getAllContactsValid() {
        UUID contactUuid = UUID.randomUUID();
        Contact contact = ContactBuilder.named(new Name("Max", "Mustermann"))
                .identifiedBy(contactUuid)
                .born("1.1.2001")
                .withPhoneNumbers(new ArrayList<>(Collections.singleton(new PhoneNumber(FieldType.MOBILE, "1234567890"))))
                .withEmails(new ArrayList<>(Collections.singleton(new Email(FieldType.WORK, "max@mustermann"))))
                .withAddresses(new ArrayList<>(Collections.singleton(new Address(FieldType.PRIVATE, "Musterstraße 1", "Musterstadt", "12345"))))
                .withLinkedContacts(new ArrayList<>(Collections.singleton(new LinkedContact(UUID.randomUUID(), ContactConnection.COLLEAGUE))))
                .build();
        when(contactService.getAllContacts()).thenReturn(List.of(contact));

        ResponseEntity<List<Contact>> response = contactController.getAllContacts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(contact, response.getBody().get(0));
    }

    @Test
    void saveContactValid() {
        UUID contactUuid = UUID.randomUUID();
        Contact contact = ContactBuilder.named(new Name("Max", "Mustermann"))
                .identifiedBy(contactUuid)
                .born("1.1.2001")
                .withPhoneNumbers(new ArrayList<>(Collections.singleton(new PhoneNumber(FieldType.MOBILE, "1234567890"))))
                .withEmails(new ArrayList<>(Collections.singleton(new Email(FieldType.WORK, "max@mustermann"))))
                .withAddresses(new ArrayList<>(Collections.singleton(new Address(FieldType.PRIVATE, "Musterstraße 1", "Musterstadt", "12345"))))
                .build();
        ContactDTO contactDTO = new ContactDTO(
                new Name("Max", "Mustermann"),
                "1.1.2001",
                new ArrayList<>(Collections.singleton(new PhoneNumber(FieldType.MOBILE, "1234567890"))),
                new ArrayList<>(Collections.singleton(new Email(FieldType.WORK, "max@mustermann"))),
                new ArrayList<>(Collections.singleton(new Address(FieldType.PRIVATE, "Musterstraße 1", "Musterstadt", "12345")))
        );

        doReturn(contactUuid).when(contactService).saveContact(contact);

        ResponseEntity<UUID> response = contactController.saveContact(contactDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contactUuid, response.getBody());
    }

    @Test
    void saveContactInvalid() {
        UUID contactUuid = UUID.randomUUID();
        Contact contact = ContactBuilder.named(new Name("Max", "Mustermann"))
                .identifiedBy(contactUuid)
                .born("1.1.2001")
                .withPhoneNumbers(new ArrayList<>(Collections.singleton(new PhoneNumber(FieldType.MOBILE, "1234567890"))))
                .withEmails(new ArrayList<>(Collections.singleton(new Email(FieldType.WORK, "max@mustermann"))))
                .withAddresses(new ArrayList<>(Collections.singleton(new Address(FieldType.PRIVATE, "Musterstraße 1", "Musterstadt", "12345"))))
                .build();
        ContactDTO contactDTO = new ContactDTO(
                new Name("Max", "Mustermann"),
                "1.1.2001",
                new ArrayList<>(Collections.singleton(new PhoneNumber(FieldType.MOBILE, "1234567890"))),
                new ArrayList<>(Collections.singleton(new Email(FieldType.WORK, "max@mustermann"))),
                new ArrayList<>(Collections.singleton(new Address(FieldType.PRIVATE, "Musterstraße 1", "Musterstadt", "12345")))
        );

        doThrow(new IllegalArgumentException()).when(contactService).saveContact(contact);

        ResponseEntity<UUID> response = contactController.saveContact(contactDTO);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteContactValid() {
        UUID contactUuid = UUID.randomUUID();

        ResponseEntity<Void> response = contactController.deleteContact(contactUuid);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void deleteContactNotFound() {
        UUID contactUuid = UUID.randomUUID();

        doThrow(new EntityNotFoundException("Contact not found")).when(contactService).deleteContact(contactUuid);

        ResponseEntity<Void> response = contactController.deleteContact(contactUuid);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void linkContactsValid() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        doNothing().when(contactService).linkContacts(uuid1, uuid2, ContactConnection.COLLEAGUE);

        ResponseEntity<Void> response = contactController.linkContacts(uuid1, uuid2.toString(), "COLLEAGUE");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void linkContactsNotFound() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        doThrow(new EntityNotFoundException("Contact not found")).when(contactService).linkContacts(uuid1, uuid2, ContactConnection.COLLEAGUE);

        ResponseEntity<Void> response = contactController.linkContacts(uuid1, uuid2.toString(), "COLLEAGUE");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void linkContactsInvalid() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();
        String connectionString = "PARENT";

        doThrow(new IllegalArgumentException()).when(contactService).linkContacts(uuid1, uuid2, ContactConnection.valueOf(connectionString));

        ResponseEntity<Void> response = contactController.linkContacts(uuid1, uuid2.toString(), connectionString);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void unlinkContactsValid() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        doNothing().when(contactService).unlinkContacts(uuid1, uuid2);

        ResponseEntity<Void> response = contactController.unlinkContacts(uuid1, uuid2.toString());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void unlinkContactsNotFound() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        doThrow(new EntityNotFoundException("Contact not found")).when(contactService).unlinkContacts(uuid1, uuid2);

        ResponseEntity<Void> response = contactController.unlinkContacts(uuid1, uuid2.toString());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void unlinkContactsInvalid() {
        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        doThrow(new IllegalArgumentException("Contact not linked")).when(contactService).unlinkContacts(uuid1, uuid2);

        ResponseEntity<Void> response = contactController.unlinkContacts(uuid1, uuid2.toString());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody());
    }
}