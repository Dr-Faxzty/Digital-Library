package persistence;

import model.Book;


public class JsonBookManager extends JsonTemplateManager<Book> {
    public JsonBookManager() {
        super("C:\\Users\\marti\\OneDrive\\Desktop\\Digital-Library\\Digital-Library\\database\\books.json", Book[].class);
    }
}