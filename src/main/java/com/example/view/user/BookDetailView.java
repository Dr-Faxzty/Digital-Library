package com.example.view.user;

import com.example.common.interfaces.IBook;
import com.example.controller.LoanController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import com.example.view.user.components.BookDetailsGridPane;
import com.example.view.user.components.HandleLoan;

import java.util.function.Supplier;

public class BookDetailView {
    private final LoanController loanController = LoanController.getInstance();
    private final HBox header = new HBox();

    public void show(Stage parent, IBook book) {
        setStyleHeader();

        header.getChildren().addAll(CreateLabel("📖", "book-detail-icon"), CreateLabel("Digital Library", "book-detail-title-app"));

        Button back = CreateButton("← Back to catalog", "book-detail-back-button");
        back.setOnAction(e -> parent.setScene((Scene) parent.getUserData()));


        HBox ratingBox = BuildBox(HBox::new, 8, CreateLabel("★ 4.5", null), CreateLabel("4.5 (2 reviews)", "book-detail-reviews"));

        Button loanButton = CreateButton("", "book-detail-loan-button");
        HandleLoan.configure(loanButton, book);

        GridPane details = new BookDetailsGridPane(book);

        VBox rightBox = BuildBox(VBox::new, 12,
                CreateLabel(book.getTitle(), "book-detail-title"),
                CreateLabel(book.getAuthor(), "book-detail-author"),
                ratingBox,
                loanButton,
                CreateLabel("Details", "book-detail-details-title"),
                details,
                CreateLabel("Synopsis", "book-detail-synopsis-title"),
                CreateSynopsis()
        );

        HBox mainContent = BuildBox(HBox::new, 48, CreateCover(book), rightBox);
        mainContent.getStyleClass().add("book-detail-main-box");

        VBox page = BuildBox(VBox::new, 16, header, back, mainContent);
        page.getStyleClass().add("book-detail-page");

        Scene scene = new Scene(page, 1100, 680);
        scene.getStylesheets().add(getClass().getResource("/style/BookDetailView.css").toExternalForm());
        parent.setUserData(parent.getScene());
        parent.setScene(scene);
    }

    private void setStyleHeader(){
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(12);
        header.setPadding(new Insets(16, 24, 16, 24));
        header.getStyleClass().add("book-detail-header");
        header.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(header, Priority.ALWAYS);
    }

    private Label CreateLabel(String title, String styleClass) {
        Label label = new Label(title);
        label.getStyleClass().add(styleClass);

        return label;
    }

    private <T extends Pane> T BuildBox(Supplier<T> constructor, double spacing, Node... children) {
        T box = constructor.get();

        // Set spacing if applicable
        if (box instanceof VBox vbox) {
            vbox.setSpacing(spacing);
        } else if (box instanceof HBox hbox) {
            hbox.setSpacing(spacing);
        }

        box.getChildren().addAll(children);
        return box;
    }

    public Button CreateButton(String text, String styleClass) {
        Button button = new Button(text);
        button.getStyleClass().add(styleClass);

        return button;
    }

    private ImageView CreateCover(IBook book){
        ImageView cover = new ImageView(new Image(book.getUrlImage()));
        cover.setFitWidth(200);
        cover.setPreserveRatio(true);
        cover.setSmooth(true);
        cover.getStyleClass().add("book-detail-cover");

        return cover;
    }

    private Text CreateSynopsis() {
        Text synopsis = new Text(
                "Year 1327. The novice Adso of Melk accompanies friar William of Baskerville to an abbey in northern Italy. "
                        + "He is a Franciscan called to a dispute between the pope’s envoys and representatives of the Church. "
                        + "The abbey is a microcosm of the tensions within the Church, and the situation worsens when a monk is found dead..."
        );
        synopsis.setWrappingWidth(600);
        synopsis.getStyleClass().add("book-detail-synopsis");

        return synopsis;
    }
}
