package de.dhbw.ase.contacts.plugins.persistence.contact;

import de.dhbw.ase.contacts.domain.entities.contact.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringContactRepository extends JpaRepository<Contact, UUID> {
}
