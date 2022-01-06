package main.backend;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;
import main.Loader;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Hearing Test is a test where a sound will play at random
 * and the user must click the button when they hear that sound
 * It calculates how long the user took to click the button
 * @author Carlos Ramos Guereca
 */
public class HearingTest {
    @FXML
    private Pane root;
    @FXML
    private Label mainTitle;
    @FXML
    private Label mainDescription;
    @FXML
    private Button startButton;
    @FXML
    private Button heardButton;

    private long startTime;
    private boolean clickOk = false;
    private Timer timer = new Timer();
    private String soundFile = "docs/SoundFile.wav";
    private Media sound = new Media(new File(soundFile).toURI().toString());
    private MediaPlayer mediaPlayer = new MediaPlayer(sound);


    /**
     * Starts the Timer and sets a delay for it to start on
     * @param actionEvent
     */
    @FXML
    private void startTimer(ActionEvent actionEvent) {
        startButton.setVisible(false);
        heardButton.setVisible(true);
        long rand = ThreadLocalRandom.current().nextLong(1000, 5000);

        TimerTask task = new TimerTask() {
            public void run(){
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        mainTitle.setText("Click!");
                        mediaPlayer.play();
                        startTime = System.nanoTime();
                        clickOk = true;
                    }
                });

            }
        };
        timer.schedule(task,rand);

    }

    /**
     * Stops the timer and calculates the time it takes for
     * the user to click the button
     * @param actionEvent
     */
    public void stopTimer(ActionEvent actionEvent) {
        if(clickOk){
            mainDescription.setText("Try again?");
            long finishTime = System.nanoTime();
            long reactionTimeNano = finishTime - startTime;
            long milliValue = TimeUnit.NANOSECONDS.toMillis(reactionTimeNano);
            mainTitle.setText("Your reaction time was " + milliValue + "ms");

        }
        else{
            mainTitle.setText("You clicked too soon!");
        }
        mediaPlayer.stop();
        clickOk = false;
        timer.purge();
        startButton.setVisible(true);
        heardButton.setVisible(false);
    }

    /**
     * Takes the User back to the main menu
     * @param actionEvent
     */
    @FXML
    private void goToMenu(ActionEvent actionEvent) {
        Pane newRoot = Loader.loadFxmlFile(Home.homePage);
        Home.scene.setRoot(newRoot);
    }
}
