package de.dhbw.ase.contacts.adapters.representations;

import de.dhbw.ase.contacts.domain.values.Address;
import de.dhbw.ase.contacts.domain.values.Email;
import de.dhbw.ase.contacts.domain.values.Name;
import de.dhbw.ase.contacts.domain.values.PhoneNumber;

import java.util.List;
import java.util.Objects;

public class ContactDTO {
    private final Name name;
    private final String birthday;
    private final List<PhoneNumber> phoneNumbers;
    private final List<Email> emails;
    private final List<Address> addresses;

    public ContactDTO(
            Name name, String birthday, List<PhoneNumber> phoneNumbers, List<Email> emails, List<Address> addresses) {
        this.name = name;
        this.birthday = birthday;
        this.phoneNumbers = phoneNumbers;
        this.emails = emails;
        this.addresses = addresses;
    }

    public Name getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ContactDTO that)) return false;
        return Objects.equals(getName(), that.getName()) && Objects.equals(getBirthday(), that.getBirthday()) && Objects.equals(getPhoneNumbers(), that.getPhoneNumbers()) && Objects.equals(getEmails(), that.getEmails()) && Objects.equals(getAddresses(), that.getAddresses());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getBirthday(), getPhoneNumbers(), getEmails(), getAddresses());
    }
}

