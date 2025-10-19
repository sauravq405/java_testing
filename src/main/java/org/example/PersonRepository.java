package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Simple in-memory repository for {@link Person} entities.
 * <p>
 * This repository uses a thread-safe in-memory store (ConcurrentHashMap)
 * to simulate database operations for CRUD functionality.
 */
public class PersonRepository {

    private static final Map<Long, Person> PERSON_DB = new ConcurrentHashMap<>();
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0L);

    /**
     * Create and save a new {@link Person} in the in-memory database.
     * An auto-generated ID will be assigned to the person.
     *
     * @param person the person to save
     * @return the saved person with an assigned ID
     */
    public Person create(Person person) {
        long id = ID_GENERATOR.incrementAndGet();
        person.setId(id);
        PERSON_DB.put(id, person);
        return person;
    }

    /**
     * Find a {@link Person} by their unique ID.
     *
     * @param id the ID of the person
     * @return an {@link Optional} containing the person if found, or empty if not
     */
    public Optional<Person> findById(Long id) {
        return Optional.ofNullable(PERSON_DB.get(id));
    }

    /**
     * Find a {@link Person} by their email address (case-insensitive).
     *
     * @param email the email to search for
     * @return an {@link Optional} containing the person if found, or empty if not
     */
    public Optional<Person> findByEmail(String email) {
        return PERSON_DB.values().stream()
                .filter(p -> p.getEmail() != null && p.getEmail().equalsIgnoreCase(email))
                .findAny();
    }

    /**
     * Find a {@link Person} by their name (case-insensitive).
     *
     * @param name the name to search for
     * @return an {@link Optional} containing the person if found, or empty if not
     */
    public Optional<Person> findByName(String name) {
        return PERSON_DB.values().stream()
                .filter(p -> p.getName() != null && p.getName().equalsIgnoreCase(name))
                .findAny();
    }

    /**
     * Retrieve all {@link Person} records from the repository.
     *
     * @return a list of all stored persons
     */
    public List<Person> findAll() {
        return new ArrayList<>(PERSON_DB.values());
    }

    /**
     * Update an existing {@link Person} if their ID exists in the repository.
     *
     * @param person the person to update
     * @return true if the update was successful, false if the person was not found
     */
    public boolean update(Person person) {
        Long id = person.getId();
        if (id == null || !PERSON_DB.containsKey(id)) {
            return false;
        }
        PERSON_DB.put(id, person);
        return true;
    }

    /**
     * Delete a {@link Person} by their ID.
     *
     * @param id the ID of the person to delete
     * @return true if the person was removed, false otherwise
     */
    public boolean deleteById(Long id) {
        return PERSON_DB.remove(id) != null;
    }

    /**
     * Clear all data from the repository and reset the ID counter.
     * <p>
     * This method is typically used in tests to reset state.
     */
    public void clear() {
        PERSON_DB.clear();
        ID_GENERATOR.set(0L);
    }
}
