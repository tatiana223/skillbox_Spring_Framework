package org.example.service;

import org.example.model.Contact;
import org.example.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    public void addContact(String fullName, String phoneNumber, String email) {
        Contact contact = new Contact(fullName, phoneNumber, email);
        contactRepository.save(contact);
    }

    public boolean deleteContact(String email) {
        return contactRepository.deleteByEmail(email);
    }

    public void saveContactsToFile(String filename) {
        contactRepository.saveAllToFile(filename);
    }

    public boolean contactExists(String email) {
        return contactRepository.findByEmail(email).isPresent();
    }
}
