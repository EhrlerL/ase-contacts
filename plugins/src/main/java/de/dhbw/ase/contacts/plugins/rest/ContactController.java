package de.dhbw.ase.contacts.plugins.rest;

import de.dhbw.ase.contacts.adapters.representations.ContactDTO;
import de.dhbw.ase.contacts.adapters.representations.mappers.contact.DTOToContactMapper;
import de.dhbw.ase.contacts.application.services.ContactService;
import de.dhbw.ase.contacts.domain.entities.contact.Contact;
import de.dhbw.ase.contacts.domain.values.Address;
import de.dhbw.ase.contacts.domain.values.Email;
import de.dhbw.ase.contacts.domain.values.Name;
import de.dhbw.ase.contacts.domain.values.PhoneNumber;
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
			System.out.println(e.getMessage());
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
	public ResponseEntity<UUID> saveContact(@RequestBody ContactDTO contactDTO) {
		try {
			Contact contact = new DTOToContactMapper().apply(contactDTO);
			UUID uuid = this.contactService.saveContact(contact);
			return ResponseEntity.ok(uuid);
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/contact/{uuid}")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully deleted Contact"),
			@ApiResponse(responseCode = "404", description = "Contact not found")
	})
	public ResponseEntity<Void> deleteContact(@PathVariable UUID uuid) {
		try {
			this.contactService.deleteContact(uuid);
			return ResponseEntity.ok().build();
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/contact/{uuid}/rename")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully renamed Contact"),
			@ApiResponse(responseCode = "400", description = "Could not rename Contact"),
			@ApiResponse(responseCode = "404", description = "Contact not found")
	})
	public ResponseEntity<UUID> renameContact(@PathVariable UUID uuid, @RequestBody Name newName) {
		try {
			this.contactService.renameContact(uuid, newName);
			return ResponseEntity.ok().build();
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/contact/{uuid}/date/update")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully updated Contact's birthday"),
			@ApiResponse(responseCode = "400", description = "Could not update Contact's birthday"),
			@ApiResponse(responseCode = "404", description = "Contact not found")
	})
	public ResponseEntity<Void> updateBirthday(@PathVariable UUID uuid, @RequestBody String birthday) {
		try {
			this.contactService.updateBirthday(uuid, birthday);
			return ResponseEntity.ok().build();
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/contact/{uuid}/address/add")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully added Address"),
			@ApiResponse(responseCode = "400", description = "Address must not be \"MOBILE\""),
			@ApiResponse(responseCode = "404", description = "Contact not found")
	})
	public ResponseEntity<Void> addAddress(@PathVariable UUID uuid, @RequestBody Address address) {
		try {
			this.contactService.addAddress(uuid, address);
			return ResponseEntity.ok().build();
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.notFound().build();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/contact/{uuid}/address/remove")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully removed Address"),
			@ApiResponse(responseCode = "400", description = "Could not remove Address"),
			@ApiResponse(responseCode = "404", description = "Contact not found")
	})
	public ResponseEntity<Void> removeAddress(@PathVariable UUID uuid, @RequestBody Address address) {
		try {
			this.contactService.removeAddress(uuid, address);
			return ResponseEntity.ok().build();
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/contact/{uuid}/email/add")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully added Email"),
			@ApiResponse(responseCode = "400", description = "Email must not be \"MOBILE\""),
			@ApiResponse(responseCode = "404", description = "Contact not found")
	})
	public ResponseEntity<Void> addEmail(@PathVariable UUID uuid, @RequestBody Email email) {
		try {
			this.contactService.addEmail(uuid, email);
			return ResponseEntity.ok().build();
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.notFound().build();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/contact/{uuid}/email/remove")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully removed Email"),
			@ApiResponse(responseCode = "400", description = "Could not remove Email"),
			@ApiResponse(responseCode = "404", description = "Contact not found")
	})
	public ResponseEntity<Void> removeEmail(@PathVariable UUID uuid, @RequestBody Email email) {
		try {
			this.contactService.removeEmail(uuid, email);
			return ResponseEntity.ok().build();
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/contact/{uuid}/phonenumber/add")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully added PhoneNumber"),
			@ApiResponse(responseCode = "400", description = "PhoneNumber must not be \"SCHOOL\""),
			@ApiResponse(responseCode = "404", description = "Contact not found")
	})
	public ResponseEntity<Void> addPhoneNumber(@PathVariable UUID uuid, @RequestBody PhoneNumber phoneNumber) {
		try {
			this.contactService.addPhoneNumber(uuid, phoneNumber);
			return ResponseEntity.ok().build();
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.notFound().build();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/contact/{uuid}/phonenumber/remove")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully removed PhoneNumber"),
			@ApiResponse(responseCode = "400", description = "Could not remove PhoneNumber"),
			@ApiResponse(responseCode = "404", description = "Contact not found")
	})
	public ResponseEntity<Void> removePhoneNumber(@PathVariable UUID uuid, @RequestBody PhoneNumber phoneNumber) {
		try {
			this.contactService.removePhoneNumber(uuid, phoneNumber);
			return ResponseEntity.ok().build();
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/contact/{contactUuid}/link")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully linked Contacts"),
			@ApiResponse(responseCode = "400", description = "Could not link Contacts"),
			@ApiResponse(responseCode = "404", description = "Contact not found")
	})
	public ResponseEntity<Void> linkContacts(@PathVariable UUID contactUuid, @RequestBody String linkedContactId,
											@RequestBody String connection) {
		try {
			UUID linkedContactUuid = UUID.fromString(linkedContactId);
			ContactConnection contactConnection = ContactConnection.valueOf(connection);
			this.contactService.linkContacts(contactUuid, linkedContactUuid, contactConnection);
			return ResponseEntity.ok().build();
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.notFound().build();
		} catch (IllegalArgumentException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}

	@DeleteMapping("/contact/{contactUuid}/unlink")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Successfully unlinked Contacts"),
			@ApiResponse(responseCode = "400", description = "Could not unlink Contacts"),
			@ApiResponse(responseCode = "404", description = "Contact not found")
	})
	public ResponseEntity<Void> unlinkContacts(@PathVariable UUID contactUuid, @RequestBody String linkedContactId) {
		try {
			UUID linkedContactUuid = UUID.fromString(linkedContactId);
			this.contactService.unlinkContacts(contactUuid, linkedContactUuid);
			return ResponseEntity.ok().build();
		} catch (EntityNotFoundException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.notFound().build();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return ResponseEntity.badRequest().build();
		}
	}
}
