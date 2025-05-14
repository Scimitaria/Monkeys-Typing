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

//run with ./gradlew run -Pmain=cs2.AIP.Mario --args="<args>"
public class Mario extends Application{
    public static void main(String[] args) {
        // Forward the raw command‑line arguments to JavaFX
        Application.launch(Playground.class, args);
    }
    @Override
    public void start(Stage stage) {
        Image placeholder   = new Image("file:square.png");
        Image flagimg       = new Image("flag.png");
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

        int population  = 50; //size of starting population
        int initActions = 100;//# of actions to start
        int addRate = 1;  //# of turns between adding actions
        int addNum  = 100;//# of actions to add

        AnimationTimer timer = new AnimationTimer() {
            int frame = 0;
            int reps  = 1;

            Eugenics e  = new Eugenics(placeholder,population,initActions);
            ArrayList<AI> kids = e.kids;

            AI goal     = new AI(flagimg,new Vec2(650, 400),new ArrayList<Integer>());

            public double distToGoal(AI ai){
                var pos=ai.pos;
                var gpos=goal.pos;
                var a=Math.pow(Math.abs(pos.getX()-gpos.getX()),2);
                var b=Math.pow(Math.abs(pos.getY()-gpos.getY()),2);
                return Math.sqrt(a+b);
            }
            double initDist = distToGoal(kids.get(0));

            @Override
            public void handle(long now) {
                g.fillRect(0, 0, 800, 800);

                for (int i = 0; i < kids.size() - 1; i++) {
                    var kid=kids.get(i);

                    //TODO: make Mario

                    kid.display(g);

                    if(kid.intersect(goal))System.exit(0);

                    //success evaluation
                    Double diff = initDist-distToGoal(kid);
                    kid.pro = diff.intValue();
                }

                frame++;
                var lenActs = kids.get(0).actions.size()-1;
                if(frame >= lenActs){
                    //can switch implementations of evolve to switch algorithms
                    e.evolve(kids,placeholder,addNum,(reps%addRate)==0);
                    frame=0;
                    reps++;
                }
            }
        };
        timer.start();
    }
}
