package cs2.AIP;

import cs2.util.Vec2;
import java.util.ArrayList;
import javafx.scene.image.Image;
import java.util.Random;
import java.util.Collections;
import java.util.Comparator;

public class Eugenics {
  public ArrayList<AI> kids = new ArrayList<AI>();
  Random rand = new Random();
  ArrayList<Integer> acts = new ArrayList<Integer>();
  ArrayList<Integer> pros = new ArrayList<Integer>();

  public Eugenics(Image ick) {
    for (int k = 0; k < 60; k++) acts.add(rand.nextInt(4));
    for (int i = 0; i < 19; i++) kids.add(new AI(ick, new Vec2(400, 400), acts));
  }

  public ArrayList<Integer> procreate(ArrayList<Integer> a1,ArrayList<Integer> a2){
    var len = a1.size();
    if(len!=a2.size())throw new IndexOutOfBoundsException("lists are not of equal length");
    var kid=new ArrayList<Integer>();
    var e = 0;
    var coinflip=0;
    for(int i=0;i<len;i++){
      coinflip=rand.nextInt(2);
      if(coinflip==1)e=a1.get(i);
      else e=a2.get(i);
      kid.add(i,e);
    }
    return kid;
  }

  //genetic algorithm
  public void evolve(ArrayList<AI> ai,Image image){
    var len = ai.size();
    var children = new ArrayList<AI>();
    Collections.sort(ai,new Comparator<AI>(){
      public int compare(AI a1, AI a2){
        if(a1.pro>a2.pro) return 1;
        if(a1.pro<a2.pro) return -1;
        return 0; 
      }
    });
    var first  = ai.get(len-1);
    var second = ai.get(len-2);
    acts=new ArrayList<Integer>();
    for(int i=0;i<kids.size();i++){
      acts=procreate(first.actions,second.actions);
      children.add(new AI(image,new Vec2(400, 400),acts));
    }
    kids=children;
  }

  //Monkeys Typing variant
  public void evolve() {
    pros = new ArrayList<Integer>();
    var len=kids.size();
    for (int j = 0; j < len; j++) pros.add(kids.get(j).pro);
    Collections.sort(pros);
    for (int fg = 0; fg < pros.size(); fg++) {
      var p=pros.get(fg);
      var kid=kids.get(fg);
      if (p == kid.pro){
        kid.pos = new Vec2(400, 400);
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
