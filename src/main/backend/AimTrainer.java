package main.backend;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import main.Loader;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import static javafx.scene.paint.Color.BLACK;

/**
 * A class that creates an aimTrainer for the users
 * It creates circles around the Pane and as you hit them it lowers
 * the total amount of targets. At the end it calculates the
 * speed at which the user hit each target.
 * @author Carlos Ramos Guereca
 */
public class AimTrainer {
    @FXML
    private Pane root;
    @FXML
    private Label reactionTime;
    @FXML
    private Label mainTitle;
    @FXML
    private Label mainDescription;
    @FXML
    private Label remainingLabel;
    @FXML
    private VBox remainingVBox;
    @FXML
    private HBox buttonHBox;
    @FXML
    private Button startButton;
    @FXML
    private Button menuButton;


    private long startTime = 0;
    private int pressed = 0;
    private int radius = 20;
    private int attempts = 30;

    /**
     *
     * @param event
     * @throws InterruptedException
     */
    @FXML
    private void startTimer(ActionEvent event) throws InterruptedException{
        pressed = 0;
        reactionTime.setVisible(false);
        long rand = ThreadLocalRandom.current().nextLong(1000, 2000);
        TimeUnit.MILLISECONDS.sleep(rand);
        startTime = System.nanoTime();
        mainTitle.setVisible(false);
        mainDescription.setVisible(false);
        draw();
    }

    /**
     * Calculates how long it took to click on all of the targets
     * also changes the display because its already end game
     */
    private void finishTimer() {

        long finishTime = System.nanoTime();
        long reactionTimeNano = (finishTime - startTime)/attempts;
        long milliValue = TimeUnit.NANOSECONDS.toMillis(reactionTimeNano);
        reactionTime.setText(milliValue + "ms per target");
        reactionTime.setVisible(true);
        remainingVBox.setVisible(false);
        buttonHBox.setVisible(true);
        mainTitle.setVisible(true);
        mainDescription.setVisible(true);


    }

    /**
     * Draws the circles on the pane.
     * Adds an eventHandler to each new circle that is being
     * created. When a circle
     * is clicked it adds a count and removes the circle
     */
    private void draw(){
        mainTitle.setVisible(false);
        mainDescription.setVisible(false);
        remainingVBox.setVisible(true);
        buttonHBox.setVisible(false);
        long randX = ThreadLocalRandom.current().nextLong(
                50, (int)root.getWidth() - radius);
        long randY = ThreadLocalRandom.current().nextLong(
                50, (int)root.getHeight() - radius);
        Circle circle = new Circle(randX,randY, radius,BLACK);
        root.getChildren().add(circle);
        EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if(pressed< attempts-1){
                    pressed++;
                    root.getChildren().remove(circle);
                    remainingLabel.setText(Integer.toString(attempts-pressed));
                    draw();
                }
                else{
                    root.getChildren().remove(circle);
                    finishTimer();
                }
            }
        };
        circle.addEventFilter(MouseEvent.MOUSE_CLICKED,eventHandler);
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
