package persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Loan;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonLoanManager {
    private static final String FILE_PATH = "db/loans.json";

    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public static void saveLoans(List<Loan> loans) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(loans, writer);
        } catch (IOException e) {
            System.out.println("Error while saving loans");
        }
    }

    public static List<Loan> loadLoans() {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Loan[] array = gson.fromJson(reader, Loan[].class);
            List<Loan> list = new ArrayList<>();
            if (array != null) {
                list.addAll(Arrays.asList(array));
            }
            return list;
        } catch (IOException e) {
            System.out.println("Error while loading loans");
            return new ArrayList<>();
        }
    }
}
