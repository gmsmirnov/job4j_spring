package ru.job4j.gsmirnov.storage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.job4j.gsmirnov.models.User;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;

/**
 * The Memory storage testing-class. It tests Memory-storage methods.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 15/06/2019
 */
public class MemoryStorageTest {
    private MemoryStorage storage;

    @Before
    public void init() {
        this.storage = new MemoryStorage();
    }

    @After
    public void clear() {
        this.storage.emptyUsers();
    }

    @Test
    public void whenAddUserThenMemoryHasIt() {
        User user = new User("user", "user");
        this.storage.addUser(user);
        assertThat(this.storage.contains(user), is(true));
    }

    @Test
    public void whenGetAllUsersThenGetListOfAllUsers() {
        User admin = new User("admin", "admin_password");
        User user = new User("user", "user_password");
        this.storage.addUser(admin);
        this.storage.addUser(user);
        List<User> users = this.storage.getAllUsers();
        assertThat(users, hasItem(admin));
        assertThat(users, hasItem(user));
        assertThat(users.size(), is(2));
    }

    @Test
    public void whenEmptyUsersTableThenItsSizeIsNull() {
        User admin = new User("admin", "admin_password");
        User user = new User("user", "user_password");
        this.storage.addUser(admin);
        this.storage.addUser(user);
        assertThat(this.storage.getAllUsers().size(), is(2));
        this.storage.emptyUsers();
        assertThat(this.storage.getAllUsers().size(), is(0));
    }

    @Test
    public void whenDeleteUserByIdThenStorageDoesNotContainIt() {
        User admin = new User("admin", "admin_password");
        this.storage.addUser(admin);
        int id = this.storage.getUserId(admin);
        assertThat(this.storage.contains(admin), is(true));
        this.storage.removeUser(id);
        assertThat(this.storage.contains(admin), is(false));
    }

    @Test
    public void whenDeleteUserByLoginThenStorageDoesNotContainIt() {
        User admin = new User("admin", "admin_password");
        this.storage.addUser(admin);
        assertThat(this.storage.contains(admin), is(true));
        this.storage.removeUser("admin");
        assertThat(this.storage.contains(admin), is(false));
    }

    @Test
    public void whenNoUserWithSuchLoginThenNull() {
        assertNull(this.storage.getUser("admin"));
    }

    @Test
    public void whenNoUserWithSuchIdThenNull() {
        assertNull(this.storage.getUser(100));
    }

    @Test
    public void whenNoSuchUserThenWrongId() {
        assertThat(this.storage.getUserId(new User("1", "1")), is(-1));
    }
}