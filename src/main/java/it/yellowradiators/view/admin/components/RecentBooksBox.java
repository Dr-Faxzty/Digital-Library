package it.yellowradiators.view.admin.components;

import it.yellowradiators.common.interfaces.IBook;
import it.yellowradiators.common.observer.ViewObserver;
import it.yellowradiators.controller.BookController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class RecentBooksBox {

    public static VBox create(BookController bookController, ViewObserver observer) {
        VBox root = createBaseLayout();

        Label title = createTitleLabel();
        ProgressIndicator loader = createLoader();

        root.getChildren().addAll(title, loader);
        bookController.loadBooksAsync(
                books -> populateBooks(root, books, loader, observer),
                () -> showError(root, loader)
        );

        return root;
    }

    private static VBox createBaseLayout() {
        VBox box = new VBox(10);
        box.setPadding(new Insets(10));
        box.setPrefWidth(450);
        box.setAlignment(Pos.CENTER);
        box.getStyleClass().add("recentbookbox-style-1");
        return box;
    }

    private static Label createTitleLabel() {
        Label title = new Label("Books Recently Added");
        title.getStyleClass().add("recentbookbox-style-2");
        return title;
    }

    private static ProgressIndicator createLoader() {
        ProgressIndicator loader = new ProgressIndicator();
        loader.setMaxSize(30, 30);
        VBox.setMargin(loader, new Insets(10, 0, 10, 0));
        return loader;
    }

    private static void populateBooks(VBox root, List<IBook> books, ProgressIndicator loader, ViewObserver observer) {
        root.getChildren().remove(loader);
        root.setAlignment(Pos.TOP_LEFT);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");

        List<IBook> recentBooks = books.stream()
                .sorted((b1, b2) -> b2.getDate().compareTo(b1.getDate()))
                .limit(4)
                .collect(Collectors.toList());

        for (IBook book : recentBooks) {
            HBox row = createBookRow(book, formatter);
            root.getChildren().add(row);
        }

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);
        root.getChildren().add(spacer);

        Label viewAll = createViewAllLabel(observer);
        root.getChildren().add(viewAll);
    }

    private static HBox createBookRow(IBook book, DateTimeFormatter formatter) {
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

        HBox rowBox = new HBox(10, cover, textBox, spacer, editBtn);
        rowBox.setAlignment(Pos.CENTER_LEFT);
        rowBox.setPadding(new Insets(8, 0, 8, 0));
        return rowBox;
    }

    private static Label createViewAllLabel(ViewObserver observer) {
        Label viewAll = new Label("View All");
        viewAll.getStyleClass().add("recentbookbox-style-6");
        VBox.setMargin(viewAll, new Insets(10, 0, 0, 2));

        viewAll.setOnMouseClicked(e -> {
            if (observer != null) observer.onViewChange("books");
        });

        return viewAll;
    }

    private static void showError(VBox root, ProgressIndicator loader) {
        root.getChildren().remove(loader);
        Label errorLabel = new Label("❌ Failed to load recent books.");
        root.getChildren().add(errorLabel);
    }
}
