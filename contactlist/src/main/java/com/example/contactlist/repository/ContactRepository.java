package com.example.contactlist.repository;

import com.example.contactlist.model.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {
    List<Contact> findAll();
    Optional<Contact> findById(Long id);
    Contact save(Contact contact);
    Contact update(Contact contact);
    void deleteId(Long id);
    void batchInsert(List<Contact> contact);

}
