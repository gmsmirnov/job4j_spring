package ru.job4j.gsmirnov.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.job4j.gsmirnov.models.User;

import java.util.List;

/**
 * The storage-class for storing users in the depending container.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 15/06/2019
 */
public class UserStorage {
    /**
     * The logger.
     */
    private static final Logger LOG = LogManager.getLogger(UserStorage.class);

    /**
     * The inner architecture of storage facility. It can be any of {@link Storage} interface implementation.
     */
    private final Storage storage;

    /**
     * The user storage constructor, creates the user storage with the specified inside architecture.
     *
     * @param storage the specified inside storage.
     */
    public UserStorage(Storage storage) {
        this.storage = storage;
        LOG.info(String.format("New empty User Storage created. The inner architecture is: '%s'.", storage.getClass()));
    }

    /**
     * Adds the specified user into the User Storage.
     *
     * @param user the specified user.
     */
    public void add(User user) {
        this.storage.addUser(user);
        LOG.info(String.format("User: '%s' stored in memory.", user));
    }

    /**
     * Gets the user from the storage by the specified id.
     *
     * @param id the specified id.
     * @return the user which is mapped to the specified id.
     */
    public User get(int id) {
        return this.storage.getUser(id);
    }

    /**
     * Gets the user from the storage by the specified login (the login id unique field).
     *
     * @param login the specified login.
     * @return the user which has the specified login.
     */
    public User get(String login) {
        return this.storage.getUser(login);
    }

    /**
     * Gets the specified user's id.
     *
     * @param user the specified user.
     * @return id which is mapped to the specified user.
     */
    public int getId(User user) {
        return this.storage.getUserId(user);
    }

    /**
     * Gets the list of all users.
     *
     * @return the list of all users.
     */
    public List<User> getAll() {
        return this.storage.getAllUsers();
    }

    /**
     * Checks if there is the specified user in the storage.
     *
     * @param user the specified user.
     * @return true if contains, false either.
     */
    public boolean contains(User user) {
        return this.storage.contains(user);
    }

    /**
     * Removes the user with the mapped id from the storage.
     *
     * @param id the specified id.
     */
    public void remove(int id) {
        this.storage.removeUser(id);
    }

    /**
     * Removes the user with the specified login (the login is unique) from the storage.
     *
     * @param login the specified login.
     */
    public void remove(String login) {
        this.storage.removeUser(login);
    }

    /**
     * Empties the users table in the storage.
     */
    public void empty() {
        this.storage.emptyUsers();
    }
}