package persistence;

import model.Book;


public class JsonBookManager extends JsonTemplateManager<Book> {
    public JsonBookManager() {
        super("database/books.json", Book[].class);
    }
}