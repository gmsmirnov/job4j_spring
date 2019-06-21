package ru.job4j.gsmirnov.storage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import ru.job4j.gsmirnov.models.User;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.*;

/**
 * The JDBC storage testing-class. It tests JDBC-storage methods.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 15/06/2019
 */
public class JdbcStorageTest {
    private ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
    private JdbcStorage storage = context.getBean(JdbcStorage.class);

    @Before
    public void init() {
        this.storage.emptyUsers();
    }

    @After
    public void clear() {
        this.storage.emptyUsers();
    }

    @Test
    public void whenAddUserThenDbStoredIt() {
        User user = new User("user", "password");
        this.storage.addUser(user);
        int id = this.storage.getUserId(user);
        User result = this.storage.getUser(id);
        assertThat(result.getLogin(), is("user"));
        assertThat(result.getPassword(), is("password"));
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

    @Test (expected = IncorrectResultSizeDataAccessException.class)
    public void whenNoUserWithSuchLoginThenException() {
        this.storage.getUser("admin");
    }

    @Test (expected = IncorrectResultSizeDataAccessException.class)
    public void whenNoUserWithSuchIdThenException() {
        this.storage.getUser(100);
    }

    @Test (expected = IncorrectResultSizeDataAccessException.class)
    public void whenNoSuchUserThenException() {
        this.storage.getUserId(new User("1", "1"));
    }
}