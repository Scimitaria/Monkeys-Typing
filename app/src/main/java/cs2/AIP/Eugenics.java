package cs2.AIP;

import cs2.util.Vec2;
import java.util.ArrayList;
import javafx.scene.image.Image;
import java.util.Random;
import java.util.Collections;
import java.util.Comparator;

public class Eugenics {
  public ArrayList<AI> kids = new ArrayList<AI>();    //agents
  ArrayList<Integer> acts = new ArrayList<Integer>(); //actions
  ArrayList<Integer> pros = new ArrayList<Integer>(); //success evaluation
  Random rand = new Random();

  public Eugenics(Image ick, int population, int actionNum) {
    for (int i = 0; i < population; i++) {
      acts = new ArrayList<Integer>();
      for (int k = 0; k < actionNum; k++) acts.add(rand.nextInt(4));
      kids.add(new AI(ick, new Vec2(400, 400), acts));
    }
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

  /*Genetic Algorithm
   * Creates a population which is then evaluated
   * based on success criteria. The best ones will
   * be selected to produce the next generation.
  */
  public void evolve(ArrayList<AI> ai,Image image,int addNum,boolean addRate){
    var len = ai.size();
    var children = new ArrayList<AI>();

    //sort AI based on success evaluation
    Collections.sort(ai,new Comparator<AI>(){
      public int compare(AI a1, AI a2){
        if(a1.pro>a2.pro) return 1;
        if(a1.pro<a2.pro) return -1;
        return 0; 
      }
    });
    //select top performers
    var first  = ai.get(len-1);
    var second = ai.get(len-2);

    //reset actions
    acts=new ArrayList<Integer>();
    for(int i=0;i<kids.size();i++){
      acts=procreate(first.actions,second.actions);
      if(addRate) for(int j=0;j<addNum;j++) acts.add(rand.nextInt(4));
      children.add(new AI(image,new Vec2(400, 400),acts));
    }
    kids=children;
  }

  public AI type(AI ai,int level, int div){
    var len = ai.actions.size();
    for(int i=0;i<len;i++){
      switch (level){
        case 2: for(int j = 0; j < len - 1; j += rand.nextInt(div / 10)) ai.actions.set(i, rand.nextInt(4)); break;
        case 1: for(int j = 0; j < len - 1; j += rand.nextInt(div / 2)) ai.actions.set(i, rand.nextInt(4)); break;
      }
    }
    return ai;
  }

  //TODO: fix MT
  /*Monkeys Typing variant
   * Similar concept to the Genetic Algorithm,
   * but without the selection of top agents.
   * Instead, random variations are introduced,
   * the magnitude of which varies inversely
   * with success evaluation.
   * 
   * This algorithm is terrible lol
  */
  public void evolve(int addNum,boolean addRate) {
    pros = new ArrayList<Integer>();
    var len=kids.size();
    for (int j = 0; j < len; j++) pros.add(kids.get(j).pro);
    Collections.sort(pros);
    for (int fg = 0; fg < pros.size(); fg++) {
      var p=pros.get(fg);
      var kid=kids.get(fg);
      if (p == kid.pro){
        kid.pos = new Vec2(400, 400);
        if(p < len / 2) type(kid,2,len);
        if((fg > 9) && (fg < len * 3 / 4)) type(kid,2,len);
        if(addRate) for(int j=0;j<addNum;j++) acts.add(rand.nextInt(4));
      }
    }
  }
}
