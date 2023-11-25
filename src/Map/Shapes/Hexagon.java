import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Hexagon extends Shape {
    public Hexagon(int [] xPoints, int [] yPoints){
        numberOfNodes=6;
        center = new Point(xPoints[0],yPoints[0]-10);
        shape =new Polygon(xPoints,yPoints,numberOfNodes);
        activePhase=Phases.INACTIVE;
        yOffset =16;
        xOffset=18;
        radius=20;
        lifeTime=UserConfiguration.lifeTime;
        color=UserConfiguration.defaultColor;
        //The hexagon has a unique kind of columns. It has two real columns next to each other
        virtualColumnNumber =2;
    }
    public static void sortTilesByY(List<Shape> cells){
        cells.sort((s1, s2) -> {
            if (s1.center.y < s2.center.y){
                return -1;
            }
            if (s1.center.y > s2.center.y){
                return 1;
            }
            return Integer.compare(s1.center.x, s2.center.x);
        });
    }
    public static void sortTileByX(List<Shape> cells){
        cells.sort((s1, s2) -> {
            if (s1.center.x < s2.center.x){
                return -1;
            }
            if (s1.center.x > s2.center.x){
                return 1;
            }
            return Integer.compare(s1.center.y, s2.center.y);
        });
    }
    public void findNeighbours(ArrayList<Shape> container){
        //This function set the neighbours of the shape.
        // The shape is a neighbour of this shape if the distance between the centers is less than the radius
        this.neighbours= container.
                stream().filter(hexagon -> hexagon.center
                .distance(this.center) <= radius && !hexagon.center.equals(this.center))
                .collect(Collectors.toCollection(ArrayList<Shape>::new));
    }
    public static int[] defaultXPositions(int xOffset){
        int[] ret =  {0, 9, 9, 0, -9, -9};
        return Arrays.stream(ret).map(x->x+xOffset).toArray();
    }
    public static int[] defaultYPositions(int yOffset){
        int[] ret =  {10, 5, -5, -10, -5, 5};
        return Arrays.stream(ret).map(y->y+yOffset).toArray();
    }
    public int[] shiftXPoints(int[] xPoints,boolean halfShift){
        return Arrays.stream(xPoints).map(x->(x+(halfShift?xOffset/2:xOffset))).toArray();
    }
    public ArrayList<Shape> cellsInTheSameLine(ArrayList<Shape> container){
        return container.stream().filter(tile-> tile.center.y==this.center.y)
                .collect(Collectors.toCollection(ArrayList::new));
    }
    public ArrayList<Shape> cellsInTheSameColumn(ArrayList<Shape> container){
        return container.stream().filter(tile-> tile.center.x==this.center.x)
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
