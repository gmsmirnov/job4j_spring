package ru.job4j.gsmirnov.models;

import java.util.Objects;

/**
 * User's model.
 *
 * @author Gregory Smirnov (artress@ngs.ru)
 * @version 1.0
 * @since 15/06/2019
 */
public class User {
    /**
     * The user's login.
     */
    private String login;

    /**
     * The user's password.
     */
    private String password;

    /**
     *
     */
    public User() {
        this.login = "";
        this.password = "";
    }

    /**
     * User-constructor. Creates user-object with the specified parameters.
     *
     * @param login the specified login.
     * @param password the specified password.
     */
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /**
     * Gets this user's login.
     *
     * @return this user's login.
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * Sets the specified value into the user's login field.
     *
     * @param login the new specified login.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets this user's password.
     *
     * @return this user's password.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the specified value into the user's password field.
     *
     * @param password the new specified password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Compares this user to another for equivalence.
     *
     * @param o another user.
     * @return true if these users are equals.
     */
    @Override
    public boolean equals(Object o) {
        boolean result;
        if (this == o) {
            result = true;
        } else if (o == null || getClass() != o.getClass()) {
            result = false;
        } else {
            User user = (User) o;
            result = Objects.equals(this.login, user.login) && Objects.equals(this.password, user.password);
        }
        return result;
    }

    /**
     * Calculates the hashCode for this user.
     *
     * @return calculated hashCode for this user.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.login, this.password);
    }

    /**
     * Generates the {@link String}-presentation for this user.
     *
     * @return the {@link String}-presentation of this user.
     */
    @Override
    public String toString() {
        return String.format("User {login='%s', password='%s'}", this.login, this.password);
    }
}