package ru.job4j.gsmirnov.storage;

import ru.job4j.gsmirnov.models.User;

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
     * Checks if there is the specified user in the container.
     *
     * @param user the specified user.
     * @return true if contains, false either.
     */
    boolean contains(User user);
}