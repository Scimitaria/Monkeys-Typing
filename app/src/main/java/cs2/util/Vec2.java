package cs2.util;

public class Vec2 {
  private double x, y;
  public Vec2(double _x, double _y) {
    this.x = _x;
    this.y = _y;
  }
  public Vec2() {
    this.x = 0;
    this.y = 0;
  }
  public static Vec2 random(double mag) {
    return new Vec2((Math.random()-0.5)*mag, (Math.random()-0.5)*mag);
  }
  public Vec2 clone() {
    return new Vec2(this.x, this.y);
  }
  public double getX() {
    return this.x;
  }
  public double getY() {
    return this.y;
  }
  public Vec2 add(Vec2 other) {
    return new Vec2(this.x + other.x, this.y + other.y);
  }
  public void addThis(Vec2 other) {
    this.x += other.x;
    this.y += other.y;
  }
}
