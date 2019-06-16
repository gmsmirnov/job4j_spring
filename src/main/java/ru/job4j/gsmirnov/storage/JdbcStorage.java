package ru.job4j.gsmirnov.storage;

import ru.job4j.gsmirnov.models.User;

/**
 * The JDBC storage-class. It stores {@link User}-objects in a Data Base.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 15/06/2019
 */
public class JdbcStorage implements Storage {
    /**
     * Adds the specified user into the storage.
     *
     * @param user the specified user.
     */
    @Override
    public void addUser(User user) {
        System.out.println(String.format("User: %s stored in DB.", user));
    }

    /**
     * Checks if there is the specified user in the container.
     *
     * @param user the specified user.
     * @return true if contains, false either.
     */
    @Override
    public boolean contains(User user) {
        return false;
    }
}
