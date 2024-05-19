package de.dhbw.ase.contacts.adapters.representations.mappers;

import de.dhbw.ase.contacts.adapters.representations.ContactDTO;
import de.dhbw.ase.contacts.domain.entities.contact.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class DTOToContactMapper implements Function<ContactDTO, Contact> {

    @Autowired
    public DTOToContactMapper() {

    }

    @Override
    public Contact apply(ContactDTO contactDTO) {
        return new Contact(
                contactDTO.getName(),
                contactDTO.getPhoneNumbers()
        );
    }

    public Contact update(Contact oldContact, ContactDTO newContactDTO) {
        oldContact.setUuid(newContactDTO.getUuid());
        oldContact.setName(newContactDTO.getName());
        oldContact.setPhoneNumbers(newContactDTO.getPhoneNumbers());
        return oldContact;
    }
}
