package cs2.AIP;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;
import cs2.util.Vec2;
import javafx.animation.AnimationTimer;

public class Mario extends Application{
    @Override
    public void start(Stage stage) {
        stage.setTitle("Monkeys Typing");
        Canvas canvas = new Canvas(800, 800);
        stage.setScene(new Scene(new StackPane(canvas)));
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.WHITE);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            int frame = 0;
            @Override
            public void handle(long now) {
                //TODO: make Mario
                frame++;
            }
        };
        timer.start();
    }
}
