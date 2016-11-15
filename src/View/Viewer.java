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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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


  public Parent panelCompany(){
	Pane panel = new Pane();
      panel.setMaxSize(HardCodedParameters.companySizeX,HardCodedParameters.companySizeY);
      panel.setTranslateX(HardCodedParameters.companyTranslateX);
      panel.setTranslateY(HardCodedParameters.companyTranslateY);
      panel.setStyle("-fx-background-color: green;");
    for (PersonModel employee : data.getUserFactory().getEmployeeOfFactory()){
        Label label = new Label(employee.getName());
        label.setTranslateX(employee.getPositionOfEntity().x+employee.getWidth()/8);
        label.setTranslateY(employee.getPositionOfEntity().y - employee.getHeight()/2);
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
    ImageView sprite = data.getTestSprite().getCurrentSprite();
    sprite.setTranslateX(data.getTestSprite().getPositionOfEntity().x);
    sprite.setTranslateY(data.getTestSprite().getPositionOfEntity().y);
    sprite.setScaleX(2);
    sprite.setScaleY(2);
    sprite.setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            System.out.println("Pressed");
        }
    });
    panel.getChildren().add(sprite);
    return panel;
  }


  public Parent panelStat(){
        Pane panel = new Pane();
        panel.setStyle("-fx-background-color: black;");
        /*Button btn = new Button();
        btn.setTranslateX(100);
        btn.setTranslateY(80);
        btn.setText("Hello World");
        */
        panel.setMaxSize(HardCodedParameters.statSizeX,HardCodedParameters.statSizeY);
        panel.setTranslateX(HardCodedParameters.statTranslateX);
        panel.setTranslateY(HardCodedParameters.statTranslateY);
        //panel.getChildren().addAll(btn);
      return panel;
  }


    public Parent panelBack(){
        Pane panel = new Pane();
        panel.setStyle("-fx-background-color: blue;");
        TextArea back = new TextArea();
        back.setEditable(false);
        back.setPrefRowCount(8);
        back.setPrefColumnCount(40);

        panel.setMaxSize(HardCodedParameters.backSizeX,HardCodedParameters.backSizeY);
        panel.setTranslateX(HardCodedParameters.backTranslateX);
        panel.setTranslateY(HardCodedParameters.backTranslateY);
        panel.getChildren().addAll(back);
        return panel;
    }

  @Override
    public Parent getMainPanel() {
        StackPane rootPane = new StackPane();
        rootPane.getChildren().addAll(panelCompany(),panelStat(),panelBack());

        return rootPane;
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
