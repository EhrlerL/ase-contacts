package de.dhbw.ase.contacts.plugins.persistence.contactlist;

import de.dhbw.ase.contacts.domain.entities.contactlist.ContactList;
import de.dhbw.ase.contacts.domain.entities.contactlist.ContactListBridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ContactListRepository implements ContactListBridgeRepository {
    @Autowired
    SpringContactListRepository springContactListRepository;
    @Override
    public void save(ContactList contactList) {
        this.springContactListRepository.save(contactList);
    }

    @Override
    public ContactList findById(UUID uuid) {
        return this.springContactListRepository.findContactListBy(uuid);
    }

    @Override
    public List<ContactList> findAll() {
        return this.springContactListRepository.findAll();
    }

    @Override
    public void deleteById(UUID uuid) {
        this.springContactListRepository.deleteById(uuid);
    }
}
