package app;

import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;

public class InteractionModel {
    private SelectionGroup selectedSquares;
    private SimpleObjectProperty<Rectangle> selectRegion;
    private Point2D start;

    public InteractionModel()
    {
        selectedSquares = new SelectionGroup();
        selectRegion = new SimpleObjectProperty<Rectangle>();
    }

    public SimpleObjectProperty<Rectangle> selectRegionProperty()
    {
        return selectRegion;
    }

    public SelectionGroup getSelectedSquares(){
        return selectedSquares;
    }

    public void startSelectRegion(double x, double y)
    {
        start = new Point2D(x,y);
        selectRegion.set(new Rectangle(x,y,0,0));
    }
    public void clear() {
        selectedSquares.itemsProperty().clear();
    }

    public void updateSelectRegion(double x, double y) {
        Rectangle selectRect = new Rectangle();
        if(x>start.getX() && y>start.getY()){
            selectRect.setWidth(x-start.getX());
            selectRect.setHeight(y-start.getY());
            selectRect.setX(start.getX());
            selectRect.setY(start.getY());
        }
        else if(x<start.getX() && y>start.getY()){
            selectRect.setWidth(start.getX()-x);
            selectRect.setHeight(y-start.getY());
            selectRect.setX(start.getX()-selectRect.getWidth());
            selectRect.setY(start.getY());
        }
        else if(x<start.getX() && y<start.getY()){
            selectRect.setWidth(start.getX()-x);
            selectRect.setHeight(start.getY()-y);
            selectRect.setX(start.getX()-selectRect.getWidth());
            selectRect.setY(start.getY()-selectRect.getHeight());
        }
        else if(x>start.getX() && y<start.getY()){
            selectRect.setWidth(x-start.getX());
            selectRect.setHeight(start.getY()-y);
            selectRect.setX(start.getX());
            selectRect.setY(start.getY()-selectRect.getHeight());
        }

        selectRegionProperty().set(selectRect);
    }
}