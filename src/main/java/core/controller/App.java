package core.controller;

import core.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

  /*
   * Program Entry Point. The UI is initialized when the primaryStage (Main window), has its content set (Scene).
   * 
   * JavaFX uses a "Play/Theater" analogy:
   * The Stage is the viewable window, with Scenes that are the primary content to be changed.
   * 
   */
  @Override
  public void start(Stage stage) throws Exception {
    // TODO Auto-generated method stub

    // Set content of the Scene, and dimensions of screen.
    MainView pane = MainView.getInstance();
    Scene scene = new Scene(pane, 1080, 720);

    // set Style of GUI components to style.css found in resources folder.
    scene.getStylesheets().addAll(new String[] {"/style.css"});

    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}
