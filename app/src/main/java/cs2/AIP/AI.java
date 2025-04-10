package cs2.AIP;

import cs2.util.Vec2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.util.ArrayList;

public class AI extends AIS {
  int pro = 0;
  ArrayList<Integer> actions = new ArrayList<Integer>();

  public AI(Image i, Vec2 p, ArrayList<Integer> a){
    super(i, p);
    actions = a;
  }

  public void display(GraphicsContext g){
    g.drawImage(img,this.pos.getX(),this.pos.getY());
  }

  public void moveLeft(){
    this.move(new Vec2(-5, 0));
  }

  public void moveUp(){
    this.move(new Vec2(0, -5));
  }

  public void moveDown(){
    this.move(new Vec2(0, 5));
  }

  public void moveRight(){
    this.move(new Vec2(5, 0));
  }

  public boolean intersect(AIS s){
    return !((this.pos.getX() > (s.pos.getX() + s.img.getWidth())) ||
            (this.pos.getY() > (s.pos.getY() + s.img.getHeight())) ||
          (this.pos.getX() + this.img.getWidth() < (s.pos.getX())) ||
          (this.pos.getY() + this.img.getHeight() < (s.pos.getY())));
  }
}