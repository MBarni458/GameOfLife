import java.awt.*;
import java.util.ArrayList;

//The ShapeConverter's main task is to help the other classes to work with the different kinds of shapes
public class ShapeConverter {

    private ShapeConverter(){}

    public static Shape newTile(int[] xPoints,int[] yPoints){
        switch (UserConfiguration.tileShape) {
            case SQUARE -> {
                return new Square(xPoints, yPoints);
            }
            case HEXAGON -> {
                return new Hexagon(xPoints, yPoints);
            }
            default -> {
                return null;
            }
        }
    }
    public static int[] defaultXPositions(int xOffset){
        switch (UserConfiguration.tileShape) {
            case HEXAGON -> {
                return Hexagon.defaultXPositions(xOffset);
            }
            case SQUARE -> {
                return Square.defaultXPositions(xOffset);
            }
            default -> {
                return new int[0];
            }
        }
    }
    public static int[] defaultYPositions(int yOffset){
        switch (UserConfiguration.tileShape) {
            case HEXAGON -> {
                return Hexagon.defaultYPositions(yOffset);
            }
            case SQUARE -> {
                return Square.defaultYPositions(yOffset);
            }
            default -> {
                return new int[0];
            }
        }
    }

    public static boolean isClicked(Point click, Shape shape){
        return switch (UserConfiguration.tileShape) {
            case SQUARE -> click.distance(((Square) shape).center) <= 9;
            case HEXAGON -> click.distance(((Hexagon) shape).center) <= 9;
        };
    }

    public static void sortTilesByY(ArrayList<Shape> container){
        switch (UserConfiguration.tileShape){
            case SQUARE -> Square.sortTilesByY(container);
            case HEXAGON -> Hexagon.sortTilesByY(container);
        }
    }

    public static void sortTilesByX(ArrayList<Shape> container){
        switch (UserConfiguration.tileShape){
            case SQUARE -> Square.sortTilesByY(container);
            case HEXAGON -> Hexagon.sortTileByX(container);
        }

    }

}
