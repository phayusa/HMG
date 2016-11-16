/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import Model.PersonModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import engine.Engine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import specifications.Require.RequireEngineService;
import specifications.Require.RequireReadService;
import specifications.Require.RequireStatisticsService;
import specifications.Service.EngineService;
import specifications.Service.ReadService;
import specifications.Service.StatisticsService;
import specifications.Service.ViewerService;
import tools.GraphicalEntity;
import tools.HardCodedParameters;

public class Viewer implements ViewerService, RequireReadService, RequireStatisticsService,RequireEngineService{
  private static final int spriteSlowDownRate=HardCodedParameters.spriteSlowDownRate;
  private static final double defaultMainWidth=HardCodedParameters.defaultWidth,
                              defaultMainHeight=HardCodedParameters.defaultHeight;
  private ReadService data;
  private StatisticsService statistics;
  private EngineService engine;


  final ImageView pic = new ImageView();
  final Label name = new Label();
  final Label binName = new Label();
  final Label description = new Label();
  private int currentIndex = -1;


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
    panel.setMaxSize(HardCodedParameters.companySizeX,HardCodedParameters.companySizeY);
    panel.setTranslateX(HardCodedParameters.companyTranslateX);
    panel.setTranslateY(HardCodedParameters.companyTranslateY);
    ImageView factoryRepresentation = data.getUserFactory().getImageOfEntity();
    factoryRepresentation.setTranslateX(data.getUserFactory().getPositionOfEntity().x);
    factoryRepresentation.setTranslateY(data.getUserFactory().getPositionOfEntity().y);
    factoryRepresentation.setFitWidth(data.getUserFactory().getWidth());
    factoryRepresentation.setFitHeight(data.getUserFactory().getHeight());

    panel.getChildren().add(factoryRepresentation);
    for (GraphicalEntity Office : data.getUserFactory().getOffices() ) {
      ImageView imageOfOffice = Office.getImageOfEntity();
      imageOfOffice.setTranslateX(Office.getPositionOfEntity().x);
      imageOfOffice.setTranslateY(Office.getPositionOfEntity().y);
      imageOfOffice.setFitWidth(Office.getWidth());
      imageOfOffice.setFitHeight(Office.getHeight());
      panel.getChildren().add(imageOfOffice);
    }
      ArrayList<PersonModel> newArray = new ArrayList<PersonModel>();
    for (PersonModel employee : data.getUserFactory().getEmployeeOfFactory()){
        if(employee.getPositionOfEntity().x < data.getUserFactory().getHideRoom().getPositionOfEntity().x && employee.getPositionOfEntity().x > data.getUserFactory().getPositionOfEntity().x){
            Label label = new Label(employee.getName());
            label.setTranslateX(employee.getPositionOfEntity().x + employee.getWidth()/8);
            label.setTranslateY(employee.getPositionOfEntity().y - employee.getHeight()/2);
            label.setTextFill(Color.BLACK);
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

    Rectangle hideRooom = new Rectangle(data.getUserFactory().getHideRoom().getWidth(),data.getUserFactory().getHideRoom().getHeight(),Color.RED);
    hideRooom.setTranslateX(data.getUserFactory().getHideRoom().getPositionOfEntity().x);
    hideRooom.setTranslateY(data.getUserFactory().getHideRoom().getPositionOfEntity().y);
    panel.getChildren().add(hideRooom);

    ProgressBar progressionOfProject = new ProgressBar(data.getProgressOfWorkInFloat());
    ProgressIndicator progressIndication = new ProgressIndicator(data.getProgressOfWorkInFloat());
    HBox ProgressionBox = new HBox();
    ProgressionBox.setAlignment(Pos.CENTER);
    ProgressionBox.getChildren().addAll(progressionOfProject,progressIndication);
    ProgressionBox.setTranslateX(HardCodedParameters.FactoryStartX + HardCodedParameters.FactoryWidth/2 - 40);
    ProgressionBox.setTranslateY(HardCodedParameters.FactoryHeight - 45);
    panel.getChildren().add(ProgressionBox);

    Label DayPresentation = new Label("Jour "+data.getCurrentDay());
    DayPresentation.setTextFill(Color.BLACK);
    DayPresentation.setFont(new Font("Arial",25));
    DayPresentation.setTranslateX(HardCodedParameters.FactoryStartX+15);
    DayPresentation.setTranslateY(HardCodedParameters.FactoryHeight - 45);
    panel.getChildren().add(DayPresentation);

    return panel;
  }

  public Parent panelStat(){
        Pane panel = new Pane();
        panel.setStyle("-fx-background-color: grey;-fx-border-color: black;");
        panel.setMaxSize(HardCodedParameters.statSizeX,HardCodedParameters.statSizeY);
        panel.setTranslateX(HardCodedParameters.statTranslateX);
        panel.setTranslateY(HardCodedParameters.statTranslateY);
        HashMap<String, Double> salaryByJob = statistics.getSalaryByJob();

	    if (!salaryByJob.isEmpty()) {
	        ObservableList<PieChart.Data> pieChartData =
				salaryByJob.entrySet().stream()
			    .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
			    .collect(Collectors.toCollection(() -> FXCollections.observableArrayList()));

	        pieChartData.forEach(dataChart ->
	        dataChart.nameProperty().bind(
	                Bindings.concat(
	                		dataChart.getName(), " ",
	                		//IF
	                		dataChart.pieValueProperty().intValue() > 1?
	                		//THEN
	                		dataChart.pieValueProperty().intValue() + "%":
	                		//ELSE
	                		"< 1%"
			                )
			        )
			);

	        final PieChart chart = new PieChart(pieChartData);
	        chart.autosize();
	        chart.setTitle("Budget Entreprise");
	        chart.setMaxSize(450, 300);
	        chart.setTranslateY(HardCodedParameters.statSizeY-400);
	        chart.setLabelLineLength(10);
	        chart.setLegendVisible(false);

	        chart.setOnMousePressed(new EventHandler<MouseEvent>() {
	            @Override
	            public void handle(MouseEvent event) {
	            	TextInputDialog dialog = new TextInputDialog(String.valueOf(data.getUserFactory().getBudget()));
	            	dialog.setTitle("Budget entreprise");
	            	dialog.setHeaderText("Entrez un nouveau budget");

	            	Optional<String> result = dialog.showAndWait();
	            	if (result.isPresent() && !result.get().isEmpty() && result.get().matches("[0-9]{1,13}(\\.[0-9]*)?") && result.get().length() > 2 ){
	            	    if (Double.parseDouble(result.get()) < statistics.getTotalSalary()) {
	            	    	Alert alert = new Alert(AlertType.INFORMATION);
	            	    	alert.setTitle("Information du budget");
	            	    	alert.setHeaderText("Budget insuffisant");
	            	    	alert.setContentText("Minimum " + statistics.getTotalSalary() + " €");

	            	    	alert.showAndWait();
	            	    }
	            	    else {
	            	    	 data.getUserFactory().setBudget(Double.parseDouble(result.get()));
	            	    }

	            	}

	            }
	        });

	        panel.getChildren().addAll(chart);
        }
      return panel;
  }


    public Parent panelBack(){
        Pane panel = new Pane();
        panel.setStyle("-fx-background-color: white;-fx-border-color: black;");
        TextArea back = new TextArea();
        back.setMaxSize(HardCodedParameters.backSizeX, HardCodedParameters.backSizeY);
        back.setTranslateX(HardCodedParameters.backTranslateX);
        back.setTranslateY(HardCodedParameters.backTranslateY);
        back.setEditable(false);
        back.setDisable(true);
   
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
