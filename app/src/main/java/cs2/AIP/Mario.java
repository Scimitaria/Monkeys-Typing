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
import java.util.List;

import cs2.util.Vec2;
import javafx.animation.AnimationTimer;

//run with ./gradlew run -Pmain=cs2.AIP.Mario --args="<args>"
public class Mario extends Application{
    public static void main(String[] args) {
        // Forward the raw command‑line arguments to JavaFX
        Application.launch(Playground.class, args);
    }
    @Override
    public void start(Stage stage) {
        Image placeholder   = new Image("file:square.png");
        stage.setTitle("Monkeys Typing");
        Canvas canvas = new Canvas(800, 800);
        stage.setScene(new Scene(new StackPane(canvas)));
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.WHITE);
        stage.show();

        // Retrieve them here
        Parameters params = getParameters();
        // The raw, position‑based args exactly as typed
        List<String> args = params.getRaw();

        int population  = 50;//size of starting population
        int initActions = 100;//# of actions to start
        int addRate = 1; //# of turns between adding actions
        int addNum  = 100;//# of actions to add

        AnimationTimer timer = new AnimationTimer() {
            int frame = 0;
            int reps  = 1;
            Eugenics e  = new Eugenics(placeholder,population,initActions);

            @Override
            public void handle(long now) {
                //TODO: make Mario
                frame++;
            }
        };
        timer.start();
    }
}
