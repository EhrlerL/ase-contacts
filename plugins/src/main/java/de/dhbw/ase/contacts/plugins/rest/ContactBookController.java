package de.dhbw.ase.contacts.plugins.rest;

import de.dhbw.ase.contacts.application.services.ContactBookService;
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
@Tag(name = "ContactBooks")
public class ContactBookController {
    private final ContactBookService contactBookService;

    public ContactBookController(ContactBookService contactBookService) {
        this.contactBookService = contactBookService;
    }

    @GetMapping("/contactbook/{uuid}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved ContactBook"),
            @ApiResponse(responseCode = "404", description = "ContactBook not found")
    })
    public ResponseEntity<ContactBook> getContactBook(@PathVariable UUID uuid) {
        try {
            ContactBook contactBook = this.contactBookService.getContactBook(uuid);
            return ResponseEntity.ok(contactBook);
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/contactbook/all")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all ContactBooks")
    })
    public ResponseEntity<List<ContactBook>> getAllContactBooks() {
        List<ContactBook> contactBooks = this.contactBookService.getAllContactBooks();
        return ResponseEntity.ok(contactBooks);
    }

    @PostMapping("/contactbook/save")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully saved ContactBook"),
            @ApiResponse(responseCode = "400", description = "Could not save ContactBook")
    })
    public ResponseEntity<UUID> saveContactBook(@RequestBody ContactBook contactBook) {
        try {
            this.contactBookService.saveContactBook(contactBook);
            return ResponseEntity.ok(contactBook.getUuid());
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            this.contactBookService.addContactToContactBook(contactBookUuid, contactUuid);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            this.contactBookService.removeContactFromContactBook(contactBookUuid, contactUuid);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/contactbook/{uuid}/rename")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully renamed ContactBook"),
            @ApiResponse(responseCode = "400", description = "Could not rename ContactBook"),
            @ApiResponse(responseCode = "404", description = "ContactBook not found")
    })
    public ResponseEntity<Void> renameContactBook(@PathVariable UUID uuid, @RequestBody String newName) {
        try {
            this.contactBookService.renameContactBook(uuid, newName);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/contactbook/{uuid}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted ContactBook"),
            @ApiResponse(responseCode = "404", description = "ContactBook not found")
    })
    public ResponseEntity<Void> deleteContactBook(@PathVariable UUID uuid) {
        try {
            this.contactBookService.deleteContactBook(uuid);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            System.out.println(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
