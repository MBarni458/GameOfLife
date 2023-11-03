import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Square extends Shape {
    public Square(int [] xPoints, int [] yPoints){
        numberOfNodes=4;
        center = new Point(xPoints[0]+10,yPoints[0]-10);
        shape =new Polygon(xPoints,yPoints,numberOfNodes);
        activePhase=Phases.INACTIVE;
        yOffset =20;
        xOffset=0;
        radius=30;
        virtualColumnNumber =1;
        lifeTime=UserConfiguration.lifeTime;
    }

    public static void sortTilesByY(ArrayList<Shape> cells){
        cells.sort((s1, s2) -> {
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
        });
    }

    public static void sortTilesByX(ArrayList<Shape> cells){
        cells.sort((s1, s2) -> {
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
        });
    }

    public void findNeighbours(ArrayList<Shape> container){
        //This function set the neighbours of the shape.
        // The shape is a neighbour of this shape if the distance between the centers is less than the radius
        this.neighbours=container.stream().filter(square -> ((Square)square).center
        .distance(this.center) <= radius && !((Square)square).center.equals(this.center))
        .collect(Collectors.toCollection(ArrayList<Shape>::new));
    }

    public static int[] defaultXPositions(int xOffset){
        int[] ret =  {-10,-10,10,10};
        return Arrays.stream(ret).map(x->x+xOffset).toArray();
    }

    public static int[] defaultYPositions(int yOffset){
        int[] ret =  {10, -10, -10, 10};
        return Arrays.stream(ret).map(y->y+yOffset).toArray();
    }

    public int[] shiftXPoints(int[] xPoints, boolean halfShift){
        return Arrays.stream(xPoints).map(x->(x+20)).toArray();
    }

    public ArrayList<Shape> cellsInTheSameLine(ArrayList<Shape> container){
        return container.stream().filter(tile->((Square)tile).center.y==this.center.y)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Shape> cellsInTheSameColumn(ArrayList<Shape> container){
        return container.stream().filter(tile->((Square)tile).center.x==this.center.x)
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
