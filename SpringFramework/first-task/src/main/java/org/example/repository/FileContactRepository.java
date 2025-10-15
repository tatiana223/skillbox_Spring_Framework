package org.example.repository;

import org.example.model.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;

@Repository
public class FileContactRepository implements ContactRepository{

    private final Map<String, Contact> contacts = new HashMap<>();

    @Value("${app.file-for-save:contacts.txt}")
    private String contactsFilePath;

    public FileContactRepository() {

    }

    @Override
    public List<Contact> findAll() {
        return new ArrayList<>(contacts.values());
    }

    @Override
    public Optional<Contact> findByEmail(String email) {
        return Optional.ofNullable(contacts.get(email));
    }

    @Override
    public void save(Contact contact) {
        contacts.put(contact.getEmail(), contact);
    }

    @Override
    public boolean deleteByEmail(String email) {
        return contacts.remove(email) != null;
    }

    @Override
    public void saveAllToFile(String filename) {
        System.out.println("Saving to file: " + filename);

        String fileToSave = filename != null ? filename : contactsFilePath;

        try (PrintWriter writer = new PrintWriter(new FileWriter(fileToSave))) {
            for (Contact contact : contacts.values()) {
                writer.println(contact.toFileString());
            }
            System.out.println("Contacts are saved in file: " + fileToSave);
        } catch (IOException e) {
            throw new RuntimeException("Error saving contacts to file: " + fileToSave, e);
        }

    }


}
