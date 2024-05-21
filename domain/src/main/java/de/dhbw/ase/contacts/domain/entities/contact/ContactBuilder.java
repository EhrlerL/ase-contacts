package de.dhbw.ase.contacts.domain.entities.contact;

import de.dhbw.ase.contacts.domain.values.*;

import java.util.List;
import java.util.UUID;

public class ContactBuilder {
    private UUID uuid;
    private Name name;
    private String birthday;
    private List<PhoneNumber> phoneNumbers;
    private List<Email> emails;
    private List<Address> addresses;
    private List<LinkedContact> linkedContacts;

    private ContactBuilder(Name name) {
        this.name = name;
    }

    public static ContactBuilder named(Name name) {
        return new ContactBuilder(name);
    }

    public ContactBuilder identifiedBy(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public ContactUnderConstruction born(String birthday) {
        this.birthday = birthday;
        return new ContactUnderConstruction();
    }

    public class ContactUnderConstruction {
        private ContactUnderConstruction() {
        }

        public ContactUnderConstruction withPhoneNumbers(List<PhoneNumber> phoneNumbers) {
            ContactBuilder.this.phoneNumbers = phoneNumbers;
            return this;
        }

        public ContactUnderConstruction withEmails(List<Email> emails) {
            ContactBuilder.this.emails = emails;
            return this;
        }

        public ContactUnderConstruction withAddresses(List<Address> addresses) {
            ContactBuilder.this.addresses = addresses;
            return this;
        }

        public ContactUnderConstruction withLinkedContacts(List<LinkedContact> linkedContacts) {
            ContactBuilder.this.linkedContacts = linkedContacts;
            return this;
        }

        public Contact build() {
            return ContactBuilder.this.build();
        }
    }

    private Contact build() {
        return new Contact(this.uuid, this.name, this.birthday, this.phoneNumbers, this.emails, this.addresses, this.linkedContacts);
    }
}
