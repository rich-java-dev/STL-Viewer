package core.view;

import core.model.ModelData;
import javafx.event.ActionEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * 
 * ModelStub
 * 
 * Basic stub displayed on left-hand side, in a list of rendered components.
 * 
 * @author richw
 *
 */
public class ModelStub extends GridPane {

  private Label idLabel = new Label("id:");
  private TextField idField = new TextField();

  ModelData parent = null;

  public ModelStub(ModelData parent) {
    super();
    this.parent = parent;

    init();
  }

  private void init() {
    idField.setEditable(false);
    idField.setText(parent.getIdProp().get());

    this.setHgap(2);
    this.setVgap(2);

    this.add(idLabel, 1, 1);
    this.add(idField, 2, 1);

    addListeners();
  }

  private void addListeners() {

    this.setOnMousePressed(a -> {
      Console.getInstance().bindModel(parent);
    });

    // Context (Right click menu over the items
    final ContextMenu context = new ContextMenu();
    final MenuItem deleteItem = new MenuItem("delete");

    deleteItem.setOnAction(a -> removeModel(a));

    context.getItems().add(deleteItem);

    this.setOnContextMenuRequested((event) -> {
      context.setX(event.getScreenX());
      context.setY(event.getScreenY());
      context.show(this.getScene().getWindow());
    });

  }

  private void removeModel(ActionEvent action) {
    CenterPane pane = CenterPane.getInstance();
    pane.remove(this.parent);
  }

}
