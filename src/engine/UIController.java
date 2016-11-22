package engine;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import specifications.Require.RequireDataService;
import specifications.Service.DataService;
import specifications.Service.UIService;
import tools.HardCodedParameters;

import java.util.ArrayList;
import java.util.Optional;

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

//
//    String nextText = readLineFromSource();
//if ("MISSING".equals(nextText)) {
//        updateMessage("Prompting for missing text");
//        FutureTask<String> futureTask = new FutureTask(
//                new MissingTextPrompt()
//        );
//        Platform.runLater(futureTask);
//        nextText = futureTask.get();
//    }
//...
//    class MissingTextPrompt implements Callable<String> {
//        private TextField textField;
//
//        @Override public String call() throws Exception {
//            final Stage dialog = new Stage();
//            dialog.setScene(createDialogScene());
//            dialog.showAndWait();
//            return textField.getText();
//        }
//  ...
//    }

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
//
//    if(data.getProgressOfWork() >= 100){
//        engine.onPause();
//        Alert loose = new Alert(AlertType.CONFIRMATION);
//        loose.setTitle("Information");
//        loose.setHeaderText("Le projet à était terminé avec succès");
//        loose.setContentText("Que voulez-vous faire ?");
//        ButtonType buttonExport = new ButtonType("Exporter Résultats");
//        ButtonType buttonReset = new ButtonType("Relancer une simulation");
//        ButtonType buttonExit = new ButtonType("Quitter", ButtonBar.ButtonData.CANCEL_CLOSE);
//        loose.getButtonTypes().setAll(buttonExport,buttonReset,buttonExit);
//        Optional<ButtonType> result = loose.showAndWait();
//        if(result.get() == buttonReset){
//            engine.resetPosition();
//        }
//        if(result.get() == buttonExit){
//            engine.stop();
//        }
//        if(result.get() == buttonExport){
//            System.out.println("Exporter");
//        }
//    }
}
