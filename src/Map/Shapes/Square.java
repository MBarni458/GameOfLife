import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Square extends Shape {
    Point center;
    public Square(int [] xPoints, int [] yPoints){
        numberOfNodes=4;
        center = new Point(xPoints[0]+10,yPoints[0]-10);
        shape =new Polygon(xPoints,yPoints,numberOfNodes);
        activePhase=Phases.Inactive;
        yOffset =20;
        xOffset=0;
        radius=30;
    }

    public void findNeighbours(ArrayList<Shape> container){
        this.neighbours=container.stream().filter(square -> ((Square)square).center.distance(this.center) <= radius && !((Square)square).center.equals(this.center)).collect(Collectors.toCollection(ArrayList<Shape>::new));
    }

    public static int[] defaultXPositions(int xOffset){
        int[] ret =  {-10,-10,10,10};
        return Arrays.stream(ret).map(x->x+xOffset).toArray();
    }

    public static int[] defaultYPositions(int yOffset){
        int[] ret =  {10, -10, -10, 10};
        return Arrays.stream(ret).map(y->y+yOffset).toArray();
    }

    public int[] shiftXPoints(int[] xPoints){
        return Arrays.stream(xPoints).map(x->(x+20)).toArray();
    }


}
