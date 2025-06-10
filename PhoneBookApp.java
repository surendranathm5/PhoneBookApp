import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple phonebook application using HashMap to store contacts.
 * Supports adding contacts, retrieving by name, and searching.
 */
public class PhoneBookApp {

    // Contact class represents a single contact's details
    static class Contact {
        private String name;
        private String phoneNumber;
        private String email;

        public Contact(String name, String phoneNumber, String email) {
            this.name = name;
            this.phoneNumber = phoneNumber;
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public String getEmail() {
            return email;
        }

        @Override
        public String toString() {
            return "Name: " + name
                + ", Phone: " + (phoneNumber.isEmpty() ? "N/A" : phoneNumber)
                + ", Email: " + (email.isEmpty() ? "N/A" : email);
        }
    }

    // PhoneBook class manages contacts using a HashMap
    static class PhoneBook {
        private Map<String, Contact> contacts;

        public PhoneBook() {
            contacts = new HashMap<>();
        }

        /**
         * Adds a new contact, or updates if name already exists.
         * @param name Contact name (unique key)
         * @param phone Phone number (can be empty)
         * @param email Email address (can be empty)
         */
        public void addContact(String name, String phone, String email) {
            if (contacts.containsKey(name)) {
                // Update existing contact
                Contact existing = contacts.get(name);
                if (!phone.isEmpty()) {
                    existing.setPhoneNumber(phone);
                }
                if (!email.isEmpty()) {
                    existing.setEmail(email);
                }
                System.out.println("Contact updated: " + name);
            } else {
                Contact contact = new Contact(name, phone, email);
                contacts.put(name, contact);
                System.out.println("Contact added: " + name);
            }
        }

        /**
         * Retrieves a contact by exact name.
         * @param name Contact name to retrieve
         * @return Contact if found, else null
         */
        public Contact getContact(String name) {
            return contacts.get(name);
        }

        /**
         * Searches contacts by partial name (case-insensitive).
         * @param query Partial name to search
         * @return List of matching contacts
         */
        public List<Contact> searchContacts(String query) {
            List<Contact> results = new ArrayList<>();
            String lowercaseQuery = query.toLowerCase();
            for (Contact c : contacts.values()) {
                if (c.getName().toLowerCase().contains(lowercaseQuery)) {
                    results.add(c);
                }
            }
            return results;
        }

        /**
         * Lists all contacts in alphabetical order of name.
         */
        public void listAllContacts() {
            if (contacts.isEmpty()) {
                System.out.println("Phonebook is empty.");
                return;
            }
            System.out.println("Listing all contacts:");
            contacts.values().stream()
                .sorted((c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()))
                .forEach(c -> System.out.println(c));
        }
    }

    // Main program with simple command line interface
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PhoneBook phoneBook = new PhoneBook();

        System.out.println("Welcome to the PhoneBook App!");
        boolean exit = false;

        while (!exit) {
            System.out.println("\nChoose an option:");
            System.out.println("1 - Add / Update contact");
            System.out.println("2 - Retrieve contact by name");
            System.out.println("3 - Search contacts by partial name");
            System.out.println("4 - List all contacts");
            System.out.println("5 - Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Enter phone number (or leave blank): ");
                    String phone = scanner.nextLine().trim();
                    System.out.print("Enter email (or leave blank): ");
                    String email = scanner.nextLine().trim();
                    if (name.isEmpty()) {
                        System.out.println("Name cannot be empty. Contact not added.");
                    } else {
                        phoneBook.addContact(name, phone, email);
                    }
                    break;

                case "2":
                    System.out.print("Enter exact name to retrieve: ");
                    String retrieveName = scanner.nextLine().trim();
                    Contact contact = phoneBook.getContact(retrieveName);
                    if (contact != null) {
                        System.out.println("Contact found:\n" + contact);
                    } else {
                        System.out.println("No contact found with name: " + retrieveName);
                    }
                    break;

                case "3":
                    System.out.print("Enter partial name to search: ");
                    String query = scanner.nextLine().trim();
                    List<Contact> results = phoneBook.searchContacts(query);
                    if (results.isEmpty()) {
                        System.out.println("No contacts match your search.");
                    } else {
                        System.out.println("Search results:");
                        results.forEach(c -> System.out.println(c));
                    }
                    break;

                case "4":
                    phoneBook.listAllContacts();
                    break;

                case "5":
                    exit = true;
                    System.out.println("Exiting PhoneBook App. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please enter a number between 1 and 5.");
                    break;
            }
        }

        scanner.close();
    }
}

