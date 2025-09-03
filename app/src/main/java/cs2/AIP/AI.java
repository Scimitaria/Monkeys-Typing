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
    double thisTop    = this.pos.getY(),
           thisLeft   = this.pos.getX(),
           thisRight  = this.pos.getX()+this.img.getWidth(),
           thisBottom = this.pos.getY()+this.img.getHeight(),
           sTop    = s.pos.getY(),
           sLeft   = s.pos.getX(),
           sRight  = s.pos.getX()+s.img.getWidth(),
           sBottom = s.pos.getY()+s.img.getHeight();

    return !((thisLeft > sRight) ||
             (thisTop > sBottom) ||
             (thisRight < sLeft) ||
             (thisBottom < sTop));
  }

  public boolean intersectTop(AIS s){
    double thisTop    = this.pos.getY(),
           thisLeft   = this.pos.getX(),
           thisRight  = this.pos.getX()+this.img.getWidth(),
           thisBottom = this.pos.getY()+this.img.getHeight(),
           sLeft   = s.pos.getX(),
           sRight  = s.pos.getX()+s.img.getWidth(),
           sBottom = s.pos.getY()+s.img.getHeight();

    return ((thisLeft < sRight)  &&
            (thisTop <= sBottom) &&
            (thisRight > sLeft)  &&
            (thisBottom >= sBottom));
  }
  public boolean intersectLeft(AIS s){
    double thisTop    = this.pos.getY(),
           thisLeft   = this.pos.getX(),
           thisRight  = this.pos.getX()+this.img.getWidth(),
           thisBottom = this.pos.getY()+this.img.getHeight(),
           sTop    = s.pos.getY(),
           sRight  = s.pos.getX()+s.img.getWidth(),
           sBottom = s.pos.getY()+s.img.getHeight();

    return ((thisLeft <= sRight)  &&
            (thisTop < sBottom)   &&
            (thisRight >= sRight) &&
            (thisBottom > sTop));
  }
  public boolean intersectRight(AIS s){
    double thisTop    = this.pos.getY(),
           thisLeft   = this.pos.getX(),
           thisRight  = this.pos.getX()+this.img.getWidth(),
           thisBottom = this.pos.getY()+this.img.getHeight(),
           sTop    = s.pos.getY(),
           sLeft   = s.pos.getX(),
           sBottom = s.pos.getY()+s.img.getHeight();

    return ((thisLeft <= sLeft)  &&
            (thisTop < sBottom)  &&
            (thisRight >= sLeft) &&
            (thisBottom > sTop));
  }
  public boolean intersectBottom(AIS s){
    double thisTop    = this.pos.getY(),
           thisLeft   = this.pos.getX(),
           thisRight  = this.pos.getX()+this.img.getWidth(),
           thisBottom = this.pos.getY()+this.img.getHeight(),
           sTop    = s.pos.getY(),
           sLeft   = s.pos.getX(),
           sRight  = s.pos.getX()+s.img.getWidth();

    return ((thisLeft < sRight)  &&
            (thisTop <= sTop) &&
            (thisRight > sLeft) &&
            (thisBottom >= sTop));
  }
}