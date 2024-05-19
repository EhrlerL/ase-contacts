package de.dhbw.ase.contacts.domain.entities.contactbook;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContactBookBridgeRepository {
    void save(ContactBook contactBook);
    Optional<ContactBook> findById(UUID uuid);
    List<ContactBook> findAll();
    void deleteById(UUID uuid);
}
