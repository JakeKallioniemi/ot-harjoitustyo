package brickbreaker.ui;

import java.util.List;
import javafx.scene.Scene;

public interface View {
    
    public void enter(List<Object> args);
    public Scene getScene();
}
