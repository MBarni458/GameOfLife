import java.awt.*;
import java.util.Arrays;

public class Square extends Shape {
    Point center;
    public Square(int [] xPoints, int [] yPoints){
        numberOfNodes=4;
        center = new Point(xPoints[0]+10,yPoints[0]-10);
        shape =new Polygon(xPoints,yPoints,numberOfNodes);
        active=false;
        yOffset =20;
        xOffset=0;
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

    public int[] shiftYPoints(int[] yPoints, boolean evenColumn){
        return yPoints;
    }

    public int[] getXPoints(){
        return shape.xpoints;
    }
    public int[] getYPoints(){
        return shape.ypoints;
    }


}
