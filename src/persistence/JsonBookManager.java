package persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Book;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class JsonBookManager {
    private static final String FILE_PATH = "db/books.json";
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .setPrettyPrinting()
            .create();

    public static void saveBooks(List<Book> books) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(books, writer);
        } catch (IOException e) {
            System.out.println("Error while saving books");
        }
    }

    public static List<Book> loadUsers() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Book[] array = gson.fromJson(reader, Book[].class);
            List<Book> list = new ArrayList<>();
            if (array != null) {
                list.addAll(Arrays.asList(array));
            }
            return list;
        } catch (IOException e) {
            System.out.println("Error while loading books");
            return new ArrayList<>();
        }
    }
}