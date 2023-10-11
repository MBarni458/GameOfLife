import java.awt.*;

//The ShapeConverter's main task is to help the other classes to work with the different kinds of shapes
public class ShapeConverter {

    public static Shape newTile(int[] xPoints,int[] yPoints){
        switch (UserConfiguration.tileShape) {
            case Square -> {
                return new Square(xPoints, yPoints);
            }
            case Hexagon -> {
                return new Hexagon(xPoints, yPoints);
            }
            default -> {
                return null;
            }
        }
    }

    public static int[] defaultXPositions(int xOffset){
        switch (UserConfiguration.tileShape) {
            case Hexagon -> {
                return Hexagon.defaultXPositions(xOffset);
            }
            case Square -> {
                return Square.defaultXPositions(xOffset);
            }
        }
        return null;
    }
    public static int[] defaultYPositions(int yOffset){
        switch (UserConfiguration.tileShape) {
            case Hexagon -> {
                return Hexagon.defaultYPositions(yOffset);
            }
            case Square -> {
                return Square.defaultYPositions(yOffset);
            }
        }
        return null;
    }

    public static boolean isClicked(Point click, Shape shape){
        return switch (UserConfiguration.tileShape) {
            case Square -> click.distance(((Square) shape).center) <= 9;
            case Hexagon -> click.distance(((Hexagon) shape).center) <= 9;
        };
    }

}
