package it.yellowradiators.persistence;

import it.yellowradiators.model.Book;


public class JsonBookManager extends JsonTemplateManager<Book> {
    private static JsonBookManager JBookMangerInstance;

    public JsonBookManager() {
        super("database/books.json", Book[].class);
    }

    public static JsonBookManager getInstance() {
        if (JBookMangerInstance == null) {
            JBookMangerInstance = new JsonBookManager();
        }
        return JBookMangerInstance;
    }
}