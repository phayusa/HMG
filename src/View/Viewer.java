/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package View;

import Model.PersonModel;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import specifications.Require.RequireReadService;
import specifications.Service.ReadService;
import specifications.Service.ViewerService;
import tools.GraphicalEntity;
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
      Rectangle factoryRepresentation = new Rectangle(data.getUserFactory().getWidth(),data.getUserFactory().getHeight(),Color.BLACK);
      factoryRepresentation.setTranslateX(data.getUserFactory().getPositionOfEntity().x);
      factoryRepresentation.setTranslateY(data.getUserFactory().getPositionOfEntity().y);
      panel.getChildren().add(factoryRepresentation);
      for (GraphicalEntity Office : data.getUserFactory().getOffices() ) {
          ImageView imageOfOffice = Office.getImageOfEntity();
          imageOfOffice.setTranslateX(Office.getPositionOfEntity().x);
          imageOfOffice.setTranslateY(Office.getPositionOfEntity().y);
          imageOfOffice.setFitWidth(Office.getWidth());
          imageOfOffice.setFitHeight(Office.getHeight());
          panel.getChildren().add(imageOfOffice);
      }
    for (PersonModel employee : data.getUserFactory().getEmployeeOfFactory()){
        Label label = new Label(employee.getName());
        label.setTranslateX(employee.getPositionOfEntity().x+employee.getWidth()/8);
        label.setTranslateY(employee.getPositionOfEntity().y - employee.getHeight()/2);
        label.setTextFill(Color.WHITE);
        ImageView imageOfEmployee = employee.getCurrentSprite();
        imageOfEmployee.setTranslateX(employee.getPositionOfEntity().x);
        imageOfEmployee.setTranslateY(employee.getPositionOfEntity().y);
        imageOfEmployee.setFitWidth(employee.getWidth());
        imageOfEmployee.setFitHeight(employee.getHeight());
        //Example of Mouse Event
        imageOfEmployee.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("Pressed "+employee.getName());
            }
        });
        panel.getChildren().addAll(label, imageOfEmployee);
    }


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
