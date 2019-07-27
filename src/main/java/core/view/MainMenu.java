package core.view;

import java.io.File;
import java.util.Arrays;
import core.model.ModelData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

public class MainMenu extends MenuBar {

  // MENUS
  private final Menu fileMenu = new Menu("_File");
  private final Menu editMenu = new Menu("_Edit");
  private final Menu runMenu = new Menu();
  private final Menu exportMenu = new Menu("_Export");
  private final Menu helpMenu = new Menu("_Help");

  // file menu items
  private final MenuItem openMI = new MenuItem("open");
  private final MenuItem saveMI = new MenuItem("save");

  // edit menu items

  // run menu items

  // export menu items

  // help menu items
  private final MenuItem aboutMI = new MenuItem("about");

  public MainMenu() {
    super();
    init();
  }

  // Attach Items to proper menus, and add event listeners
  private void init() {

    // enable Mnemonics on the menus
    for(Menu menu : Arrays.asList(fileMenu, editMenu, runMenu, helpMenu, exportMenu))
      menu.setMnemonicParsing(true);

    fileMenu.getItems().addAll(openMI, saveMI);
    editMenu.getItems().addAll();
    runMenu.getItems().addAll();
    exportMenu.getItems().addAll();
    helpMenu.getItems().addAll(aboutMI);

    this.getMenus().addAll(fileMenu, editMenu, exportMenu, helpMenu);
    setListeners();
  }

  // add event handlers to the menu items, such that when they are clicked/selected, an event is trigger.
  private void setListeners() {

    openMI.setOnAction((a) -> {
      FileChooser chooser = new FileChooser();
      chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
      File loadFile = chooser.showOpenDialog(this.getScene().getWindow());

      if(loadFile != null) {
        ModelData model = new ModelData(loadFile.getAbsolutePath(), Color.BROWN, Color.AQUAMARINE);
        model.setPos(20, 20, 0);
        model.render();
        CenterPane.getInstance().add(model);

      }

    });

    saveMI.setOnAction(a -> {
      StringBuffer message = new StringBuffer();
      message.append("This is just a demo!\n");
      message.append("To use this feature, please finish developing this project!\n");

      Dialog helpDialog = new Dialog<String>();
      helpDialog.setContentText(message.toString());
      helpDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
      helpDialog.showAndWait();

    });

    aboutMI.setOnAction(a -> {
      StringBuffer message = new StringBuffer();
      message.append("This is a generic help dialog.\n");
      message.append("This purpose of this small app is to demonstrate basic software development techniques.\n");

      Dialog helpDialog = new Dialog<String>();
      helpDialog.setContentText(message.toString());
      helpDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
      helpDialog.showAndWait();

    });

  }

}
