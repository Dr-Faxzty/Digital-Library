package com.example.utils;

import javafx.application.Platform;
import org.junit.jupiter.api.BeforeAll;

import java.util.concurrent.CountDownLatch;

public abstract class JavaFxTestBase {
    @BeforeAll
    static void initToolkit() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(latch::countDown);
        latch.await();
    }
}
