package view.user.components;

import common.interfaces.IBook;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.user.BookDetailView;

public class BookCard extends VBox {
    public BookCard(IBook book) {
        getStyleClass().add("book-card");
        setAlignment(Pos.TOP_LEFT);
        setSpacing(6);;

        getChildren().addAll(CreateImageView(book.getUrlImage()), CreateTitle(book.getTitle()), CreateAuthor(book.getAuthor()), CreateAvailability(book));

        setOnMouseClicked(e -> new BookDetailView().show((Stage) getScene().getWindow(), book));
    }

    private ImageView CreateImageView(String url) {
        ImageView imageView = new ImageView(new Image(url));
        imageView.setFitWidth(140);
        imageView.setFitHeight(180);

        return imageView;
    }

    private Label CreateTitle(String text){
        Label title = new Label(text);
        title.getStyleClass().add("book-title");

        return title;
    }

    private Label CreateAuthor(String text) {
        Label author = new Label(text);
        author.getStyleClass().add("book-author");

        return author;
    }

    private Label CreateAvailability(IBook book) {
        Label availability = new Label(book.available() ? "✓ Available" : "✗ Not available");
        availability.getStyleClass().add(book.available() ? "available" : "not-available");

        return availability;
    }
}