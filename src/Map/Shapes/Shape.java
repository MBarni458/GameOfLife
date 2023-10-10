import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

//The Shape class is the ancestor of all the shapes of the tiles
//It makes a much easier to store the different kind of shapes
public abstract class Shape extends JPanel {
    int numberOfNodes;
    Polygon shape;
    Point center;
   boolean active;
    LinkedList<Shape> neighbours;

    //These two function is responsible for the coordinates correct place
    public abstract int []shiftXPoints(int[] xPoints);
    public abstract int []shiftYPoints(int[] xPoints, boolean evenLine);
}
