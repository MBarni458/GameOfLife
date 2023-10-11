import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JPanel;

public class MapBuilder extends JPanel{

    ArrayList<Shape> list = new ArrayList<>();

    public MapBuilder(){
        setPreferredSize(new Dimension(500, 500));
        this.addMouseListener(ml);


        int yDefaultOffset=30;
        int xDefaultOffset =30;

        int[] xPoints;
        int[] yPoints;

        xPoints=ShapeConverter.defaultXPositions(xDefaultOffset);
        yPoints=ShapeConverter.defaultYPositions(yDefaultOffset);

        int shapeCounter=0;
        int lineCounter=0;
        for (int i=0;i<550;i++){

            list.add(ShapeConverter.newTile(xPoints,yPoints));

            xPoints=list.get(0).shiftXPoints(xPoints);

            shapeCounter++;
            if (shapeCounter==25){
                shapeCounter=0;
                lineCounter++;
                xPoints=ShapeConverter.defaultXPositions(xDefaultOffset+((lineCounter%2==0)?0:list.get(0).xOffset/2));
                yPoints=Arrays.stream(yPoints).map(y->y+list.get(0).yOffset).toArray();
            }
        }

    }

    MouseListener ml = new MouseListener() {
        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("x:"+ e.getPoint().x + " y:"+e.getPoint().y);
            Point click = new Point(e.getX(),e.getY());
            for (Shape element: list){
                if (ShapeConverter.isClicked(click,element)){
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