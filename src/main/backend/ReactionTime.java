package main.backend;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import main.Loader;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Reaction Time is a test where the screen will change color
 * and the user needs to click a button. The speed at which
 * the user clicked the button is displayed at the end.
 * @author Carlos Ramos Guereca
 */
public class ReactionTime {
    @FXML
    private Pane root;
    @FXML
    private Label reactionTime;
    @FXML
    private Label titleMain;

    private long startTime;
    private boolean clickOk = false;
    private Timer timer = new Timer();

    private LongProperty reactionTimeValue;


    public ReactionTime() {
        reactionTimeValue = new SimpleLongProperty(0);
    }
    /**
     * onAction for start button
     * starts the timer and sets the task it needs to be completed
     * by the time the timer ends
     * @param event
     * @throws InterruptedException
     */
    @FXML
    private void startTimer(ActionEvent event) throws InterruptedException {
        reactionTime.setStyle("-fx-progress-color: white");
        titleMain.setText("Wait for it...");
        root.setStyle("-fx-background-color: red");
        reactionTime.setVisible(false);
        long rand = ThreadLocalRandom.current().nextLong(1000, 5000);
        TimerTask task = new TimerTask() {
            public void run(){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        titleMain.setText("Click!");
                        root.setStyle("-fx-background-color: green");
                        startTime = System.nanoTime();
                        clickOk = true;
                    }
                });

            }
        };
        timer.schedule(task,rand);
    }
    /**
     * Counts the time it took the user to click the button
     * If the user clicked too early it prompts them to start again
     * @param event
     * @throws InterruptedException
     */
    @FXML
    private void finishTimer(ActionEvent event) throws InterruptedException {
        // my click was valid
        if(clickOk == true){
            reactionTime.setVisible(true);
            long finishTime = System.nanoTime();
            long reactionTimeNano = finishTime - startTime;
            long milliValue = TimeUnit.NANOSECONDS.toMillis(reactionTimeNano);
            String temp = String.valueOf(milliValue) + " ms";
            reactionTime.setText( temp);
            titleMain.setText("Try again?");
            timer.purge();
        }
        else{
            titleMain.setText("Too Soon!");
            reactionTime.setVisible(true);
            reactionTime.setVisible(false);
            timer.purge();
        }
        root.setStyle("-fx-background-color: darkcyan");
        clickOk = false;
    }

    /**
     * On click for the menu button
     * Takes you back to the main menu
     * @param actionEvent
     */
    @FXML
    private void goToMenu(ActionEvent actionEvent) {
        Pane newRoot = Loader.loadFxmlFile(Home.homePage);
        Home.scene.setRoot(newRoot);
    }
}
