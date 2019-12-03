package brickbreaker.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.stage.Stage;

public class ViewManager {

    private Map<String, View> views;
    private Stage stage;

    public ViewManager() {
        views = new HashMap<>();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void addView(String name, View view) {
        views.put(name, view);
    }

    public void changeView(String name, List<Object> args) {
        View view = views.get(name);
        view.enter(args);
        stage.setScene(view.getScene());
    }
}
