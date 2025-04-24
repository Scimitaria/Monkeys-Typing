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
            AI goal = new AI(goalimg,new Vec2(500, 500),new ArrayList<Integer>());

            public Double distToGoal(AI ai){
                var pos=ai.pos;
                var gpos=goal.pos;
                var a=Math.pow(Math.abs(pos.getX()-gpos.getX()),2);
                var b=Math.pow(Math.abs(pos.getY()-gpos.getY()),2);
                return Math.sqrt(a+b);
            }
            double initDist = distToGoal(e.kids.get(0));

            @Override
            public void handle(long now) {
                g.fillRect(0, 0, 800, 800);
                for (int i = 0; i < e.kids.size() - 1; i++) {
                    var kid=e.kids.get(i);
                    switch(kid.actions.get(frame)){
                        case 0: kid.moveLeft();  break;
                        case 1: kid.moveRight(); break;
                        case 2: kid.moveUp();    break;
                        case 3: kid.moveDown();  break;
                        default: throw new IndexOutOfBoundsException("illegal movement command");
                    }
                    kid.display(g);
                    if(kid.intersect(goal))break;//TODO: supposed to end but doesn't
                    //success evaluation
                    Double diff = initDist-distToGoal(kid);
                    kid.pro = diff.intValue();
                }
                goal.display(g);
                frame++;
                if(frame>=59){//TODO: increase action #
                    e.evolve(e.kids,image);
                    frame=0;
                }
            }
        };
        timer.start();
    }
}
//TODO: update gitignore
