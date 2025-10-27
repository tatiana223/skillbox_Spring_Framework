package com.example.contactlist;

import com.example.contactlist.model.Contact;
import com.example.contactlist.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataBaseContactsCreator {

    private final ContactService contactService;

    @EventListener(ApplicationReadyEvent.class)
    public void createContactData() {

        List<Contact> contacts = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            int value = i + 1;
            Contact contact = new Contact();
            contact.setId((long) value);
            contact.setFirstName("firstName " + value);
            contact.setLastName("lastName " + value);
            contact.setEmail("email " + value);
            contact.setPhone("phone " + value);

            contacts.add(contact);
        }

        contactService.batchInsert(contacts);
    }

}
