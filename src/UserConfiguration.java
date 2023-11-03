import java.io.*;

public class UserConfiguration implements Serializable {
    enum TileShape{
        SQUARE,
        HEXAGON
    }
    private static int id=1;
    public static TileShape tileShape = TileShape.SQUARE;
    public static int rowsOfTheMap=20;
    public static int columnsOfTheMap=20;
    public static int underPopulation=1;
    public static int overPopulation=4;
    public static int optimalPopulation =3;
    public static int lifeTime=0;
    public static int speedOfSimulation =500;
    public static boolean activeSimulation = false;

    public static void setId(int newId){
        id=newId;
    }
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
        lifeTime=newLifeTimeOfACell;
    }
    public static void setSpeedOfSimulation(int newSpeedOfSimulation){
        speedOfSimulation=newSpeedOfSimulation;
    }
    public static void setActiveSimulation(boolean newActiveSimulation){
        activeSimulation=newActiveSimulation;
    }
    public static void saveConfiguration(File outputfile) throws IOException{
        BufferedWriter bufferedWriter =new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputfile)));
        bufferedWriter.write("id;TileShape;rowsNumber;colsNumber;simulationSpeed;optimalPopulation;underPopulation;overPopulation,lifeTime");
        bufferedWriter.newLine();
        bufferedWriter.write(id+";"+tileShape.toString()+";"+rowsOfTheMap+";"+columnsOfTheMap+";"+speedOfSimulation+";"+optimalPopulation+";"+underPopulation+";"+overPopulation+";"+lifeTime);
        bufferedWriter.close();
    }

    public static void loadConfiguration(File inputfile) throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputfile)));
        bufferedReader.readLine();
        String data[]= bufferedReader.readLine().split(";");
        setId(Integer.parseInt(data[0]));
        setTileShape(ShapeConverter.tileShapeSelector(data[1]));
        setRowsOfTheMap(Integer.parseInt(data[2]));
        setColumnsOfTheMap(Integer.parseInt(data[3]));
        setSpeedOfSimulation(Integer.parseInt(data[4]));
        setOptimalPopulation(Integer.parseInt(data[5]));
        setUnderPopulation(Integer.parseInt(data[6]));
        setOverPopulation(Integer.parseInt(data[7]));

    }

}
