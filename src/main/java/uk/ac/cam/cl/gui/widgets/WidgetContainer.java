package uk.ac.cam.cl.gui.widgets;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import uk.ac.cam.cl.Main;
import uk.ac.cam.cl.data.Unit;

/**
 * Wraps a Widget with a border.
 *
 * @author Ben Cole
 */
public class WidgetContainer extends BorderPane {
  private Widget widget;
  private Integer position;
  private boolean onMain = true;
  private Widget main;
  private Settings setting;
  private BorderPane top;
  private Button swipe;
  private Region spacer;
  private StackPane view;
  private Label nameLabel;
  private HBox bottom;

  public WidgetContainer(Widget widget, Integer pos) {
    super();
    this.widget = widget;
    spacer = new Region();
    spacer.getStyleClass().add("button-spacer");
    setCenter(widget);
    position = pos;
    main = widget;
    nameLabel = new Label(getWidgetName());
    bottom = new HBox();
    bottom.getChildren().addAll(nameLabel);
    setBottom(bottom);
    getStyleClass().add("widget_container");

    if (main.getAvailableUnits().size() > 0) {
      top = new BorderPane();
      swipe = new Button();
      swipe.setGraphic(new ImageView(Main.SETTINGS_ICON));
      setting = new Settings(main);
      view = new StackPane();
      view.getChildren().addAll(setting, main);

      top.setRight(swipe);
      swipe.setOnAction(
          (actionEvent) -> {
            swap();
          });

      setCenter(view);
      setTop(top);
    }
  }

  private void swap() {
    if (onMain) {
      FadeTransition ft = new FadeTransition(Duration.millis(500), main);
      ft.setFromValue(1.0);
      ft.setToValue(0.0);
      ft.setCycleCount(1);
      ft.play();

      swipe.setGraphic(new ImageView(Main.BACK_ICON));
      top.setRight(spacer);
      top.setCenter(new Label(main.getName() + " Settings"));
      top.setLeft(swipe);
      setBottom(null);

      FadeTransition ft2 = new FadeTransition((Duration.millis(500)), setting);
      ft2.setFromValue(0.0);
      ft2.setToValue(1.0);
      ft2.setCycleCount(1);
      ft2.play();
    } else {
      FadeTransition ft = new FadeTransition(Duration.millis(500), setting);
      ft.setFromValue(1.0);
      ft.setToValue(0.0);
      ft.setCycleCount(1);
      ft.play();

      swipe.setGraphic(new ImageView(Main.SETTINGS_ICON));
      top.setLeft(null);
      top.setCenter(null);
      top.setRight(swipe);
      setBottom(nameLabel);

      FadeTransition ft2 = new FadeTransition((Duration.millis(500)), main);
      ft2.setFromValue(0.0);
      ft2.setToValue(1.0);
      ft2.setCycleCount(1);
      ft2.play();

      // Force the widget to reload in case its settings have changed
      main.refresh();
      nameLabel.setText(getWidgetName());
    }

    Node first = view.getChildren().remove(0);
    view.getChildren().add(first);

    onMain = !onMain;
  }

  /**
   * Returns the formatted widget name followed by its unit in brackets.
   *
   * @return the formatted name of the widget with a unit
   */
  private String getWidgetName() {
    Unit unit = main.getUnit();
    if (unit != Unit.NONE) return main.getName() + " (" + main.getUnit() + ")";
    else return main.getName();
  }

  /**
   * Returns the position of the widget in the main screen
   *
   * @return the position of the widget
   */
  public int getPosition() {
    return position;
  }

  /**
   * Sets the position of the widget in the main screen
   *
   * @param position the position to set the widget to
   */
  public void setPosition(int position) {
    this.position = position;
  }

  /**
   * Get the widget contained by this container
   *
   * @return the widget contained by this container
   */
  public Widget getWidget() {
    return widget;
  }
}
