package app;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Toolbar extends Pane {
    public ToggleGroup shapeGroup;
    public ToggleButton squareButton;
    public ToggleButton circleButton;
    public ToggleButton triangleButton;
    public Button cutButton;
    public Button pasteButton;
    final Tooltip squaretip;
    final Tooltip circletip;
    final Tooltip triangletip;
    final Tooltip cuttip;
    final Tooltip pastetip;
    public Toolbar(){
        this.shapeGroup = new ToggleGroup();
        Image squareImg = new Image(getClass().getClassLoader().getResourceAsStream("images/square.png"));
        Image circleImg = new Image(getClass().getClassLoader().getResourceAsStream("images/circle.png"));
        Image triangleImg = new Image(getClass().getClassLoader().getResourceAsStream("images/triangle.png"));
        Image cutImg = new Image(getClass().getClassLoader().getResourceAsStream("images/cut.png"));
        Image pasteImg = new Image(getClass().getClassLoader().getResourceAsStream("images/paste.png"));
        squaretip = new Tooltip("Square");
        circletip = new Tooltip("Circle");
        triangletip = new Tooltip("Triangle");
        cuttip = new Tooltip("Cut");
        pastetip = new Tooltip("Paste");

        this.squareButton = new ToggleButton("", new ImageView(squareImg));
        this.circleButton = new ToggleButton("", new ImageView(circleImg));
        this.triangleButton = new ToggleButton("", new ImageView(triangleImg));
        this.cutButton = new Button("", new ImageView(cutImg));
        this.pasteButton = new Button("", new ImageView(pasteImg));
        squareButton.setTooltip(squaretip);
        circleButton.setTooltip(circletip);
        triangleButton.setTooltip(triangletip);
        cutButton.setTooltip(cuttip);
        pasteButton.setTooltip(pastetip);

        shapeGroup.getToggles().addAll(squareButton,circleButton,triangleButton);
        squareButton.setSelected(true);

        HBox shapes = new HBox(squareButton,circleButton,triangleButton);
        HBox cutpaste = new HBox(cutButton,pasteButton);
        shapes.setMinWidth(140);
        cutpaste.setMinWidth(100);
        HBox buttons = new HBox();
        buttons.setPadding(new Insets(5,20,5,5));
        buttons.getChildren().addAll(shapes, cutpaste);
        getChildren().addAll(buttons);
        this.setMinWidth(400.0);
        this.setMinHeight(30.0);
        this.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        toolbarController();
    }

    public void toolbarController(){
        cutButton.setOnMouseClicked(event -> {
            Main.iModel.getSelectedSquares().itemsProperty().removeAll(Main.model.squareList2Property2());
            if (Main.model.squareList2Property2().size()>0) {
                Main.model.resetSquareList2();
            }
            Main.model.squareList2Property2().addAll(Main.iModel.getSelectedSquares().itemsProperty());
            Main.model.squareListProperty().removeAll(Main.iModel.getSelectedSquares().itemsProperty());
        });
        pasteButton.setOnMouseClicked(event -> {
            Main.model.squareListProperty().addAll(Main.model.squareList2Property());
            Main.model.resetSquareList2();
            Main.iModel.clear();
        });
    }

}
