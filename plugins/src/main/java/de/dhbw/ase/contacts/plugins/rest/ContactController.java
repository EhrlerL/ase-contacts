package de.dhbw.ase.contacts.plugins.rest;

import de.dhbw.ase.contacts.application.services.ContactService;
import de.dhbw.ase.contacts.domain.entities.contact.Contact;
import de.dhbw.ase.contacts.domain.entities.contactbook.ContactBook;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Contacts")
public class ContactController {
	private final ContactService contactService;

	public ContactController(ContactService contactService) {
		this.contactService = contactService;
	}

	@GetMapping("/contact/{uuid}")
	public Contact getContact(@PathVariable UUID uuid) {
		return this.contactService.getContact(uuid);
	}

	@GetMapping("/contact/all")
	public List<Contact> getAllContacts() {
		return this.contactService.getAllContacts();
	}

	@PostMapping("/contact/save")
	public String saveContact(@RequestBody Contact contact) {
		try {
			boolean success = this.contactService.saveContact(contact);
			if (success) {
				return "Contact saved: " + contact.getUuid();
			} else {
				throw new Exception("ERROR: Could not save contact");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "Could not save contact";
		}
	}

	@DeleteMapping("/contact/delete/{uuid}")
	public String deleteContact(@PathVariable UUID uuid) {
		try {
			boolean success = this.contactService.deleteContact(uuid);
			if (success) {
				return "Contact deleted: " + uuid;
			} else {
				throw new Exception("ERROR: Could not delete contact");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "Could not delete contact with UUID: " + uuid;
		}
	}

	/*@PostMapping("/contact/{uuid}/link")
	public String linkContact(@PathVariable UUID contactUuid, @RequestBody UUID linkedContactUuid) {
		try {
			boolean success = this.contactService.linkContacts();
			if (success) {
				return "Contact " + linkedContactUuid + " was linked to " + contactUuid;
			} else {
				throw new Exception("ERROR: Could not link contacts");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "Could not link " + linkedContactUuid + " to " + contactUuid;
		}
	}*/
}
