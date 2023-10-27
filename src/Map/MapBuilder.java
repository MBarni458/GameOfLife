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
        synchronized (tiles){
            ShapeConverter.sortTilesByY(tiles);
            xPoints=ShapeConverter.defaultXPositions(xDefaultOffset+((UserConfiguration.rowsOfTheMap%2!=0)?0:tiles.get(0).xOffset/2));
            yPoints=tiles.get(tiles.size()-1).shape.ypoints;
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
        synchronized (tiles){
            ShapeConverter.sortTilesByY(tiles);
            tiles.removeAll(tiles.get(tiles.size()-1).cellsInTheSameLine(tiles));
            findNeighbours();
            tiles.notify();
        }

        }

    public void addNewColumn(int parity) {
        synchronized (tiles) {
                ShapeConverter.sortTilesByX(tiles);
                yPoints = tiles.get(0).shape.ypoints;
                xPoints = tiles.get(tiles.size() - 1).shape.xpoints;
                xPoints = tiles.get(0).shiftXPoints(xPoints, true);

                if (parity % 2 != 0 && tiles.get(0).divShapePerCol != 1) {
                    yPoints = Arrays.stream(yPoints).map(y -> y + tiles.get(0).yOffset).toArray();
                }

                for (int i = 0; i < UserConfiguration.rowsOfTheMap / tiles.get(0).divShapePerCol; i++) {
                    tiles.add(ShapeConverter.newTile(xPoints, yPoints));
                    yPoints = Arrays.stream(yPoints).map(y -> y + tiles.get(0).yOffset * tiles.get(0).divShapePerCol).toArray();
                }
                findNeighbours();
                tiles.notify();
        }
    }
    public synchronized void removeColumn(){
        synchronized (tiles){
                ShapeConverter.sortTilesByX(tiles);
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