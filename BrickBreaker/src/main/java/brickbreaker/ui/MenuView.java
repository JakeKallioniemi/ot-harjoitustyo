package brickbreaker.ui;

import static brickbreaker.BrickBreaker.GAME_HEIGHT;
import static brickbreaker.BrickBreaker.GAME_WIDTH;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class MenuView implements View {

    private List<Text> menuItems;
    private int selectedItem;
    private ViewManager viewManager;
    private Scene scene;

    public MenuView(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Override
    public Scene getScene() {
        return scene;
    }

    @Override
    public void enter(List<Object> args) {
        menuItems = new ArrayList<>();
        selectedItem = 0;
        scene = createScene();
    }

    private Scene createScene() {
        Text title = new Text("BRICK BREAKER");
        title.setTextAlignment(TextAlignment.CENTER);
        title.setFont(new Font(100));
        title.setFill(Color.WHITE);

        Text play = createMenuItem("PLAY");
        Text score = createMenuItem("SCORES");
        Text exit = createMenuItem("EXIT");

        VBox menuItemsBox = new VBox(play, score, exit);
        menuItemsBox.setAlignment(Pos.CENTER);
        menuItemsBox.setSpacing(10);

        StackPane root = new StackPane(title, menuItemsBox);
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setMargin(title, new Insets(70));

        Scene menuScene = new Scene(root, GAME_WIDTH, GAME_HEIGHT, Color.BLACK);

        menuScene.setOnKeyPressed(event -> {
            handleKeyPress(event);
        });

        highlight();

        return menuScene;
    }

    private Text createMenuItem(String text) {
        Text item = new Text(text);
        item.setTextAlignment(TextAlignment.CENTER);
        item.setFont(new Font(70));
        item.setFill(Color.WHITE);
        menuItems.add(item);
        return item;
    }

    private void handleKeyPress(KeyEvent event) {
        switch (event.getCode()) {
            case UP:
                selectedItem--;
                if (selectedItem < 0) {
                    selectedItem = menuItems.size() - 1;
                }
                highlight();
                break;
            case DOWN:
                selectedItem++;
                if (selectedItem >= menuItems.size()) {
                    selectedItem = 0;
                }
                highlight();
                break;
            case ENTER:
                select(menuItems.get(selectedItem).getText());
                break;
        }
    }

    private void select(String text) {
        switch (text) {
            case "PLAY":
                viewManager.changeView("PLAY", null);
                break;
            case "SCORES":
                viewManager.changeView("SCORES", null);
                break;
            case "EXIT":
                Platform.exit();
                break;
        }
    }

    private void highlight() {
        menuItems.forEach(i -> i.setFill(Color.WHITE));
        menuItems.get(selectedItem).setFill(Color.AQUA);
    }
}
