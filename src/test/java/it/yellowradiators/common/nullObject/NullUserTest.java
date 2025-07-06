package test.java.it.yellowradiators.common.nullObject;

import it.yellowradiators.common.enums.Role;
import it.yellowradiators.common.interfaces.IUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class NullUserTest {
    class NullUserTest{
        private IUser nullUser;

        @BeforeEach
        void setUp() {
            nullUser = new it.yellowradiators.common.nullObject.NullUser();
        }

        @Test
        void testIsNullReturnsTrue() {
            assert nullUser.isNull();
        }

        @Test
        void testGettersReturnDefaults() {
            assert "".equals(nullUser.getName());
            assert "".equals(nullUser.getSurname());
            assert "".equals(nullUser.getTaxIdCode());
            assert "".equals(nullUser.getEmail());
            assert "".equals(nullUser.getPassword());
            assert Role.USER.equals(nullUser.getRole());
        }

        @Test
        void testToStringIsEmpty() {
            assert "".equals(nullUser.toString());
        }

        @Test
        void testSettersDoNotThrow() {
            nullUser.setName("X");
            nullUser.setSurname("X");
            nullUser.setTaxIdCode("X");
            nullUser.setEmail("X");
            nullUser.setPassword("X");
            nullUser.setRole(Role.ADMIN);
        }
    }
}
