package com.example.manager;

import com.example.common.enums.Role;
import com.example.common.interfaces.IUser;
import com.example.common.nullObject.NullUser;
import com.example.model.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class SessionManagerTest {

    private SessionManager sessionManager;

    @BeforeEach
    void setUp() {
        sessionManager = SessionManager.getInstance();
        sessionManager.logout();
    }

    @Test
    void testSingletonInstance() {
        SessionManager first = SessionManager.getInstance();
        SessionManager second = SessionManager.getInstance();
        assertSame(first, second);
    }

    @Test
    void testLogin() {
        IUser user = new User("Alice", "Rossi", "RSSALC00A01F205Z", "alice@mail.com", "hashed", Role.USER);
        sessionManager.login(user);
        assertEquals(user, sessionManager.getLoggedUser());
        assertTrue(sessionManager.isLoggedIn());
    }

    @Test
    void testLogout() {
        IUser user = new User("Bob", "Verdi", "VRDBOB01A01H501T", "bob@mail.com", "hashed", Role.ADMIN);
        sessionManager.login(user);
        sessionManager.logout();
        assertTrue(sessionManager.getLoggedUser() instanceof NullUser);
        assertFalse(sessionManager.isLoggedIn());
    }

    @Test
    void testIsLoggedIn_WhenNotLogged() {
        assertFalse(sessionManager.isLoggedIn());
    }

    @Test
    void testIsAdmin_True() {
        IUser admin = new User("Admin", "One", "ADMONE01A01H501T", "admin@mail.com", "hashed", Role.ADMIN);
        sessionManager.login(admin);
        assertTrue(sessionManager.isAdmin());
    }

    @Test
    void testIsAdmin_False_WhenNotLogged() {
        sessionManager.logout();
        assertFalse(sessionManager.isAdmin());
    }

    @Test
    void testIsAdmin_False_WhenUserIsNotAdmin() {
        IUser user = new User("Charlie", "Neri", "NRICHR02A01H501T", "charlie@mail.com", "hashed", Role.USER);
        sessionManager.login(user);
        assertFalse(sessionManager.isAdmin());
    }

    @Test
    void testGetLoggedUser_WhenNotLogged() {
        sessionManager.logout();
        assertTrue(sessionManager.getLoggedUser() instanceof NullUser);
    }
}
