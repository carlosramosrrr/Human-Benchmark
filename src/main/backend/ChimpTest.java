package main.backend;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import main.Loader;
import java.util.*;

import static javafx.scene.paint.Color.*;

/**
 * ChimpTest is a test that creates a grid with numbers.
 * The user must click the squares increasing order
 * The first click will make each square loss its number and
 * the user has to remember the number for each square.
 * The grid increases in size and the number of total squares
 * to memorize
 * @author Carlos Ramos Guereca
 */
public class ChimpTest {
    @FXML
    private GridPane root;
    @FXML
    private Label info;
    @FXML
    private Button startButton;
    private int count = 1;
    private int gridWidth = 8;
    private int gridHeight = 5;
    private int level = 4;
    private int strikes = 1;
    private boolean firstClick = true;

    private List<int[]>   pairRandList = new ArrayList<>();
    private List<StackPane> stackList = new ArrayList<>();

    /**
     * sets random locations depending on the modular grid.
     * Will add new targets into the paiRandList and shuffle.
     */
    private void getRandom() {
        pairRandList.clear();
        for (int i = 1; i < gridHeight; i++) {
            for (int j = 1; j < gridWidth; j++) {
                if(!(i == j)){
                    pairRandList.add(new int[]{i, j});
                }
            }
        }
        Collections.shuffle(pairRandList);
    }

    /**
     * Starts the test
     * @param event
     * @throws InterruptedException
     */
    @FXML
    private void startTest(ActionEvent event) throws InterruptedException {
        root.setStyle("-fx-background-color: darkcyan");
        startButton.setVisible(false);
        draw();
    }

    /**
     * resets the board and the count;
     */
    private void reset() {
        firstClick = true;
        count = 1;
        stackList.clear();
        root.getChildren().clear();
        info.setText("You are in level " + (level-3) +
                " you have "+ (strikes -1)+" strikes");
        draw();
    }

    /**
     * draw gets the list of indexes from pairRandList in order to designate
     * the new targets once its there it sets them up as targets
     * and creates the rest of the board as well. Creates event handles
     * for them
     * as well.
     */
    private void draw() {
        getRandom();
        for (int l = 1; l <= level; l++) {
            int rowRand = pairRandList.get(0)[0];
            int colRand = pairRandList.get(0)[1];
            pairRandList.remove(0);
            for (int i = 1; i < gridWidth; i++) {
                for (int j = 1; j < gridHeight; j++) {
                    if (colRand == i && rowRand == j) {
                        Rectangle rect = new Rectangle(50, 50);
                        rect.setFill(WHITE);
                        Label numLabel = new Label(Integer.toString(l));
                        StackPane stack = new StackPane();
                        stack.setId(Integer.toString(l));
                        stack.getChildren().addAll(rect, numLabel);

                        stackList.add(stack);

                        EventHandler<MouseEvent> eventHandler =
                                new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent e) {
                                stack.getChildren().setAll(
                                        new Rectangle(50, 50, DARKCYAN));
                                int id = Integer.parseInt(stack.getId());
                                if (firstClick) {
                                    for (int i = 0; i < stackList.size(); i++){
                                        if (stackList.get(i).
                                                getChildren().size() > 1) {
                                            stackList.get(i).getChildren().
                                                    get(1).setVisible(false);
                                        }
                                    }
                                    firstClick = false;
                                }
                                if (id == count) {
                                    count++;
                                } else if (strikes >= 3) {
                                    gameFailed();
                                } else {
                                    strikes++;
                                    reset();
                                }
                                if (count - 1 == level) {
                                    level++;
                                    reset();
                                }
                            }
                        };
                        stack.addEventFilter(
                                MouseEvent.MOUSE_CLICKED,eventHandler);
                        root.setVgap(5);
                        root.setHgap(5);
                        root.add(stack, i, j);
                    }
                }
            }

        }
    }

    /**
     * When the user ends the round this is
     * Displayed
     */
    private void gameFailed() {
        root.getChildren().clear();
        info.setText("You made it to level " + (level - 4));
        completeReset();
    }

    /**
     * Resets the game once a full round has ended
     * and the user wants to play again
     */
    private void completeReset() {
        level = 4;
        strikes = 1;
        firstClick = true;
        count = 1;
        stackList.clear();
        root.getChildren().clear();
        info.setText("You are in level " + (level-3) +
                " you have "+ (strikes -1)+" strikes");
        startButton.setVisible(true);
    }

    /**
     * Goes back to the main menu
     * @param actionEvent
     */
    @FXML
    private void goToMenu(ActionEvent actionEvent) {
        Pane newRoot = Loader.loadFxmlFile(Home.homePage);
        Home.scene.setRoot(newRoot);
    }
}