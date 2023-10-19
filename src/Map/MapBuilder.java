import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import javax.swing.JPanel;

public class MapBuilder extends JPanel{

    public ArrayList<Shape> tiles;
    private int yDefaultOffset;
    private int xDefaultOffset;
    private int[] xPoints;
    private int[] yPoints;

    public MapBuilder(ArrayList<Shape> tiles, MouseListener mouseEvent){
        this.tiles=tiles;
        setPreferredSize(new Dimension(800, 500));
        this.addMouseListener(mouseEvent);

        yDefaultOffset=30;
        xDefaultOffset =30;

        xPoints=ShapeConverter.defaultXPositions(xDefaultOffset);
        yPoints=ShapeConverter.defaultYPositions(yDefaultOffset);

        drawMap();
        findNeighbours();
    }

    public void drawMap(){
        int shapeCounter=0;
        int lineCounter=0;
        int countOfAllShapes=UserConfiguration.rowsOfTheMap *UserConfiguration.columnsOfTheMap;
        for (int i=0;i<countOfAllShapes;i++){

            tiles.add(ShapeConverter.newTile(xPoints,yPoints));

            xPoints=tiles.get(0).shiftXPoints(xPoints);

            shapeCounter++;
            if (shapeCounter==UserConfiguration.columnsOfTheMap){
                shapeCounter=0;
                lineCounter++;
                xPoints=ShapeConverter.defaultXPositions(xDefaultOffset+((lineCounter%2==0)?0:tiles.get(0).xOffset/2));
                yPoints=Arrays.stream(yPoints).map(y->y+tiles.get(0).yOffset).toArray();
            }
        }
    }

    public void findNeighbours(){
        for (Shape element:tiles){
            element.findNeighbours(tiles);
        }
    }

    public void addNewLine(){
        xPoints=tiles.get(0).shape.xpoints;
        xPoints=ShapeConverter.defaultXPositions(xDefaultOffset+((UserConfiguration.rowsOfTheMap%2!=0)?0:tiles.get(0).xOffset/2));
        yPoints=Arrays.stream(tiles.get(tiles.size()-1).shape.ypoints).map(y->y+tiles.get(0).yOffset).toArray();
         for (int i=0;i<UserConfiguration.columnsOfTheMap;i++){
             tiles.add(ShapeConverter.newTile(xPoints,yPoints));
             xPoints=tiles.get(0).shiftXPoints(xPoints);
         }
    }
    public void removeLine(){
            tiles.removeAll(tiles.get(tiles.size()-1).cellsInTheSameLine(tiles));
        }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(Shape element: tiles){
            g.setColor((element.activePhase==Shape.Phases.Inactive)?Color.white:Color.red);
            g.fillPolygon(element.shape);
            g.setColor(Color.black);
            g.drawPolygon(element.shape);
        }
    }
}