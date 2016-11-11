/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package View;

import Model.PersonModel;
import tools.HardCodedParameters;

import specifications.Service.ViewerService;
import specifications.Service.ReadService;
import specifications.Require.RequireReadService;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

public class Viewer implements ViewerService, RequireReadService{
  private static final int spriteSlowDownRate=HardCodedParameters.spriteSlowDownRate;
  private static final double defaultMainWidth=HardCodedParameters.defaultWidth,
                              defaultMainHeight=HardCodedParameters.defaultHeight;
  private ReadService data;
  private ImageView heroesAvatar;
  private Image heroesSpriteSheet;
  private ArrayList<Rectangle2D> heroesAvatarViewports;
  private ArrayList<Integer> heroesAvatarXModifiers;
  private ArrayList<Integer> heroesAvatarYModifiers;
  private int heroesAvatarViewportIndex;
  private double xShrink,yShrink,shrink,xModifier,yModifier,heroesScale;

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
      ImageView imageOfEmployee = new ImageView(employee.getImageOfEntity());
      imageOfEmployee.setTranslateX(employee.getPositionOfEntity().x);
      imageOfEmployee.setTranslateY(employee.getPositionOfEntity().y);
      imageOfEmployee.setScaleX(0.5);
      imageOfEmployee.setScaleY(0.5);
      panel.getChildren().add(imageOfEmployee);
    }
    return panel;
  }

  @Override
  public void setMainWindowWidth(double width){
    xShrink=width/defaultMainWidth;
  }
  
  @Override
  public void setMainWindowHeight(double height){
    yShrink=height/defaultMainHeight;
  }
}
