package cs2.AIP;

import cs2.util.Vec2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class AIS {
  protected Image img;
  protected Vec2 pos;

  public AIS(Image i, Vec2 p) {
    img = i;
    pos = p;
  }

  public void display(GraphicsContext g) {
    g.drawImage(img, this.pos.getX(), this.pos.getY());
  }

  public boolean intersect(AIS s) {
    return !((this.pos.getX() > (s.pos.getX() + s.img.getWidth()))   ||
             (this.pos.getY() > (s.pos.getY() + s.img.getHeight()))  ||
             (this.pos.getX() + this.img.getWidth() < (s.pos.getX()))||
             (this.pos.getY() + this.img.getHeight() < (s.pos.getY())));
  }

  public void move(Vec2 delta) {
    this.pos.addThis(delta);
  }
}