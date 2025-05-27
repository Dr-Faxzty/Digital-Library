package persistence;

import model.Book;


public class JsonBookManager extends JsonTemplateManager<Book> {
    private static JsonBookManager JBookMangerInstance;

    public JsonBookManager() {
        super("C:\\Users\\marti\\OneDrive\\Desktop\\Digital-Library\\database\\books.json", Book[].class);
    }

    public static JsonBookManager getInstance() {
        if (JBookMangerInstance == null) {
            JBookMangerInstance = new JsonBookManager();
        }
        return JBookMangerInstance;
    }
}