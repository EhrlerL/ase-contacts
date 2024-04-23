package de.dhbw.ase.contacts.plugins.rest;

import io.swagger.v3.oas.models.info.Contact;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

public class ContactListController {
    @GetMapping("/list/{uuid}")
    public String getContactList(@PathVariable UUID uuid) { return "list: " + uuid; }

    @GetMapping("/list/all")
    public String getAllLists() { return "all lists"; }

    @PostMapping("/list/save")
    public String saveList(@RequestBody List<Contact> list) { return "save list"; }

    @DeleteMapping("/list/delete/{uuid}")
    public String deleteList(@PathVariable UUID uuid) { return "delete list"; }
}
