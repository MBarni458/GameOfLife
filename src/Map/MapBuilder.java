import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JPanel;

public class MapBuilder extends JPanel{

    ArrayList<Shape> list = new ArrayList<Shape>();

    public MapBuilder(){
        setPreferredSize(new Dimension(500, 500));
        this.addMouseListener(ml);


        int yDefaultOffset=30;
        int xDefaultOffset =30;

        int[] xPoints=Hexagon.defaultXPositions(xDefaultOffset);
        int[] yPoints=Hexagon.defaultYPositions(yDefaultOffset);

        int counter=0;
        for (int i=0;i<750;i++){

            list.add(new Hexagon(xPoints,yPoints));

            xPoints=Hexagon.shiftXPoints(xPoints);
            yPoints=Hexagon.shiftYPoints(yPoints,(i%2==0));

            counter++;
            if (counter==50){
                counter=0;
                xPoints=Hexagon.defaultXPositions(xDefaultOffset);
                yPoints=Arrays.stream(yPoints).map(y->y+30).toArray();
            }
        }

    }

    MouseListener ml = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("x:"+ e.getPoint().x + " y:"+e.getPoint().y);
            Point click = new Point(e.getX(),e.getY());
            for (Shape element: list){
                System.out.println("x:"+((Hexagon)element).center.x + " y:"+((Hexagon)element).center.y);
                if (click.distance(((Hexagon)element).center) <=9){
                    element.active=!element.active;
                    repaint();
                    break;
                }
            }
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

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(Shape element: list){
            g.setColor((!element.active)?Color.white:Color.red);
            g.fillPolygon(element.shape);
            g.setColor(Color.black);
            g.drawPolygon(element.shape);
        }
    }
}