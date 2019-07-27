package core.view;

import core.model.ModelData;
import core.view.components.CTextField;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.converter.NumberStringConverter;

/**
 * 
 * Console
 * 
 * Basic display of model data, including translational, rotational, and scale values
 * 
 * The values are Bi-directionally bounded to the models, which allows easily reading and updating of values.
 * 
 * @author richw
 *
 */
public class Console extends GridPane {

  private static Console instance = null;

  private Label idLabel = new Label("id:");
  private TextField idField = new TextField();

  private Label fileLabel = new Label("File:");
  private TextField fileField = new TextField();

  private Button fileButton = new Button("...");

  // local Position values.
  private CTextField xPosField = new CTextField("", "0-9\\.\\-", 10);
  private CTextField yPosField = new CTextField("", "0-9\\.\\-", 10);
  private CTextField zPosField = new CTextField("", "0-9\\.\\-", 10);

  private CTextField angleXField = new CTextField("", "0-9\\.\\-", 10);
  private CTextField angleYField = new CTextField("", "0-9\\.\\-", 10);

  private CTextField scaleField = new CTextField("", "0-9\\.\\-", 10);

  // Global Positioning values.
  private CTextField gXField = new CTextField("", "0-9\\.\\-", 10);
  private CTextField gYField = new CTextField("", "0-9\\.\\-", 10);
  private CTextField gZField = new CTextField("", "0-9\\.\\-", 10);

  private Button homeButton = new Button("home");

  public static synchronized Console getInstance() {
    if(instance == null) {
      instance = new Console();
    }

    return instance;
  }

  private Console() {
    super();
    init();
  }

  private void init() {

    this.setHgap(2);
    this.setVgap(2);

    this.add(idLabel, 1, 1);
    this.add(idField, 2, 1);

    this.add(fileLabel, 1, 2);
    this.add(fileField, 2, 2);
    this.add(fileButton, 3, 2);

    this.add(new Label("Position:"), 4, 1);
    this.add(new Label("X:"), 4, 2);
    this.add(new Label("Y:"), 4, 3);
    this.add(new Label("Z:"), 4, 4);

    this.add(xPosField, 5, 2);
    this.add(yPosField, 5, 3);
    this.add(zPosField, 5, 4);

    this.add(new Label("Angle"), 6, 1);
    this.add(new Label("angle X"), 6, 2);
    this.add(new Label("angle Y"), 6, 3);

    this.add(angleXField, 7, 2);
    this.add(angleYField, 7, 3);

    this.add(new Label("Scale"), 8, 1);
    this.add(scaleField, 8, 2);

    this.add(new Label("Global Pos:"), 10, 1);
    this.add(new Label("X:"), 10, 2);
    this.add(new Label("Y:"), 10, 3);
    this.add(new Label("Z:"), 10, 4);

    this.add(gXField, 11, 2);
    this.add(gYField, 11, 3);
    this.add(gZField, 11, 4);

    this.add(new Label("Auto Home"), 12, 1);
    this.add(homeButton, 12, 2);

    CenterPane pane = CenterPane.getInstance();

    gXField.textProperty().bindBidirectional(pane.getGXPos(), new NumberStringConverter());
    gYField.textProperty().bindBidirectional(pane.getGYPos(), new NumberStringConverter());
    gZField.textProperty().bindBidirectional(pane.getGZPos(), new NumberStringConverter());

    setListeners();
  }

  private void setListeners() {

    homeButton.setOnAction(a -> {
      gXField.setText("0");
      gYField.setText("0");
      gZField.setText("0");

    });
  }

  private void unbindAll() {
    scaleField.textProperty().unbind();
    idField.textProperty().unbind();
    fileField.textProperty().unbind();

    angleXField.textProperty().unbind();
    angleYField.textProperty().unbind();
    xPosField.textProperty().unbind();
    yPosField.textProperty().unbind();
    zPosField.textProperty().unbind();
  }

  // Bind the Properties of the model to the Console, such that they are bi-directional.
  public void bindModel(ModelData model) {
    unbindAll();
    scaleField.textProperty().bindBidirectional(model.getScaleProperty(), new NumberStringConverter());
    idField.textProperty().bindBidirectional(model.getIdProp());
    fileField.textProperty().bindBidirectional(model.getFilePathProp());

    angleXField.textProperty().bindBidirectional(model.getAngleX(), new NumberStringConverter());
    angleYField.textProperty().bindBidirectional(model.getAngleY(), new NumberStringConverter());
    xPosField.textProperty().bindBidirectional(model.getXPos(), new NumberStringConverter());
    yPosField.textProperty().bindBidirectional(model.getYPos(), new NumberStringConverter());
    zPosField.textProperty().bindBidirectional(model.getZPos(), new NumberStringConverter());
  }

}
