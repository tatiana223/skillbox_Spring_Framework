package org.example;

import org.example.repository.FileContactRepository;
import org.example.service.ContactService;

public class TestApp {
    public static void main(String[] args) {
        FileContactRepository repository = new FileContactRepository();
        ContactService service = new ContactService(repository);

        System.out.println("=== Add contact ===");

        service.addContact("Contact1", "899999993342", "t@gmail.com");
        service.addContact("Contact2", "+79162223344", "maria@mail.ru");

        System.out.println("=== All contacts ===");
        service.getAllContacts().forEach(contact -> System.out.println(contact));

        System.out.println("=== contactExists ===");
        System.out.println("t@gmail.com Exists: " + service.contactExists("t@gmail.com"));
        System.out.println("non-existent@mail.ru Exists: " + service.contactExists("maria@mail.ru"));

        System.out.println("=== Delete Contact ===");
        boolean deleted = service.deleteContact("ivan@mail.ru");
        System.out.println("ivan@mail.ru delete: " + deleted);

        System.out.println("=== Contact after delete ===");
        service.getAllContacts().forEach(contact ->
                System.out.println(contact)
        );
    }
}
