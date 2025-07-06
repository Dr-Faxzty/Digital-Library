package it.yellowradiators.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.yellowradiators.common.adapter.InterfaceAdapter;
import it.yellowradiators.common.adapter.LocalDateAdapter;
import it.yellowradiators.common.interfaces.IBook;
import it.yellowradiators.common.interfaces.ILoan;
import it.yellowradiators.common.interfaces.IUser;
import it.yellowradiators.model.Book;
import it.yellowradiators.model.Loan;
import it.yellowradiators.model.User;
import it.yellowradiators.utils.FxTaskRunner;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public abstract class JsonTemplateManager<T> {
    private final String filePath;
    private final Class<T[]> arrayType;
    private final Gson gson;

    public JsonTemplateManager(String filePath, Class<T[]> arrayType) {
        this.filePath = filePath;
        this.arrayType = arrayType;
        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(IBook.class, new InterfaceAdapter<>(Book.class))
                .registerTypeAdapter(IUser.class, new InterfaceAdapter<>(User.class))
                .registerTypeAdapter(ILoan.class, new InterfaceAdapter<>(Loan.class))
                .setPrettyPrinting()
                .create();
    }

    public boolean save(List<T> items) {
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(items, writer);
            return true;
        } catch (IOException e) {
            System.out.println("Error while saving data: " + filePath);
            return false;
        }
    }

    public List<T> load() {
        try (Reader reader = new FileReader(filePath)) {
            T[] array = gson.fromJson(reader, arrayType);
            List<T> list = new ArrayList<>();
            if (array != null) list.addAll(Arrays.asList(array));
            return list;
        } catch (IOException e) {
            System.out.println("Error while loading data: " + filePath);
            return new ArrayList<>();
        }
    }

    public void loadAsync(Consumer<List<T>> onSuccess, Runnable onError) {
        FxTaskRunner.runAsync(this::load, onSuccess, onError);
    }

    public void saveAsync(List<T> items, Consumer<Boolean> onSuccess, Runnable onError) {
        FxTaskRunner.runAsync(() -> save(items), onSuccess, onError);
    }
}