package ru.job4j.gsmirnov.storage;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.job4j.gsmirnov.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * The JDBC storage-class. It stores {@link User}-objects in a Data Base.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 15/06/2019
 */
public class JdbcStorage implements Storage {
    /**
     * The logger.
     */
    private static final Logger LOG = LogManager.getLogger(JdbcStorage.class);

    /**
     * The minimum number of idle connections in the pool.
     */
    private final static int CONFIG_MIN_IDLE_CONNECTIONS = 5;

    /**
     * The maximum number of idle connections in the pool.
     */
    private final static int CONFIG_MAX_IDLE_CONNECTIONS = 10;

    /**
     * The number of the <code>maxOpenPreparedStatements</code> property.
     */
    private final static int CONFIG_MAX_PREPARED_STATEMENTS = 100;

    /**
     * Constant for users table column named 'id'.
     */
    private final static String COLUMN_ID = "id";

    /**
     * Constant for users table column named 'login'.
     */
    public final static String COLUMN_LOGIN = "login";

    /**
     * Constant for users table column named 'password'.
     */
    public final static String COLUMN_PASSWORD = "password";

    /**
     * The connections' pool.
     */
    private static final BasicDataSource SOURCE = new BasicDataSource();

    /**
     * Spring JDBC core.
     */
    private final JdbcTemplate jdbcTemplate;

    /**
     * JDBC-storage constructor. Create an object to work with MySQL database.
     *
     * @param url MySQL database's url.
     * @param username MySQL database's username.
     * @param password MySQL database's password for the specified user.
     * @param driver MySQL driver - ConnectorJ.
     */
    public JdbcStorage(String url, String username, String password, String driver) {
        this.jdbcTemplate = new JdbcTemplate(SOURCE);
        SOURCE.setDriverClassName(driver);
        SOURCE.setUrl(url);
        SOURCE.setUsername(username);
        SOURCE.setPassword(password);
        SOURCE.setMinIdle(CONFIG_MIN_IDLE_CONNECTIONS);
        SOURCE.setMaxIdle(CONFIG_MAX_IDLE_CONNECTIONS);
        SOURCE.setMaxOpenPreparedStatements(CONFIG_MAX_PREPARED_STATEMENTS);
    }

    /**
     * Adds the specified user into the storage.
     *
     * @param user the specified user.
     */
    @Override
    public void addUser(User user) {
        this.jdbcTemplate.update("insert into users(login, password) values (?, ?);", user.getLogin(), user.getPassword());
    }

    /**
     * Gets the user from the storage by the specified id.
     *
     * @param id the specified id.
     * @return the user which is mapped to the specified id.
     * @throws IncorrectResultSizeDataAccessException – if the query does not return exactly one row.
     */
    @Override
    public User getUser(int id) throws IncorrectResultSizeDataAccessException {
        return this.jdbcTemplate.queryForObject("select login, password from users where id = ?;", new UserMapper(), id);
    }

    /**
     * Gets the user from the storage by the specified login (the login id unique field).
     *
     * @param login the specified login.
     * @return the user which has the specified login.
     * @throws IncorrectResultSizeDataAccessException – if the query does not return exactly one row.
     */
    @Override
    public User getUser(String login) throws IncorrectResultSizeDataAccessException {
        return this.jdbcTemplate.queryForObject("select login, password from users where login = ?;", new UserMapper(), login);
    }

    /**
     * Gets the specified user's id.
     *
     * @param user the specified user.
     * @return id which is mapped to the specified user.
     * @throws IncorrectResultSizeDataAccessException – if the query does not return exactly one row.
     */
    @Override
    public int getUserId(User user) throws IncorrectResultSizeDataAccessException {
        return this.jdbcTemplate.queryForObject("select id from users where login = ?;", Integer.class, user.getLogin());
    }

    /**
     * Gets the list of all users.
     *
     * @return the list of all users.
     */
    @Override
    public List<User> getAllUsers() {
        return this.jdbcTemplate.query("select login, password from users;", new UserMapper());
    }

    /**
     * Checks if there is the specified user in the container.
     *
     * @param user the specified user.
     * @return true if contains, false either.
     */
    @Override
    public boolean contains(User user) {
        List<User> strList  = this.jdbcTemplate.query("select login, password from users", new UserMapper());
        return strList.contains(user);
    }

    /**
     * Removes the user with the mapped id from the storage.
     *
     * @param id the specified id.
     */
    @Override
    public void removeUser(int id) {
        this.jdbcTemplate.update("delete from users where id = ?;", id);
    }

    /**
     * Removes the user with the specified login (the login is unique) from the storage.
     *
     * @param login the specified login.
     */
    @Override
    public void removeUser(String login) {
        this.jdbcTemplate.update("delete from users where login = ?;", login);
    }

    /**
     * Empties the users table in the storage.
     */
    @Override
    public void emptyUsers() {
        this.jdbcTemplate.update("delete from users;");
    }
}

/**
 * The RowMapper class. It maps one object per requested row into User-model.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 20/06/2019
 */
class UserMapper implements RowMapper<User> {
    /**
     * Creates User-object from query result table row.
     *
     * @param rs the {@link ResultSet} query result table.
     * @param rowNum the number of the current row.
     * @return the User-object for the current row.
     * @throws SQLException if the SQL problems were encountered getting column values.
     */
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setLogin(rs.getString(JdbcStorage.COLUMN_LOGIN));
        user.setPassword(rs.getString(JdbcStorage.COLUMN_PASSWORD));
        return user;
    }
}