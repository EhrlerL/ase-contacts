package de.dhbw.ase.contacts.adapters.representations.mappers;

import de.dhbw.ase.contacts.adapters.representations.ContactDTO;
import de.dhbw.ase.contacts.domain.entities.contact.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ContactToDTOMapper implements Function<Contact, ContactDTO> {

    @Lazy
    @Autowired
    public ContactToDTOMapper() {

    }

   @Override
   public ContactDTO apply(Contact contact) {
        return new ContactDTO(
                contact.getUuid(),
                contact.getName(),
                contact.getPhoneNumbers()
        );
   }
}