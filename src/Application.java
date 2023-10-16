import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class Application {
    public ArrayList<Shape> container = new ArrayList<>();
    public MapBuilder map;
    Simulation sim;
    public MouseListener userInput =new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            Point click = new Point(e.getX(),e.getY());
                findClickedShape(click);
                System.out.println("Clicked");
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

    public Application(UserConfiguration.TileShape playMode){

        UserConfiguration.tileShape= playMode;


        JFrame frame = new JFrame("Game Of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,550);

        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UserConfiguration.setactiveSimulation(!UserConfiguration.activeSimulation);
                System.out.println("I'mPressed");
            }
        });

        startButton.setBounds(700, 100, 100, 50);
        startButton.setVisible(true);


        map= new MapBuilder(container,userInput);
        sim =new Simulation(container,map);
        map.add(startButton);
        frame.add(map);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        sim.execute();
    }

    public void findClickedShape(Point click){
        synchronized (container){
            for (Shape element: container){
                if (ShapeConverter.isClicked(click,element)){
                    if (element.activePhase == Shape.Phases.Inactive){
                        element.activePhase = Shape.Phases.Active;
                    }
                    else{
                        element.activePhase= Shape.Phases.Inactive;
                    }
                    break;
                }
            }
            map.repaint();
        }
    }

}
