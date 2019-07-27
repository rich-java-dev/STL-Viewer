package core.view;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import core.model.ModelData;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

/**
 * 
 * MainView
 * 
 * basic container class which ties together all of the UI components which belong to the Scene.
 * 
 * @author richw
 *
 */
public class MainView extends BorderPane {

  /*
   * singleton pattern design. should only be 1 instance of MainView,
   * that can be accessible to any other classes methods via MainView.getInstance()
   * 
   */
  private static MainView instance = null;

  private final MainMenu menu = new MainMenu();
  private final CenterPane view = CenterPane.getInstance();
  private final Console console = Console.getInstance();

  private MainView() {
    super();
    init();
  }

  public static synchronized MainView getInstance() {
    if(instance == null) {
      instance = new MainView();
    }

    return instance;
  }

  private void init() {

    view.addLight();

    // load in default "guitar" stl file from project resource path
    for(String stl : Arrays.asList("guitar", "tennis")) {

      try {
        File defaultStlFile = new File(stl + ".stl");
        if(!defaultStlFile.exists() || !defaultStlFile.isFile()) {
          Files.copy(this.getClass().getResourceAsStream("/" + stl + ".stl"), defaultStlFile.toPath());
        }

        ModelData model = new ModelData(defaultStlFile.getAbsolutePath(), Color.RED, Color.ORANGE);

        model.setIdValue(stl);
        model.setScale(2);
        model.render();
        view.add(model);

      }
      catch(Exception e) {
        e.printStackTrace();
      }
    }

    this.setTop(menu);
    this.setCenter(view);
    this.setBottom(console);

  }

}
