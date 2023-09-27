package app;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class View extends Pane {
	public static final Color FILL_COLOR = Color.GREEN;
	public static final Color SELECTED_FILL_COLOR = Color.BLUE;
	private static Group root;
	public Toolbar toolbar;

	public View() {
		Main.model.squareListProperty().addListener(new ListChangeListener<Shape>() {
			@Override
			public void onChanged(Change<? extends Shape> c) {
				while (c.next())
				{
					for (Shape r : c.getRemoved())
						root.getChildren().remove(r);

					for (Shape r : c.getAddedSubList())
					{
						r.setStroke(Color.BLACK);
						r.setFill(FILL_COLOR);
						root.getChildren().add(r);
					}
				}
			}
		});
		Main.iModel.getSelectedSquares().itemsProperty().addListener(new ListChangeListener<Shape>() {
			@Override
			public void onChanged(Change<? extends Shape> c) {
				deselectAll();
				for (Shape r : Main.iModel.getSelectedSquares().itemsProperty())
				{
					selectSquare(r);
				}
			}
		});

		Main.iModel.selectRegionProperty().addListener(new ChangeListener<Shape>() {
			@Override
			public void changed(ObservableValue<? extends Shape> observable, Shape oldValue,
								Shape newValue) {
				root.getChildren().remove(oldValue);

				if (newValue !=null)
				{
					root.getChildren().add(newValue);
					newValue.setFill(new Color(0,0,.5,.3));
					newValue.setStroke(new Color(0,0,.5,1));
				}
			}
		});
		this.toolbar = new Toolbar();
		root = new Group();
		getChildren().addAll(root,toolbar);
	}

	public void deselect(Shape r)
	{
		r.setFill(FILL_COLOR);
		r.setStrokeWidth(1);
	}

	public void deselectAll() {
		for (Shape s : Main.model.squareListProperty())
		{
			deselect(s);
		}
	}

	public void selectSquare(Shape node) {
		node.setFill(View.SELECTED_FILL_COLOR);
		node.setStrokeWidth(4);
	}
}