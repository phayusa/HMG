/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: engine/Engine.java 2015-03-11 buixuan.
 * ******************************************************/
package engine;

import Model.FactoryModel;
import Model.PersonModel;
import tools.*;

import specifications.Service.EngineService;
import specifications.Service.DataService;
import specifications.Require.RequireDataService;

import java.lang.ref.PhantomReference;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class Engine implements EngineService, RequireDataService{


  private Timer engineClock;
  private DataService dataOfWorld;
//  private User_Entry.COMMAND command;
  private Random gen;
  private boolean keyLeft, keyRight, keyUp, keyDown;
  private int index;

  public Engine(){}

  @Override
  public void bindDataService(DataService service){
    dataOfWorld=service;
  }

  @Override
  public void init(){
    engineClock = new Timer();
    gen = new Random();
    keyLeft = false;
    keyRight = false;
    keyUp = false;
    keyDown = false;
    index = 0;
  }

  @Override
  public void start(){
    engineClock.schedule(new TimerTask(){
      public void run() {
//        updateAllPositionWithKey();
        updateAllPositionWithFinalPosition(index/5);
        if(index<dataOfWorld.getUserFactory().getEmployeeOfFactory().size()*5)
          index++;
//      data.setSoundEffect(Sound.SOUND.None);
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

  private void updateMoveHeroeGeneral(AnimationSprite objectToMove){
    if(objectToMove.isRight()) {
      objectToMove.setNbAnim(2);
      objectToMove.setPositionWithSpeed(10,0);
      return;
    }
    if(objectToMove.isLeft()) {
      objectToMove.setNbAnim(1);
      objectToMove.setPositionWithSpeed(-10,0);
      return;
    }
    if(objectToMove.isUp()) {
      objectToMove.setNbAnim(3);
      objectToMove.setPositionWithSpeed(0,-10);
      return;
    }
    if(objectToMove.isDown()) {
      objectToMove.setNbAnim(0);
      objectToMove.setPositionWithSpeed(0,10);
      return;
    }

    if(!objectToMove.isLeft() && !objectToMove.isRight() && !objectToMove.isDown() && !objectToMove.isUp())
      objectToMove.stopAnim();
  }

  //Problem of priority at out
  private void updateMoveHeroeAtOut(AnimationSprite objectToMove){

    if(objectToMove.isUp()) {
      objectToMove.setNbAnim(3);
      objectToMove.setPositionWithSpeed(0,-10);
      return;
    }
    if(objectToMove.isDown()) {
      objectToMove.setNbAnim(0);
      objectToMove.setPositionWithSpeed(0,10);
      return;
    }
    if(objectToMove.isRight()) {
      objectToMove.setNbAnim(2);
      objectToMove.setPositionWithSpeed(10,0);
      return;
    }
    if(objectToMove.isLeft()) {
      objectToMove.setNbAnim(1);
      objectToMove.setPositionWithSpeed(-10,0);
      return;
    }

    if(!objectToMove.isLeft() && !objectToMove.isRight() && !objectToMove.isDown() && !objectToMove.isUp())
      objectToMove.stopAnim();
  }

  private void updateAllPositionWithKey(){
    for (PersonModel employee:dataOfWorld.getUserFactory().getEmployeeOfFactory()) {
      employee.setLeft(keyLeft);
      employee.setRight(keyRight);
      employee.setDown(keyDown);
      employee.setUp(keyUp);
    }
  }

  private void updateAllPositionWithFinalPosition(int maxIndex){
    for (int i=0;i<maxIndex;i++) {
      PersonModel employee = dataOfWorld.getUserFactory().getEmployeeOfFactory().get(i);
      employee.setRight(!(employee.getNewPosition().x < employee.getPositionOfEntity().x + 50));
      employee.setLeft(!(employee.getNewPosition().x > employee.getPositionOfEntity().x - 50));
      employee.setDown(!(employee.getNewPosition().y < employee.getPositionOfEntity().y + 50));
      employee.setUp(!(employee.getNewPosition().y > employee.getPositionOfEntity().y - 50));
      if (employee.getNewPosition().x == HardCodedParameters.EmployeeStartX)
        updateMoveHeroeAtOut(employee);
      else
        updateMoveHeroeGeneral(employee);
    }
  }

  @Override
  public void resetPosition() {
    dataOfWorld.setUserFactory(new FactoryModel(
    		new GraphicalEntity(
    				new Position(HardCodedParameters.FactoryStartX,HardCodedParameters.FactoryStartY),
    				HardCodedParameters.FactoryWidth,
    				HardCodedParameters.FactoryHeight,
    				HardCodedParameters.urlBackground
    				)
    		));
    index = 0;
  }

  @Override
  public void allLeave() {
    int halfFactory = HardCodedParameters.FactoryHeight/3;
    for (PersonModel Employee : dataOfWorld.getUserFactory().getEmployeeOfFactory()) {
      Employee.setNewPosition(new Position(HardCodedParameters.EmployeeStartX,HardCodedParameters.FactoryStartY+halfFactory));
    }
  }



}
