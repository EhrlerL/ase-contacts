package de.dhbw.ase.contacts.plugins.persistence.contactbook;

import de.dhbw.ase.contacts.domain.entities.contact.Contact;
import de.dhbw.ase.contacts.domain.entities.contactbook.ContactBook;
import de.dhbw.ase.contacts.domain.entities.contactbook.ContactBookBridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ContactBookRepository implements ContactBookBridgeRepository {
    private final SpringContactBookRepository springContactBookRepository;

    @Autowired
    public ContactBookRepository(SpringContactBookRepository springContactBookRepository) {
        this.springContactBookRepository = springContactBookRepository;
    }

    @Override
    public void save(ContactBook contactBook) {
        this.springContactBookRepository.save(contactBook);
    }

    @Override
    public Optional<ContactBook> findById(UUID uuid) {
        return this.springContactBookRepository.findById(uuid);
    }

    @Override
    public List<ContactBook> findAll() {
        return this.springContactBookRepository.findAll();
    }

    @Override
    public void deleteById(UUID uuid) {
        this.springContactBookRepository.deleteById(uuid);
    }
}
