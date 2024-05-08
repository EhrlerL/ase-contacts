package de.dhbw.ase.contacts.plugins.rest;

import de.dhbw.ase.contacts.application.services.ContactService;
import de.dhbw.ase.contacts.domain.entities.contactbook.ContactBook;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ContactBookController {
    private final ContactService contactService;

    public ContactBookController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/contactbook/{uuid}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ContactBook"),
            @ApiResponse(responseCode = "404", description = "ContactBook not found")
    })
    public ResponseEntity<ContactBook> getContactBook(@PathVariable UUID uuid) {
        try {
            ContactBook contactBook = this.contactService.getContactBook(uuid);
            return ResponseEntity.ok(contactBook);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/contactbook/all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all ContactBooks")
    })
    public ResponseEntity<List<ContactBook>> getAllContactBooks() {
        List<ContactBook> contactBooks = this.contactService.getAllContactBooks();
        return ResponseEntity.ok(contactBooks);
    }

    @PostMapping("/contactbook/save")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully saved ContactBook"),
            @ApiResponse(responseCode = "400", description = "Could not save ContactBook")
    })
    public ResponseEntity<String> saveContactBook(@RequestBody ContactBook contactBook) {
        try {
            this.contactService.saveContactBook(contactBook);
            return ResponseEntity.ok("ContactBook saved: " + contactBook.getUuid());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not save contact book");
        }
    }

    @PostMapping("/contactbook/{contactBookUuid}/addcontact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added Contact to ContactBook"),
            @ApiResponse(responseCode = "400", description = "Could not add Contact to ContactBook"),
            @ApiResponse(responseCode = "404", description = "ContactBook or Contact not found")
    })
    public ResponseEntity<String> addContactToContactBook(@PathVariable UUID contactBookUuid, @RequestBody String contactStringId) {
        try {
            UUID contactUuid = UUID.fromString(contactStringId);
            this.contactService.addContactToContactBook(contactBookUuid, contactUuid);
            return ResponseEntity.ok("Added contact to contact book");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not add contact to contact book");
        }
    }

    @PostMapping("/contactbook/{contactBookUuid}/removecontact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully removed Contact from ContactBook"),
            @ApiResponse(responseCode = "400", description = "Could not remove Contact from ContactBook"),
            @ApiResponse(responseCode = "404", description = "ContactBook or Contact not found")
    })
    public ResponseEntity<String> removeContactFromContactBook(@PathVariable UUID contactBookUuid, @RequestBody String contactStringId) {
        try {
            UUID contactUuid = UUID.fromString(contactStringId);
            this.contactService.removeContactFromContactBook(contactBookUuid, contactUuid);
            return ResponseEntity.ok("Removed contact from contact book");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Could not remove contact from contact book");
        }
    }

    @DeleteMapping("/contactbook/delete/{uuid}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted ContactBook"),
            @ApiResponse(responseCode = "404", description = "ContactBook not found")
    })
    public String deleteContactBook(@PathVariable UUID uuid) {
        try {
            boolean success = this.contactService.deleteContactBook(uuid);
            if (success) {
                return "ContactList deleted: " + uuid;
            } else {
                throw new Exception("ERROR: Could not delete contact book");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Could not delete contact book with UUID: " + uuid;
        }
    }
}
