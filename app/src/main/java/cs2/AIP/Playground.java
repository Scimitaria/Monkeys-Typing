package cs2.AIP;

import cs2.util.Vec2;
import java.util.List;
import java.util.ArrayList;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.scene.canvas.GraphicsContext;
import javafx.application.Application;
import javafx.animation.AnimationTimer;
import java.lang.invoke.WrongMethodTypeException;

//run with ./gradlew run -Pmain=cs2.AIP.Playground --args="<args>"
public class Playground extends Application {
    public static void main(String[] args) {
        // Forward the raw command‑line arguments to JavaFX
        Application.launch(Playground.class, args);
    }

    public Integer toInt(String s){
        return Integer.parseInt(s);
    }

    public boolean toBool(String s){
        switch(s){
            case  "true": case  "True": return  true;
            case "false": case "False": return false;
            default: throw new WrongMethodTypeException("arg is not a recognized bool");
        }
    }

    @Override
    public void start(Stage stage) {
        //set up graphics window
        Image image   = new Image("file:square.png"),
              goalimg = new Image("file:goal.jpg"),
              obsimg  = new Image("file:obs.png");
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
        int argLen = args.size();

        //TODO: MT stops working when run uses certain params
        /*if<20 freezes, if < 10 errs
        * java cpu usage jumps to 100% - computation error causing excess usage?
        * could be shell script autoterminating if so
        * GA doesn't crack even at 100,000 agents though*/
        int population  = (argLen>0) ? toInt(args.get(0)) : 50,        //size of starting population
            initActions = (argLen>1) ? toInt(args.get(1)) : 100,       //# of actions to start
            addRate     = (argLen>2) ? toInt(args.get(2)) : 1,         //# of turns between adding actions
            addNum      = (argLen>3) ? toInt(args.get(3)) : population,//# of actions to add
            numParents  = (argLen>4) ? toInt(args.get(4)) : 2;         //# of parents for new generation
        boolean algorithmToggle = (argLen>5) ? toBool(args.get(5)) : true;//choose algorithm

        AnimationTimer timer = new AnimationTimer() {
            int frame = 0,reps = 1;

            Eugenics e  = new Eugenics(image,population,initActions);

            AI goal  = new AI(goalimg,new Vec2(650, 400),new ArrayList<Integer>()),
            obstacle = new AI(obsimg,new Vec2(500, 375),new ArrayList<Integer>());

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
