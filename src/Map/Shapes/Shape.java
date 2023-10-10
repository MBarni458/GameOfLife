import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public abstract  class Shape extends JPanel {
    int numberOfNodes;
    Polygon shape;
    Point center;
   boolean active;
    LinkedList<Shape> neighbours;
}
