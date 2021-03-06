package uk.ac.cam.cl.gui;

import java.util.Calendar;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;

import uk.ac.cam.cl.Main;
import uk.ac.cam.cl.data.DataManager;

/**
 * Represents the grid pane displayed at the bottom of the app including the time selection slider
 *
 * @author Max Campman
 */
public class BottomBar extends GridPane {
  private Main parent;
  private DataManager dm = DataManager.getInstance();

  /**
   * Creates a new bottom bar to be used with the given main class
   *
   * @param parent the main class
   */
  public BottomBar(Main parent) {
    super();
    this.parent = parent;
    setId("bottom-bar");

    Slider daySelect = new Slider();
    daySelect.setId("day-select");
    daySelect.setMin(0);
    daySelect.setMax(6);
    daySelect.setPrefWidth(300.0);
    daySelect.setValue(dm.getDay());
    daySelect.setBlockIncrement(1);
    daySelect.setMajorTickUnit(1);
    daySelect.setMinorTickCount(0);
    daySelect.setShowTickLabels(true);
    daySelect.setShowTickMarks(true);
    daySelect.setSnapToTicks(true);
    daySelect.setLabelFormatter(
        new StringConverter<Double>() {

          @Override
          public String toString(Double f) {
            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            switch ((int) (Math.round(f) + day - Calendar.SUNDAY) % 7) {
              case 0:
                return "Sun";
              case 1:
                return "Mon";
              case 2:
                return "Tue";
              case 3:
                return "Wed";
              case 4:
                return "Thu";
              case 5:
                return "Fri";
              case 6:
                return "Sat";
              default:
                return "";
            }
          }

          @Override
          public Double fromString(String s) {
            return 0.0;
          }
        });

    daySelect
        .valueProperty()
        .addListener(
            new ChangeListener<Number>() {
              @Override
              public void changed(
                  ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                dm.setDay(newValue.intValue());
              }
            });

    GridPane.setHalignment(daySelect, HPos.CENTER);
    setAlignment(Pos.CENTER);
    add(daySelect, 0, 0);
  }
}
