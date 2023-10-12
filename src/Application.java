import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Application {

    public Application(UserConfiguration.TileShape playMode){
        UserConfiguration.tileShape= playMode;

        JFrame frame = new JFrame("Hexagon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        map= new MapBuilder(container,userInput);
        frame.add(map);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    ArrayList<Shape> container = new ArrayList<>();
    MapBuilder map;
    MouseListener userInput =new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            Point click = new Point(e.getX(),e.getY());
            findClickedShape(click);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    };

    public void findClickedShape(Point click){
        for (Shape element: container){
            if (ShapeConverter.isClicked(click,element)){
                element.active=!element.active;
                map.repaint();
                break;
            }
        }
    }
}
