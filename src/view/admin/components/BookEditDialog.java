package view.admin.components;

import controller.BookController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Book;
import common.interfaces.IBook;

public class BookEditDialog {
    private final BookController bookController = BookController.getInstance();

    public void show(Stage parentStage, IBook book, Runnable onSaveCallback) {
        boolean isNew = book.isNull();
        Stage dialog = createDialog(parentStage, isNew ? "Add Book" : "Edit Book");

        VBox root = createFormLayout();
        DialogFields fields = initializeFields(book);
        addFormRows(root, fields);

        HBox buttons = createButtons(dialog, book, fields, isNew, onSaveCallback);
        root.getChildren().add(buttons);

        dialog.setScene(new Scene(root));
        dialog.showAndWait();
    }

    private Stage createDialog(Stage parentStage, String title) {
        Stage dialog = new Stage();
        dialog.initOwner(parentStage);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle(title);
        return dialog;
    }

    private VBox createFormLayout() {
        VBox root = new VBox(16);
        root.getStyleClass().add("editBook-style-1");
        root.setPadding(new Insets(24));
        root.setPrefWidth(500);
        return root;
    }

    private DialogFields initializeFields(IBook book) {
        DialogFields fields = new DialogFields();

        fields.titleField = new TextField(book.getTitle());
        fields.authorField = new TextField(book.getAuthor());
        fields.imageUrlField = new TextField(book.getUrlImage());
        fields.isbnField = new TextField(book.getIsbn());
        fields.datePicker = new DatePicker(book.getDate());

        fields.typeBox = new ComboBox<>();
        fields.typeBox.getItems().addAll("Science", "Narrative", "Fantasy", "Other");
        fields.typeBox.setValue(book.getType());

        fields.availableBox = new CheckBox("Available");
        fields.availableBox.setSelected(book.available());

        fields.titleField.getStyleClass().add("editBook-style-3");
        fields.authorField.getStyleClass().add("editBook-style-3");
        fields.imageUrlField.getStyleClass().add("editBook-style-3");
        fields.isbnField.getStyleClass().add("editBook-style-3");
        fields.datePicker.getStyleClass().add("editBook-style-3");
        fields.typeBox.getStyleClass().add("editBook-style-3");
        fields.availableBox.getStyleClass().add("editBook-style-4");

        fields.titleField.setPrefWidth(220);
        fields.authorField.setPrefWidth(220);
        fields.isbnField.setPrefWidth(200);
        fields.datePicker.setPrefWidth(200);

        return fields;
    }

    private void addFormRows(VBox root, DialogFields fields) {
        HBox row1 = new HBox(16,
                labeledField("Title *", fields.titleField),
                labeledField("Author *", fields.authorField)
        );

        VBox row2 = labeledField("Cover Image (URL) *", fields.imageUrlField);

        HBox row3 = new HBox(16,
                labeledField("ISBN *", fields.isbnField),
                labeledField("Publication Year *", fields.datePicker)
        );

        HBox row4 = new HBox(16,
                labeledField("Category *", fields.typeBox),
                wrapCheckbox("Available", fields.availableBox)
        );

        row1.setAlignment(Pos.CENTER_LEFT);
        row3.setAlignment(Pos.CENTER_LEFT);
        row4.setAlignment(Pos.CENTER_LEFT);

        root.getChildren().addAll(row1, row2, row3, row4);
    }

    private HBox createButtons(Stage dialog, IBook originalBook, DialogFields fields, boolean isNew, Runnable onSaveCallback) {
        Button cancelBtn = new Button("Cancel");
        cancelBtn.getStyleClass().addAll("editBook-style-5", "editBook-style-7");
        cancelBtn.setCancelButton(true);
        cancelBtn.setOnAction(e -> dialog.close());

        Button saveBtn = new Button(isNew ? "Add Book" : "Update Book");
        saveBtn.getStyleClass().addAll("editBook-style-5", "editBook-style-6");
        saveBtn.setDefaultButton(true);
        saveBtn.setOnAction(e -> handleSave(dialog, originalBook, fields, isNew, onSaveCallback));

        HBox buttons = new HBox(10, cancelBtn, saveBtn);
        buttons.setAlignment(Pos.CENTER_RIGHT);
        return buttons;
    }

    private void handleSave(Stage dialog, IBook originalBook, DialogFields fields, boolean isNew, Runnable onSaveCallback) {
        try {
            Book updatedBook = new Book(
                    fields.isbnField.getText(),
                    fields.titleField.getText(),
                    fields.authorField.getText(),
                    fields.datePicker.getValue(),
                    fields.typeBox.getValue(),
                    fields.availableBox.isSelected(),
                    fields.imageUrlField.getText()
            );

            boolean result = isNew
                    ? bookController.addBook(updatedBook)
                    : bookController.updateBook(originalBook.getIsbn(), updatedBook);

            if (result) {
                if (onSaveCallback != null) onSaveCallback.run();
                dialog.close();
            } else {
                showAlert(Alert.AlertType.ERROR, isNew ? "Book already exists." : "Update failed.");
            }
        } catch (Exception ex) {
            showAlert(Alert.AlertType.ERROR, "Error while saving Book");
        }
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

    private void showAlert(Alert.AlertType type, String message) {
        new Alert(type, message).show();
    }

    private static class DialogFields {
        TextField titleField;
        TextField authorField;
        TextField imageUrlField;
        TextField isbnField;
        DatePicker datePicker;
        ComboBox<String> typeBox;
        CheckBox availableBox;
    }
}