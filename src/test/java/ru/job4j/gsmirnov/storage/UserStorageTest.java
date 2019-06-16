package ru.job4j.gsmirnov.storage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.job4j.gsmirnov.models.User;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * The user storage-class test. Tests methods and behavior.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 15/06/2019
 */
public class UserStorageTest {
    @Test
    public void whenAddUserThenStorageContainsIt() {
        MemoryStorage memory = new MemoryStorage();
        UserStorage storage = new UserStorage(memory);
        User user = new User("admin", "admin");
        storage.add(user);
        assertThat(storage.contains(user), is(true));
    }

    @Test
    public void whenLoadContextThenNotNull() {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
        UserStorage memory = context.getBean(UserStorage.class);
        User user = new User("admin", "admin");
        memory.add(user);
        assertThat(memory.contains(user), is(true));
    }
}