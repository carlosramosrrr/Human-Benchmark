package main.backend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import main.Loader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Verbal Memory is a test where a user is given a word and thhey
 * must say whether they have seen that word before or if its new.
 * It will give points if the decision was the wrong choice.
 * @author Carlos Ramos Guereca
 */
public class VerbalMemory {

    @FXML
    private Pane root;
    @FXML
    private Label info;
    @FXML
    private Label display;
    @FXML
    private Button start;
    @FXML
    private Button seenButton;
    @FXML
    private Button newButton;
    @FXML
    private Button resetButton;
    @FXML
    private Button menuButton;

    private String currentWord = "";
    private int score = 0;
    private int lives = 3;
    private ArrayList<String> words  = new ArrayList<String>(List.of(
            "Literally","Ironic","Irregardless", "Whom","Colonel",
            "Disinterested", "Enormity", "Nonplussed","Lieutenant",
            "Cat","Dog","Lion","Tiger","Zoo","Steam","Boiler",
            "John","Choose","Computer","Book","Notebook","Computer",
            "Microphone","Speaker","Keyboard","Mouse","Restaurant","Maya",
            "Bottle","Packet","Tissue","Glasses","Sweet","Photo","Camera",
            "Stamp","Postcard","Dictionary","Coin","Brush","Key","Wallet",
            "Umbrella","Pen","Lighter","Match","Case","Clip","Scissors",
            "Rubber","Passport","Sport","Laptop","Rubbish","Mirror",
            "Painkiller","Sunscreen","Toothbrush","Player","Battery",
            "Bin","Magazine","Clock","Plane","Slope","Adjacent","Line",
            "Circle","Rectangle","Rhombus","Triangle","Sphere","Ellipse",
            "Eclipse","Moon","Jupiter","Neptune","Mars","Earth","Pluto",
            "Venus","Saturn","Nova","Star","Octopus","Available","Button",
            "Radio","Fish","Rat","Pokemon","Shirt","Slipper","Pants",
            "Stairs","Bathroom","Bag","Soap","Towel","Comb","Watch","Plates",
            "Shoes","Stove","Gas","Money","Bed","Butter"
    ));
    private ArrayList<String> seenWords = new ArrayList<>();
    /**
     * onAction event
     * starts the trial
     * @param actionEvent
     */
    @FXML
    private void startTrial(ActionEvent actionEvent) {
        start.setVisible(false);
        seenButton.setVisible(true);
        newButton.setVisible(true);
        resetButton.setVisible(false);
        menuButton.setVisible(false);
        setTheGame();
    }

    /**
     * Sets up the game
     */
    private void setTheGame(){
        Collections.shuffle(words);
        currentWord = words.get(0);
        info.setText("Lives | " + lives + "    Score| " + score);
        display.setText(currentWord);
        checkEndGame();
    }

    /**
     * Checks the list of seenwords to see if the word
     * has been seen again
     * if it has been seen it adds points
     * if not it adds to seenWords and decrements lifes
     * @param actionEvent
     */
    @FXML
    private void seenWord(ActionEvent actionEvent) {
        if(seenWords.contains(currentWord)){
            score++;
            setTheGame();
        }
        else{
            seenWords.add(currentWord);
            lives--;
            setTheGame();
        }
    }

    /**
     * if the new word is already in seenWords then
     * it decrements the lives and sets it up again.
     * If not it increases the points and sets the
     * new word into seenWords.
     * @param actionEvent
     */
    @FXML
    private void newWord(ActionEvent actionEvent) {
        if(seenWords.contains(currentWord)){
            lives--;
            setTheGame();
        }
        else{
            score++;
            seenWords.add(currentWord);
            setTheGame();
        }
    }

    /**
     * Checks the status of the endGame
     */
    private void checkEndGame() {
        if(lives == 0){
            info.setText("Verbal Memory");
            display.setText(score +" words");
            seenButton.setVisible(false);
            newButton.setVisible(false);
            resetButton.setVisible(true);
            menuButton.setVisible(true);
        }
    }

    /**
     * onAction resets the trial
     * @param actionEvent
     */
    @FXML
    private void resetTrial(ActionEvent actionEvent) {
        lives = 3;
        score = 0;
        startTrial(actionEvent);
    }
    /**
     * onAction button goes to the main menu
     * @param actionEvent
     */
    @FXML
    private void goToMenu(ActionEvent actionEvent) {
        Pane newRoot = Loader.loadFxmlFile(Home.homePage);
        Home.scene.setRoot(newRoot);
    }
}
