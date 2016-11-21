/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package View;

import Model.PersonModel;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import specifications.Require.RequireEngineService;
import specifications.Require.RequireReadService;
import specifications.Require.RequireStatisticsService;
import specifications.Service.EngineService;
import specifications.Service.ReadService;
import specifications.Service.StatisticsService;
import specifications.Service.ViewerService;
import tools.GraphicalEntity;
import tools.HardCodedParameters;

import java.util.ArrayList;

public class Viewer implements ViewerService, RequireReadService, RequireStatisticsService,RequireEngineService{
  private static final int spriteSlowDownRate=HardCodedParameters.spriteSlowDownRate;
  private static final double defaultMainWidth=HardCodedParameters.defaultWidth,
                              defaultMainHeight=HardCodedParameters.defaultHeight;
  private ReadService data;
  private StatisticsService statistics;
  private EngineService engine;
    private  double currentWidth, currentHeight;



  public Viewer(){}
  
  @Override
  public void bindReadService(ReadService service){
	  data=service;
  }
  
  @Override
  public void bindStatisticsService(StatisticsService statisticsService) {
	  statistics=statisticsService;  	
  }

    @Override
  public void bindEngineService(EngineService service) {
    engine = service;
  }


  @Override
  public void init(){

    
  }

  public Parent panelCompany(){

	Pane panel = new Pane();
    panel.setMaxSize(data.getUserFactory().getRatioWidth() * currentWidth,data.getUserFactory().getRatioHeight() * currentHeight);
    panel.setTranslateX(data.getUserFactory().getRatioX());
    panel.setTranslateY(data.getUserFactory().getRatioY());
    ImageView factoryRepresentation = data.getUserFactory().getImageOfEntity();
    factoryRepresentation.setTranslateX(data.getUserFactory().getRatioX() * currentWidth);
    factoryRepresentation.setTranslateY(data.getUserFactory().getRatioY() * currentHeight);
    factoryRepresentation.setFitWidth(data.getUserFactory().getRatioWidth() * currentWidth);
    factoryRepresentation.setFitHeight(data.getUserFactory().getRatioHeight() * currentHeight);
//      double res = data.getUserFactory().getRatioHeight() * currentHeight;
      //To RUN FOR UNKNOWN REASON
//      double res_bis = data.getUserFactory().getHeight()/HardCodedParameters.defaultHeight * currentHeight;
//      System.err.println("new "+res+" old "+res_bis);
//      factoryRepresentation.setFitHeight(res);
//      factoryRepresentation.setFitWidth(data.getUserFactory().getWidth()/HardCodedParameters.defaultWidth * currentWidth);
//      factoryRepresentation.setFitHeight(data.getUserFactory().getHeight()/HardCodedParameters.defaultHeight * currentHeight);
//    factoryRepresentation.setFitHeight(data.getUserFactory().getRatioHeight() * currentHeight);
//      System.err.println((data.getUserFactory().getWidth()/ HardCodedParameters.defaultWidth));
//      System.err.println((data.getUserFactory().getHeight()/ HardCodedParameters.defaultHeight));

    panel.getChildren().add(factoryRepresentation);
    for (GraphicalEntity Office : data.getUserFactory().getOffices() ) {
      ImageView imageOfOffice = Office.getImageOfEntity();
      imageOfOffice.setTranslateX(Office.getRatioX() * currentWidth);
      imageOfOffice.setTranslateY(Office.getRatioY() * currentHeight);
      imageOfOffice.setFitWidth(Office.getRatioWidth() * currentWidth);
      imageOfOffice.setFitHeight(Office.getRatioHeight() * currentHeight);
      panel.getChildren().add(imageOfOffice);
    }
      ArrayList<PersonModel> newArray = new ArrayList<PersonModel>();
    for (PersonModel employee : data.getUserFactory().getEmployeeOfFactory()){
        if(employee.getPositionOfEntity().x < data.getUserFactory().getHideRoom().getPositionOfEntity().x && employee.getPositionOfEntity().x > data.getUserFactory().getPositionOfEntity().x){
            ImageView imageOfEmployee = employee.getCurrentSprite();
            imageOfEmployee.setTranslateX(employee.getRatioX() * currentWidth);
            imageOfEmployee.setTranslateY(employee.getRatioY() * currentHeight);
            imageOfEmployee.setFitWidth(employee.getRatioWidth() * currentWidth);
            imageOfEmployee.setFitHeight(employee.getRatioHeight() * currentHeight);
//            res_bis = employee.getHeight()/HardCodedParameters.defaultHeight * currentHeight;
//            res_bis = employee.getWidth()/HardCodedParameters.defaultWidth * currentWidth;
            //Example of Mouse Event
            imageOfEmployee.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("Name: " + employee.getName() +
                                        "\nJob: " + employee.getJob() +
                                        "\nSalary: " + (int) employee.getSalary() + "€");

                }
            });
            panel.getChildren().addAll(employee.getLabelOfEmployee(currentWidth,currentHeight), imageOfEmployee);

        }
        if(employee.isInFactory())
            newArray.add(employee);
        else{
            if(employee.getPositionOfEntity().x > data.getUserFactory().getPositionOfEntity().x)
                newArray.add(employee);
        }
//        else
//            engine.ClearEmployeeOfNotInAction();
            //        if(employee.getPositionOfEntity().x < data.getUserFactory().getPositionOfEntity().x){
//            engine.ClearEmployeeOfNotInAction();
//        }
    }
    engine.ClearEmployeeOfNotInAction(newArray);

    ImageView hideRooom = data.getUserFactory().getHideRoom().getImageOfEntity();
    hideRooom.setTranslateX(data.getUserFactory().getHideRoom().getRatioX() * currentWidth);
    hideRooom.setTranslateY(data.getUserFactory().getHideRoom().getRatioY() * currentHeight);
    hideRooom.setFitWidth(data.getUserFactory().getHideRoom().getRatioWidth() * currentWidth);
    hideRooom.setFitHeight(data.getUserFactory().getHideRoom().getRatioHeight() * currentHeight);
    panel.getChildren().add(hideRooom);

    ProgressBar progressionOfProject = new ProgressBar(data.getProgressOfWorkInFloat());
    ProgressIndicator progressIndication = new ProgressIndicator(data.getProgressOfWorkInFloat());
    HBox ProgressionBox = new HBox();
      ProgressionBox.setPrefWidth(200/HardCodedParameters.defaultWidth * currentWidth);
//      ProgressionBox.setPrefHeight(20/HardCodedParameters.defaultHeight * currentHeight);
    ProgressionBox.setAlignment(Pos.CENTER);
    ProgressionBox.getChildren().addAll(progressionOfProject,progressIndication);
    ProgressionBox.setTranslateX((HardCodedParameters.FactoryStartX + HardCodedParameters.FactoryWidth/2 - 40)/HardCodedParameters.defaultWidth * currentWidth);
    ProgressionBox.setTranslateY((HardCodedParameters.FactoryHeight - 50)/HardCodedParameters.defaultHeight * currentHeight);
      panel.getChildren().add(ProgressionBox);

    Label DayPresentation = new Label("Jour "+data.getCurrentDay()+"/"+data.getNumberOfDaysForProject());
    DayPresentation.setTextFill(Color.BLACK);
    DayPresentation.setFont(new Font("Arial",0.0347222222 * currentHeight));
    DayPresentation.setTranslateX((HardCodedParameters.FactoryStartX+15)/HardCodedParameters.defaultWidth * currentWidth);
    DayPresentation.setTranslateY((HardCodedParameters.FactoryHeight - 45)/HardCodedParameters.defaultHeight * currentHeight);
      panel.getChildren().add(DayPresentation);


    return panel;
  }

  public Parent panelStat(){
        VBox panel = new VBox(100/HardCodedParameters.defaultHeight * currentHeight);
      panel.setMaxHeight(currentHeight);
        panel.setStyle("-fx-background-color: grey;-fx-border-color: black;");
        panel.setMaxSize(HardCodedParameters.statSizeX/HardCodedParameters.defaultWidth*currentWidth,HardCodedParameters.statSizeY/HardCodedParameters.defaultHeight*currentHeight);
        panel.setFillWidth(true);
//        panel.setTranslateX(HardCodedParameters.statTranslateX);
//        panel.setTranslateY(HardCodedParameters.statTranslateY);
        
        final PieChart chartStatic = data.getEstimateChart();
	  	chartStatic.autosize();
	  	chartStatic.setTitle("Budget estimé");
//	  	chartStatic.setMaxSize(400/HardCodedParameters.defaultWidth * currentWidth, 200/HardCodedParameters.defaultHeight * currentHeight);
        chartStatic.setPrefHeight(200/HardCodedParameters.defaultHeight * currentHeight);
        chartStatic.setPrefWidth(400/HardCodedParameters.defaultWidth * currentWidth);
//	  	chartStatic.setTranslateY(20);
	  	chartStatic.setLabelLineLength(10/HardCodedParameters.defaultWidth * currentWidth);
	  	chartStatic.setLegendVisible(false);

	  	panel.getChildren().addAll(chartStatic);
	    	
        final PieChart chart = data.getSimulateChart();
        chart.autosize();
        chart.setTitle("Budget simulé");
        chart.setMaxSize(400/HardCodedParameters.defaultWidth * currentWidth, 200/HardCodedParameters.defaultHeight * currentHeight);
//        chart.setTranslateY(HardCodedParameters.statSizeY-250);
        chart.setLabelLineLength(10/HardCodedParameters.defaultWidth * currentWidth);
        chart.setLegendVisible(false);

        chart.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	Alert alert = new Alert(AlertType.INFORMATION);
    	    	alert.setTitle("Information du budget");
    	    	alert.setHeaderText("Budget initial");
    	    	alert.setContentText(data.getUserFactory().getBudget() + " €");
    	    	alert.showAndWait();
            }
        });

	  panel.getChildren().addAll(chart);
        
      return panel;
  }


    public Parent panelBack(){
        Pane panel = new Pane();
        panel.setStyle("-fx-background-color: white;-fx-border-color: black;");
        TextArea back = new TextArea(data.getLogsInString());
        back.setMaxSize(HardCodedParameters.backSizeX/HardCodedParameters.defaultWidth * currentWidth, HardCodedParameters.backSizeY/HardCodedParameters.defaultHeight * currentHeight);
        back.setEditable(false);
        back.setWrapText(true);


        panel.setMaxSize(HardCodedParameters.backSizeX/HardCodedParameters.defaultWidth * currentWidth,HardCodedParameters.backSizeY/HardCodedParameters.defaultHeight * currentHeight);
//        panel.setTranslateX(HardCodedParameters.backTranslateX);
//        panel.setTranslateY(HardCodedParameters.backTranslateY);
        panel.getChildren().addAll(back);
        return panel;
    }

  @Override
    public Parent getMainPanel(double currentWidth,double currentHeight) {
      this.currentWidth = currentWidth;
      this.currentHeight = currentHeight;
      AnchorPane rootPane = new AnchorPane();
//        rootPane.setPrefSize(HardCodedParameters.defaultWidth,HardCodedParameters.defaultHeight);
        AnchorPane.setTopAnchor(panelCompany(),0.0);
        AnchorPane.setLeftAnchor(panelCompany(),0.0);

//      AnchorPane.setTopAnchor(panelCompany(),0.0);
//      AnchorPane.setRightAnchor(panelCompany(),0.0);
//
//
//      AnchorPane.setBottomAnchor(panelCompany(),0.0);
//      AnchorPane.setRightAnchor(panelCompany(),0.0);

      VBox t = new VBox(1);
      t.setTranslateX((HardCodedParameters.FactoryStartX+HardCodedParameters.FactoryWidth + 50)/HardCodedParameters.defaultWidth * currentWidth);
      t.setTranslateY(panelCompany().getTranslateY()/HardCodedParameters.defaultHeight * currentHeight);
      t.getChildren().addAll(panelStat(),panelBack());

      rootPane.getChildren().addAll(panelCompany(),t);
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
