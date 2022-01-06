package main.backend;

import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import main.Loader;

/**
 * Home is a home class that contains the locations of all
 * the other games in the Human Benchmark game
 * @author Carlos Ramos Guereca
 */
public class Home {
    private static final String directory = "resources/";
    public static String homePage;
    private final String reactionTimePage;
    private final String aimTrainerPage;
    private final String chimpTestPage;
    private final String visualMemoryPage;
    private final String typingPage;
    private final String numberMemoryPage;
    private final String verbalMemoryPage;
    private final String hearingTestPage;

    protected static Scene scene;

    /**
     * Contains all the strings for the fxml pages.
     * This is the main menu that is displayed at the beginning.
     * @author Carlos Ramos Guereca
     */
    public Home() {
        homePage = directory + "home.fxml";
        reactionTimePage = directory + "reactionTime.fxml";
        aimTrainerPage = directory + "aimTrainer.fxml";
        chimpTestPage = directory + "chimpTest.fxml";
        visualMemoryPage = directory + "visualMemory.fxml";
        typingPage = directory + "typingTest.fxml";
        numberMemoryPage = directory + "numberMemory.fxml";
        verbalMemoryPage = directory + "verbalMemory.fxml";
        hearingTestPage = directory + "hearingTest.fxml";
    }

    /**
     * gets the scene
     * @return
     */
    protected Scene getScene() {
        return scene;
    }

    /**
     * sets the scene
     * @param scene
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * initializes the page
     */
    public void initialize() {

    }

    /**
     * Sets the scene to aim Trainer
     * @param actionEvent
     */
    public void aimTrainerClicked(MouseEvent actionEvent){
        Pane newRoot = Loader.loadFxmlFile(aimTrainerPage);
        scene.setRoot(newRoot);
    }

    /**
     * sets the scene to chimpTestClicked
     * @param actionEvent
     */
    public void chimpTestClicked(MouseEvent actionEvent) {
        Pane newRoot = Loader.loadFxmlFile(chimpTestPage);
        scene.setRoot(newRoot);
    }

    /**
     * sets the scene to Visual Test
     * @param actionEvent
     */
    public void visualTestClicked(MouseEvent actionEvent){
        Pane newRoot = Loader.loadFxmlFile(visualMemoryPage);
        scene.setRoot(newRoot);
    }

    /**
     * sets the scene to number memory test
     * @param actionEvent
     */
    public void numberMemoryTestClicked(MouseEvent actionEvent){
        Pane newRoot = Loader.loadFxmlFile(numberMemoryPage);
        scene.setRoot(newRoot);
    }

    /**
     * sets the scene to verbal memory test
     * @param actionEvent
     */
    public void verbalMemoryTestClicked(MouseEvent actionEvent){
        Pane newRoot = Loader.loadFxmlFile(verbalMemoryPage);
        scene.setRoot(newRoot);
    }

    /**
     * sets the scene to typing test
     * @param actionEvent
     */
    public void typingPageTestClicked(MouseEvent actionEvent){
        Pane newRoot = Loader.loadFxmlFile(typingPage);
        scene.setRoot(newRoot);
    }

    /**
     * sets the scene to hearing test
     * @param actionEvent
     */
    public void hearingPageTestClicked(MouseEvent actionEvent){
        Pane newRoot = Loader.loadFxmlFile(hearingTestPage);
        scene.setRoot(newRoot);
    }

    /**
     * sets the scene to reaction test
     * @param mouseEvent
     */
    public void reactionTimeClicked(MouseEvent mouseEvent) {
        Pane newRoot = Loader.loadFxmlFile(reactionTimePage);
        scene.setRoot(newRoot);
    }
}
