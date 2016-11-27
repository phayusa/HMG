/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package View;

import java.util.ArrayList;

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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import specifications.Require.RequireEngineService;
import specifications.Require.RequireReadService;
import specifications.Require.RequireStatisticsService;
import specifications.Require.RequireUiService;
import specifications.Service.EngineService;
import specifications.Service.ReadService;
import specifications.Service.StatisticsService;
import specifications.Service.UIService;
import specifications.Service.ViewerService;
import tools.GraphicalEntity;
import tools.HardCodedParameters;

public class Viewer implements ViewerService, RequireReadService, RequireStatisticsService, RequireEngineService, RequireUiService{
  private double ScreenWidth,ScreenHeight;
  private ReadService data;
  private StatisticsService statistics;
  private EngineService engine;
  private UIService UIService;
  
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
	public void bindUiService(UIService service) {
		UIService = service;
	}

  @Override
  public void init(){

    
  }

  public Parent panelCompany(){

	Pane panel = new Pane();
    panel.setMaxSize(HardCodedParameters.companySizeX/HardCodedParameters.defaultWidth * ScreenWidth,HardCodedParameters.companySizeY/HardCodedParameters.defaultHeight * ScreenHeight);
    panel.setTranslateX(HardCodedParameters.companyTranslateX/HardCodedParameters.defaultWidth * ScreenWidth);
    panel.setTranslateY(HardCodedParameters.companyTranslateY/HardCodedParameters.defaultHeight * ScreenHeight);
    ImageView factoryRepresentation = data.getUserFactory().getImageOfEntity();
    factoryRepresentation.setTranslateX(data.getUserFactory().getPositionOfEntity().x/HardCodedParameters.defaultWidth * ScreenWidth);
    factoryRepresentation.setTranslateY(data.getUserFactory().getPositionOfEntity().y/HardCodedParameters.defaultHeight * ScreenHeight);
    factoryRepresentation.setFitWidth(data.getUserFactory().getWidth()/HardCodedParameters.defaultWidth * ScreenWidth);
    factoryRepresentation.setFitHeight(data.getUserFactory().getHeight()/HardCodedParameters.defaultHeight * ScreenHeight);
    factoryRepresentation.setOnMousePressed(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
        	UIService.addEmployeeDialog();
        }
    });
    

    panel.getChildren().add(factoryRepresentation);
    for (GraphicalEntity Office : data.getUserFactory().getOffices() ) {
      ImageView imageOfOffice = Office.getImageOfEntity();
      imageOfOffice.setTranslateX(Office.getPositionOfEntity().x/HardCodedParameters.defaultWidth * ScreenWidth);
      imageOfOffice.setTranslateY(Office.getPositionOfEntity().y/HardCodedParameters.defaultHeight * ScreenHeight);
      imageOfOffice.setFitWidth(Office.getWidth()/HardCodedParameters.defaultWidth * ScreenWidth);
      imageOfOffice.setFitHeight(Office.getHeight()/HardCodedParameters.defaultHeight * ScreenHeight);
      panel.getChildren().add(imageOfOffice);
    }
      ArrayList<PersonModel> newArray = new ArrayList<PersonModel>();
    for (PersonModel employee : data.getUserFactory().getEmployeeOfFactory()){
        if(employee.getPositionOfEntity().x < data.getUserFactory().getHideRoom().getPositionOfEntity().x && employee.getPositionOfEntity().x > data.getUserFactory().getPositionOfEntity().x){
            Label label = new Label(employee.getName());
            label.setTranslateX((employee.getPositionOfEntity().x + employee.getWidth()/8)/HardCodedParameters.defaultWidth * ScreenWidth);
            label.setTranslateY((employee.getPositionOfEntity().y - employee.getHeight()/2)/HardCodedParameters.defaultHeight * ScreenHeight);
            label.setTextFill(Color.BLACK);
            ImageView imageOfEmployee = employee.getCurrentSprite();
            imageOfEmployee.setTranslateX(employee.getPositionOfEntity().x/HardCodedParameters.defaultWidth * ScreenWidth);
            imageOfEmployee.setTranslateY(employee.getPositionOfEntity().y/HardCodedParameters.defaultHeight * ScreenHeight);
            imageOfEmployee.setFitWidth(employee.getWidth()/HardCodedParameters.defaultWidth * ScreenWidth);
            imageOfEmployee.setFitHeight(employee.getHeight()/HardCodedParameters.defaultHeight * ScreenHeight);
            panel.getChildren().addAll(label, imageOfEmployee);

        }
        if(employee.isInFactory())
            newArray.add(employee);
        else{
            if(employee.getPositionOfEntity().x > data.getUserFactory().getPositionOfEntity().x)
                newArray.add(employee);
        }
    }
    engine.ClearEmployeeOfNotInAction(newArray);

    ImageView hideRooom = data.getUserFactory().getHideRoom().getImageOfEntity();
    hideRooom.setTranslateX(data.getUserFactory().getHideRoom().getPositionOfEntity().x/HardCodedParameters.defaultWidth * ScreenWidth);
    hideRooom.setTranslateY(data.getUserFactory().getHideRoom().getPositionOfEntity().y/HardCodedParameters.defaultHeight * ScreenHeight);
    hideRooom.setFitWidth(data.getUserFactory().getHideRoom().getWidth()/HardCodedParameters.defaultWidth * ScreenWidth);
    hideRooom.setFitHeight(data.getUserFactory().getHideRoom().getHeight()/HardCodedParameters.defaultHeight * ScreenHeight);
    panel.getChildren().add(hideRooom);

    ProgressBar progressionOfProject = new ProgressBar(data.getProgressOfWorkInFloat());
    ProgressIndicator progressIndication = new ProgressIndicator(data.getProgressOfWorkInFloat());
    HBox ProgressionBox = new HBox();
    ProgressionBox.setAlignment(Pos.CENTER);
    ProgressionBox.getChildren().addAll(progressionOfProject,progressIndication);
    ProgressionBox.setTranslateX((HardCodedParameters.FactoryStartX + HardCodedParameters.FactoryWidth/2.0 - 40.0) / HardCodedParameters.defaultWidth * ScreenWidth);
    ProgressionBox.setTranslateY((HardCodedParameters.FactoryHeight - 45.0)/HardCodedParameters.defaultHeight * ScreenHeight);
    panel.getChildren().add(ProgressionBox);

    Label DayPresentation = new Label("Jour "+data.getCurrentDay()+"/"+data.getNumberOfDaysForProject());
    DayPresentation.setTextFill(Color.BLACK);
    DayPresentation.setFont(new Font("Arial",25.0));
    DayPresentation.setTranslateX((HardCodedParameters.FactoryStartX+15.0)/HardCodedParameters.defaultWidth * ScreenWidth);
    DayPresentation.setTranslateY((HardCodedParameters.FactoryHeight - 45.0)/HardCodedParameters.defaultHeight * ScreenHeight);
    panel.getChildren().add(DayPresentation);


    return panel;
  }

  public Parent panelStat(){
        Pane panel = new Pane();
        panel.setStyle("-fx-background-color: grey;-fx-border-color: black;");
        panel.setMinWidth(HardCodedParameters.statSizeX/HardCodedParameters.defaultWidth * ScreenWidth);
        panel.setMinHeight(HardCodedParameters.statSizeY/HardCodedParameters.defaultHeight * ScreenHeight);
        panel.setTranslateX(HardCodedParameters.statTranslateX/HardCodedParameters.defaultWidth * ScreenWidth);
        panel.setTranslateY(HardCodedParameters.statTranslateY/HardCodedParameters.defaultHeight * ScreenHeight);
        
        final PieChart chartStatic = data.getEstimateChart();
	  	chartStatic.autosize();
	  	chartStatic.setTitle("Budget estimé");
	  	chartStatic.setMaxSize(400.0/HardCodedParameters.defaultWidth * ScreenWidth, 200/HardCodedParameters.defaultHeight*ScreenHeight);
	  	chartStatic.setTranslateY(20.0/HardCodedParameters.defaultHeight*ScreenHeight);
	  	chartStatic.setLabelLineLength(10.0/HardCodedParameters.defaultWidth*ScreenWidth);
	  	chartStatic.setLegendVisible(false);

	  	panel.getChildren().addAll(chartStatic);
	    	
        final PieChart chart = data.getSimulateChart();
        chart.autosize();
        chart.setTitle("Budget simulé");
        chart.setMaxSize(400.0/HardCodedParameters.defaultWidth*ScreenWidth, 200/HardCodedParameters.defaultHeight*ScreenHeight);
        chart.setTranslateY((HardCodedParameters.statSizeY-250.0)/HardCodedParameters.defaultHeight*ScreenHeight);
        chart.setLabelLineLength(10.0/HardCodedParameters.defaultWidth*ScreenWidth);
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

        Label back = new Label(data.getLogsInString(ScreenHeight));
        back.setMinWidth(HardCodedParameters.backSizeX/HardCodedParameters.defaultWidth*ScreenWidth);
        back.setMinHeight(HardCodedParameters.backSizeY/HardCodedParameters.defaultHeight*ScreenHeight);

        panel.setTranslateX(HardCodedParameters.backTranslateX/HardCodedParameters.defaultWidth*ScreenWidth);
        panel.setTranslateY(HardCodedParameters.backTranslateY/HardCodedParameters.defaultHeight*ScreenHeight);
        panel.getChildren().addAll(back);
        return panel;
    }

  @Override
    public Parent getMainPanel(double ScreenWidth, double ScreenHeight) {
        AnchorPane rootPane = new AnchorPane();
        this.ScreenWidth = ScreenWidth;
        this.ScreenHeight = ScreenHeight;
      AnchorPane.setTopAnchor(panelCompany(),0.0);
      AnchorPane.setLeftAnchor(panelCompany(),0.0);

      AnchorPane.setBottomAnchor(panelBack(),0.0);
      AnchorPane.setRightAnchor(panelBack(),0.0);

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
