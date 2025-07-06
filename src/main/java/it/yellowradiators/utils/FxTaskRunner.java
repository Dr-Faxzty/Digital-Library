package it.yellowradiators.utils;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class FxTaskRunner {
    public static <T> void runAsync(Supplier<T> backgroundTask, Consumer<T> onSuccess, Runnable onFailure) {
        Service<T> service = new Service<>() {
            @Override
            protected Task<T> createTask() {
                return new Task<>() {
                    @Override
                    protected T call() {
                        return backgroundTask.get();
                    }
                };
            }
        };

        service.setOnSucceeded(e -> onSuccess.accept(service.getValue()));
        service.setOnFailed(e -> onFailure.run());
        service.start();
    }
}