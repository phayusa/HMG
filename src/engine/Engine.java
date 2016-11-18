/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: engine/Engine.java 2015-03-11 buixuan.
 * ******************************************************/
package engine;

import Model.FactoryModel;
import Model.PersonModel;
import specifications.Require.RequireUiService;
import specifications.Service.UIService;
import tools.*;

import specifications.Service.EngineService;
import specifications.Service.DataService;
import specifications.Require.RequireDataService;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class Engine implements EngineService, RequireDataService, RequireUiService{


  private Timer engineClock;
  private DataService dataOfWorld;
  private UIService Ui;
  private Random gen;
  private boolean keyLeft, keyRight, keyUp, keyDown;
  private int index,FinalIndex;
  private Timer updateDay;
  private boolean InPause;

  public Engine(){}

  @Override
  public void bindDataService(DataService service){
    dataOfWorld=service;
  }


  @Override
  public void bindUiService(UIService service) {
    Ui = service;
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
    FinalIndex = dataOfWorld.getUserFactory().getEmployeeOfFactory().size() * 5;
    InPause = false;
    updateDay = new Timer();
    updateDay.schedule(new TimerTask() {
      @Override
      public void run() {
        if(index == FinalIndex && !InPause)
          DayProgression();
      }
    },0,HardCodedParameters.TimeBetweenDaysInMilli);
  }

  @Override
  public void start(){
    engineClock.schedule(new TimerTask(){
      public void run() {
//        updateAllPositionWithKey();

        if(!InPause) {
          if (index < FinalIndex) {
            index++;
            updateAllPositionWithFinalPosition(index / 5);
          } else {
            updateAllPositionWithFinalPosition();
//          DayProgression();
          }
        }
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
  public void onPause() {
    InPause = !InPause;
    for (PersonModel employee : dataOfWorld.getUserFactory().getEmployeeOfFactory()) {
      employee.stopAnim();
    }
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

  private void updateAllPositionWithFinalPosition(){
    for (PersonModel employee : dataOfWorld.getUserFactory().getEmployeeOfFactory()) {
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
    dataOfWorld.setCurrentDay(1);
  }

  @Override
  public void allLeave() {
    int halfFactory = HardCodedParameters.FactoryHeight/3;
    for (PersonModel Employee : dataOfWorld.getUserFactory().getEmployeeOfFactory()) {
      Employee.setNewPosition(new Position(HardCodedParameters.EmployeeStartX,HardCodedParameters.FactoryStartY+halfFactory));
      Employee.setInFactory(false);
    }
  }

  public void DayProgression(){
    int newDay = dataOfWorld.getCurrentDay() + 1;

    if(newDay == dataOfWorld.getNumberOfDaysForProject()){
      //TODO : open the dialog
      return;
    }
    dataOfWorld.setCurrentDay(newDay);
    Ui.addLineLog("Jour "+newDay+":");

    int halfFactory = HardCodedParameters.FactoryHeight/3;
//    for (int i=0;i<dataOfWorld.getMaxProgressionByDay();i++){
      for (PersonModel Employee:dataOfWorld.getUserFactory().getEmployeeOfFactory()) {
          //TODO : draw a number and add with a pourcent of wage. If inferior of 1 the diff is stocked in personModel and if this difference is greater than 1 -> the employee leave the factory
          int nextRandom = gen.nextInt(10);
          if(nextRandom == 0){
            Employee.setInFactory(false);
            Employee.setNewPosition(new Position(HardCodedParameters.EmployeeStartX,HardCodedParameters.FactoryStartY+halfFactory));
            Ui.addLineLog(Employee.getName()+" part du projet.");
          }
          nextRandom = gen.nextInt(2);
          if(nextRandom == 1){
            dataOfWorld.setProgressionOfWork(dataOfWorld.getProgressOfWork() + 1);
            Ui.addLineLog(Employee.getName()+" a fait progresser le projet de 1 %");
          }
      }
//    }
  }

  @Override
  public void ClearEmployeeOfNotInAction(ArrayList<PersonModel> test){
    if(test.size() != dataOfWorld.getUserFactory().getEmployeeOfFactory().size())
      dataOfWorld.setEmployeeOfFactory(test);
  }


  public int getIndex() {
    return index;
  }
}
