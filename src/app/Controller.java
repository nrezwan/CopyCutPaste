package app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

	public class Controller {
		public enum State {READY, DRAG_SELECTION_STARTED, DRAG_ITEMS_STARTED}
		private State state;

		public Controller() {
			Main.view.addEventHandler(MouseEvent.ANY, new MouseHandler());
			state = State.READY;
		}

		public class MouseHandler implements EventHandler<MouseEvent>{
			private double prevX = 0, prevY = 0;

			@Override
			public void handle(MouseEvent e) {
				switch(state)
				{
					case READY:
						if (e.getEventType() == MouseEvent.MOUSE_PRESSED) {
							prevX = e.getSceneX();
							prevY = e.getSceneY();

							if (Shape.class.isAssignableFrom(e.getTarget().getClass()))
							{
								Shape node = ((Shape) e.getTarget());
								node.toFront();

								if (!Main.iModel.getSelectedSquares().itemsProperty().contains(node))
								{
									if (!e.isControlDown())
										Main.iModel.getSelectedSquares().itemsProperty().clear();

									Main.iModel.getSelectedSquares().itemsProperty().add(node);
								}
								else if (e.isControlDown())
								{
									Main.iModel.getSelectedSquares().itemsProperty().remove(node);
								}
							}
							else
							{
								Main.iModel.getSelectedSquares().itemsProperty().clear();
							}
						}
						else if (e.getEventType()==MouseEvent.DRAG_DETECTED)
						{
							if (e.getTarget().getClass() == Main.view.getClass())
							{
								state = State.DRAG_SELECTION_STARTED;
								Main.iModel.startSelectRegion(e.getX(), e.getY());
							}
							else if (Shape.class.isAssignableFrom(e.getTarget().getClass()))
							{
								state = State.DRAG_ITEMS_STARTED;
							}
						}
						else if (e.getEventType()==MouseEvent.MOUSE_RELEASED)
						{
							if (e.getTarget().getClass()==Main.view.getClass() && Main.view.toolbar.squareButton.isSelected())
							{
								Main.model.addSquare(e.getX(), e.getY());
							}
							if (e.getTarget().getClass()==Main.view.getClass() && Main.view.toolbar.circleButton.isSelected())
							{
								Main.model.addCircle(e.getX(), e.getY());
							}
							if (e.getTarget().getClass()==Main.view.getClass() && Main.view.toolbar.triangleButton.isSelected())
							{
								Main.model.addTriangle(e.getX(), e.getY());
							}

							if (Shape.class.isAssignableFrom(e.getTarget().getClass()))
							{
								if (!e.isControlDown())
								{
									Main.iModel.getSelectedSquares().itemsProperty().clear();
								}
							}
						}
						break;	//end State.READY

					case DRAG_SELECTION_STARTED:
						if (e.getEventType() == MouseEvent.MOUSE_DRAGGED)
						{
							Main.iModel.updateSelectRegion(e.getX(), e.getY());
						}
						else if (e.getEventType() == MouseEvent.MOUSE_RELEASED)
						{
							state = State.READY;
							selectObjectsWithRegion();
							Main.iModel.selectRegionProperty().setValue(null);
						}
						break;// end State.DRAG_SELECTION_STARTED

					case DRAG_ITEMS_STARTED:
						if (e.getEventType()==MouseEvent.MOUSE_DRAGGED)
						{
							moveSelected(e.getSceneX() - prevX, e.getSceneY() - prevY);
							prevX = e.getSceneX();
							prevY = e.getSceneY();
						}

						else if (e.getEventType()==MouseEvent.MOUSE_RELEASED)
						{
							state = State.READY;
						}
						break; //end State.DRAG_ITEMS_STARTED
				}//end switch(state)
			}
		}

		private void selectObjectsWithRegion() {
			Rectangle selectRegion = Main.iModel.selectRegionProperty().getValue();
			Main.iModel.getSelectedSquares().itemsProperty().clear();

			if (selectRegion != null)
			{
				for (Shape s : Main.model.squareListProperty())
				{
					Point2D topLeft = new Point2D(s.getBoundsInParent().getMinX(), s.getBoundsInParent().getMinY());
					Point2D bottomRight = new Point2D(s.getBoundsInParent().getMaxX(), s.getBoundsInParent().getMaxY());
					Point2D topRight = new Point2D(s.getBoundsInParent().getMaxX(), s.getBoundsInParent().getMinY());
					Point2D bottomLeft = new Point2D(s.getBoundsInParent().getMinX(), s.getBoundsInParent().getMaxY());

					if (selectRegion.contains(topLeft) && selectRegion.contains(bottomRight) || selectRegion.contains(topRight) && selectRegion.contains(bottomLeft) || selectRegion.contains(bottomLeft) && selectRegion.contains(topRight) || selectRegion.contains(bottomRight) && selectRegion.contains(topLeft))
					{
						Main.iModel.getSelectedSquares().itemsProperty().add(s);
					}
				}
			}
		}

		private void moveSelected(double addX, double addY)
		{
			for (Shape r : Main.iModel.getSelectedSquares().itemsProperty())
			{
				r.setTranslateX(r.getLayoutX() + r.getTranslateX() + addX);
				r.setTranslateY(r.getLayoutY() + r.getTranslateY() + addY);
			}
		}
}
