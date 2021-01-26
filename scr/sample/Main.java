package sample;

import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.List;

public class Main extends Application {

    Stage window;
    Scene scene;
    BorderPane layout;

    List<Rectangle> borderList = new ArrayList<>();

    static Set<Integer> player1List = new HashSet<>();
    static Set<Integer> player2List = new HashSet<>();

    enum Enum {
        NEW, CLASSIC, M42, ULTM42, BOT
    }

    int scorePl1 = 0;
    int scorePl2 = 0;

    boolean player1Turn = true;
    boolean didWinBoolean = false;
    boolean playingWithBot = false;

    static Label Pl1Score;
    static Label Pl2Score;
    static Label midLab;

    List<TilePane> tilePanearr = new ArrayList<>();

    Image Xground = new Image("file:X_Letter.png");
    Image Oground = new Image("file:Circle.png");

    @Override
    public void start(Stage primaryStage){
        window = primaryStage;

        //Main layout
        layout = new BorderPane();
        layout.getStylesheets().add("stylesheets/stylesheet.css");
        layout.setId("layout");

        //Center layout of the tiles
        GridPane gridBoard = new GridPane();
        gridBoard.setPadding(new Insets(5, 10, 10, 10));


        for(int i=0; i < 3; i++){
            for (int j=0; j < 3; j++) {
                TilePane tile = new TilePane();
                tile.getChildren().add(borderCreator(tile));
                GridPane.setConstraints(tile, i, j);
                tilePanearr.add(tile);
                gridBoard.getChildren().add(tile);

            }
        }

        //Top part
        HBox top = new HBox();
        top.setSpacing(40);
        top.setPadding(new Insets(10, 10, 0, 10));

        //Adding labels
        Pl1Score = new Label("X:   " + scorePl1 + "\n ");
        Pl1Score.setAlignment(Pos.BASELINE_LEFT);
        Pl1Score.setId("label");

        midLab = new Label(" ");
        midLab.setAlignment(Pos.BASELINE_CENTER);
        midLab.setId("label");

        Pl2Score = new Label("O:   " + scorePl2 + "\n ");
        Pl2Score.setAlignment(Pos.BASELINE_RIGHT);
        Pl2Score.setId("label");

        //Adding Labels to the top part
        Menu menu = new Menu("Modes");

        MenuItem classic = new MenuItem("Classic");
        classic.setOnAction(ev -> buttonsActions(Enum.CLASSIC));
        classic.setId("menuitem");

        MenuItem mode42 = new MenuItem("42 mode");
        mode42.setOnAction(ev -> buttonsActions(Enum.M42));
        mode42.setId("menuitem");

        MenuItem ultmode42 = new MenuItem("Ultimate 42 mode");
        ultmode42.setOnAction(ev -> buttonsActions(Enum.ULTM42));
        ultmode42.setId("menuitem");

        MenuItem botmode = new MenuItem("Player/Bot");
        botmode.setOnAction(ev -> buttonsActions(Enum.BOT));
        botmode.setId("menuitem");

        menu.getItems().addAll(classic, mode42, ultmode42, botmode);
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menu);

        //New game button
        Button but = new Button("New game");
        but.setId("clickable");
        but.setOnAction(ev -> buttonsActions(Enum.NEW));

        //Adding Labels to the top part
        top.getChildren().addAll(menuBar,Pl1Score, midLab, but, Pl2Score);

        layout.setCenter(gridBoard);
        layout.setTop(top);

        scene = new Scene(layout, 620, 740);
        window.setScene(scene);
        window.setTitle("Tic Tac Toe");
        window.setResizable(false);
        window.show();
    }


    private Rectangle borderCreator(TilePane tile) {

        //Creating the inside of a tile
        Rectangle border = new Rectangle(200, 200);
        border.setFill(null);
        border.setStroke(Color.BLACK);
        border.setHeight(200);
        border.setWidth(200);

        Random randomNumber = new Random();

        tile.setOnMouseClicked(e -> {

            int a = tilePanearr.indexOf(tile) + 1;

            //If player 1 turn then adding to the list 1 else..
            if (player1Turn) {
                player1List.add(a);
                border.setFill(new ImagePattern(Xground));
            } else {
                player2List.add(a);
                border.setFill(new ImagePattern(Oground));
            }

            tile.setDisable(true);
            borderList.add(border);

            //If did win disabling every tile
            if (didWin())
                disabler();
            else if (playingWithBot) {

                a = randomNumber.nextInt(9) + 1;
                while (player1List.contains(a) || player2List.contains(a)) {
                    a = randomNumber.nextInt(9) + 1;
                }

                player2List.add(a);

                border.setDisable(true);
                ((Rectangle)tilePanearr.get(a - 1).getChildren().get(0)).setFill(new ImagePattern(Oground));

                borderList.add(border);

                if (didWin())
                    disabler();

            } else//If solo then changing player turn
                player1Turn = !player1Turn;

        });

        return border;
    }

    private boolean didWin() {
        for (List<?> l : WinningConditions.winning) {

            //If player has any combination
            if (player1List.containsAll(l)) {
                scorePl1 += 1;
                Pl1Score.setText("X:  " + scorePl1 + "\n ");
                midLab.setText("WIN ");
                return true;
            } else if (player2List.containsAll(l)) {
                midLab.setText("WIN ");
                scorePl2 += 1;
                Pl2Score.setText("O:  " + scorePl2 + "\n ");
                return true;
            }
        }if (player1List.size() + player2List.size() == 9){
            midLab.setText("DRAW");
            return true;
        }

        return false;
    }
    private void  disabler(){
        for (TilePane tp : tilePanearr)
            tp.setDisable(true);
    }

    private void buttonsActions(Enum a) {

        for (TilePane tp : tilePanearr)
            tp.setDisable(false);

        for (Rectangle rc : borderList)
            rc.setFill(null);

        midLab.setText(" ");
        player1List.clear();
        player2List.clear();
        player1Turn = true;
        didWinBoolean = false;

        switch (a){

            case CLASSIC -> {
                Xground = new Image("file:X_Letter.png");
                Oground = new Image("file:Circle.png");
            } case M42 -> {
                Xground = new Image("file:42transparent.png");
                Oground = new Image("file:24transparent.png");
            } case ULTM42 -> {
                Xground = new Image("file:nicer42.png");
                Oground = new Image("file:nicer24.png");
            }case NEW -> {}

            case BOT -> playingWithBot = !playingWithBot;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}

