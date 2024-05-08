package de.dhbw.ase.contacts.plugins.rest;

import de.dhbw.ase.contacts.application.services.ContactService;
import de.dhbw.ase.contacts.domain.entities.contact.Contact;
import de.dhbw.ase.contacts.domain.values.enums.ContactConnection;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
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
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved Contact"),
			@ApiResponse(responseCode = "404", description = "Contact not found")
	})
	public ResponseEntity<Contact> getContact(@PathVariable UUID uuid) {
		try {
			Contact contact = this.contactService.getContact(uuid);
			return ResponseEntity.ok(contact);
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/contact/all")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully retrieved all Contacts"),
	})
	public ResponseEntity<List<Contact>> getAllContacts() {
		List<Contact> contacts = this.contactService.getAllContacts();
		return ResponseEntity.ok(contacts);
	}

	@PostMapping("/contact/save")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully saved Contact"),
			@ApiResponse(responseCode = "400", description = "Could not save Contact")
	})
	public ResponseEntity<Void> saveContact(@RequestBody Contact contact) {
		try {
			this.contactService.saveContact(contact);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/contact/delete/{uuid}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully deleted Contact"),
			@ApiResponse(responseCode = "404", description = "Contact not found")
	})
	public ResponseEntity<Void> deleteContact(@PathVariable UUID uuid) {
		try {
			this.contactService.deleteContact(uuid);
			return ResponseEntity.ok().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/contact/{contactUuid}/link")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully linked Contacts"),
			@ApiResponse(responseCode = "400", description = "Could not link Contacts"),
			@ApiResponse(responseCode = "404", description = "Contact not found")
	})
	public ResponseEntity<Void> linkContact(@PathVariable UUID contactUuid, @RequestBody String linkedContactStringId,
											@RequestBody String connection) {
		try {
			UUID linkedContactUuid = UUID.fromString(linkedContactStringId);
			ContactConnection contactConnection = ContactConnection.valueOf(connection);
			this.contactService.linkContacts(contactUuid, linkedContactUuid, contactConnection);
			return ResponseEntity.ok().build();
		} catch (EntityNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
}
