package ru.job4j.gsmirnov.storage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.job4j.gsmirnov.models.User;

/**
 * The storage-class for storing users in the depending container.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 15/06/2019
 */
@Component
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
    @Autowired
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
     * Checks if there is the specified user in the storage.
     *
     * @param user the specified user.
     * @return true if contains, false either.
     */
    public boolean contains(User user) {
        return this.storage.contains(user);
    }
}