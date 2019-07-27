package core.view.components;

import javafx.scene.control.TextField;

/**
 * CTextField
 * 
 * extension of text field which provides constraints.
 * maxInputSize determines the number of characters permitted, as well as the physic width of the field.
 * 
 * regex invokes pattern matching, which will prevent invalid characters.
 * 
 * @author richw
 *
 */
public class CTextField extends TextField {

  private final int maxInputSize;
  private final String regex;

  public CTextField(String text, String regex, int maxInputSize) {
    this(text, regex, maxInputSize, true);
  }

  public CTextField(String text, String regex, int maxInputSize, boolean forceUpperCase) {
    super(text);
    this.maxInputSize = maxInputSize;
    this.regex = regex;
    this.setMaxWidth(maxInputSize * 10.5 + 15);
    setConstraintListener(forceUpperCase);
  }

  private void setConstraintListener(boolean forceUpperCase) {
    this.textProperty().addListener((obs, oldVal, newVal) -> {

      if(forceUpperCase) {
        newVal = newVal.toUpperCase(); // force to upper-case.
      }

      // Pattern/input check
      newVal = newVal.replaceAll("[^" + regex + "]", "");

      // Length checks
      if(newVal.length() > maxInputSize) {
        newVal = newVal.substring(0, maxInputSize);
      }

      this.setText(newVal);
    });
  }

}
