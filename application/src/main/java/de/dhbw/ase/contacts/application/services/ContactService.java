package de.dhbw.ase.contacts.application.services;


import de.dhbw.ase.contacts.domain.entities.contact.ContactBridgeRepository;
import de.dhbw.ase.contacts.domain.entities.contactlist.ContactListBridgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    private final ContactBridgeRepository contactBridgeRepository;
    private final ContactListBridgeRepository contactListBridgeRepository;

    @Autowired
    public ContactService(ContactBridgeRepository contactBridgeRepository, ContactListBridgeRepository contactListBridgeRepository) {
        this.contactBridgeRepository = contactBridgeRepository;
        this.contactListBridgeRepository = contactListBridgeRepository;
    }


}
