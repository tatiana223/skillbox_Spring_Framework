package com.example.contactlist.controller;

import com.example.contactlist.model.Contact;
import com.example.contactlist.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public String listContact(Model model) {

        model.addAttribute("contacts", contactService.findAll());
        return "contacts/list";

    }


    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("contact", new Contact());
        model.addAttribute("isEdit", false);
        return "contacts/form";
    }

    @PostMapping
    public String saveContact(@ModelAttribute Contact contact) {
        contactService.save(contact);
        return "redirect:/contacts";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Contact contact = contactService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid contact id: " + id));
        model.addAttribute("contact", contact);
        model.addAttribute("isEdit", true);
        return "contacts/form";
    }

    @GetMapping("/delete/{id}")
    public String deleteContact(@PathVariable Long id) {
        contactService.deleteId(id);
        return "redirect:/contacts";
    }

}
