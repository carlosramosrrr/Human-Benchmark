package main.backend;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import main.Loader;


/**
 * Typing Test - test the speed of your typing skills
 *
 * @author Carlos Ramos Guereca
 */
public class TypingTest {

    @FXML
    private Pane root;
    @FXML
    private Label mainTitle;
    @FXML
    private Label mainDescription;
    @FXML
    private TextFlow mainTextFlow;
    @FXML
    private Button startButton;
    @FXML
    private Button menuButton;
    @FXML
    private TextField mainTextField;
    private boolean endGame = false;
    private int wordCount = 1;
    private long startTime;
    private int charCount = 0;
    private int location = 0;
    private String quote = "I hate when people start typing while I'm still " +
            "typing like I know you see those dots dude wait your turn...";

    /**
     * Will reset the board at the beginning of each round
     * Sets up the quote in a TextFlow by children.
     * @param actionEvent
     */
    @FXML
    private void startTypingTest(ActionEvent actionEvent) {
        reset();
        for(int i = 0; i < quote.length(); i++){
            Text text = new Text(String.valueOf(quote.charAt(i)));
            if(text.getText().charAt(0) == ' ') {
                wordCount++;
            }
            charCount++;
            mainTextFlow.getChildren().add(text);
        }
        mainTextFlow.setStyle("-fx-background-color:#EECFA1");
        startTime = System.nanoTime();
    }
    /**
     * Resets all of the counts and sets elements to false
     * and true.
     */
    private void reset(){
        location =0;
        wordCount = 0;
        charCount = 0;

        mainTextFlow.setVisible(true);
        mainDescription.setText("Typing Test");
        startButton.setVisible(false);
        menuButton.setVisible(false);
        startButton.setText("Try again");
        mainTextField.setVisible(true);
    }
    /**
     * Keeps track of all of the keys that are being pressed.
     * If its a backSpace it checks the
     * length of 'location' and decreases the location.
     * When it reached the same amount of characters of the
     * TextFlow then the game checks
     * to see if its valid.
     * Spaces location ++
     *
     * @param keyEvent
     */
    @FXML
    private void backSpace(KeyEvent keyEvent) {
        if(charCount == location){
           if(checkValidEndGame()) {
             //  location++;
               gameEndScreen();
               location++;
           }
        }
        if(keyEvent.getCode() == KeyCode.BACK_SPACE){
            if(location >0){
                location--;
            }
            ((Text)mainTextFlow.getChildren().get(location))
                    .setFill(Color.BLACK);
        }
        else if(keyEvent.getCode() == KeyCode.SPACE){
            location++;
        }
        else if(keyEvent.getText().length() > 0
                && keyEvent.getText().charAt(0) >= 33
                &&  keyEvent.getText().charAt(0) <= 126)
        {
            char currentChar = ((Text)mainTextFlow.getChildren()
                    .get(location)).getText().charAt(0);
            if (currentChar  == keyEvent.getText().charAt(0) ||
                    currentChar + 32 == keyEvent.getText().charAt(0)) {
                ((Text) mainTextFlow.getChildren().get(location))
                        .setFill(Color.GREEN);
                location++;

            } else {
                ((Text) mainTextFlow.getChildren()
                        .get(location)).setFill(Color.RED);
                location++;
            }
        }
    }

    /**
     * Checks for characters that were typed wrong.
     * It checks the TextField for size and compares it with the
     * TextFlow. Also checks to see if that any character is red then
     * it continues or ends.
     * @return
     */
    private boolean checkValidEndGame() {
        for(int i = 0; i <mainTextField.getCharacters().length(); i ++){
            if(i < charCount){
                if(((Text)mainTextFlow.getChildren().get(i)).getFill()
                        == Color.RED){
                    endGame = false;
                    return false;
                }
                else {
                    endGame = true;
                }
            }
        }
        return true;
    }

    /**
     * Sets the end of screen sceen.
     */
    private void gameEndScreen() {
        long finishTime = System.nanoTime();

        double elapsedTime = finishTime- startTime;
        double seconds = elapsedTime / 1000000000.0;
        float wpm = (float) ((((double) wordCount)/seconds)*60);
        mainDescription.setText(wpm + "words per minute");

        mainTextFlow.setVisible(false);
        mainTextFlow.getChildren().clear();
        mainTextField.clear();
        startButton.setVisible(true);
        menuButton.setVisible(true);
        startButton.setText("Try again");
        mainTextField.setVisible(false);
        mainTextField.setText("");
    }
    /**
     * onAction Event go to main menu
     * @param actionEvent
     */
    @FXML
    private void goToMenu(ActionEvent actionEvent) {
        Pane newRoot = Loader.loadFxmlFile(Home.homePage);
        Home.scene.setRoot(newRoot);
    }
}