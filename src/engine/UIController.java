package engine;

import specifications.Require.RequireDataService;
import specifications.Service.DataService;
import specifications.Service.UIService;
import tools.HardCodedParameters;

import java.util.ArrayList;

/**
 * Created by sokomo on 18/11/16.
 */
public class UIController implements RequireDataService, UIService{

    private DataService data;

    @Override
    public void bindDataService(DataService service) {
        data = service;
    }


    @Override
    public void init() {

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
