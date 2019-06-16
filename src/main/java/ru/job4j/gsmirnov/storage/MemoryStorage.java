package ru.job4j.gsmirnov.storage;

import ru.job4j.gsmirnov.models.User;

import java.util.Map;
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
        this.memory.put(id.getAndIncrement(), user);
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
}
