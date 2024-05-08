package de.dhbw.ase.contacts.plugins.rest;

import de.dhbw.ase.contacts.application.services.ContactService;
import de.dhbw.ase.contacts.domain.entities.contactbook.ContactBook;
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
    public ResponseEntity<Void> saveContactBook(@RequestBody ContactBook contactBook) {
        try {
            this.contactService.saveContactBook(contactBook);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/contactbook/{contactBookUuid}/addcontact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added Contact to ContactBook"),
            @ApiResponse(responseCode = "400", description = "Could not add Contact to ContactBook"),
            @ApiResponse(responseCode = "404", description = "ContactBook or Contact not found")
    })
    public ResponseEntity<Void> addContactToContactBook(@PathVariable UUID contactBookUuid, @RequestBody String contactStringId) {
        try {
            UUID contactUuid = UUID.fromString(contactStringId);
            this.contactService.addContactToContactBook(contactBookUuid, contactUuid);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/contactbook/{contactBookUuid}/removecontact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully removed Contact from ContactBook"),
            @ApiResponse(responseCode = "400", description = "Could not remove Contact from ContactBook"),
            @ApiResponse(responseCode = "404", description = "ContactBook or Contact not found")
    })
    public ResponseEntity<Void> removeContactFromContactBook(@PathVariable UUID contactBookUuid, @RequestBody String contactStringId) {
        try {
            UUID contactUuid = UUID.fromString(contactStringId);
            this.contactService.removeContactFromContactBook(contactBookUuid, contactUuid);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/contactbook/delete/{uuid}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted ContactBook"),
            @ApiResponse(responseCode = "404", description = "ContactBook not found")
    })
    public ResponseEntity<Void> deleteContactBook(@PathVariable UUID uuid) {
        try {
            this.contactService.deleteContactBook(uuid);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
