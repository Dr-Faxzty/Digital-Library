package view.admin.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import manager.BookManager;
import model.Book;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class RecentBooksBox {

    public static VBox create() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setPrefWidth(450);
        box.getStyleClass().add("recentbookbox-style-1");

        Label title = new Label("Books Recently Added");
        title.getStyleClass().add("recentbookbox-style-2");

        box.getChildren().add(title);

        List<Book> books = BookManager.getInstance().getAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");

        books.stream()
                .sorted((b1, b2) -> b2.getDate().compareTo(b1.getDate()))
                .limit(4)
                .forEach(book -> {
            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER_LEFT);
            row.setPadding(new Insets(8, 0, 8, 0));

            ImageView cover = new ImageView();
            try {
                cover.setImage(new Image(book.getUrlImage(), 32, 48, true, true));
            } catch (Exception e) {
                cover.setImage(new Image("https://via.placeholder.com/32x48", 32, 48, true, true));
            }
            cover.setFitWidth(32);
            cover.setFitHeight(48);

            Label titleLabel = new Label(book.getTitle());
            titleLabel.getStyleClass().add("recentbookbox-style-3");

            Label subtitle = new Label(book.getAuthor() + " • Added on " + book.getDate().format(formatter));
            subtitle.getStyleClass().add("recentbookbox-style-4");

            VBox textBox = new VBox(2, titleLabel, subtitle);

            Label editBtn = new Label("edit");
            editBtn.getStyleClass().add("recentbookbox-style-5");

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            row.getChildren().addAll(cover, textBox, spacer, editBtn);

            box.getChildren().add(row);
        });

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Label viewAll = new Label("View All");
        viewAll.getStyleClass().add("recentbookbox-style-6");
        VBox.setMargin(viewAll, new Insets(10, 0, 0, 2));

        box.getChildren().addAll(spacer, viewAll);

        return box;
    }
}
