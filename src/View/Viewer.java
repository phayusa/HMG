/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package View;

import Model.PersonModel;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import specifications.Require.RequireReadService;
import specifications.Service.ReadService;
import specifications.Service.ViewerService;
import tools.HardCodedParameters;

public class Viewer implements ViewerService, RequireReadService{
  private static final int spriteSlowDownRate=HardCodedParameters.spriteSlowDownRate;
  private static final double defaultMainWidth=HardCodedParameters.defaultWidth,
                              defaultMainHeight=HardCodedParameters.defaultHeight;
  private ReadService data;

  public Viewer(){}
  
  @Override
  public void bindReadService(ReadService service){
    data=service;
  }

  @Override
  public void init(){

    
  }

  @Override
  public Parent getPanel(){
	Group panel = new Group();
    for (PersonModel employee : data.getUserFactory().getEmployeeOfFactory()){
        Label label = new Label(employee.getName());
        label.setTranslateX(employee.getPositionOfEntity().x+employee.getWidth()/8);
        label.setTranslateY(employee.getPositionOfEntity().y - employee.getHeight()/2);
        ImageView imageOfEmployee = employee.getCurrentSprite();
        imageOfEmployee.setTranslateX(employee.getPositionOfEntity().x);
        imageOfEmployee.setTranslateY(employee.getPositionOfEntity().y);
        imageOfEmployee.setFitWidth(employee.getWidth());
        imageOfEmployee.setFitHeight(employee.getHeight());
        panel.getChildren().addAll(label, imageOfEmployee);
    }
    ImageView sprite = data.getTestSprite().getCurrentSprite();
    sprite.setTranslateX(data.getTestSprite().getPositionOfEntity().x);
    sprite.setTranslateY(data.getTestSprite().getPositionOfEntity().y);
    sprite.setScaleX(2);
    sprite.setScaleY(2);
    panel.getChildren().add(sprite);
    return panel;
  }

 
  
//  @Override
//  public void setMainWindowWidth(double width){
//    xShrink=width/defaultMainWidth;
//  }
//
//  @Override
//  public void setMainWindowHeight(double height){
//    yShrink=height/defaultMainHeight;
//  }
}
