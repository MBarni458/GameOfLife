import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Hexagon extends Shape {
    public Hexagon(int [] xPoints, int [] yPoints){
        numberOfNodes=6;
        center = new Point(xPoints[0],yPoints[0]-10);
        shape =new Polygon(xPoints,yPoints,numberOfNodes);
        activePhase=Phases.Inactive;
        yOffset =16;
        xOffset=18;
        radius=20;
        divShapePerCol=2;
    }

    public void findNeighbours(ArrayList<Shape> container){
        this.neighbours=container.stream().filter(hexa -> ((Hexagon)hexa).center.distance(this.center) <= radius && !((Hexagon)hexa).center.equals(this.center)).collect(Collectors.toCollection(ArrayList<Shape>::new));
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
        return container.stream().filter(tile->((Hexagon)tile).center.y==this.center.y).collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Shape> cellsInTheSameColumn(ArrayList<Shape> container){
        return container.stream().filter(tile->((Hexagon)tile).center.x==this.center.x).collect(Collectors.toCollection(ArrayList::new));
    }

}
