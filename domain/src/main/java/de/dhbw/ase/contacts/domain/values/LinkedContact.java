package de.dhbw.ase.contacts.domain.values;

import de.dhbw.ase.contacts.domain.entities.contact.Contact;
import de.dhbw.ase.contacts.domain.values.enums.ContactConnection;
import jakarta.persistence.Embeddable;

import java.util.UUID;

@Embeddable
public class LinkedContact {
    private final UUID linkedContactUuid;
    private final ContactConnection connection;

    public LinkedContact(UUID linkedContactUuid, ContactConnection connection) {
        this.linkedContactUuid = linkedContactUuid;
        this.connection = connection;
    }

    public LinkedContact() {
        this.linkedContactUuid = null;
        this.connection = null;
    }

    public LinkedContact(ContactConnection connection) {
        this.linkedContactUuid = null;
        this.connection = connection;
    }

    public UUID getLinkedContactUuid() {
        return linkedContactUuid;
    }

    public ContactConnection getConnection() {
        return connection;
    }

}
