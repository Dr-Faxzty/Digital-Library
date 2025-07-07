package com.example.common.interfaces;

import java.util.List;
import java.util.function.Predicate;

public interface Manager<T> {
    List<T> getAll();
    List<T> search(Predicate<T> filter);
}