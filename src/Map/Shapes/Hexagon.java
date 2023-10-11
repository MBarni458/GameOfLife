import java.awt.*;
import java.util.Arrays;

public class Hexagon extends Shape {
    Point center;
    public Hexagon(int [] xPoints, int [] yPoints){
        numberOfNodes=6;
        center = new Point(xPoints[0],yPoints[0]-10);
        System.out.println(center.x);
        shape =new Polygon(xPoints,yPoints,numberOfNodes);
        active=false;
        yOffset =16;
        xOffset=18;
    }
    public static int[] defaultXPositions(int xOffset){
        int[] ret =  {0, 9, 9, 0, -9, -9};
        return Arrays.stream(ret).map(x->x+xOffset).toArray();
    }

    public static int[] defaultYPositions(int yOffset){
        int[] ret =  {10, 5, -5, -10, -5, 5};
        return Arrays.stream(ret).map(y->y+yOffset).toArray();
    }

    public int[] shiftXPoints(int[] xPoints){
        return Arrays.stream(xPoints).map(x->(x+xOffset)).toArray();
    }
}
