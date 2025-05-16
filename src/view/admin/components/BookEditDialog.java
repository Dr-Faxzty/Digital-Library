package view.admin.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Book;

public class BookEditDialog {

    public void show(Stage parentStage, Book book) {
        Stage dialog = new Stage();
        dialog.initOwner(parentStage);
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.setTitle("Edit Book");

        VBox root = new VBox(16);
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

        root.getChildren().addAll(
                labeledField("Title *", titleField),
                labeledField("Author *", authorField),
                labeledField("Cover Image (URL) *", imageUrlField),
                labeledField("ISBN *", isbnField),
                labeledField("Publication Year *", datePicker),
                labeledField("Category *", typeBox),
                availableBox
        );


        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        Button cancelBtn = new Button("Cancel");
        Button saveBtn = new Button("Update Book");

        cancelBtn.setOnAction(e -> dialog.close());

        saveBtn.setOnAction(e -> {
            try {
                book.setTitle(titleField.getText());
                book.setAuthor(authorField.getText());
                book.setUrlImage(imageUrlField.getText());
                book.setIsbn(isbnField.getText());
                book.setDate(datePicker.getValue());
                book.setType(typeBox.getValue());
                book.setAvailable(availableBox.isSelected());

                // BookManager.getInstance().save(); // salva se necessario

                dialog.close();
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
        VBox box = new VBox(4, l, input);
        VBox.setMargin(box, new Insets(4));
        return box;
    }
}
