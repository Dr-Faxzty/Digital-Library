package persistence;

import common.interfaces.IBook;


public class JsonBookManager extends JsonTemplateManager<IBook> {
    private static JsonBookManager JBookMangerInstance;

    public JsonBookManager() {
        super("C:\\Users\\marti\\OneDrive\\Desktop\\Digital-Library\\Digital-Library\\database\\books.json", IBook[].class);
    }

    public static JsonBookManager getInstance() {
        if (JBookMangerInstance == null) {
            JBookMangerInstance = new JsonBookManager();
        }
        return JBookMangerInstance;
    }
}