import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
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

        createMap();
    }

    public void createMap(){
        tiles.clear();

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
            xPoints=tiles.get(0).shiftXPoints(xPoints,false);
            shapeCounter++;
            if (shapeCounter==UserConfiguration.columnsOfTheMap){
                shapeCounter=0;
                lineCounter++;
                //Some shapes like hexagon has special xOffset. For every second line the points has a half shift on the x-axis
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
        //To use this function the tiles have to have at least valid one element
        synchronized (tiles){
            //The first shape in the new line has the x coordinates of default and the y of the bottom right.
            //For this reason before get the coordinates the tiles (ArrayList) have to be sorted by the y coordinates plus one yOffset
            ShapeConverter.sortTilesByY(tiles);
            //In every odd lines the shapes with more than 4 sides have a plus half x offset
            xPoints=ShapeConverter.defaultXPositions(xDefaultOffset+((UserConfiguration.rowsOfTheMap%2!=0)?0:tiles.get(0).xOffset/2));
            //Get the y coordinates of the last shape. It has to be on the bottom because of the sort
            yPoints=tiles.get(tiles.size()-1).shape.ypoints;
            //Shift the y coordinates one line lower
            yPoints=Arrays.stream(yPoints).map(y->y+tiles.get(0).yOffset).toArray();
            for (int i=0;i<UserConfiguration.columnsOfTheMap;i++){
                tiles.add(ShapeConverter.newTile(xPoints,yPoints));
                xPoints=tiles.get(0).shiftXPoints(xPoints,false);
            }
            findNeighbours();
            tiles.notify();
        }
    }
    public synchronized void removeLine(){
        //To use this function the tiles have to have at least valid one element
        synchronized (tiles){
            //First the shapes have to be arranged by the y coordinates
            ShapeConverter.sortTilesByY(tiles);
            //Because the shape with largest y coordinates is at the end of the tiles (the ArrayList) the ones with the same y should be removed
            tiles.removeAll(tiles.get(tiles.size()-1).cellsInTheSameLine(tiles));
            findNeighbours();
            tiles.notify();
        }

        }

    public void addNewColumn(int parity) {
        //To use this function the tiles have to have at least valid one element
        synchronized (tiles) {
            //The first shape in the new line has the y coordinates of default and the x of the bottom right.
            //For this reason before get the coordinates the tiles (ArrayList) have to be sorted by the x coordinates plus xOffset
            ShapeConverter.sortTilesByX(tiles);
            yPoints = tiles.get(0).shape.ypoints;
            //Get the x coordinates of the last shape. It has to be on the right side because of the sort
            xPoints = tiles.get(tiles.size() - 1).shape.xpoints;
            //Shift one column away from the last one. It is a half shift because of the shapes with more than  4 sides
            //One real hexagon column is actually disconnected  hexagons with the same center.y coordinates.
            // It causes half as many hexagons in a real column. For this one hexagon column is actually a normal column and a half shift one
            xPoints = tiles.get(0).shiftXPoints(xPoints, true);
            //If shape has to have more than one real column for one virtual
            //Then the shape must have less y offset between lines for each off columns
            if (parity % 2 != 0 && tiles.get(0).virtualColumnNumber != 1) {
                yPoints = Arrays.stream(yPoints).map(y -> y + tiles.get(0).yOffset).toArray();
            }
            for (int i = 0; i < UserConfiguration.rowsOfTheMap / tiles.get(0).virtualColumnNumber; i++) {
                tiles.add(ShapeConverter.newTile(xPoints, yPoints));
                //The offset of the tiles is depends on have many shapes are in a column
                yPoints = Arrays.stream(yPoints).map(y -> y + tiles.get(0).yOffset * tiles.get(0).virtualColumnNumber).toArray();
            }
            findNeighbours();
            tiles.notify();
        }
    }
    public synchronized void removeColumn(){
        //To use this function the tiles have to have at least valid one element
        synchronized (tiles){
                ShapeConverter.sortTilesByX(tiles);
                //Because the tiles are sorted by the x coordinates the last element is at the left side of the map
                tiles.removeAll(tiles.get(tiles.size() - 1).cellsInTheSameColumn(tiles));
                findNeighbours();
                tiles.notify();
        }

    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(Shape element: tiles){
            g.setColor((element.activePhase==Shape.Phases.INACTIVE)?Color.white:Color.red);
            g.fillPolygon(element.shape);
            g.setColor(Color.black);
            g.drawPolygon(element.shape);
        }
    }
}