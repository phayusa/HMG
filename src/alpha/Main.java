/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: alpha/Main.java 2015-03-11 buixuan.
 * ******************************************************/
package alpha;

import View.Viewer;
import data.DataOfWorld;
import engine.Engine;
import engine.StatisticsController;
import engine.UIController;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import specifications.Service.DataService;
import specifications.Service.EngineService;
import specifications.Service.StatisticsService;
import specifications.Service.UIService;
import specifications.Service.ViewerService;
import tools.HardCodedParameters;
import tools.Sound;
import tools.User_Entry;

//import algorithm.RandomWalker;

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
  private boolean firstStart = true;


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
    ((Viewer)viewer).bindUiService(Ui);
    ((Viewer) viewer).bindEngineService(engine);
    ((Engine) engine).bindUiService(Ui);


   
    
    launch(args);
  }

  @Override 
  public void start(Stage stage) {

	  if (firstStart){
		  if (!Ui.getStartPanel(stage)) {
			System.exit(0);
		  }else {
			data.init();
		    engine.init();
		    statistics.init();
		    viewer.init();
		    Ui.init();
	
		    firstStart = false;
		  }
	  }
	  System.out.println(data.getCurrentDay());
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
//	          if (event.getCode()==KeyCode.LEFT) engine.releaseHeroesCommand(User_Entry.COMMAND.LEFT);
//	          if (event.getCode()==KeyCode.RIGHT) engine.releaseHeroesCommand(User_Entry.COMMAND.RIGHT);
//	          if (event.getCode()==KeyCode.UP) engine.releaseHeroesCommand(User_Entry.COMMAND.UP);
//	          if (event.getCode()==KeyCode.DOWN) engine.releaseHeroesCommand(User_Entry.COMMAND.DOWN);
          event.consume();
        }
    });
//	    scene.widthProperty().addListener(new ChangeListener<Number>() {
//	        @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
//	          viewer.setMainWindowWidth(newSceneWidth.doubleValue());
//	        }
//	    });
//	    scene.heightProperty().addListener(new ChangeListener<Number>() {
//	        @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
//	          viewer.setMainWindowHeight(newSceneHeight.doubleValue());
//	        }
//	    });

    
    stage.setScene(scene);
    stage.setWidth(HardCodedParameters.defaultWidth);
    stage.setHeight(HardCodedParameters.defaultHeight);
    stage.setResizable(false);
//	    stage.setMaximized(true);
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
          case SPACE:
            data.setProgressionOfWork(data.getProgressOfWork() + 1);
            break;
          case V:
            data.setCurrentDay(data.getCurrentDay() + 1);
            break;
          case F:
            engine.RemoveFirst();
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
        switch (data.getSound()){
	        case EmployeeLeave:
	          new MediaPlayer(new Media(getHostServices().getDocumentBase()+"Ressource/sound/OUH.mp3")).play();
	          break;
	        case EmployeeAdd:
	          new MediaPlayer(new Media(getHostServices().getDocumentBase()+"Ressource/sound/hello.mp3")).play();

	          break;
	        case Keyboard:
	          new MediaPlayer(new Media(getHostServices().getDocumentBase()+"Ressource/sound/keyboard.mp3")).play();
	          break;
	        default:
	          break;
	    }
	    data.setSound(Sound.SOUND.None);
      }
    };
    timer.start();
  }
}
