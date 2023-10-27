import java.io.Serializable;

public class UserConfiguration implements Serializable {
    enum TileShape{
        SQUARE,
        HEXAGON
    }
    public static TileShape tileShape = TileShape.SQUARE;
    public static int rowsOfTheMap=20;
    public static int columnsOfTheMap=20;
    public static int underPopulation=1;
    public static int overPopulation=4;
    public static int optimalPopulation =3;
    public static int lifetimeOfACell=1;
    public static int speedOfSimulation =500;
    public static boolean activeSimulation = false;

    public static void setTileShape(TileShape newTileShape) {
        tileShape=newTileShape;
    }
    public static void setColumnsOfTheMap(int newColumnsOfTheMap){
        columnsOfTheMap=newColumnsOfTheMap;
    }
    public static void setRowsOfTheMap(int newRowsOfTheMap){
        rowsOfTheMap=newRowsOfTheMap;
    }
    public static void setUnderPopulation(int newUnderPopulation){
        underPopulation=newUnderPopulation;
    }
    public static void setOverPopulation(int newOverPopulation){
        overPopulation=newOverPopulation;
    }
    public static void setOptimalPopulation(int newOptimalPopulation){
        optimalPopulation=newOptimalPopulation;
    }
    public static void setLifetimeOfACell(int newLifeTimeOfACell){
        lifetimeOfACell=newLifeTimeOfACell;
    }
    public static void setSpeedOfSimulation(int newSpeedOfSimulation){
        speedOfSimulation=newSpeedOfSimulation;
    }
    public static void setActiveSimulation(boolean newActiveSimulation){
        activeSimulation=newActiveSimulation;
    }
}
