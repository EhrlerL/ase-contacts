package de.dhbw.ase.contacts.plugins.persistence.contactbook;

import de.dhbw.ase.contacts.domain.entities.contactbook.ContactBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringContactBookRepository extends JpaRepository<ContactBook, UUID> {
}
