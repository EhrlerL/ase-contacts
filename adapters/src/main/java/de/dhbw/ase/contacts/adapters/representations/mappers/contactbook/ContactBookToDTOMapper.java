package de.dhbw.ase.contacts.adapters.representations.mappers.contactbook;

import de.dhbw.ase.contacts.adapters.representations.ContactBookDTO;
import de.dhbw.ase.contacts.domain.entities.contactbook.ContactBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ContactBookToDTOMapper implements Function<ContactBook, ContactBookDTO> {

    @Lazy
    @Autowired
    public ContactBookToDTOMapper() {

    }

    @Override
    public ContactBookDTO apply(ContactBook contactBook) {
        return new ContactBookDTO(
                contactBook.getTitle()
        );
    }
}
