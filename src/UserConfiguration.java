import java.io.Serializable;

public class UserConfiguration implements Serializable {
    enum TileShape{
        Square,
        Hexagon
    }
    public static TileShape tileShape;
    public static int underPopulation=1;
    public static int overPopulation=4;
    public static int optimalPopulation =3;
    public static boolean activeSimulation = true;

    public static void setactiveSimulation(boolean isActive){
        activeSimulation=isActive;
    }



}
