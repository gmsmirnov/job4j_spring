package ru.job4j.gsmirnov.storage;

import ru.job4j.gsmirnov.models.User;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * The memory storage-class. It stores {@link User}-objects in thread safe structure ({@link ConcurrentHashMap}) in memory.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 15/06/2019
 */
public class MemoryStorage implements Storage {
    /**
     * The thread-safe memory storage.
     */
    private final Map<Integer, User> memory = new ConcurrentHashMap<>();

    /**
     * Current hash-map id, it increments after addition of every new user.
     */
    private AtomicInteger id = new AtomicInteger(0);

    /**
     * Adds the specified user into the storage.
     *
     * @param user the specified user.
     */
    @Override
    public void addUser(User user) {
        this.memory.put(this.id.getAndIncrement(), user);
    }

    /**
     * Gets the user from the storage by the specified id.
     *
     * @param id the specified id.
     * @return the user which is mapped to the specified id.
     */
    @Override
    public User getUser(int id) {
        return this.memory.get(id);
    }

    /**
     * Gets the user from the storage by the specified login (the login id unique field).
     *
     * @param login the specified login.
     * @return the user which has the specified login.
     */
    @Override
    public User getUser(String login) {
        User result = null;
        for (User user : this.getAllUsers()) {
            if (login.equals(user.getLogin())) {
                result = user;
                break;
            }
        }
        return result;
    }

    /**
     * Gets the specified user's id.
     *
     * @param user the specified user.
     * @return id which is mapped to the specified user.
     */
    @Override
    public int getUserId(User user) {
        int result = -1;
        Set<Map.Entry<Integer, User>> set = this.memory.entrySet();
        for (Map.Entry<Integer, User> entry : set) {
            if (entry.getValue().equals(user)) {
                result = entry.getKey();
                break;
            }
        }
        return result;
    }

    /**
     * Gets the list of all users.
     *
     * @return the list of all users.
     */
    @Override
    public List<User> getAllUsers() {
        return new LinkedList<User>(this.memory.values());
    }

    /**
     * Checks if there is the specified user in the container.
     *
     * @param user the specified user.
     * @return true if contains, false either.
     */
    @Override
    public boolean contains(User user) {
        return this.memory.containsValue(user);
    }

    /**
     * Removes the user with the mapped id from the storage.
     *
     * @param id the specified id.
     */
    @Override
    public void removeUser(int id) {
        this.memory.remove(id);
    }

    /**
     * Removes the user with the specified login (the login is unique) from the storage.
     *
     * @param login the specified login.
     */
    @Override
    public void removeUser(String login) {
        Set<Map.Entry<Integer, User>> set = this.memory.entrySet();
        for (Map.Entry<Integer, User> entry : set) {
            if (login.equals(entry.getValue().getLogin())) {
                this.removeUser(entry.getKey());
                break;
            }
        }
    }

    /**
     * Empties the users table in the storage.
     */
    @Override
    public void emptyUsers() {
        this.memory.clear();
        this.id = new AtomicInteger(0);
    }
}