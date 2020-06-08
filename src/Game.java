import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Obiekt <code>Game</code> glowna klasa gry 2048
 * @author Adam Lichy
 * @version 1.0
 */

public class Game extends Application {

    GridPane grid;
    Board board;
    Label message;
    Label points;
    double timePoints = 0;
    long startTime;

    /**
     * Metoda <code>setGrid</code> wypelnia grid elementami tablicy board oraz ustala parametry wyswietlania
     */

    private void setGrid(Board board, StackPane stackPane) {
        grid = new GridPane();
        for (int i=0 ; i<Board.SIZE ; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                int elementValue = board.getBoardElement(i,j);
                Label label;
                if (elementValue==0) {
                    label = new Label(" ");
                    label.setStyle("-fx-background-color: rgba(255,255,255,1)");
                } else {
                    label = new Label(String.valueOf(elementValue));
                    label.setStyle("-fx-background-color: rgba(252,1,1," +
                            Math.log(elementValue)/11.0 + "); " +
                            "-fx-font-size: 20");
                }
                grid.add(label, j, i);
                label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                label.setAlignment(Pos.CENTER);
            }
        }
        grid.setMaxSize(Board.SIZE*100, Board.SIZE*100);
        grid.setAlignment(Pos.CENTER);
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setPercentWidth(100.0/Board.SIZE);
        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setPercentHeight(100.0/Board.SIZE);
        for (int i=0 ; i<Board.SIZE ; i++) {
            grid.getColumnConstraints().add(columnConstraints);
            grid.getRowConstraints().add(rowConstraints);
        }
        stackPane.getChildren().clear();
        stackPane.getChildren().add(grid);
    }

    /**
     * Metoda <code>calcPoints</code> liczy zdobyte punkty podczas gry
     */

    private void calcPoints(long startTime) {
        long currentTime = System.nanoTime();
        timePoints+= (double)board.getPoints()/((double)(currentTime-startTime)/1000000);
        this.startTime=currentTime;
    }

    /**
     * Metoda <code>start</code> glowna metoda klasy Game, zawiera mechanikie gry oraz budowanue GUI (javaFX) oraz obsluge klawiszy
     */

    @Override
    public void start(Stage primaryStage) {
        startTime = System.nanoTime();
        board = new Board();
        board.emptyBoard();
        board.addNewRandomToBoard();
        board.addNewRandomToBoard();
        calcPoints(startTime);
        Button load = new Button("Wczytaj");
        load.setStyle("-fx-font-size: 20");
        load.setFocusTraversable(false);
        HBox.setHgrow(load, Priority.ALWAYS);
        StackPane stackPane = new StackPane();
        load.setMaxWidth(Double.MAX_VALUE);
        load.setOnAction(actionEvent -> {
            try {
                board = board.loadBoard();
                setGrid(board,stackPane);
            } catch (FileNotFoundException e) {
                System.out.println("Brak zapisanej gry.");

            } catch (IOException e) {
                System.out.println("Błąd");

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        Button save = new Button("Zapisz");
        save.setFocusTraversable(false);
        save.setStyle("-fx-font-size: 20");
        HBox.setHgrow(save, Priority.ALWAYS);
        save.setMaxWidth(Double.MAX_VALUE);
        save.setOnAction(actionEvent -> {
            try {
                board.saveBoard(board);
            } catch (IOException e) {
                System.out.println("Błąd");
            }
        });

        HBox hbox = new HBox(load, save);
        setGrid(board,stackPane);
        stackPane.setMaxSize(Board.SIZE*100, Board.SIZE*100);
        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: rgba(252,120,120,0.19)");
        borderPane.setCenter(stackPane);
        hbox.setAlignment(Pos.CENTER);
        borderPane.setBottom(hbox);
        message = new Label(" ");
        message.setMaxWidth(Double.MAX_VALUE);
        message.setAlignment(Pos.CENTER);
        message.setStyle("-fx-font-size: 20");
        points = new Label("Punkty: 0");
        points.setStyle("-fx-font-size: 20");
        points.setAlignment(Pos.CENTER);
        points.setMaxWidth(Double.MAX_VALUE);

        VBox messageBox = new VBox(points, message);
        borderPane.setTop(messageBox);

        Scene scene = new Scene(borderPane,600,600);
        scene.setOnKeyPressed(actionEvent -> {
            int rotated = 0;
            if (board.winCheck(board.getBoard())) {
                message.setText("Wygrana!");
                setGrid(board,stackPane);
                return;
            }
            switch (actionEvent.getCode()) {
                case DOWN:
                    rotated = 1;
                    break;
                case RIGHT:
                    rotated = 2;
                    break;
                case UP:
                    rotated = 3;
                    break;
                case LEFT:
                    break;
            }
            board.setBoard(board.rotateBoard(board.getBoard(), rotated));
            if (board.looseCheck(board.getBoard())) {
                message.setText("Przegrana!");
                board.setBoard(board.rotateBoard(board.getBoard(),4-rotated));
                return;
            }
            if (!board.moveIsPossible(board.getBoard(), true)) {
                message.setText("Ruch niemozliwy!");
                board.setBoard(board.rotateBoard(board.getBoard(),4-rotated));
                return;
            }
            board.setBoard(board.rotateBoard(board.getBoard(),4-rotated));
            if (board.winCheck(board.getBoard())) {
                message.setText("Wygrana!");
                setGrid(board,stackPane);
                return;
            }
            board.addNewRandomToBoard();
            setGrid(board,stackPane);
            calcPoints(startTime);
            message.setText("");
            points.setText("Punkty: " + String.format("%.2f",timePoints));

        });
        primaryStage.setTitle("2048 - Adam Lichy");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Metoda <code>main</code> statyczna metoda odpalajaca gre
     */

    public static void main(String[] args) {
        launch(args);
    }
}