package org.example.repository;

import org.example.model.Contact;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {

    List<Contact> findAll();

    Optional<Contact> findByEmail(String email);

    void save(Contact contact);

    boolean deleteByEmail(String email);

    void saveAllToFile(String filename);

}
