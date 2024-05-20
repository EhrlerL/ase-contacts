package de.dhbw.ase.contacts.plugins.rest;

import de.dhbw.ase.contacts.adapters.representations.ContactDTO;
import de.dhbw.ase.contacts.adapters.representations.mappers.ContactToDTOMapper;
import de.dhbw.ase.contacts.adapters.representations.mappers.DTOToContactMapper;
import de.dhbw.ase.contacts.application.services.ContactService;
import de.dhbw.ase.contacts.domain.entities.contact.Contact;
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

    @Mock
    private ContactToDTOMapper contactToDTOMapper;

    @Mock
    private DTOToContactMapper dtoToContactMapper;


    @Test
    void getContactValid() {
        UUID contactUuid = UUID.randomUUID();
        Contact contact = new Contact(
                contactUuid,
                new Name("Max", "Mustermann"),
                "1.1.2001",
                new ArrayList<>(Collections.singleton(new PhoneNumber(FieldType.MOBILE, "1234567890"))),
                new ArrayList<>(Collections.singleton(new Email(FieldType.WORK, "max@mustermann"))),
                new ArrayList<>(Collections.singleton(new Address(FieldType.PRIVATE, "Musterstraße 1", "Musterstadt", "12345"))),
                new ArrayList<>(Collections.singleton(new LinkedContact(UUID.randomUUID(), ContactConnection.COLLEAGUE)))
                );

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
        Contact contact = new Contact(
                contactUuid,
                new Name("Max", "Mustermann"),
                "1.1.2001",
                new ArrayList<>(Collections.singleton(new PhoneNumber(FieldType.MOBILE, "1234567890"))),
                new ArrayList<>(Collections.singleton(new Email(FieldType.WORK, "max@mustermann"))),
                new ArrayList<>(Collections.singleton(new Address(FieldType.PRIVATE, "Musterstraße 1", "Musterstadt", "12345"))),
                new ArrayList<>(Collections.singleton(new LinkedContact(UUID.randomUUID(), ContactConnection.COLLEAGUE)))
        );
        ContactDTO contactDTO = new ContactDTO(
                contactUuid,
                new Name("Max", "Mustermann"),
                new ArrayList<>(Collections.singleton(new PhoneNumber(FieldType.MOBILE, "1234567890")))
        );
        when(contactService.getAllContacts()).thenReturn(List.of(contact));

        ResponseEntity<List<ContactDTO>> response = contactController.getAllContacts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals(contactDTO, response.getBody().get(0));
    }

    @Test
    void saveContactValid() {
        UUID contactUuid = UUID.randomUUID();
        Contact contact = new Contact(
                contactUuid,
                new Name("Max", "Mustermann"),
                "1.1.2001",
                new ArrayList<>(Collections.singleton(new PhoneNumber(FieldType.MOBILE, "1234567890"))),
                new ArrayList<>(Collections.singleton(new Email(FieldType.WORK, "max@mustermann"))),
                new ArrayList<>(Collections.singleton(new Address(FieldType.PRIVATE, "Musterstraße 1", "Musterstadt", "12345"))),
                new ArrayList<>(Collections.singleton(new LinkedContact(UUID.randomUUID(), ContactConnection.COLLEAGUE)))
        );

        ResponseEntity<UUID> response = contactController.saveContact(contact);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(contactUuid, response.getBody());
    }

    @Test
    void saveContactInvalid() {
        UUID contactUuid = UUID.randomUUID();
        Contact contact = new Contact(
                contactUuid,
                new Name("Max", "Mustermann"),
                "1.1.2001",
                new ArrayList<>(Collections.singleton(new PhoneNumber(FieldType.MOBILE, "1234567890"))),
                new ArrayList<>(Collections.singleton(new Email(FieldType.WORK, "max@mustermann"))),
                new ArrayList<>(Collections.singleton(new Address(FieldType.PRIVATE, "Musterstraße 1", "Musterstadt", "12345"))),
                new ArrayList<>(Collections.singleton(new LinkedContact(UUID.randomUUID(), ContactConnection.COLLEAGUE)))
        );

        doThrow(new IllegalArgumentException()).when(contactService).saveContact(contact);

        ResponseEntity<UUID> response = contactController.saveContact(contact);

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
        Contact contact1 = new Contact(
                uuid1,
                new Name("Max", "Mustermann"),
                "1.1.2001",
                new ArrayList<>(Collections.singleton(new PhoneNumber(FieldType.MOBILE, "1234567890"))),
                new ArrayList<>(Collections.singleton(new Email(FieldType.WORK, "max@mustermann"))),
                new ArrayList<>(Collections.singleton(new Address(FieldType.PRIVATE, "Musterstraße 1", "Musterstadt", "12345"))),
                new ArrayList<>()
        );
        Contact contact2 = new Contact(
                uuid2,
                new Name("Max", "Mustermann"),
                "1.1.2001",
                new ArrayList<>(Collections.singleton(new PhoneNumber(FieldType.MOBILE, "1234567890"))),
                new ArrayList<>(Collections.singleton(new Email(FieldType.WORK, "max@mustermann"))),
                new ArrayList<>(Collections.singleton(new Address(FieldType.PRIVATE, "Musterstraße 1", "Musterstadt", "12345"))),
                new ArrayList<>()
        );

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
        Contact contact1 = new Contact(
                uuid1,
                new Name("Max", "Mustermann"),
                "1.1.2001",
                new ArrayList<>(Collections.singleton(new PhoneNumber(FieldType.MOBILE, "1234567890"))),
                new ArrayList<>(Collections.singleton(new Email(FieldType.WORK, "max@mustermann"))),
                new ArrayList<>(Collections.singleton(new Address(FieldType.PRIVATE, "Musterstraße 1", "Musterstadt", "12345"))),
                new ArrayList<>(Collections.singleton(new LinkedContact(uuid2, ContactConnection.COLLEAGUE)))
        );
        Contact contact2 = new Contact(
                uuid2,
                new Name("Max", "Mustermann"),
                "1.1.2001",
                new ArrayList<>(Collections.singleton(new PhoneNumber(FieldType.MOBILE, "1234567890"))),
                new ArrayList<>(Collections.singleton(new Email(FieldType.WORK, "max@mustermann"))),
                new ArrayList<>(Collections.singleton(new Address(FieldType.PRIVATE, "Musterstraße 1", "Musterstadt", "12345"))),
                new ArrayList<>(Collections.singleton(new LinkedContact(uuid1, ContactConnection.COLLEAGUE)))
        );

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