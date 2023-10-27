import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static javax.swing.JFrame.*;

public class Application{
    public ArrayList<Shape> container = new ArrayList<>();
    public MapBuilder map;
    public UserSettingsMenu userMenu;
    public Simulation simulation;
    public JFrame frame;
    public MouseListener userInput =new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            Point click = new Point(e.getX(),e.getY());
                findClickedShape(click);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            //There is no reason to do anything on this event
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            //There is no reason to do anything on this event
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            //There is no reason to do anything on this event
        }

        @Override
        public void mouseExited(MouseEvent e) {
            //There is no reason to do anything on this event
        }
    };

    public Application(){

        frame = new JFrame("Game Of Life");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(1000,550);
        frame.setLocationRelativeTo(null);

        map= new MapBuilder(container,userInput);
        frame.add(map);

        simulation =new Simulation(container,map);
        simulation.execute();

        userMenu= new UserSettingsMenu(map);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, map, userMenu);
        splitPane.setOneTouchExpandable(false);
        splitPane.setDividerLocation(750);

        frame.add(splitPane);

        frame.setVisible(true);

    }

    public void findClickedShape(Point click){
        synchronized (container){
            for (Shape element: container){
                if (ShapeConverter.isClicked(click,element)){
                    if (element.activePhase == Shape.Phases.INACTIVE){
                        element.activePhase = Shape.Phases.ACTIVE;
                    }
                    else{
                        element.activePhase= Shape.Phases.INACTIVE;
                    }
                    break;
                }
            }
            map.repaint();
        }
    }

}
