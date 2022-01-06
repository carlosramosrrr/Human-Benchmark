package main.backend;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import main.Loader;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Number Memory is a test where a random number is displayed and the
 * user needs to remember what number was displayed. As the level
 * increases the size of the number increases as well.
 * @author Carlos Ramos Guereca
 */
public class NumberMemory {
    @FXML
    private Pane root;
    @FXML
    private Label title;
    @FXML
    private Button start;
    @FXML
    private TextField inputGuess;
    @FXML
    private VBox displayVBox;
    @FXML
    private Button submit;

    private int round = 1;
    private boolean displayed = false;
    private long numberToGuess = 0;
    private Timer timer = new Timer("Timer");

    /**
     * Starts the timer and also sets up the schedule for that tas.
     * @throws InterruptedException
     */
    private void startTimer() throws InterruptedException {
        long rand = ThreadLocalRandom.current().nextLong(3000, 4000);
        TimerTask task = new TimerTask() {

            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        start.setVisible(true);
                        inputGuess.setVisible(true);
                        title.setText("What was the Number?");
                        start.setVisible(false);
                        start.setManaged(false);
                        submit.setVisible(true);
                    }
                });
            }
        };
        timer.schedule(task,rand);
    }

    /**
     * Displays the random number I get
     * This all depends on the round in which the player is in
     */
    private void displayNumber() {
        Random rand = new Random();

        long sum =0;
        for(int i = 0; i < (round); i++){
            int randomValue = rand.nextInt(9);
            sum += randomValue *((long)Math.pow(10,(round-1-i)));
        }
        title.setText(String.valueOf(sum));
        if(title.getText().equals(String.valueOf(sum))){
            displayed = true;
        }
        numberToGuess = sum;

        submit.setVisible(false);
        inputGuess.setVisible(false);
    }

    /**
     * onAction method for the submit the guess
     * Checks to see if the values are the same and
     * then it moves you up a round.
     * @param event
     * @throws InterruptedException
     */
    @FXML
    private void submitGuess(ActionEvent event) throws InterruptedException {
        long input = Long.parseLong(inputGuess.getText());
        if(input == numberToGuess){
            round++;
            displayNumber();
            startTimer();
        }
        else{
            roundOver();
        }

    }

    /**
     * Once the round is over it shows you how
     * far you went
     */
    private void roundOver() {
        title.setText("You made it to round " + round + " ");
        inputGuess.setVisible(false);
        start.setVisible(true);
        start.setText("Restart?");
        submit.setVisible(false);
    }

    /**
     * onAction button displays the number and starts
     * the timer
     * @param event
     * @throws InterruptedException
     */
    @FXML
    private void startTest(ActionEvent event) throws InterruptedException {
        start.setVisible(false);
        numberToGuess = 1;
        round = 1;
        displayNumber();
        startTimer();
    }

    /**
     * onAction button goes back to the main menu
     * @param actionEvent
     */
    @FXML
    private void goToMenu(ActionEvent actionEvent) {
        Pane newRoot = Loader.loadFxmlFile(Home.homePage);
        Home.scene.setRoot(newRoot);
    }
}
