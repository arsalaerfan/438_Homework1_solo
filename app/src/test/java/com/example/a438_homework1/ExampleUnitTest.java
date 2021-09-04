package com.example.a438_homework1;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 *
 *
 */

public class ExampleUnitTest {

    @Test
    public void verifyUsername() {
        List<User> users =  new ArrayList<>();
        User test = new User(1, "Bob", "bobTheBuilder", "bobBuilder@gmail.com");
        users.add(test);
        assertTrue(LoginActivity.validUsername(users, "bobTheBuilder"));
    }

    @Test
    public void verifyDiffPasswords() {
        assertFalse(LoginActivity.validPassword("hello", "helloooo"));
    }

    @Test
    public void verifyPassword() {
        assertTrue(LoginActivity.validPassword("123", "123"));
    }
}