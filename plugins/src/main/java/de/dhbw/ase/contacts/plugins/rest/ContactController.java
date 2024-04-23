package de.dhbw.ase.contacts.plugins.rest;

import io.swagger.v3.oas.models.info.Contact;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ContactController {
	@GetMapping("/contact/{uuid}")
	public String getContact(@PathVariable UUID uuid) {
	        return "contact: " + uuid;
	    }

	@GetMapping("/contact/all")
	public String getAllContacts() { return "all contacts"; }

	@PostMapping("/contact/save")
	public String saveContact(@RequestBody Contact contact) { return "save contact"; }

	@DeleteMapping("/contact/delete/{uuid}")
	public String deleteContact(@PathVariable UUID uuid) { return "delete contact"; }

}
