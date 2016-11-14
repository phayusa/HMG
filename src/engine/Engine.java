/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: engine/Engine.java 2015-03-11 buixuan.
 * ******************************************************/
package engine;

import Model.PersonModel;
import tools.AnimationSprite;
import tools.HardCodedParameters;
import tools.User_Entry;

import specifications.Service.EngineService;
import specifications.Service.DataService;
import specifications.Require.RequireDataService;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class Engine implements EngineService, RequireDataService{
//  private static final double friction=HardCodedParameters.friction,
//                              heroesStep=HardCodedParameters.heroesStep,
//                              phantomStep=HardCodedParameters.phantomStep;
  private Timer engineClock;
  private DataService dataOfWorld;
//  private User_Entry.COMMAND command;
  private Random gen;
  private int testNbAnim;
  private boolean keyLeft, keyRight, keyUp, keyDown;

  public Engine(){}

  @Override
  public void bindDataService(DataService service){
    dataOfWorld=service;
  }

  @Override
  public void init(){
    engineClock = new Timer();
//    command = User_Entry.COMMAND.NONE;
    gen = new Random();/**/
    keyLeft = false;
    keyRight = false;
    keyUp = false;
    keyDown = false;
    testNbAnim = 1;
  }

  @Override
  public void start(){
    engineClock.schedule(new TimerTask(){
      public void run() {
        updateMoveHeroe(dataOfWorld.getTestSprite());
        for(PersonModel employee : dataOfWorld.getUserFactory().getEmployeeOfFactory()){
          updateMoveHeroe(employee);
        }
//        if(testNbAnim == 4) {
//          dataOfWorld.getTestSprite().stopAnim();
//          testNbAnim++;
//        }
//        if(testNbAnim < 4){
//          dataOfWorld.getTestSprite().setNbAnim(testNbAnim);
//        }

        //System.out.println("Game step #"+data.getStepNumber()+": checked.");

//        if (gen.nextInt(10)<3) spawnPhantom();
//
//        updateSpeedHeroes();
//        updateCommandHeroes();
//        updatePositionHeroes();
//
//        ArrayList<PhantomService> phantoms = new ArrayList<PhantomService>();
//        int score=0;
//
//        data.setSoundEffect(Sound.SOUND.None);
//
//        for (PhantomService p:data.getPhantoms()){
//          if (p.getAction()==PhantomService.MOVE.LEFT) keyLeft(p);
//          if (p.getAction()==PhantomService.MOVE.RIGHT) keyRight(p);
//          if (p.getAction()==PhantomService.MOVE.UP) keyUp(p);
//          if (p.getAction()==PhantomService.MOVE.DOWN) keyDown(p);
//
//          if (collisionHeroesPhantom(p)){
//            data.setSoundEffect(Sound.SOUND.HeroesGotHit);
//            score++;
//          } else {
//            if (p.getPosition().x>0) phantoms.add(p);
//          }
//        }
//
//        data.addScore(score);
//
//        data.setPhantoms(phantoms);

        //data.setStepNumber(data.getStepNumber()+1);
      }
    },0,HardCodedParameters.enginePaceMillis);
  }

  @Override
  public void stop(){
    engineClock.cancel();
    System.exit(0);
  }

  @Override
  public void setHeroesCommand(User_Entry.COMMAND c){
    switch (c){
      case LEFT:
        keyLeft=true;
        break;
      case RIGHT:
        keyRight=true;
        break;
      case UP:
        keyUp=true;
        break;
      case DOWN:
        keyDown=true;
        break;
    }
  }

  @Override
  public void releaseHeroesCommand(User_Entry.COMMAND c){
    switch (c){
      case LEFT:
        keyLeft=false;
        break;
      case RIGHT:
        keyRight=false;
        break;
      case UP:
        keyUp=false;
        break;
      case DOWN:
        keyDown=false;
        break;
    }
  }

  public void updateMoveHeroe(AnimationSprite objectToMove){
    if(keyDown) {
      objectToMove.setNbAnim(0);
      objectToMove.setPositionWithSpeed(0,10);
    }
    if(keyUp) {
      objectToMove.setNbAnim(3);
      objectToMove.setPositionWithSpeed(0,-10);
    }
    if(keyRight) {
      objectToMove.setNbAnim(2);
      objectToMove.setPositionWithSpeed(10,0);
    }
    if(keyLeft) {
      objectToMove.setNbAnim(1);
      objectToMove.setPositionWithSpeed(-10,0);
    }
    if(!keyLeft && !keyDown && !keyRight && !keyUp)
      objectToMove.stopAnim();
  }

}
