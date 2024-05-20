package de.dhbw.ase.contacts.domain.values;

import de.dhbw.ase.contacts.domain.values.enums.ContactConnection;
import jakarta.persistence.Embeddable;

import java.util.Objects;
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

    public UUID getLinkedContactUuid() {
        return linkedContactUuid;
    }

    public ContactConnection getConnection() {
        return connection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LinkedContact)) return false;
        LinkedContact that = (LinkedContact) o;
        return Objects.equals(getLinkedContactUuid(), that.getLinkedContactUuid()) && getConnection() == that.getConnection();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLinkedContactUuid(), getConnection());
    }
}
