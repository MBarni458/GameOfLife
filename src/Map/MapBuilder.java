import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JPanel;

public class MapBuilder extends JPanel{

    ArrayList<Shape> tiles;
    public MapBuilder(ArrayList<Shape> tiles, MouseListener mouseEvent){
        this.tiles=tiles;
        setPreferredSize(new Dimension(500, 500));
        this.addMouseListener(mouseEvent);


        int yDefaultOffset=30;
        int xDefaultOffset =30;

        int[] xPoints;
        int[] yPoints;

        xPoints=ShapeConverter.defaultXPositions(xDefaultOffset);
        yPoints=ShapeConverter.defaultYPositions(yDefaultOffset);

        int shapeCounter=0;
        int lineCounter=0;
        for (int i=0;i<550;i++){

            tiles.add(ShapeConverter.newTile(xPoints,yPoints));

            xPoints=tiles.get(0).shiftXPoints(xPoints);

            shapeCounter++;
            if (shapeCounter==25){
                shapeCounter=0;
                lineCounter++;
                xPoints=ShapeConverter.defaultXPositions(xDefaultOffset+((lineCounter%2==0)?0:tiles.get(0).xOffset/2));
                yPoints=Arrays.stream(yPoints).map(y->y+tiles.get(0).yOffset).toArray();
            }
        }
        for (Shape element:tiles){
            element.findNeighbours(tiles);
        }
    }


    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(Shape element: tiles){
            g.setColor((!element.active)?Color.white:Color.red);
            g.fillPolygon(element.shape);
            g.setColor(Color.black);
            g.drawPolygon(element.shape);
        }
    }
}