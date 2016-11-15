/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package View;

import java.util.HashMap;
import java.util.stream.Collectors;

import Model.PersonModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import specifications.Require.RequireReadService;
import specifications.Require.RequireStatisticsService;
import specifications.Service.ReadService;
import specifications.Service.StatisticsService;
import specifications.Service.ViewerService;
import tools.GraphicalEntity;
import tools.HardCodedParameters;

public class Viewer implements ViewerService, RequireReadService, RequireStatisticsService{
  private static final int spriteSlowDownRate=HardCodedParameters.spriteSlowDownRate;
  private static final double defaultMainWidth=HardCodedParameters.defaultWidth,
                              defaultMainHeight=HardCodedParameters.defaultHeight;
  private ReadService data;
  private StatisticsService statistics;
  
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
  public void init(){

    
  }

  public Parent panelCompany(){
	Pane panel = new Pane();
      panel.setMaxSize(HardCodedParameters.companySizeX,HardCodedParameters.companySizeY);
      panel.setTranslateX(HardCodedParameters.companyTranslateX);
      panel.setTranslateY(HardCodedParameters.companyTranslateY);

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
        if(employee.getPositionOfEntity().x < data.getUserFactory().getHideRoom().getPositionOfEntity().x && employee.getPositionOfEntity().x > data.getUserFactory().getPositionOfEntity().x){
            Label label = new Label(employee.getName());
            label.setTranslateX(employee.getPositionOfEntity().x + employee.getWidth()/8);
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
                    System.out.println("Name: " + employee.getName() +
                                        "\nJob: " + employee.getJob() +
                                        "\nSalary: " + (int) employee.getSalary() + "€");

                }
            });
            panel.getChildren().addAll(label, imageOfEmployee);
        }
    }

    Rectangle hideRooom = new Rectangle(data.getUserFactory().getHideRoom().getWidth(),data.getUserFactory().getHideRoom().getHeight(),Color.RED);
    hideRooom.setTranslateX(data.getUserFactory().getHideRoom().getPositionOfEntity().x);
    hideRooom.setTranslateY(data.getUserFactory().getHideRoom().getPositionOfEntity().y);
    panel.getChildren().add(hideRooom);

    return panel;
  }

  public Parent panelStat(){
        Pane panel = new Pane();
        panel.setStyle("-fx-background-color: grey;");
        panel.setMaxSize(HardCodedParameters.statSizeX,HardCodedParameters.statSizeY);
        panel.setTranslateX(HardCodedParameters.statTranslateX);
        panel.setTranslateY(HardCodedParameters.statTranslateY);
        HashMap<String, Double> salaryByJob = statistics.getSalaryByJob();
        
        ObservableList<PieChart.Data> pieChartData =
        		salaryByJob.entrySet().stream()
        	    .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
        	    .collect(Collectors.toCollection(() -> FXCollections.observableArrayList()));
       
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Company stat");
        chart.setMaxSize(400, 400);
        chart.setTranslateX(0);
        chart.setTranslateY(100);

        panel.getChildren().add(chart);
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
