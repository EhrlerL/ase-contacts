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
                contactDTO.getBirthday(),
                contactDTO.getPhoneNumbers(),
                contactDTO.getEmails(),
                contactDTO.getAddresses()
        );
    }

    public Contact update(Contact oldContact, ContactDTO newContactDTO) {
        oldContact.setName(newContactDTO.getName());
        oldContact.setBirthday(newContactDTO.getBirthday());
        oldContact.setPhoneNumbers(newContactDTO.getPhoneNumbers());
        oldContact.setEmails(newContactDTO.getEmails());
        oldContact.setAddresses(newContactDTO.getAddresses());
        return oldContact;
    }
}
