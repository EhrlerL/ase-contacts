package de.dhbw.ase.contacts.adapters.representations.mappers.contact;

import de.dhbw.ase.contacts.adapters.representations.ContactDTO;
import de.dhbw.ase.contacts.domain.entities.contact.Contact;
import de.dhbw.ase.contacts.domain.entities.contact.ContactBuilder;
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
        return ContactBuilder.named(contactDTO.getName())
                .born(contactDTO.getBirthday())
                .withPhoneNumbers(contactDTO.getPhoneNumbers())
                .withEmails(contactDTO.getEmails())
                .withAddresses(contactDTO.getAddresses())
                .build();
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
