package core.view;

import core.model.ModelData;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/*
 * CenterPane
 * 
 * Basic container for the primary 3d view.
 * 
 * Follows Singleton pattern design.
 * 
 */
public class CenterPane extends HBox {

  // global position data binding variables
  private DoubleProperty gXPos = new SimpleDoubleProperty(0);
  private DoubleProperty gYPos = new SimpleDoubleProperty(0);
  private DoubleProperty gZPos = new SimpleDoubleProperty(0);

  private final Group content = new Group();
  private final VBox pallette = new VBox();

  private static CenterPane instance = null;

  private CenterPane() {
    super();
    init();
  }

  public static synchronized CenterPane getInstance() {
    if(instance == null) {
      instance = new CenterPane();
    }

    return instance;
  }

  public void init() {

    // Create and position camera
    PerspectiveCamera camera = new PerspectiveCamera(true);
    camera.getTransforms().addAll(
        new Rotate(-20, Rotate.Y_AXIS),
        new Rotate(-20, Rotate.X_AXIS),
        new Translate(0, 0, -15));

    camera.translateXProperty().bind(gXPos);
    camera.translateYProperty().bind(gYPos);
    camera.translateZProperty().bind(gZPos);

    content.getChildren().add(camera);

    this.getChildren().addAll(new ScrollPane(pallette), content);

    setListeners();
  }

  // primary method for adding new 3d rendered groups to the view.
  public void add(ModelData model) {
    content.getChildren().add(model);
    pallette.getChildren().add(model.getStub());
  }

  public void remove(ModelData model) {
    content.getChildren().remove(model);
    pallette.getChildren().remove(model.getStub());
  }

  public void remove(int idx) {
    content.getChildren().remove(idx);
  }

  public void addLight() {
    PointLight light = new PointLight(Color.AZURE);
    content.getChildren().add(light);
  }

  public void clear() {
    content.getChildren().clear();
  }

  private void setListeners() {

    this.setOnMousePressed(event -> {
      Object target = event.getTarget();

      if(target instanceof MeshView) {
        ModelData model = (ModelData)((MeshView)target).getParent();

        Console.getInstance().bindModel(model);

        model.setAnchorX(event.getX());
        model.setAnchorY(event.getY());
        model.setAnchorAngleX(model.getAngleX().get());
        model.setAnchorAngleY(model.getAngleY().get());
      }
    });

    this.setOnMouseDragged(event -> {
      MouseButton btn = event.getButton();
      Object target = event.getTarget();

      if(target instanceof MeshView) {
        ModelData model = (ModelData)((MeshView)target).getParent();

        if(btn == MouseButton.PRIMARY) {
          model.translate(event.getSceneX(), event.getSceneY());
        }
        else if(btn == MouseButton.SECONDARY) { // Rotations
          model.setAngleX(event.getSceneY());
          model.setAngleY(event.getSceneX());
        }

      }

    });

    content.addEventHandler(ScrollEvent.SCROLL, event -> { // Attach a scroll listener
      double delta = event.getDeltaY() * 0.001; // Get how much scroll was done in Y axis, scaled back for smoother changes

      Object target = event.getTarget();

      if(target instanceof MeshView) {
        ModelData model = (ModelData)((MeshView)target).getParent();
        double scale = model.getScale() * (1 + delta);
        model.setScale(scale);
      }

    });

  }

  public DoubleProperty getGXPos() {
    return gXPos;
  }

  public void setGXPos(DoubleProperty gXPos) {
    this.gXPos = gXPos;
  }

  public DoubleProperty getGYPos() {
    return gYPos;
  }

  public void setGYPos(DoubleProperty gYPos) {
    this.gYPos = gYPos;
  }

  public DoubleProperty getGZPos() {
    return gZPos;
  }

  public void setGZPos(DoubleProperty gZPos) {
    this.gZPos = gZPos;
  }

}
