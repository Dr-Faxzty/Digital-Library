package it.yellowradiators.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashUtilTest {

    @Test
    void testHashPassword_SameInputProducesSameHash() {
        String hash1 = HashUtil.hashPassword("password123");
        String hash2 = HashUtil.hashPassword("password123");

        assertEquals(hash1, hash2);
    }

    @Test
    void testHashPassword_DifferentInputsProduceDifferentHashes() {
        String hash1 = HashUtil.hashPassword("password123");
        String hash2 = HashUtil.hashPassword("differentPassword");

        assertNotEquals(hash1, hash2);
    }

    @Test
    void testHashPassword_NotNullOrEmpty() {
        String hash = HashUtil.hashPassword("any");
        assertNotNull(hash);
        assertFalse(hash.isEmpty());
    }

    @Test
    void testHashPassword_CorrectLength() {
        String hash = HashUtil.hashPassword("abc");
        assertEquals(64, hash.length(), "SHA-256 hash should be 64 hex characters long");
    }
}
