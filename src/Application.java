import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
public class Application extends JFrame{
    private final ArrayList<Shape> container = new ArrayList<>();
    private final MapBuilder map;

    public Application(){
        this.setTitle("Game Of Life");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1000,700);
        this.setLocationRelativeTo(null);
        MouseListener userInput = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Point click = new Point(e.getX(), e.getY());
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
        map= new MapBuilder(container, userInput);
        this.add(map);

        Simulation simulation = new Simulation(container, map);
        simulation.execute();

        UserSettingsMenu userMenu = new UserSettingsMenu(map);

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
