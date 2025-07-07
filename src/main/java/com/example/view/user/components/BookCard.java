package com.example.view.user.components;

import com.example.common.interfaces.IBook;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.view.user.BookDetailView;

public class BookCard extends VBox {
    public BookCard(IBook book) {
        getStyleClass().add("book-card");
        setAlignment(Pos.TOP_LEFT);
        setSpacing(6);;

        getChildren().addAll(CreateImageView(book.getUrlImage()), CreateLabel(book.getTitle(), "book-title"), CreateLabel(book.getAuthor(), "book-author"), CreateLabel(book.available() ? "✓ Available" : "✗ Not available", book.available() ? "available" : "not-available"));

        setOnMouseClicked(e -> new BookDetailView().show((Stage) getScene().getWindow(), book));
    }

    private ImageView CreateImageView(String url) {
        ImageView imageView = new ImageView(new Image(url));
        imageView.setFitWidth(140);
        imageView.setFitHeight(180);

        return imageView;
    }

    private Label CreateLabel(String text, String styleClass) {
        Label title = new Label(text);
        title.getStyleClass().add(styleClass);

        return title;
    }
}