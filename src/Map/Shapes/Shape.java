import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public abstract  class Shape extends JPanel {
    int numberOfNodes;
    Polygon shape;
    Point center;
   boolean active;
    int radius;
   int xOffset;
   int yOffset;
    ArrayList<Shape> neighbours;
    public abstract int[] shiftXPoints(int[] xPoints);
    public abstract  void findNeighbours(ArrayList<Shape> container);
}
