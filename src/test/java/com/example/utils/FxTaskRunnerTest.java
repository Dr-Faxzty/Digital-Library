package com.example.utils;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

public class FxTaskRunnerTest extends JavaFxTestBase {

    @Test
    void testRunAsyncSuccess() throws InterruptedException {
        AtomicReference<String> result = new AtomicReference<>();
        CountDownLatch latch = new CountDownLatch(1);

        FxTaskRunner.runAsync(
                () -> "success",
                value -> {
                    result.set(value);
                    latch.countDown();
                },
                () -> fail("onFailure should not be called")
        );

        latch.await();
        assertEquals("success", result.get());
    }

    @Test
    void testRunAsyncFailure() throws InterruptedException {
        AtomicBoolean failed = new AtomicBoolean(false);
        CountDownLatch latch = new CountDownLatch(1);

        FxTaskRunner.runAsync(
                () -> { throw new RuntimeException("fail"); },
                value -> fail("onSuccess should not be called"),
                () -> {
                    failed.set(true);
                    latch.countDown();
                }
        );

        latch.await();
        assertTrue(failed.get());
    }
}
