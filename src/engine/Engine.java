/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: engine/Engine.java 2015-03-11 buixuan.
 * ******************************************************/
package engine;

import tools.HardCodedParameters;
import tools.Position;
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
  private boolean moveLeft,moveRight,moveUp,moveDown;

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
    moveLeft = false;
    moveRight = false;
    moveUp = false;
    moveDown = false;
  }

  @Override
  public void start(){
    engineClock.schedule(new TimerTask(){
      public void run() {
        updateMoveHeroe();
        System.out.println(dataOfWorld.getTestSprite().getPositionOfEntity().x +" "+dataOfWorld.getTestSprite().getPositionOfEntity().y);
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
//          if (p.getAction()==PhantomService.MOVE.LEFT) moveLeft(p);
//          if (p.getAction()==PhantomService.MOVE.RIGHT) moveRight(p);
//          if (p.getAction()==PhantomService.MOVE.UP) moveUp(p);
//          if (p.getAction()==PhantomService.MOVE.DOWN) moveDown(p);
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
    if (c==User_Entry.COMMAND.LEFT) moveLeft=true;
    if (c==User_Entry.COMMAND.RIGHT) moveRight=true;
    if (c==User_Entry.COMMAND.UP) moveUp=true;
    if (c==User_Entry.COMMAND.DOWN) moveDown=true;
  }

  @Override
  public void releaseHeroesCommand(User_Entry.COMMAND c){
    if (c==User_Entry.COMMAND.LEFT) moveLeft=false;
    if (c==User_Entry.COMMAND.RIGHT) moveRight=false;
    if (c==User_Entry.COMMAND.UP) moveUp=false;
    if (c==User_Entry.COMMAND.DOWN) moveDown=false;
  }

  public void updateMoveHeroe(){
    if(moveDown)
      dataOfWorld.getTestSprite().setPositionOfEntity(new Position(dataOfWorld.getTestSprite().getPositionOfEntity().x,dataOfWorld.getTestSprite().getPositionOfEntity().y + dataOfWorld.getTestSprite().getSpeed()));
    if(moveUp)
      dataOfWorld.getTestSprite().setPositionOfEntity(new Position(dataOfWorld.getTestSprite().getPositionOfEntity().x,dataOfWorld.getTestSprite().getPositionOfEntity().y - dataOfWorld.getTestSprite().getSpeed()));
    if(moveRight)
      dataOfWorld.getTestSprite().setPositionOfEntity(new Position(dataOfWorld.getTestSprite().getPositionOfEntity().x + dataOfWorld.getTestSprite().getSpeed(),dataOfWorld.getTestSprite().getPositionOfEntity().y));
    if(moveLeft)
      dataOfWorld.getTestSprite().setPositionOfEntity(new Position(dataOfWorld.getTestSprite().getPositionOfEntity().x - dataOfWorld.getTestSprite().getSpeed(),dataOfWorld.getTestSprite().getPositionOfEntity().y ));
  }
}
