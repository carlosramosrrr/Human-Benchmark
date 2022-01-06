package main.backend;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import main.Loader;

import java.util.*;

import static javafx.scene.paint.Color.*;

/**
 * Visual memory is a class that creates
 * a game where the user test the amount of
 * squares on a grid they can memorize
 * @author Carlos Ramos Guereca
 */
public class VisualMemory {
    @FXML
    private GridPane root;
    @FXML
    private Label info;
    @FXML
    private Button resetButton;
    @FXML
    private Button startButton;
    private int level = 3;
    private int count = 0;
    private int gridHeight = 3;
    private int gridWidth = 3;
    private int  strikes= 0;
    private int lifes = 3;

    private boolean endGame = false;

    private List<int[]> pairRandList = new ArrayList<>();

    /**
     * gets a an ArrayList of a 2 d array with positions for row and col
     * that are random and will determine which blocks will be targets that
     * need to be memorized.
     */
    private void getRandom(){
        pairRandList.clear();
        for (int i = 0; i < gridHeight; i++) {
            for (int j = 0; j < gridWidth; j++) {
                if(!(i == j)){
                    pairRandList.add(new int[]{i, j});
                }
            }
        }
        Collections.shuffle(pairRandList);
    }

    /**
     * Resets the board and sets the label once again.
     * @throws InterruptedException
     */
    private void reset() throws InterruptedException {
        root.getChildren().clear();
        info.setText("You are in level " + (level-2)
                + " you have "+ (strikes )+" strikes lifes: " +lifes);
        count = 0;
        strikes = 0;
        draw();
    }

    /**
     * When the first button is clicked this function
     * runs and it sets up the first call
     * to draw.
     * @param event
     * @throws InterruptedException
     */
    @FXML
    private void startTest(ActionEvent event) throws InterruptedException {
        startButton.setVisible(false);
        root.getChildren().clear();
        info.setText("You are in level " + (level-2)
                + " you have "+ (strikes )+" strikes");
        draw();
    }

    /**
     * Labels the blocks and targets that need to be memorized
     * depending on the level the # of targets increase. uses the arrayList
     * pairRandList in order to get random values for each new board.
     * Sets a Timer and TimerTask that flip the blocks to different colors
     * waits 3 seconds than it flips.
     * @throws InterruptedException
     */
    private void setTargets() throws InterruptedException {
        for (int l = 0; l < level; l++) {
            int rowRand = pairRandList.get(0)[0];
            int colRand = pairRandList.get(0)[1];
            pairRandList.remove(0);
            Rectangle rect = (Rectangle) root.getChildren().get(
                    (colRand) * (gridWidth) + rowRand);
            rect.setFill(WHITE);
            rect.setId("target");
        }
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run(){
                if(!endGame) {
                    for (int i = 0; i < gridWidth ; i++) {
                        for (int j = 0; j < gridHeight ; j++) {
                            Rectangle rect = (Rectangle)root.getChildren()
                                    .get((i)*(gridWidth)+j);
                            if (rect.getId().equals("target")) {
                                rect.setFill(PINK);
                            }
                        }
                    }
                }
            }
        };
        timer.schedule(task,3000l);
    }

    /**
     * draws the board for the game
     * Sets the logic for the game with the onAction
     * sets an eventHandler for the rectangles as well.
     * @throws InterruptedException
     */
    private void draw() throws InterruptedException {
            getRandom();
            for (int i = 0; i < gridWidth; i++) {
                for (int j = 0; j < gridHeight; j++) {
                        Rectangle rect = new Rectangle(50, 50);
                            rect.setFill(PINK);
                        rect.setId("nonTarget");

                        root.add(rect, i, j);
                        EventHandler<MouseEvent> eventHandler
                                = new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent e) {
                                rect.setFill(WHITE);
                                String id = rect.getId();
                                if(id.equals("target")){
                                    count++;
                                    rect.setFill(WHITE);
                                    }
                                else if(id.equals("nonTarget")){
                                    strikes++;
                                }
                                if(strikes == 3){
                                    lifes--;
                                    strikes = 0;
                                    try {
                                        reset();
                                    } catch (InterruptedException iE) {
                                        iE.printStackTrace();
                                    }
                                }
                                if(lifes == 0){
                                    endGame = true;
                                    endOfGame();
                                }
                                else if(count == level){
                                    level++;
                                    if(level % 5 == 0 || level % 7 ==0){
                                        gridWidth++;
                                        gridHeight++;
                                    }
                                    try {
                                        reset();
                                    } catch (InterruptedException iE) {
                                        iE.printStackTrace();
                                    }
                                }
                                info.setText("You are in level "
                                        + (level-2) + " you have "+ (strikes )+
                                        " strikes lifes: " +lifes);
                            }
                        };
                        rect.addEventFilter(
                                MouseEvent.MOUSE_CLICKED, eventHandler);
                        root.setVgap(5);
                        root.setHgap(5);
                }
            }
            if(!endGame && lifes > 0){
                setTargets();
            }
            if(endGame){
                endOfGame();
            }
    }

    /**
     * Sets the end of the game
     */
    private void endOfGame() {
        root.getChildren().clear();
        info.setText("You finished on level "+ (level-2));
        resetButton.setVisible(true);

    }

    /**
     * Goes to main menu
     * @param actionEvent
     */
    @FXML
    private void goToMenu(ActionEvent actionEvent) {
        Pane newRoot = Loader.loadFxmlFile(Home.homePage);
        Home.scene.setRoot(newRoot);
    }

    /**
     * Resets the game when one has already ended
     * @param event
     * @throws InterruptedException
     */
    @FXML
    private void resetTest(ActionEvent event) throws InterruptedException {
        level = 3;
        gridHeight = 3;
        gridWidth = 3;
        lifes = 3;
        resetButton.setVisible(false);
        endGame = false;
        reset();
        startTest(event);
    }
}
