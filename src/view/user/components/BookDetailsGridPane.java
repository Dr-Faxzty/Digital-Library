package view.user.components;

import common.interfaces.IBook;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class BookDetailsGridPane extends GridPane {

    public BookDetailsGridPane(IBook book) {
        setHgap(80);
        setVgap(10);
        setPadding(new Insets(4, 0, 16, 0));

        add(createDetail("Published year", "1980"), 0, 0);
        add(createDetail("Language", "English"), 0, 1);
        add(createDetail("ISBN", book.getIsbn()), 0, 2);
        add(createDetail("Publisher", "Bompiani"), 1, 0);
        add(createDetail("Pages", "512"), 1, 1);
        add(createDetail("Genre", "Narrative"), 1, 2);
    }

    private VBox createDetail(String labelText, String valueText) {
        Label label = new Label(labelText);
        label.getStyleClass().add("book-detail-details-label");

        Label value = new Label(valueText);
        value.getStyleClass().add("book-detail-details-value");

        return new VBox(label, value);
    }
}
