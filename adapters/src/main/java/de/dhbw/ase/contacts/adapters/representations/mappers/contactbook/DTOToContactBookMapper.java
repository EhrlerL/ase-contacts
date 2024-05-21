package de.dhbw.ase.contacts.adapters.representations.mappers.contactbook;

import de.dhbw.ase.contacts.adapters.representations.ContactBookDTO;
import de.dhbw.ase.contacts.domain.entities.contactbook.ContactBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DTOToContactBookMapper implements Function<ContactBookDTO, ContactBook> {

    @Autowired
    public DTOToContactBookMapper() {

    }

    @Override
    public ContactBook apply(ContactBookDTO contactBookDTO) {
        return new ContactBook(contactBookDTO.getTitle());
    }

    public ContactBook update(ContactBook oldContactBook, ContactBookDTO newContactBookDTO) {
        oldContactBook.setTitle(newContactBookDTO.getTitle());
        return oldContactBook;
    }
}
