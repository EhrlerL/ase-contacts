package de.dhbw.ase.contacts.plugins.persistence.contactlist;

import de.dhbw.ase.contacts.domain.entities.contactlist.ContactList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringContactListRepository extends JpaRepository<ContactList, UUID> {
    public ContactList findContactListBy(UUID uuid);
}
