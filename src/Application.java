import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import static javax.swing.JFrame.*;

public class Application extends JFrame{
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

        this.setTitle("Game Of Life");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setLocationRelativeTo(null);

        map= new MapBuilder(container,userInput);
        this.add(map);

        simulation =new Simulation(container,map);
        simulation.execute();

        userMenu= new UserSettingsMenu(map);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, map, userMenu);
        splitPane.setOneTouchExpandable(false);
        splitPane.setDividerLocation(750);

        this.add(splitPane);

        this.setVisible(true);
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
