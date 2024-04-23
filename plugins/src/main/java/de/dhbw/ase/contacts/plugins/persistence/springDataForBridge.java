package de.dhbw.ase.contacts.plugins.persistence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.dhbw.ase.contacts.domain.entities.EntityExample;
@Repository
public interface springDataForBridge extends JpaRepository<EntityExample, String> {

}
