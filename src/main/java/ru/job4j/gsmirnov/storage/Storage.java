package ru.job4j.gsmirnov.storage;

import ru.job4j.gsmirnov.models.User;

import java.util.List;

/**
 * The basic storage interface which provides methods to work with it. Its implementations can be used in other
 * specific storage facilities as an inside architecture.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 15/06/2019
 */
public interface Storage {
    /**
     * Adds the specified user into the storage.
     *
     * @param user the specified user.
     */
    void addUser(User user);

    /**
     * Gets the user from the storage by the specified id.
     *
     * @param id the specified id.
     * @return the user which is mapped to the specified id.
     */
    User getUser(int id);

    /**
     * Gets the user from the storage by the specified login (the login id unique field).
     *
     * @param login the specified login.
     * @return the user which has the specified login.
     */
    User getUser(String login);

    /**
     * Gets the specified user's id.
     *
     * @param user the specified user.
     * @return id which is mapped to the specified user.
     */
    int getUserId(User user);

    /**
     * Gets the list of all users.
     *
     * @return the list of all users.
     */
    List<User> getAllUsers();

    /**
     * Checks if there is the specified user in the container.
     *
     * @param user the specified user.
     * @return true if contains, false either.
     */
    boolean contains(User user);

    /**
     * Removes the user with the mapped id from the storage.
     *
     * @param id the specified id.
     */
    void removeUser(int id);

    /**
     * Removes the user with the specified login (the login is unique) from the storage.
     *
     * @param login the specified login.
     */
    void removeUser(String login);

    /**
     * Empties the users table in the storage.
     */
    void emptyUsers();
}