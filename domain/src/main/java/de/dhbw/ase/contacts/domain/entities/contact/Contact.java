package de.dhbw.ase.contacts.domain.entities.contact;

import de.dhbw.ase.contacts.domain.values.LinkedContact;
import de.dhbw.ase.contacts.domain.values.Address;
import de.dhbw.ase.contacts.domain.values.Email;
import de.dhbw.ase.contacts.domain.values.Name;
import de.dhbw.ase.contacts.domain.values.PhoneNumber;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Contact {
    @Id
    @GeneratedValue
    private UUID uuid;
    private Name name;
    private String birthday;
    @ElementCollection
    private List<PhoneNumber> phoneNumbers;
    @ElementCollection
    private List<Email> emails;
    @ElementCollection
    private List<Address> addresses;
    @ElementCollection
    private List<LinkedContact> linkedContacts;

    public Contact(UUID uuid, Name name, String birthday, List<PhoneNumber> phoneNumbers, List<Email> emails, List<Address> addresses, List<LinkedContact> linkedContacts) {
        this.uuid = uuid;
        this.name = name;
        this.birthday = birthday;
        this.phoneNumbers = phoneNumbers;
        this.emails = emails;
        this.addresses = addresses;
        this.linkedContacts = linkedContacts;
    }

    public Contact(Name name, String birthday, List<PhoneNumber> phoneNumbers, List<Email> emails, List<Address> addresses) {
        this.name = name;
        this.birthday = birthday;
        this.phoneNumbers = phoneNumbers;
        this.emails = emails;
        this.addresses = addresses;
    }

    public Contact(Name name, List<PhoneNumber> phoneNumbers) {
        this.name = name;
        this.phoneNumbers = phoneNumbers;
    }

    public Contact(Name name) {
        this.name = name;
        this.birthday = "";
        this.phoneNumbers = new ArrayList<>();
        this.emails = new ArrayList<>();
        this.addresses = new ArrayList<>();
    }

    public Contact() {
        this.name = new Name();
        this.birthday = "";
        this.phoneNumbers = new ArrayList<>();
        this.emails = new ArrayList<>();
        this.addresses = new ArrayList<>();
    }

    public UUID getUuid() {
        return uuid;
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

    public List<LinkedContact> getLinkedContacts() {
        return linkedContacts;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public void setEmails(List<Email> emails) {
        this.emails = emails;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void addAddress(Address address) {
        this.addresses.add(address);
    }

    public void removeAddress(Address address) {
        this.addresses.remove(address);
    }

    public void addEmail(Email email) {
        this.emails.add(email);
    }

    public void removeEmail(Email email) {
        this.emails.remove(email);
    }

    public void addPhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumbers.add(phoneNumber);
    }

    public void removePhoneNumber(PhoneNumber phoneNumber) {
        this.phoneNumbers.remove(phoneNumber);
    }

    public void addLinkedContact(LinkedContact link) {
        this.linkedContacts.add(link);
    }

    public void removeLinkedContact(LinkedContact link) {
        this.linkedContacts.remove(link);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;
        Contact contact = (Contact) o;
        return Objects.equals(getName(), contact.getName()) && Objects.equals(getBirthday(), contact.getBirthday()) && Objects.equals(getPhoneNumbers(), contact.getPhoneNumbers()) && Objects.equals(getEmails(), contact.getEmails()) && Objects.equals(getAddresses(), contact.getAddresses());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getBirthday(), getPhoneNumbers(), getEmails(), getAddresses());
    }

}
