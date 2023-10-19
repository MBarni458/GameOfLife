import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract  class Shape extends JPanel {

    enum Phases{
        Active,
        Inactive,
        Born,
        Die
    }
    int numberOfNodes;
    Polygon shape;
    Point center;

    Phases activePhase;
    int radius;
   int xOffset;
   int yOffset;
    ArrayList<Shape> neighbours;
    public abstract int[] shiftXPoints(int[] xPoints);
    public abstract  void findNeighbours(ArrayList<Shape> container);
    public abstract  ArrayList<Shape> cellsInTheSameLine(ArrayList<Shape> container);
}
