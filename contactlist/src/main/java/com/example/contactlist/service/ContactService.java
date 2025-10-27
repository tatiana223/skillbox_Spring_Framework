package com.example.contactlist.service;

import com.example.contactlist.model.Contact;
import com.example.contactlist.repository.ContactRepository;
import com.example.contactlist.repository.JdbcContactRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactRepository contactRepository;

    public List<Contact> findAll() {
        return contactRepository.findAll();
    }

    public Optional<Contact> findById(long id) {
        return contactRepository.findById(id);
    }

    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    public Contact update(Contact contact) {
        return contactRepository.update(contact);
    }

    public void deleteId(long id) {
        contactRepository.deleteId(id);
    }

    public void batchInsert(List<Contact> contacts) {
        contactRepository.batchInsert(contacts);

    }

}
