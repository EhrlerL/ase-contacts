package de.dhbw.ase.contacts.plugins.persistence.contact;

import de.dhbw.ase.contacts.domain.entities.contact.Contact;
import de.dhbw.ase.contacts.domain.entities.contact.ContactBridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ContactRepository implements ContactBridgeRepository {
    @Autowired
    SpringContactRepository springContactRepository;

    @Override
    public void save(Contact contact) {
        this.springContactRepository.save(contact);
    }

    @Override
    public Contact findById(UUID uuid) {
        return this.springContactRepository.findContactBy(uuid);
    }

    @Override
    public List<Contact> findAll() {
        return this.springContactRepository.findAll();
    }

    @Override
    public void deleteById(UUID uuid) {
        this.springContactRepository.deleteById(uuid);
    }
}
