package ru.job4j.gsmirnov.storage;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import ru.job4j.gsmirnov.models.User;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;

/**
 * The user storage-class test. Tests methods and behavior. The inner storage configures by xml-file.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 15/06/2019
 */
public class UserStorageTest {
    private UserStorage storage;

    @Before
    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        this.storage = context.getBean(UserStorage.class);
        this.storage.empty();
    }

    @After
    public void clear() {
        this.storage.empty();
        this.storage = null;
    }

    @Test
    public void whenLoadContextThenNotNull() {
        assertNotNull(this.storage);
    }

    @Test
    public void whenAddUserThenStorageContainsIt() {
        User user = new User("admin", "admin");
        this.storage.add(user);
        assertThat(this.storage.contains(user), is(true));
    }

    @Test
    public void whenGetAllUsersThenGetListOfAllUsers() {
        User admin = new User("admin", "admin_password");
        User user = new User("user", "user_password");
        this.storage.add(admin);
        this.storage.add(user);
        List<User> users = this.storage.getAll();
        assertThat(users, hasItem(admin));
        assertThat(users, hasItem(user));
        assertThat(users.size(), Is.is(2));
    }

    @Test
    public void whenEmptyUsersTableThenItsSizeIsNull() {
        User admin = new User("admin", "admin_password");
        User user = new User("user", "user_password");
        this.storage.add(admin);
        this.storage.add(user);
        assertThat(this.storage.getAll().size(), Is.is(2));
        this.storage.empty();
        assertThat(this.storage.getAll().size(), Is.is(0));
    }

    @Test
    public void whenDeleteUserByIdThenStorageDoesNotContainIt() {
        User admin = new User("admin", "admin_password");
        this.storage.add(admin);
        int id = this.storage.getId(admin);
        assertThat(this.storage.contains(admin), Is.is(true));
        this.storage.remove(id);
        assertThat(this.storage.contains(admin), Is.is(false));
    }

    @Test
    public void whenDeleteUserByLoginThenStorageDoesNotContainIt() {
        User admin = new User("admin", "admin_password");
        this.storage.add(admin);
        assertThat(this.storage.contains(admin), Is.is(true));
        this.storage.remove("admin");
        assertThat(this.storage.contains(admin), Is.is(false));
    }

    /**
     * Exception catches only if the storage is JDBC.
     */
    @Test (expected = IncorrectResultSizeDataAccessException.class)
    public void whenNoUserWithSuchLoginThenException() {
        this.storage.get("admin");
    }

    /**
     * Exception catches only if the storage is JDBC.
     */
    @Test (expected = IncorrectResultSizeDataAccessException.class)
    public void whenNoUserWithSuchIdThenException() {
        this.storage.get(100);
    }

    /**
     * Exception catches only if the storage is JDBC.
     */
    @Test (expected = IncorrectResultSizeDataAccessException.class)
    public void whenNoSuchUserThenException() {
        this.storage.getId(new User("1", "1"));
    }
}