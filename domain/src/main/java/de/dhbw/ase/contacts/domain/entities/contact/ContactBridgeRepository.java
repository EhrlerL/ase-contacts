package de.dhbw.ase.contacts.domain.entities.contact;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContactBridgeRepository {
    void save(Contact contact);
    Optional<Contact> findById(UUID uuid);
    List<Contact> findAll();
    void deleteById(UUID uuid);
}
