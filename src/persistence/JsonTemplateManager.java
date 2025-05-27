package persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import common.adapter.InterfaceAdapter;
import common.adapter.LocalDateAdapter;
import common.interfaces.IBook;
import common.interfaces.ILoan;
import common.interfaces.IUser;
import model.Book;
import model.Loan;
import model.User;
import utils.FxTaskRunner;

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