import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

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

    public static void sortTilesByY(ArrayList<Shape> container){
        switch (UserConfiguration.tileShape){
            case Square ->{
                container.sort(new Comparator<Shape>() {
                    @Override
                    public int compare(Shape s1, Shape s2) {
                        if (((Square)s1).center.y < ((Square)s2).center.y){
                            return -1;
                        }
                        if (((Square)s1).center.y > ((Square)s2).center.y){
                            return 1;
                        }
                        if (((Square)s1).center.x < ((Square)s2).center.x){
                            return -1;
                        }
                        if (((Square)s1).center.x > ((Square)s2).center.x){
                            return 1;
                        }
                        return 0;
                    }
                });
            }
            case Hexagon ->{
                container.sort(new Comparator<Shape>() {
                    @Override
                    public int compare(Shape s1, Shape s2) {
                        if (((Hexagon)s1).center.y < ((Hexagon)s2).center.y){
                            return -1;
                        }
                        if (((Hexagon)s1).center.y > ((Hexagon)s2).center.y){
                            return 1;
                        }
                        if (((Hexagon)s1).center.x < ((Hexagon)s2).center.x){
                            return -1;
                        }
                        if (((Hexagon)s1).center.x > ((Hexagon)s2).center.x){
                            return 1;
                        }
                        return 0;
                    }
                });
            }
        }

    }

    public static void sortTilesByX(ArrayList<Shape> container){
        switch (UserConfiguration.tileShape){
            case Square ->{
                container.sort(new Comparator<Shape>() {
                    @Override
                    public int compare(Shape s1, Shape s2) {
                        if (((Square)s1).center.x < ((Square)s2).center.x){
                            return -1;
                        }
                        if (((Square)s1).center.x > ((Square)s2).center.x){
                            return 1;
                        }
                        if (((Square)s1).center.y < ((Square)s2).center.y){
                            return -1;
                        }
                        if (((Square)s1).center.y > ((Square)s2).center.y){
                            return 1;
                        }
                        return 0;
                    }
                });
            }
            case Hexagon ->{
                container.sort(new Comparator<Shape>() {
                    @Override
                    public int compare(Shape s1, Shape s2) {
                        if (((Hexagon)s1).center.x < ((Hexagon)s2).center.x){
                            return -1;
                        }
                        if (((Hexagon)s1).center.x > ((Hexagon)s2).center.x){
                            return 1;
                        }
                        if (((Hexagon)s1).center.y < ((Hexagon)s2).center.y){
                            return -1;
                        }
                        if (((Hexagon)s1).center.y > ((Hexagon)s2).center.y){
                            return 1;
                        }
                        return 0;
                    }
                });
            }
        }

    }

}
