/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: alpha/Main.java 2015-03-11 buixuan.
 * ******************************************************/
package alpha;

import View.Viewer;
//import algorithm.RandomWalker;
import data.DataOfWorld;
import engine.Engine;
import engine.StatisticsController;
import engine.UIController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import specifications.Service.*;
import tools.HardCodedParameters;
import tools.User_Entry;

public class Main extends Application{
  //---HARD-CODED-PARAMETERS---//
  private static String fileName = HardCodedParameters.defaultParamFileName;

  //---VARIABLES---//
  private static DataService data;
  private static EngineService engine;
  private static StatisticsService statistics;
  private static ViewerService viewer;
  private static AnimationTimer timer;
  private static UIService Ui;

  //---EXECUTABLE---//
  public static void main(String[] args) {
    //readArguments(args);

    data = new DataOfWorld();
    engine = new Engine();
    statistics = new StatisticsController();
    Ui = new UIController();
    viewer = new Viewer();

    ((Engine)engine).bindDataService(data);
    ((Engine)engine).bindStatisticsService(statistics);
    ((StatisticsController)statistics).bindDataService(data);
    ((UIController)Ui).bindDataService(data);
    ((Viewer)viewer).bindStatisticsService(statistics);
    ((Viewer)viewer).bindReadService(data);
    ((Viewer) viewer).bindEngineService(engine);
    ((Engine) engine).bindUiService(Ui);


    data.init();
    engine.init();
    statistics.init();
    viewer.init();
    Ui.init();
    
    launch(args);
  }

  @Override public void start(Stage stage) {
    final Scene scene = new Scene(((Viewer)viewer).getMainPanel());
       
    scene.setFill(Color.CORNFLOWERBLUE);

    scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
      @Override
        public void handle(KeyEvent event) {
          switch (event.getCode()){
            case LEFT:
              engine.releaseHeroesCommand(User_Entry.COMMAND.LEFT);
            case RIGHT:
              engine.releaseHeroesCommand(User_Entry.COMMAND.RIGHT);
            case UP:
              engine.releaseHeroesCommand(User_Entry.COMMAND.UP);
            case DOWN:
              engine.releaseHeroesCommand(User_Entry.COMMAND.DOWN);
          }
//          if (event.getCode()==KeyCode.LEFT) engine.releaseHeroesCommand(User_Entry.COMMAND.LEFT);
//          if (event.getCode()==KeyCode.RIGHT) engine.releaseHeroesCommand(User_Entry.COMMAND.RIGHT);
//          if (event.getCode()==KeyCode.UP) engine.releaseHeroesCommand(User_Entry.COMMAND.UP);
//          if (event.getCode()==KeyCode.DOWN) engine.releaseHeroesCommand(User_Entry.COMMAND.DOWN);
          event.consume();
        }
    });
//    scene.widthProperty().addListener(new ChangeListener<Number>() {
//        @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
//          viewer.setMainWindowWidth(newSceneWidth.doubleValue());
//        }
//    });
//    scene.heightProperty().addListener(new ChangeListener<Number>() {
//        @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
//          viewer.setMainWindowHeight(newSceneHeight.doubleValue());
//        }
//    });
    

    stage.setScene(scene);
    stage.setWidth(HardCodedParameters.defaultWidth);
    stage.setHeight(HardCodedParameters.defaultHeight);
    stage.setResizable(false);
//    stage.setMaximized(true);
    stage.setOnShown(new EventHandler<WindowEvent>() {
      @Override public void handle(WindowEvent event) {
        engine.start();
        statistics.start();
      }
    });
    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override public void handle(WindowEvent event) {
        engine.stop();
        statistics.stop();
      }
    });

    scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
      @Override
      public void handle(KeyEvent event) {
        switch (event.getCode()){
          case LEFT:
            engine.setHeroesCommand(User_Entry.COMMAND.LEFT);
            break;
          case RIGHT:
            engine.setHeroesCommand(User_Entry.COMMAND.RIGHT);
            break;
          case UP:
            engine.setHeroesCommand(User_Entry.COMMAND.UP);
            break;
          case DOWN:
            engine.setHeroesCommand(User_Entry.COMMAND.DOWN);
            break;
          case R:
            engine.resetPosition();
            statistics.resetStat();
            break;
          case L:
            engine.allLeave();
            break;
          case P:
            engine.onPause();
            break;
          case T:
            Ui.addLineLog("TATATA");
            break;
          case Q:
          case ESCAPE:
            System.exit(0);
            break;


        }
        event.consume();
      }
    });

    stage.setTitle("Company Manager");
    stage.show();
    
    timer = new AnimationTimer() {
      @Override public void handle(long l) {
        scene.setRoot(((Viewer)viewer).getMainPanel());
//        switch (data.getSoundEffect()){
//          case PhantomDestroyed:
//            new MediaPlayer(new Media(getHostServices().getDocumentBase()+"src/sound/waterdrip.mp3")).play();
//            break;
//          case HeroesGotHit:
//            new MediaPlayer(new Media(getHostServices().getDocumentBase()+"src/sound/waterdrip.mp3")).play();
//            break;
//          default:
//            break;
//        }
      }
    };
    timer.start();
  }

//  //---ARGUMENTS---//
//  private static void readArguments(String[] args){
//    if (args.length>0 && args[0].charAt(0)!='-'){
//      System.err.println("Syntax error: use option -h for help.");
//      return;
//    }
//    for (int i=0;i<args.length;i++){
//      if (args[i].charAt(0)=='-'){
//	if (args[i+1].charAt(0)=='-'){
//	  System.err.println("Option "+args[i]+" expects an argument but received none.");
//	  return;
//	}
//	switch (args[i]){
//	  case "-inFile":
//	    fileName=args[i+1];
//	    break;
//	  case "-h":
//	    System.out.println("Options:");
//	    System.out.println(" -inFile FILENAME: (UNUSED AT THE MOMENT) set file name for input parameters. Default name is"+HardCodedParameters.defaultParamFileName+".");
//	    break;
//	  default:
//	    System.err.println("Unknown option "+args[i]+".");
//	    return;
//	}
//	i++;
//      }
//    }
//  }
}
