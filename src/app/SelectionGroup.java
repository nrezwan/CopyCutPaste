package app;

import java.util.ArrayList;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.BoundingBox;
import javafx.scene.shape.Shape;

public class SelectionGroup {
	private SimpleListProperty<Shape> items;
	private BoundingBox bounds = new BoundingBox(0, 0, 0, 0);

		public SelectionGroup()
		{
			ArrayList<Shape> list = new ArrayList<Shape>();
			ObservableList<Shape> observableList = (ObservableList<Shape>) FXCollections.observableArrayList(list);
			items = new SimpleListProperty<Shape>(observableList);

			itemsProperty().addListener(new ListChangeListener<Shape>() {
				@Override
				public void onChanged(Change<? extends Shape> c) {
					calculateBoundingBox();
				}
			});
		}

		public BoundingBox getBoundingBox()
		{
			calculateBoundingBox();
			return bounds;
		}

		public SimpleListProperty<Shape> itemsProperty()
		{
			return items;
		}

		private void calculateBoundingBox()
		{
			double 	minX = Double.MAX_VALUE, minY = Double.MAX_VALUE,
					maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;
			for (Shape r : itemsProperty()) {
				if (r.getScaleX() < minX)
					minX = r.getScaleX();
				if (r.getScaleY() < minY)
					minY = r.getScaleY();
				if (r.getScaleX() + r.getLayoutX() > maxX)
					maxX = r.getScaleX() + r.getLayoutX();
				if (r.getScaleY() + r.getLayoutY() > maxY)
					maxY = r.getScaleY() + r.getLayoutY();
			}
			bounds = new BoundingBox(minX, minY, maxX - minX, maxY - minY);
		}
}
