import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public abstract  class Shape implements Serializable {

    //Each Shape has 4 phases
    enum Phases{
        //The Shape is Active if it has enough neighbours
        ACTIVE,
        //The Shape is Inactive if it doesn't have enough neighbours
        INACTIVE,
        //The Born is a temporary phase. It is before the Shape becomes Active
        BORN,
        //The Dying is a temporary phase. It is before the Shape becomes Inactive
        DYING
    }
    int lifeTime;
    int numberOfNodes;
    //The not square shapes have fewer columns because of the delay between the lines. To solve this problem every shape has the shapePerColumns variable. It tells how many shapes are in a column
    int virtualColumnNumber;
    //The shape is stores all the information to draw the shape. It stores the coordinates of the nodes too.
    Polygon shape;
    //The center of the polygon
    Point center;
    //The current status
    Phases activePhase;
    //The radius represents the max distance between the center of the shape and it's neighbour's center.
    int radius;
   int xOffset;
   int yOffset;
   Color color;
    ArrayList<Shape> neighbours;
    //Add the xOffset to each number of the xPoints. If the halfShift is true it adds only half of the xOffset
    public abstract int[] shiftXPoints(int[] xPoints,boolean halfShift);
    public abstract  void findNeighbours(ArrayList<Shape> container);
    public long numberOfLivingNeighbours(){
        try {
            return this.neighbours.stream().filter(c -> (c.activePhase == Shape.Phases.ACTIVE) || (c.activePhase == Shape.Phases.DYING)).count();
        } catch (NullPointerException e){
            return 0;
        }
    }
    public void born(){
        this.activePhase=Phases.BORN;
        this.lifeTime=UserConfiguration.lifeTime;
        resetColor();
    }
    public void dying(){
        fadeColor();
        if (this.lifeTime<=0){
            this.activePhase=Phases.DYING;
        } else {
            this.lifeTime--;
        }
    }
    //Find the cells with the same center.x
    public abstract  ArrayList<Shape> cellsInTheSameLine(ArrayList<Shape> container);
    //Find the cells with the same center.y
    public abstract  ArrayList<Shape> cellsInTheSameColumn(ArrayList<Shape> container);

    public void fadeColor(){
        int red = this.color.getRed() + 255 / (UserConfiguration.lifeTime + 1);
        int green = this.color.getGreen() + 255 / (UserConfiguration.lifeTime + 1);
        int blue = this.color.getBlue() + 255 / (UserConfiguration.lifeTime + 1);
        this.color = new Color(Math.min(250, red), Math.min(250, green), Math.min(250, blue));
    }

    public void resetColor(){
        this.color=UserConfiguration.defaultColor;
    }

    public static void saveFigure(ArrayList<Shape> shapes, File outputFile) throws NullPointerException{
        try {
            ObjectOutputStream objectOutputStream= new ObjectOutputStream(new FileOutputStream(outputFile));

            for (Shape element: shapes){
                element.neighbours=null;
            }
            objectOutputStream.writeObject(shapes);
        } catch (IOException e){
            System.out.println("Unexpected IO error: "+e.getMessage());
        }
    }
    public static ArrayList<Shape> loadFigure(File inputFile){
        try {
            ObjectInputStream objectInputStream= new ObjectInputStream(new FileInputStream(inputFile));
            ArrayList<Shape> container =(ArrayList<Shape>) objectInputStream.readObject();
            ArrayList<Integer> rows = new ArrayList<>();
            for (Shape element: container){
                if (!rows.contains(element.shape.ypoints[0])){
                    rows.add(element.shape.ypoints[0]);
                }
            }
            UserConfiguration.setActiveSimulation(false);
            UserConfiguration.setRowsOfTheMap(rows.size());
            UserConfiguration.setColumnsOfTheMap(container.size()/rows.size());
            if(container.size()>0){
                switch (container.get(0).shape.xpoints.length){
                    case 4 -> UserConfiguration.setTileShape(UserConfiguration.TileShape.SQUARE);
                    case 6 -> UserConfiguration.setTileShape(UserConfiguration.TileShape.HEXAGON);
                }
            }
            return container;
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Unexpected IO error: "+e.getMessage());
            return new ArrayList<>();
        }
    }
}
