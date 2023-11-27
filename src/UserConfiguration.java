import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Field;

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
    public static int lifeTime=0;
    public static int speedOfSimulation =500;
    public static Color defaultColor= Color.BLUE;
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
        bufferedWriter.write("TileShape;rowsNumber;colsNumber;simulationSpeed;optimalPopulation;underPopulation;overPopulation,lifeTime");
        bufferedWriter.newLine();
        bufferedWriter.write(tileShape.toString()+";"+rowsOfTheMap+";"+columnsOfTheMap+";"+speedOfSimulation+";"+optimalPopulation+";"+underPopulation+";"+overPopulation+";"+lifeTime);
        bufferedWriter.close();
    }
    public static boolean loadConfiguration(File inputfile) throws IOException{
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(inputfile)));
            bufferedReader.readLine();
            String data[] = bufferedReader.readLine().split(";");
            System.out.println(data.length);
            if(data.length!=8){
                throw new Exception("Incorrect File");
            }
            bufferedReader.close();
            setTileShape(ShapeConverter.tileShapeSelector(data[0]));
            setRowsOfTheMap(Integer.parseInt(data[1]));
            setColumnsOfTheMap(Integer.parseInt(data[2]));
            setSpeedOfSimulation(Integer.parseInt(data[3]));
            setOptimalPopulation(Integer.parseInt(data[4]));
            setUnderPopulation(Integer.parseInt(data[5]));
            setOverPopulation(Integer.parseInt(data[6]));
            setLifetimeOfACell(Integer.parseInt(data[7]));
            return true;
        } catch (Exception e){
            JDialog ioError = new JDialog();
            ioError.setTitle("Error");
            ioError.add(new JLabel("Select a correct file",SwingConstants.CENTER));
            ioError.setSize(500,100);
            ioError.setResizable(false);
            ioError.show();
            return false;
        }
    }
}
