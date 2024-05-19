package de.dhbw.ase.contacts.adapters.representations;

import de.dhbw.ase.contacts.domain.values.Name;
import de.dhbw.ase.contacts.domain.values.PhoneNumber;

import java.util.List;
import java.util.UUID;

public class ContactDTO {
    private final UUID uuid;
    private final Name name;
    private final List<PhoneNumber> phoneNumbers;

    public ContactDTO(UUID uuid, Name name, List<PhoneNumber> phoneNumbers) {
        this.uuid = uuid;
        this.name = name;
        this.phoneNumbers = phoneNumbers;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Name getName() {
        return name;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

}

