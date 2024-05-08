package de.dhbw.ase.contacts.plugins.persistence.contact;

import de.dhbw.ase.contacts.domain.entities.contact.Contact;
import de.dhbw.ase.contacts.domain.entities.contact.ContactBridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ContactRepository implements ContactBridgeRepository {
    private final SpringContactRepository springContactRepository;

    @Autowired
    public ContactRepository(SpringContactRepository springContactRepository) {
        this.springContactRepository = springContactRepository;
    }

    @Override
    public void save(Contact contact) {
        this.springContactRepository.save(contact);
    }

    @Override
    public Optional<Contact> findById(UUID uuid) {
        return this.springContactRepository.findById(uuid);
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
