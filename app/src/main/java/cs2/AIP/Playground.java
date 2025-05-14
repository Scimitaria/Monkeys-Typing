package cs2.AIP;

import cs2.util.Vec2;
import java.util.List;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.canvas.GraphicsContext;

//run with ./gradlew run -Pmain=cs2.AIP.Playground --args="<args>"
public class Playground extends Application {
    public static void main(String[] args) {
        // Forward the raw command‑line arguments to JavaFX
        Application.launch(Playground.class, args);
    }

    public Integer toInt(String s){
        return Integer.parseInt(s);
    }

    @Override
    public void start(Stage stage) {
        Image image   = new Image("file:square.png");
        Image goalimg = new Image("file:goal.jpg");
        Image obsimg  = new Image("file:obs.png");
        Canvas canvas = new Canvas(800, 800);
        stage.setTitle("Monkeys Typing");
        stage.setScene(new Scene(new StackPane(canvas)));
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.WHITE);
        stage.show();

        // Retrieve them here
        Parameters params = getParameters();
        // The raw, position‑based args exactly as typed
        List<String> args = params.getRaw();

        //TODO: get arg assignment to work
        int population  = 50; //size of starting population
        int initActions = 100;//# of actions to start
        int addRate = 1;  //# of turns between adding actions
        int addNum  = 100;//# of actions to add
        int numParents = 2; //# of parents for new generation
        boolean algorithmToggle = true;

        AnimationTimer timer = new AnimationTimer() {
            int frame = 0;
            int reps  = 1;

            Eugenics e  = new Eugenics(image,population,initActions);

            AI goal     = new AI(goalimg,new Vec2(650, 400),new ArrayList<Integer>());
            AI obstacle = new AI(obsimg,new Vec2(500, 375),new ArrayList<Integer>());

            public double distToGoal(AI ai){
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
                    var kid = e.kids.get(i);
                    //take action
                    //TODO: make obstacle work
                        switch(kid.actions.get(frame)){
                            case 0: kid.moveLeft();  break;
                            case 1: if(!kid.intersect(obstacle))kid.moveRight(); break;
                            case 2: kid.moveUp();    break;
                            case 3: kid.moveDown();  break;
                            default: throw new IndexOutOfBoundsException("illegal movement command");
                        }
                    kid.display(g);

                    if(kid.intersect(goal))System.exit(0);

                    //success evaluation
                    Double diff = initDist-distToGoal(kid);
                    kid.pro = diff.intValue();
                }
                goal.display(g);
                obstacle.display(g);

                frame++;
                var lenActs = e.kids.get(0).actions.size()-1;
                if(frame >= lenActs){
                    //can switch implementations of evolve to switch algorithms
                    //TODO: parameterize algorithm switch
                    if(algorithmToggle)
                         e.evolve(e.kids,image,numParents,addNum,(reps%addRate)==0);
                    else e.evolve(addNum,(reps%addRate)==0);

                    frame=0;
                    reps++;
                }
            }
        };
        timer.start();
    }
}
