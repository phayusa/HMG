/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: alpha/Main.java 2015-03-11 buixuan.
 * ******************************************************/
package alpha;

import java.io.File;

import View.Viewer;
//import algorithm.RandomWalker;
import data.DataOfWorld;
import engine.Engine;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import specifications.Service.DataService;
import specifications.Service.EngineService;
import specifications.Service.ViewerService;
import tools.CSVReader;
import tools.HardCodedParameters;
import tools.User_Entry;

public class Main extends Application{
  //---HARD-CODED-PARAMETERS---//
  private static String fileName = HardCodedParameters.defaultParamFileName;

  //---VARIABLES---//
  private static DataService data;
  private static EngineService engine;
  private static ViewerService viewer;
  private static AnimationTimer timer;

  //---EXECUTABLE---//
  public static void main(String[] args) {
    //readArguments(args);

    data = new DataOfWorld();
    engine = new Engine();
    viewer = new Viewer();

    ((Engine)engine).bindDataService(data);
    ((Viewer)viewer).bindReadService(data);

    data.init();
    engine.init();
    viewer.init();
    
    launch(args);
  }

  @Override public void start(Stage stage) {
    final Scene scene = new Scene(((Viewer)viewer).getPanel());

    scene.setFill(Color.CORNFLOWERBLUE);
    scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
      @Override
        public void handle(KeyEvent event) {
          if (event.getCode()==KeyCode.LEFT) engine.setHeroesCommand(User_Entry.COMMAND.LEFT);
          if (event.getCode()==KeyCode.RIGHT) engine.setHeroesCommand(User_Entry.COMMAND.RIGHT);
          if (event.getCode()==KeyCode.UP) engine.setHeroesCommand(User_Entry.COMMAND.UP);
          if (event.getCode()==KeyCode.DOWN) engine.setHeroesCommand(User_Entry.COMMAND.DOWN);
          if (event.getCode() == KeyCode.Q || event.getCode() == KeyCode.ESCAPE ) { System.exit(0);}
          event.consume();
        }
    });
    scene.setOnKeyReleased(new EventHandler<KeyEvent>(){
      @Override
        public void handle(KeyEvent event) {
          if (event.getCode()==KeyCode.LEFT) engine.releaseHeroesCommand(User_Entry.COMMAND.LEFT);
          if (event.getCode()==KeyCode.RIGHT) engine.releaseHeroesCommand(User_Entry.COMMAND.RIGHT);
          if (event.getCode()==KeyCode.UP) engine.releaseHeroesCommand(User_Entry.COMMAND.UP);
          if (event.getCode()==KeyCode.DOWN) engine.releaseHeroesCommand(User_Entry.COMMAND.DOWN);
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
    stage.setOnShown(new EventHandler<WindowEvent>() {
      @Override public void handle(WindowEvent event) {
        engine.start();
      }
    });
    stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
      @Override public void handle(WindowEvent event) {
        engine.stop();
      }
    });
    
   
    stage.show();
    
    timer = new AnimationTimer() {
      @Override public void handle(long l) {
        scene.setRoot(((Viewer)viewer).getPanel());
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
