package org.example.init;

import org.example.model.Contact;
import org.example.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Component
@Profile("init")
public class ContactInitializer {

    private final ContactRepository contactRepository;

    @Value("${app.default-contacts-path:/default-contacts.txt}")
    private String contactsFilePath;

    public ContactInitializer(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @PostConstruct
    public void init() {
        System.out.println("=== Initializing contacts from: " + contactsFilePath + " ===");
        loadContactsFromFile();
    }

    private void loadContactsFromFile() {
        String resourcePath = contactsFilePath.startsWith("/")
                ? contactsFilePath.substring(1)
                : contactsFilePath;

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            if (inputStream == null) {
                throw new RuntimeException("File not found in classpath: " + resourcePath);
            }

            String line;
            int loadedCount = 0;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    try {
                        Contact contact = parseContactLine(line);
                        contactRepository.save(contact);
                        loadedCount++;
                    } catch (IllegalArgumentException e) {
                        System.err.println("Skipped invalid line: " + line);
                    }
                }
            }
            System.out.println("Successfully loaded " + loadedCount + " contacts");

        } catch (IOException e) {
            throw new RuntimeException("Error loading contacts from: " + contactsFilePath, e);
        }
    }

    private Contact parseContactLine(String line) {
        String[] parts = line.split(";");
        if (parts.length == 3) {
            return new Contact(parts[0].trim(), parts[1].trim(), parts[2].trim());
        }
        throw new IllegalArgumentException("Invalid line format: " + line);
    }
}