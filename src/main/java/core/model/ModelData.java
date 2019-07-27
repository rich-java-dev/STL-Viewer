package core.model;

import java.io.File;
import com.interactivemesh.jfx.importer.stl.StlMeshImporter;
import core.view.ModelStub;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Point3D;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.MeshView;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

public class ModelData extends Group {

  // use Properties to allow bidirectional binding.
  private final StringProperty id = new SimpleStringProperty();
  private final StringProperty filePath = new SimpleStringProperty();

  private Color pColor = Color.BROWN;
  private Color sColor = Color.WHITE;

  private MeshView mesh = null;
  private ModelStub stub = null;

  private double anchorX = 0;
  private double anchorY = 0;
  private double anchorAngleX = 0;
  private double anchorAngleY = 0;

  private final DoubleProperty xPos = new SimpleDoubleProperty(0);
  private final DoubleProperty yPos = new SimpleDoubleProperty(0);
  private final DoubleProperty zPos = new SimpleDoubleProperty(0);

  private final DoubleProperty angleX = new SimpleDoubleProperty(0);
  private final DoubleProperty angleY = new SimpleDoubleProperty(0);
  private final DoubleProperty scale = new SimpleDoubleProperty(1);

  public ModelData() {
    super();

    init();
  }

  public ModelData(String filePath, Color pColor, Color sColor) {
    super();
    this.filePath.setValue(filePath);
    this.pColor = pColor;
    this.sColor = sColor;

    init();
  }

  public ModelData(String filePath) {
    super();
    this.filePath.setValue(filePath);

    init();
  }

  private void init() {
    Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
    Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);

    xRotate.angleProperty().bind(angleX);
    yRotate.angleProperty().bind(angleY);

    this.getTransforms().addAll(xRotate, yRotate);

    render();
    generateStub();
  }

  public void render() {
    this.getChildren().clear();

    mesh = loadMeshView();
    mesh.scaleXProperty().bind(scale);
    mesh.scaleYProperty().bind(scale);
    mesh.scaleZProperty().bind(scale);

    this.translateXProperty().bind(xPos);
    this.translateYProperty().bind(yPos);
    this.translateZProperty().bind(zPos);

    PhongMaterial material = new PhongMaterial(pColor);
    material.setSpecularColor(sColor);
    material.setSpecularPower(32);
    mesh.setMaterial(material);

    mesh.getTransforms().setAll(new Rotate(38, Rotate.Z_AXIS), new Rotate(20, Rotate.X_AXIS));

    Color ambientColor = Color.rgb(80, 80, 80, 0);
    AmbientLight ambient = new AmbientLight(ambientColor);

    this.getChildren().addAll(mesh);

    this.getChildren().addAll(ambient);

  }

  private MeshView loadMeshView() {
    File file = new File(getFilePath());
    StlMeshImporter importer = new StlMeshImporter();
    importer.read(file);

    return new MeshView(importer.getImport());
  }

  public String getFilePath() {
    return filePath.get();
  }

  public MeshView getMeshView() {
    try {
      Node meshNode = this.getChildren().get(0);
      if(meshNode instanceof MeshView) {
        return (MeshView)meshNode;
      }

    }
    catch(Exception e) {
    }

    return null;
  }

  public void setFilePath(String filePath) {
    this.filePath.setValue(filePath);
  }

  public DoubleProperty getScaleProperty() {
    return scale;
  }

  public void setScale(double scale) {
    this.scale.set(scale);
  }

  public double getScale() {
    return scale.get();
  }

  public void setPos(double xPos, double yPos, double zPos) {
    setX(xPos);
    setY(yPos);
    setZ(zPos);
  }

  public double getX() {
    return xPos.get();
  }

  public void setX(double x) {
    xPos.set(x);
  }

  public double getY() {
    return yPos.get();
  }

  public void setY(double y) {
    yPos.set(y);
  }

  public double getZ() {
    return mesh.getTranslateZ();
  }

  public void setZ(double z) {
    zPos.set(z);
  }

  public Color getpColor() {
    return pColor;
  }

  public void setpColor(Color pColor) {
    this.pColor = pColor;
  }

  public Color getsColor() {
    return sColor;
  }

  public void setsColor(Color sColor) {
    this.sColor = sColor;
  }

  private void generateStub() {
    stub = new ModelStub(this);
  }

  public ModelStub getStub() {
    return stub;
  }

  public void rotate(int angle, Point3D axis) {
    Rotate rotate = new Rotate(angle, axis);
    this.getTransforms().addAll(rotate);
  }

  public void translate(double x, double y) {
    xPos.set(x);
    yPos.set(y);
// Translate translate = new Translate(x, y);
// this.getTransforms().addAll(translate);
  }

  public MeshView getMesh() {
    return mesh;
  }

  public void setMesh(MeshView mesh) {
    this.mesh = mesh;
  }

  public void setAngleX(double angle) {
    angleX.set(anchorAngleX - (anchorY - angle));
  }

  public void setAngleY(double angle) {
    angleY.set(anchorAngleY + (anchorX + angle));
  }

  public double getAnchorX() {
    return anchorX;
  }

  public void setAnchorX(double anchorX) {
    this.anchorX = anchorX;
  }

  public double getAnchorY() {
    return anchorY;
  }

  public void setAnchorY(double anchorY) {
    this.anchorY = anchorY;
  }

  public double getAnchorAngleX() {
    return anchorAngleX;
  }

  public void setAnchorAngleX(double anchorAngleX) {
    this.anchorAngleX = anchorAngleX;
  }

  public double getAnchorAngleY() {
    return anchorAngleY;
  }

  public void setAnchorAngleY(double anchorAngleY) {
    this.anchorAngleY = anchorAngleY;
  }

  public DoubleProperty getAngleX() {
    return angleX;
  }

  public DoubleProperty getAngleY() {
    return angleY;
  }

  public DoubleProperty getXPos() {
    return xPos;
  }

  public DoubleProperty getYPos() {
    return yPos;
  }

  public DoubleProperty getZPos() {
    return zPos;
  }

  public StringProperty getIdProp() {
    return id;
  }

  public StringProperty getFilePathProp() {
    return filePath;
  }

  public void setIdValue(String id) {
    this.id.setValue(id);
  }

}
