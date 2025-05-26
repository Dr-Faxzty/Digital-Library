package view.admin.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Book;
import controller.BookController;
import common.interfaces.IBook;

public class BookEditDialog {
    private final BookController bookController = BookController.getInstance();

    public void show(Stage parentStage, IBook book,  Runnable onSaveCallback) {
        boolean isNew = book.isNull();
        Stage dialog = new Stage();
        dialog.initOwner(parentStage);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle("Edit Book");

        VBox root = new VBox(16);
        root.getStyleClass().add("editBook-style-1");
        root.setPadding(new Insets(24));
        root.setPrefWidth(500);


        TextField titleField = new TextField(book.getTitle());
        TextField authorField = new TextField(book.getAuthor());
        TextField imageUrlField = new TextField(book.getUrlImage());
        TextField isbnField = new TextField(book.getIsbn());
        DatePicker datePicker = new DatePicker(book.getDate());
        ComboBox<String> typeBox = new ComboBox<>();
        typeBox.getItems().addAll("Science", "Narrative", "Fantasy", "Other");
        typeBox.setValue(book.getType());

        CheckBox availableBox = new CheckBox("Available");
        availableBox.setSelected(book.available());

        titleField.getStyleClass().add("editBook-style-3");
        authorField.getStyleClass().add("editBook-style-3");
        imageUrlField.getStyleClass().add("editBook-style-3");
        isbnField.getStyleClass().add("editBook-style-3");
        datePicker.getStyleClass().add("editBook-style-3");
        typeBox.getStyleClass().add("editBook-style-3");
        availableBox.getStyleClass().add("editBook-style-4");
        
        titleField.setPrefWidth(220);
        authorField.setPrefWidth(220);
        isbnField.setPrefWidth(200);
        datePicker.setPrefWidth(200);



        HBox row1 = new HBox(16,
                labeledField("Title *", titleField),
                labeledField("Author *", authorField)
        );

        VBox row2 = labeledField("Cover Image (URL) *", imageUrlField);

        HBox row3 = new HBox(16,
                labeledField("ISBN *", isbnField),
                labeledField("Publication Year *", datePicker)
        );

        HBox row4 = new HBox(16,
                labeledField("Category *", typeBox),
                wrapCheckbox("Available", availableBox)
        );

        row1.setAlignment(Pos.CENTER_LEFT);
        row3.setAlignment(Pos.CENTER_LEFT);
        row4.setAlignment(Pos.CENTER_LEFT);

        root.getChildren().addAll(row1, row2, row3, row4);


        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        Button cancelBtn = new Button("Cancel");
        cancelBtn.getStyleClass().addAll("editBook-style-5", "editBook-style-7");
        cancelBtn.setCancelButton(true);

        Button saveBtn = new Button(isNew ? "Add Book" : "Update Book");
        saveBtn.getStyleClass().addAll("editBook-style-5", "editBook-style-6");
        saveBtn.setDefaultButton(true);

        cancelBtn.setOnAction(e -> dialog.close());

        saveBtn.setOnAction(e -> {
            try {
                String updatedIsbn = isbnField.getText();
                Book updatedBook = new Book(
                        updatedIsbn,
                        titleField.getText(),
                        authorField.getText(),
                        datePicker.getValue(),
                        typeBox.getValue(),
                        availableBox.isSelected(),
                        imageUrlField.getText()
                );

                boolean result = isNew
                        ? bookController.addBook(updatedBook)
                        : bookController.updateBook(book.getIsbn(), updatedBook);

                if (result) {
                    if (onSaveCallback != null) onSaveCallback.run();
                    dialog.close();
                } else {
                    new Alert(Alert.AlertType.ERROR,
                            isNew ? "Book already exists." : "Update failed."
                    ).show();
                }
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Error while saving Book").show();
            }
        });

        buttons.getChildren().addAll(cancelBtn, saveBtn);
        root.getChildren().add(buttons);

        dialog.setScene(new Scene(root));
        dialog.showAndWait();
    }

    private VBox labeledField(String label, Control input) {
        Label l = new Label(label);
        l.getStyleClass().add("editBook-style-2");
        VBox box = new VBox(4, l, input);
        VBox.setMargin(box, new Insets(4));
        return box;
    }

    private VBox wrapCheckbox(String labelText, CheckBox checkBox) {
        Label label = new Label(labelText);
        label.getStyleClass().add("editBook-style-2");

        VBox box = new VBox(4, label, checkBox);
        VBox.setMargin(box, new Insets(4));
        return box;
    }

}
