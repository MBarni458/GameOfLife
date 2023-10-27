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
    int divShapePerCol;
    Polygon shape;
    Point center;

    Phases activePhase;
    int radius;
   int xOffset;
   int yOffset;
    ArrayList<Shape> neighbours;
    public abstract int[] shiftXPoints(int[] xPoints,boolean halfShift);
    public abstract  void findNeighbours(ArrayList<Shape> container);
    public abstract  ArrayList<Shape> cellsInTheSameLine(ArrayList<Shape> container);
    public abstract  ArrayList<Shape> cellsInTheSameColumn(ArrayList<Shape> container);
}
