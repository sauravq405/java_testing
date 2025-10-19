package org.example;

import java.util.List;
import java.util.Optional;
import java.util.Objects;

/**
 * Service layer for Person operations.
 * Validates input and delegates persistence to PersonRepository.
 */
public class PersonService {

    private final PersonRepository repo;

    public PersonService(PersonRepository repo) {
        this.repo = Objects.requireNonNull(repo, "repo must not be null");
    }

    /**
     * Create a new Person after validating required fields and ensuring
     * no other person exists with the same email (case-insensitive).
     *
     * @param person the person to create
     * @return the saved person (with id assigned)
     * @throws IllegalArgumentException if name or email is empty
     * @throws RuntimeException if a person with same email already exists
     */
    public Person create(Person person) {
        checkNotEmpty(person.getName(), "Name is required");
        checkNotEmpty(person.getEmail(), "Email is required");

        Optional<Person> byEmail = repo.findByEmail(person.getEmail());
        if (byEmail.isPresent()) {
            throw new RuntimeException("Person with email '" + person.getEmail() + "' already exists");
        }

        return repo.create(person);
    }

    /**
     * Find a person by id.
     */
    public Optional<Person> findById(Long id) {
        return repo.findById(id);
    }

    /**
     * Return all persons.
     */
    public List<Person> findAll() {
        return repo.findAll();
    }

    /**
     * Find a person by email (case-insensitive).
     */
    public Optional<Person> findByEmail(String email) {
        return repo.findByEmail(email);
    }

    /**
     * Update an existing person after validation.
     *
     * Note: repo.update(...) returns boolean indicating success.
     *
     * @param person the person to update
     * @return true if update succeeded, false otherwise
     * @throws IllegalArgumentException if name or email is empty
     */
    public boolean update(Person person) {
        checkNotEmpty(person.getName(), "Name is required");
        checkNotEmpty(person.getEmail(), "Email is required");
        return repo.update(person);
    }

    /**
     * Delete a person by id.
     *
     * @param id id of person to delete
     * @return true if deleted, false if not found
     */
    public boolean deleteById(Long id) {
        return repo.deleteById(id);
    }

    /**
     * Helper method which validates that the given string is not null or empty.
     * <p>
     * Throws a {@link RuntimeException} if the value is invalid.
     *
     * @param value   the string to check
     * @param message the exception message if validation fails
     * @throws RuntimeException if the value is null or empty
     */
    private void checkNotEmpty(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new RuntimeException(message);
        }
    }

}

