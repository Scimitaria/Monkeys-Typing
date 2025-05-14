package cs2.AIP;

import cs2.util.Vec2;
import java.util.ArrayList;
import javafx.scene.image.Image;
import java.util.Random;
import java.util.Collections;
import java.util.Comparator;

public class Eugenics {
  public ArrayList<AI> kids = new ArrayList<AI>();      //agents
  ArrayList<Integer>   acts = new ArrayList<Integer>(); //actions
  Random rand = new Random();

  public Eugenics(Image ick, int population, int actionNum) {
    for (int i = 0; i < population; i++) {
      acts = new ArrayList<Integer>();
      for (int k = 0; k < actionNum; k++) acts.add(rand.nextInt(4));
      kids.add(new AI(ick, new Vec2(400, 400), acts));
    }
  }

  //custom instance of sort for AI
  public void sort(ArrayList<AI> AIlst){
    Collections.sort(kids,new Comparator<AI>(){
      public int compare(AI a1, AI a2){
        if(a1.pro>a2.pro) return 1;
        if(a1.pro<a2.pro) return -1;
        return 0;
      }
    });
  }

  //mixes attributes of the best agents to create the next generation
  //TODO: check w/ more than two
  public ArrayList<Integer> procreate(ArrayList<AI> ai){
    var kid=new ArrayList<Integer>();
    var coinflip=0;

    //pick random between selections
    for(int i=0;i<ai.get(0).actions.size();i++){
      //choose random parent
      coinflip=rand.nextInt(ai.size());
      kid.add(i,ai.get(coinflip).actions.get(i));
    }
    return kid;
  }

  /*Genetic Algorithm
   * Creates a population which is then evaluated
   * based on success criteria. The best ones will
   * be selected to produce the next generation.
  */
  public void evolve(ArrayList<AI> ai,Image image,int numParents,int addNum,boolean addRate){
    var children = new ArrayList<AI>();
    ArrayList<AI> parents = new ArrayList<AI>();
    int size = ai.size();

    //sort AI based on success evaluation
    sort(ai);
    //select top performers
    if (numParents >= size) parents = ai;
    else parents = new ArrayList<>(ai.subList(size - numParents, size));

    for(int i=0;i<kids.size();i++){
      //make new actions
      acts=procreate(parents);
      if(addRate) for(int j=0;j<addNum;j++) acts.add(rand.nextInt(4));

      //add new agent
      children.add(new AI(image,new Vec2(400, 400),acts));
    }
    kids=children;
  }


  //randomly generate new actions
  public AI type(AI ai,int level, int div){
    var len = ai.actions.size();

    //randomize based on success evaluation
    for(int i=0;i<len;i++){
      //vary randomly based on level
      //level is based on success eval
      switch (level){
        case 2: for(int j = 0; j < len - 1; j += rand.nextInt(div / 10)) ai.actions.set(i, rand.nextInt(4)); break;
        case 1: for(int j = 0; j < len - 1; j += rand.nextInt(div / 2)) ai.actions.set(i, rand.nextInt(4)); break;
      }
    }

    return ai;
  }

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
    var len=kids.size();

    //sort AI based on success evaluation
    sort(kids);

    for (int fg = 0; fg < kids.size(); fg++) {
      var kid=kids.get(fg);
      //reset position
      kid.pos = new Vec2(400, 400);

      //least successful change most
      if(kid.pro < len / 2) type(kid,2,len);
      //more successful change less; most successful don't change
      if((fg > (len / 2)) && (fg < len * (3 / 4))) type(kid,1,len);

      //add additional random actions
      if(addRate) for(int j=0;j<addNum;j++) kid.actions.add(rand.nextInt(4));
    }
  }
}