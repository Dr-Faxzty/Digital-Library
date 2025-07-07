package com.example.view.user.components;

import com.example.common.enums.BookCategoryType;
import com.example.common.strategy.BookOrderType;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;

public class CategorySelector extends VBox {
    private final ComboBox<String> orderCombo = new ComboBox<>();
    private final HBox categories = new HBox();

    public CategorySelector(Consumer<String> onCategorySelected, Runnable onOrderChanged) {
        getStyleClass().add("categories-section");
        categories.setSpacing(8);


        VBox sectionWrapper = new VBox();
        sectionWrapper.getStyleClass().add("categories-section");

        VBox container = new VBox();
        container.setSpacing(12);

        Label categoryTitle = new Label("Categories");
        categoryTitle.getStyleClass().add("category-title");

        CreateCategoryButtons(onCategorySelected);

        container.getChildren().addAll(categoryTitle, createTopRow(onOrderChanged));
        sectionWrapper.getChildren().add(container);
        getChildren().add(container);
    }

    private HBox createTopRow(Runnable onOrderChanged){
        HBox orderBox = CreateOrderBox(onOrderChanged);

        HBox topRow = new HBox(categories, orderBox);
        topRow.setSpacing(12);
        topRow.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(orderBox, Priority.ALWAYS);

        return topRow;
    }

    private HBox CreateOrderBox(Runnable onOrderChanged) {
        HBox orderBox = new HBox();
        orderBox.setSpacing(8);
        orderBox.setAlignment(Pos.CENTER_RIGHT);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label orderLabel = new Label("Order by:");
        orderLabel.getStyleClass().add("order-label");

        orderCombo.getItems().addAll(
                BookOrderType.MOST_RECENT.getLabel(),
                BookOrderType.AUTHOR.getLabel(),
                BookOrderType.TITLE_A_Z.getLabel()
        );
        orderCombo.setValue(BookOrderType.MOST_RECENT.getLabel());
        orderCombo.getStyleClass().add("order-combo");
        orderCombo.setOnAction(e -> onOrderChanged.run());

        orderBox.getChildren().addAll(spacer, orderLabel, orderCombo);

        return orderBox;
    }

    private void CreateCategoryButtons(Consumer<String> onCategorySelected) {
        for (BookCategoryType category : BookCategoryType.values()) {
            Button categoryButton = new Button(category.getLabel());
            categoryButton.getStyleClass().add("category-button");
            categoryButton.setOnAction(e -> onCategorySelected.accept(category.getLabel()));
            categories.getChildren().add(categoryButton);
        }
    }
}
