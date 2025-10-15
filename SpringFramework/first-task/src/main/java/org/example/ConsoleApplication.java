package org.example;

import org.example.service.ContactService;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class ConsoleApplication implements ApplicationInterface {
    private final ContactService contactService;
    private final Scanner scanner;

    public ConsoleApplication(ContactService contactService) {
        this.contactService = contactService;
        this.scanner = new Scanner(System.in); // create ourselves
    }

    @Override
    public void start() {
        System.out.println("=== Contacts ===");
        showMenu();
        processUserInput();
    }

    private void showMenu() {
        System.out.println("1. Show all contacts");
        System.out.println("2. Add contact");
        System.out.println("3. Delete contact");
        System.out.println("4. Save contacts to file");
        System.out.println("5. Exit");
        System.out.print("Choose action: ");
    }

    private void processUserInput() {
        boolean running = true;

        while (running) {
            showMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    showAllContacts();
                    break;
                case "2":
                    addContact();
                    break;
                case "3":
                    deleteContact();
                    break;
                case "4":
                    saveContacts();
                    break;
                case "5":
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            if (running) {
                System.out.println();
            }
        }
    }

    private void showAllContacts() {
        var contacts = contactService.getAllContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            System.out.println("=== All Contacts ===");
            contacts.forEach(contact ->
                    System.out.println(contact)
            );
        }
    }

    private void addContact() {
        System.out.println("=== Add New Contact ===");
        System.out.print("Enter full name: ");
        String fullName = scanner.nextLine().trim();

        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine().trim();

        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        // Basic validation
        if (fullName.isEmpty() || phone.isEmpty() || email.isEmpty()) {
            System.out.println("Error: All fields are required!");
            return;
        }

        if (contactService.contactExists(email)) {
            System.out.println("Error: Contact with this email already exists!");
            return;
        }

        try {
            contactService.addContact(fullName, phone, email);
            System.out.println("Contact added successfully!");
        } catch (Exception e) {
            System.out.println("Error adding contact: " + e.getMessage());
        }
    }

    private void deleteContact() {
        System.out.println("=== Delete Contact ===");
        System.out.print("Enter email of contact to delete: ");
        String email = scanner.nextLine().trim();

        if (email.isEmpty()) {
            System.out.println("Error: Email cannot be empty!");
            return;
        }

        if (contactService.deleteContact(email)) {
            System.out.println("Contact deleted successfully!");
        } else {
            System.out.println("Contact not found!");
        }
    }

    private void saveContacts() {
        System.out.println("=== Save Contacts to File ===");
        System.out.print("Enter filename (or press Enter for default): ");
        String filename = scanner.nextLine().trim();

        if (filename.isEmpty()) {
            filename = null; // use default filename from properties
        }

        try {
            contactService.saveContactsToFile(filename);
            System.out.println("Contacts saved successfully!");
        } catch (Exception e) {
            System.out.println("Error saving contacts: " + e.getMessage());
        }

    }


}