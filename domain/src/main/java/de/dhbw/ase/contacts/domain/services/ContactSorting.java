package de.dhbw.ase.contacts.domain.services;

import de.dhbw.ase.contacts.domain.entities.contact.Contact;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ContactSorting {

    public static List<Contact> sortByNameAsc(List<Contact> contacts) {
        return contacts.stream()
                .sorted(Comparator.comparing(c -> c.getName().getLastName()))
                .collect(Collectors.toList());
    }

}
