package engine;

import java.util.ArrayList;
import java.util.Optional;
import Model.PersonModel;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ButtonBar;
import specifications.Require.RequireDataService;
import specifications.Service.DataService;
import specifications.Service.UIService;
import tools.HardCodedParameters;
import tools.Sound.SOUND;

/**
 * Created by sokomo on 18/11/16.
 */
public class UIController implements RequireDataService, UIService{

    private DataService data;
    private String result;

    @Override
    public void bindDataService(DataService service) {
        data = service;
    }


    @Override
    public void init() {
        result = "none";
    }

    @Override
    public void addLineLog(String line) {
        ArrayList<String> copyOfLogs = data.getCurrentLog();
        if(copyOfLogs.size() >= HardCodedParameters.maxLines){
            copyOfLogs.remove(0);
        }
        copyOfLogs.add(line);
        data.setTotalLog(data.getTotalLog().append(line));
        data.setCurrentLog(copyOfLogs);
    }
    
    public void addEmployeeDialog() {
    	Dialog<ArrayList<String>> dialog = new Dialog<>();
    	dialog.setTitle("Employee manager");
    	dialog.setHeaderText("Add employee");

   
    	// Set the button types.
    	ButtonType loginButtonType = new ButtonType("Ajouter", ButtonData.OK_DONE);
    	dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

    	// Create the username and password labels and fields.
    	GridPane grid = new GridPane();
    	grid.setHgap(10);
    	grid.setVgap(10);
    	grid.setPadding(new Insets(20, 150, 10, 10));

    	TextField employeeName = new TextField();
    	employeeName.setPromptText("Nom");
    	TextField employeeJob = new TextField();
    	employeeJob.setPromptText("Fonction");
    	TextField employeeSalary = new TextField();
    	employeeSalary.setPromptText("Salaire");

    	grid.add(new Label("Nom :"), 0, 0);
    	grid.add(employeeName, 1, 0);
    	grid.add(new Label("Fonction :"), 0, 1);
    	grid.add(employeeJob, 1, 1);
      	grid.add(new Label("Salaire :"), 0, 2);
    	grid.add(employeeSalary, 1, 2);

    	// Enable/Disable login button depending on whether a username was entered.
    	Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
    	loginButton.setDisable(true);    	
    	
    	// Do some validation (using the Java 8 lambda syntax).
    	employeeJob.textProperty().addListener((observable, oldValue, newValue) -> {
    		if (newValue.trim().isEmpty()) {
    			loginButton.setDisable(true);
    		}
    	});
    	employeeSalary.textProperty().addListener((test, test1, test2) -> {
		      
            if (!test2.matches("\\d*")) {
            	employeeSalary.setText(test2.replaceAll("[^\\d.]", ""));
            }
            else{
            	if (test2.trim().isEmpty()) {
					loginButton.setDisable(true);
				}
				else {
					loginButton.setDisable(false);
				}
            }
		});

    	dialog.getDialogPane().setContent(grid);
    	ArrayList<String> newEmployee = new ArrayList<>();
    	
    	// Request focus on the username field by default.
    	Platform.runLater(() -> employeeName.requestFocus());

    	// Convert the result to a username-password-pair when the login button is clicked.
    	dialog.setResultConverter(dialogButton -> {
    	    if (dialogButton == loginButtonType) {
    	    	newEmployee.add(employeeName.getText());
    	    	newEmployee.add(employeeJob.getText());
    	    	newEmployee.add(employeeSalary.getText());
    	        return newEmployee;
    	    }
    	    return null;
    	});

    	Optional<ArrayList<String>> result = dialog.showAndWait();

    	result.ifPresent(employee -> {
    		if (!employee.get(2).isEmpty() && employee.get(2).matches("[0-9]{1,13}(\\.[0-9]*)?")
    				&& employee.get(2).length() > 2 ){
    		
    		  	data.getUserFactory().addNewEmployeePosition(employee.get(0), employee.get(1), Double.parseDouble(employee.get(2)));
    		  	data.setSound(SOUND.EmployeeAdd);
         	    }
	           	else {
	           		Alert alert = new Alert(AlertType.INFORMATION);
        	    	alert.setTitle("Information du budget");
        	    	alert.setHeaderText("Budget insuffisant");
        	    	alert.setContentText("Salaire Faux !");
        	    	alert.showAndWait();
        	    
	          	}
    		
    	});
    }

    @Override
    public void dialogEndProject() {
        result = "check";
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert loose = new Alert(Alert.AlertType.CONFIRMATION);
                loose.setTitle("Information");
                loose.setHeaderText("Le projet à était terminé avec succès");
                loose.setContentText("Que voulez-vous faire ?");
                ButtonType buttonExport = new ButtonType("Exporter Résultats");
                ButtonType buttonReset = new ButtonType("Relancer une simulation");
                ButtonType buttonExit = new ButtonType("Quitter", ButtonBar.ButtonData.CANCEL_CLOSE);
                loose.getButtonTypes().setAll(buttonExport,buttonReset,buttonExit);
                Optional<ButtonType> UserResult = loose.showAndWait();
                if(UserResult.get() == buttonExport){
                    result = "export";
                }
                if(UserResult.get() == buttonReset){
                    result = "reset";
                }
                if(UserResult.get() == buttonExit){
                    result = "exit";
                }
            }
        });
    }

    @Override
    public void dialogEndDay() {
        result = "check";
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert loose = new Alert(Alert.AlertType.CONFIRMATION);
                loose.setTitle("Information");
                loose.setHeaderText("Vous avez dépassé le nombre de jour prévu.");
                loose.setContentText("Que voulez-vous faire ?");
                ButtonType buttonContinue = new ButtonType("Continuer");
                ButtonType buttonReset = new ButtonType("Relancer une simulation");
                ButtonType buttonExport = new ButtonType("Exporter Résultats");
                ButtonType buttonExit = new ButtonType("Quitter", ButtonBar.ButtonData.CANCEL_CLOSE);
                loose.getButtonTypes().setAll(buttonContinue,buttonExport,buttonReset,buttonExit);
                Optional<ButtonType> UserResult = loose.showAndWait();
                if(UserResult.get() == buttonExport){
                    result = "continue";
                }
                if(UserResult.get() == buttonExport){
                    result = "export";
                }
                if(UserResult.get() == buttonReset){
                    result = "reset";
                }
                if(UserResult.get() == buttonExit){
                    result = "exit";
                }
                return;
            }
        });
    }


    @Override
    public void dialogClearExport() {
        result = "clear";
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Alert loose = new Alert(Alert.AlertType.CONFIRMATION);
                loose.setTitle("Information");
                loose.setHeaderText("L'exportation est un succès.");
                loose.setContentText("Que voulez-vous faire ?");
                ButtonType buttonReset = new ButtonType("Relancer une simulation");
                ButtonType buttonExit = new ButtonType("Quitter", ButtonBar.ButtonData.CANCEL_CLOSE);
                loose.getButtonTypes().setAll(buttonReset,buttonExit);
                Optional<ButtonType> UserResult = loose.showAndWait();
                if(UserResult.get() == buttonReset){
                    result = "reset";
                }
                if(UserResult.get() == buttonExit){
                    result = "exit";
                }
                return;
            }
        });
    }

    @Override
    public String getResult() {
        return result;
    }

    @Override
    public void setResult(String result) {
        this.result = result;
    }

    //    if(data.getUserFactory().getEmployeeOfFactory().isEmpty()){
//        engine.onPause();
//        Alert loose = new Alert(AlertType.CONFIRMATION);
//        loose.setTitle("Information");
//        loose.setHeaderText("Vous n'avez plus d'employée.");
//        loose.setContentText("Choisissez ce que vous voulez faire");
//        ButtonType buttonAdd = new ButtonType("Ajouter Employée");
//        ButtonType buttonReset = new ButtonType("Relancer une simulation");
//        ButtonType buttonExit = new ButtonType("Quitter", ButtonBar.ButtonData.CANCEL_CLOSE);
//        loose.getButtonTypes().setAll(buttonAdd,buttonReset,buttonExit);
//        Optional<ButtonType> result = loose.showAndWait();
//        if(result.get() == buttonAdd){
//            System.out.println("Ajouter !!!");
//        }
//        if(result.get() == buttonReset){
//            engine.resetPosition();
//        }
//        if(result.get() == buttonExit){
//            engine.stop();
//        }
//    }
}
