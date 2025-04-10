package cs2.AIP;

import java.util.ArrayList;
import cs2.util.Vec2;
import javafx.scene.image.Image;
import java.util.Random;
import java.util.Collections;

public class Eugenics {
  public ArrayList<AI> kids = new ArrayList<AI>();
  Random rand = new Random();
  ArrayList<Integer> acts = new ArrayList<Integer>();
  ArrayList<Integer> pros = new ArrayList<Integer>();

  public Eugenics(Image ick) {
    for (int k = 0; k < 60; k++) acts.add(rand.nextInt(4));
    for (int i = 0; i < 19; i++) kids.add(new AI(ick, new Vec2(400, 400), acts));
  }

  public void evolve() {
    pros = new ArrayList<Integer>();
    var len=kids.size();
    for (int j = 0; j < len; j++) pros.add(kids.get(j).pro);
    Collections.sort(pros);
    for (int fg = 0; fg < pros.size(); fg++) {
      var p=pros.get(fg);
      var kid=kids.get(fg);
      if (p == kid.pro){
        kids.get(fg).pos = new Vec2(400, 400);
        if (p < len / 2) {
          for (int i = 0; i < kid.actions.size() - 1; i += rand.nextInt(len / 10)) kid.actions.set(i, rand.nextInt(4));
        }
        if ((fg > 9) && (fg < len * 3 / 4)) {
          for (int i = 0; i < kid.actions.size() - 1; i += rand.nextInt(len / 2)) kid.actions.set(i, rand.nextInt(4));
        }
      }
    }
  }
}