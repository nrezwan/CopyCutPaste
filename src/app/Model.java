package app;

import java.util.ArrayList;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Model {
	private SimpleListProperty<Shape> squareListProperty;
	private SimpleListProperty<Shape> squareListProperty2;
	private int squareSideLength;

	public Model(int squareSideLength) {
		ArrayList<Shape> list = new ArrayList<Shape>();
		ObservableList<Shape> observableList = (ObservableList<Shape>) FXCollections.observableArrayList(list);
		squareListProperty = new SimpleListProperty<Shape>(observableList);
		ObservableList<Shape> observableList2 = FXCollections.observableArrayList(list);
		squareListProperty2 = new SimpleListProperty<>(observableList2);
		this.squareSideLength = squareSideLength;
	}

	public SimpleListProperty<Shape> squareListProperty(){
		return squareListProperty;
	}
	public ObservableList<Shape> squareList2Property() {
		return squareListProperty2.get();
	}
	public SimpleListProperty<Shape> squareList2Property2() {
		return squareListProperty2;
	}
	public int getSquareSideLength() {return squareSideLength;}
	public void addSquare(double x, double y) {
		double squareX = x - Main.model.getSquareSideLength()/2;
		double squareY = y - Main.model.getSquareSideLength()/2;
		Rectangle newSquare = new Rectangle(squareX, squareY, squareSideLength, squareSideLength);
		squareListProperty.add(newSquare);
	}
	public void addCircle(double x, double y) {
		double squareX = x + (Main.model.getSquareSideLength()/2)/2;
		double squareY = y + (Main.model.getSquareSideLength()/2)/2;
		Circle circle = new Circle(squareX, squareY, squareSideLength/2);
		squareListProperty.add(circle);
	}
	public void addTriangle(double x, double y) {
		double squareX = x + (Main.model.getSquareSideLength()/2);
		double squareY = y + (Main.model.getSquareSideLength()/2);
		double sideLength = Main.model.getSquareSideLength() * (2 / Math.pow(3, 0.5));
		double SQUARESIDELENGTH = Main.model.getSquareSideLength();
		Polygon triangle = new Polygon();
		triangle.getPoints().addAll(new Double[]{squareX, squareY-SQUARESIDELENGTH/2, squareX-sideLength/2, squareY+SQUARESIDELENGTH/2, squareX+sideLength/2, squareY+SQUARESIDELENGTH/2 });
		squareListProperty.add(triangle);
	}
	public void deleteSquareAt(int x, int y)
	{
		Shape delSquare = getSquareAt(x, y);
		squareListProperty.remove(delSquare);
	}

	private Shape getSquareAt(int x, int y) {
		Shape square = null;
		for (Shape s : squareListProperty)
		{
			if (s.contains(x, y))
			{
				square = s;
			}
		}
		return square;
	}

	public void resetSquareList2() {
		squareListProperty2.clear();
	}
}
