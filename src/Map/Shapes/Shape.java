import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract  class Shape extends JPanel {

    //Each Shape has 4 phases
    enum Phases{
        //The Shape is Active if it has enough neighbours
        ACTIVE,
        //The Shape is Inactive if it doesn't have enough neighbours
        INACTIVE,
        //The Born is a temporary phase. It is before the Shape becomes Active
        BORN,
        //The Dying is a temporary phase. It is before the Shape becomes Inactive
        DYING
    }
    int numberOfNodes;
    //The not square shapes have fewer columns because of the delay between the lines. To solve this problem every shape has the shapePerColumns variable. It tells how many shapes are in a column
    int virtualColumnNumber;
    //The shape is stores all the information to draw the shape. It stores the coordinates of the nodes too.
    Polygon shape;
    //The center of the polygon
    Point center;
    //The current status
    Phases activePhase;
    //The radius represents the max distance between the center of the shape and it's neighbour's center.
    int radius;
   int xOffset;
   int yOffset;
    ArrayList<Shape> neighbours;
    //Add the xOffset to each number of the xPoints. If the halfShift is true it adds only half of the xOffset
    public abstract int[] shiftXPoints(int[] xPoints,boolean halfShift);
    public abstract  void findNeighbours(ArrayList<Shape> container);
    //Find the cells with the same center.x
    public abstract  ArrayList<Shape> cellsInTheSameLine(ArrayList<Shape> container);
    //Find the cells with the same center.y
    public abstract  ArrayList<Shape> cellsInTheSameColumn(ArrayList<Shape> container);
}
