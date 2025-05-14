package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import manager.LoanManager;
import manager.SessionManager;
import model.Book;
import model.Loan;
import model.User;

import java.time.LocalDate;

public class BookDetailView {

    public void show(Stage parent, Book book) {

        HBox header = new HBox();
        header.setAlignment(Pos.CENTER_LEFT);
        header.setSpacing(12);
        header.setPadding(new Insets(16, 24, 16, 24));
        header.setStyle("-fx-background-color: white; -fx-border-color: #f0f0f0; -fx-border-width: 0 0 1 0;");
        header.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(header, Priority.ALWAYS);

        Label icon = new Label("📖");
        icon.setStyle("-fx-font-size: 18px;");

        Label titleApp = new Label("Digital Library");
        titleApp.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        header.getChildren().addAll(icon, titleApp);


        Button back = new Button("← Back to catalog");
        back.setStyle("""
            -fx-font-size: 14px;
            -fx-text-fill: #333;
            -fx-font-weight: bold;
            -fx-background-color: transparent;
            -fx-border-color: transparent;
            -fx-padding: 8 0 0 24;
            -fx-cursor: hand;
        """);
        back.setOnAction(e -> parent.setScene((Scene) parent.getUserData()));


        ImageView cover = new ImageView(new Image(book.getUrlImage()));
        cover.setFitWidth(200);
        cover.setPreserveRatio(true);
        cover.setSmooth(true);
        cover.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 8, 0.1, 0, 2);");


        Label bookTitle = new Label(book.getTitle());
        bookTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label author = new Label(book.getAuthor());
        author.setStyle("-fx-font-size: 14px; -fx-text-fill: #555;");

        Label rating = new Label("★ 4.5");
        Label reviews = new Label("4.5 (2 reviews)");
        reviews.setStyle("-fx-font-size: 13px; -fx-text-fill: #666;");
        HBox ratingBox = new HBox(8, rating, reviews);



        User user = SessionManager.getInstance().getLoggedUser();
        LoanManager loanManager = LoanManager.getInstance();

        Loan existingLoan = loanManager.search(l ->
                l.getBook().equals(book) && l.getUser().equals(user) && l.isInProgress()
        ).stream().findFirst().orElse(null);

        Button loanButton = new Button();
        loanButton.setStyle("""
            -fx-background-color: #34A853;
            -fx-text-fill: white;
            -fx-padding: 8 16;
            -fx-font-size: 13px;
            -fx-background-radius: 12;
            -fx-cursor: hand;
        """);

        if (!book.available() && existingLoan == null) {
            loanButton.setDisable(true);
            loanButton.setText("✓ Already Loaned");
        } else if (existingLoan != null) {
            loanButton.setText("↩ Return Book");
            loanButton.setOnAction(e -> {
                loanManager.returnBook(existingLoan);
                book.setAvailable(true);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Book returned successfully!");
                alert.showAndWait();
                Stage stage = (Stage) loanButton.getScene().getWindow();
                new UserView().start(stage);
            });
        } else {
            loanButton.setText("📚 Loan this book");
            loanButton.setOnAction(e -> {
                loanManager.loanBook(book, user, LocalDate.now().plusDays(14));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Loan registered successfully!");
                alert.showAndWait();
                Stage stage = (Stage) loanButton.getScene().getWindow();
                new UserView().start(stage);
            });
        }


        Label detailsTitle = new Label("Details");
        detailsTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        GridPane details = new GridPane();
        details.setHgap(80);
        details.setVgap(10);
        details.setPadding(new Insets(4, 0, 16, 0));

        details.add(new VBox(new Label("Published year"), new Label("1980")), 0, 0);
        details.add(new VBox(new Label("Language"), new Label("English")), 0, 1);
        details.add(new VBox(new Label("ISBN"), new Label(book.getIsbn())), 0, 2);
        details.add(new VBox(new Label("Publisher"), new Label("Bompiani")), 1, 0);
        details.add(new VBox(new Label("Pages"), new Label("512")), 1, 1);
        details.add(new VBox(new Label("Genre"), new Label("Narrative")), 1, 2);

        details.getChildren().forEach(node -> {
            if (node instanceof VBox vbox) {
                for (int i = 0; i < vbox.getChildren().size(); i++) {
                    if (vbox.getChildren().get(i) instanceof Label label) {
                        if (i == 0) label.setStyle("-fx-text-fill: #777;");
                        else label.setStyle("-fx-text-fill: #333;");
                    }
                }
            }
        });


        Label synopsisLabel = new Label("Synopsis");
        synopsisLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        Text synopsis = new Text(
                "Year 1327. The novice Adso of Melk accompanies friar William of Baskerville to an abbey in northern Italy. "
                        + "He is a Franciscan called to a dispute between the pope’s envoys and representatives of the Church. "
                        + "The abbey is a microcosm of the tensions within the Church, and the situation worsens when a monk is found dead..."
        );
        synopsis.setWrappingWidth(600);
        synopsis.setStyle("-fx-font-size: 13px; -fx-fill: #444;");

        VBox rightBox = new VBox(12, bookTitle, author, ratingBox, loanButton, detailsTitle, details, synopsisLabel, synopsis);
        rightBox.setAlignment(Pos.TOP_LEFT);

        HBox mainContent = new HBox(48, cover, rightBox);
        mainContent.setPadding(new Insets(24));
        mainContent.setStyle("-fx-background-color: white; -fx-background-radius: 20; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 16, 0.2, 0, 4);");

        VBox page = new VBox(header, back, mainContent);
        page.setStyle("-fx-background-color: #f9f9f9;");
        page.setSpacing(16);
        page.setPadding(new Insets(0, 72, 48, 72));

        Scene scene = new Scene(page, 1100, 680);
        parent.setUserData(parent.getScene());
        parent.setScene(scene);
    }
}
