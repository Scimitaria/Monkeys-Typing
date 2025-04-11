package cs2.AIP;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
//run with ./gradlew run -Pmain=cs2.AIP.Playground
public class Playground extends Application {
    @Override
    public void start(Stage stage) {
        Image image = new Image("file:square.png");
        Image goalimg = new Image("file:goal.jpg");
        stage.setTitle("Monkeys Typing");
        Canvas canvas = new Canvas(800, 800);
        stage.setScene(new Scene(new StackPane(canvas)));
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.WHITE);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            int frame = 0;
            Eugenics e = new Eugenics(image);

            @Override
            public void handle(long now) {
                for (int i = 0; i < e.kids.size() - 1; i++) {
                    g.fillRect(0, 0, 800, 800);
                    var kid=e.kids.get(i);
                    switch(kid.actions.get(frame)){
                        case 0: kid.moveLeft();  break;
                        case 1: kid.moveRight(); break;
                        case 2: kid.moveUp();    break;
                        case 3: kid.moveDown();  break;
                        default: throw new IndexOutOfBoundsException("illegal movement command");
                    }
                    kid.display(g);
                    //success evaluation
                    kid.pro = Long.valueOf(Math.round(kid.pos.getX())).intValue();
                }
                frame++;
                if(frame>=59){
                    e.evolve(e.kids,image);
                    frame=0;
                }
            }
        };
        timer.start();
    }
}
//TODO: fix figure out why only one AI displays, improve learning
