package com.example.manager;

import com.example.common.enums.Role;
import com.example.common.interfaces.IUser;
import com.example.common.nullObject.NullUser;
import com.example.model.User;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    private UserManager manager;
    private IUser user1;
    private IUser user2;

    @BeforeEach
    void setUp() {
        manager = new UserManager();
        user1 = new User("Alice", "Rossi", "RSSALC00A01F205Z", "alice@mail.com", "hashed123", Role.USER);
        user2 = new User("Bob", "Verdi", "VRDBOB01A01H501T", "bob@mail.com", "hashed456", Role.ADMIN);
        manager.setInitialUsers(List.of(user1, user2));
    }

    @Test
    void testSingletonInstance() {
        UserManager first = UserManager.getInstance();
        UserManager second = UserManager.getInstance();
        assertSame(first, second);
    }

    @Test
    void testAddUser() {
        IUser newUser = new User("Eva", "Bianchi", "BNCEVA02A01H501T", "eva@mail.com", "pw", Role.USER);
        manager.addUser(newUser);
        assertTrue(manager.getAll().contains(newUser));
    }

    @Test
    void testRemoveUser() {
        manager.removeUser(user1);
        assertFalse(manager.getAll().contains(user1));
    }

    @Test
    void testFindByEmail_Found() {
        IUser found = manager.findByEmail("alice@mail.com");
        assertEquals(user1, found);
    }

    @Test
    void testFindByEmail_NotFound() {
        IUser found = manager.findByEmail("notfound@mail.com");
        assertTrue(found instanceof NullUser);
    }

    @Test
    void testExistsByEmail_True() {
        assertTrue(manager.existsByEmail("bob@mail.com"));
    }

    @Test
    void testExistsByEmail_False() {
        assertFalse(manager.existsByEmail("ghost@mail.com"));
    }

    @Test
    void testRegister_Success() {
        IUser registered = manager.register("Charlie", "Neri", "NRICHR03A01H501T", "charlie@mail.com", "securepw", Role.USER);
        assertEquals("charlie@mail.com", registered.getEmail());
        assertFalse(registered instanceof NullUser);
    }

    @Test
    void testRegister_DuplicateEmail() {
        IUser registered = manager.register("Another", "Alice", "RSSALC00A01F205Z", "alice@mail.com", "pw", Role.USER);
        assertTrue(registered instanceof NullUser);
    }

    @Test
    void testLogin_Success() {
        IUser registered = manager.register("Dino", "Blu", "BLUDIN04A01H501T", "dino@mail.com", "mypassword", Role.USER);
        IUser logged = manager.login("dino@mail.com", "mypassword");
        assertEquals(registered, logged);
    }

    @Test
    void testLogin_Fail_WrongPassword() {
        IUser registered = manager.register("Eli", "Azzurro", "AZZELI05A01H501T", "eli@mail.com", "correctpw", Role.USER);
        IUser logged = manager.login("eli@mail.com", "wrongpw");
        assertTrue(logged instanceof NullUser);
    }

    @Test
    void testLogin_Fail_NonExistingUser() {
        IUser logged = manager.login("nouser@mail.com", "nopw");
        assertTrue(logged instanceof NullUser);
    }

    @Test
    void testSearch() {
        List<IUser> admins = manager.search(user -> user.getRole() == Role.ADMIN);
        assertEquals(1, admins.size());
        assertEquals(user2, admins.get(0));
    }

    @Test
    void testGetAll() {
        List<IUser> all = manager.getAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(user1));
        assertTrue(all.contains(user2));
    }
}
