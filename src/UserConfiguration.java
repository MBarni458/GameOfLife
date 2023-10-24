import java.io.Serializable;

public class UserConfiguration implements Serializable {
    enum TileShape{
        Square,
        Hexagon
    }
    public static TileShape tileShape = TileShape.Square;
    public static int rowsOfTheMap=20;
    public static int columnsOfTheMap=20;
    public static int underPopulation=1;
    public static int overPopulation=4;
    public static int optimalPopulation =3;
    public static int lifetimeOfACell=1;
    public static int speedOfSimulation =500;
    public static boolean activeSimulation = false;
}
