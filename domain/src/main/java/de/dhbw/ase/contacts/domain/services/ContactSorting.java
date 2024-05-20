package de.dhbw.ase.contacts.domain.services;

import de.dhbw.ase.contacts.domain.entities.contact.Contact;
import de.dhbw.ase.contacts.domain.entities.contactbook.ContactBook;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ContactSorting {

    public static ContactBook sortByNameAsc(List<Contact> contacts, ContactBook contactBook) {
        List<Contact> sortedContacts = contacts.stream()
                .sorted(Comparator.comparing(c -> c.getName().getLastName()))
                .collect(Collectors.toList());
        contactBook.setContacts(sortedContacts.stream().map(Contact::getUuid).collect(Collectors.toList()));
        return contactBook;
    }

}
