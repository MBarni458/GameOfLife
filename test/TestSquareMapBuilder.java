import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TestSquareMapBuilder {

    MapBuilder testMap;
    ArrayList<Shape> testList;

    @Before
    public void init(){
        UserConfiguration.setTileShape(UserConfiguration.TileShape.SQUARE);
        testList=new ArrayList<>();
    }

    @Test
    public void mapSize(){
        UserConfiguration.rowsOfTheMap=5;
        UserConfiguration.columnsOfTheMap=5;
        testMap= new MapBuilder(testList,null);
        Assert.assertEquals(25,testList.size());
    }

    @Test
    public void addLine(){
        UserConfiguration.rowsOfTheMap=5;
        UserConfiguration.columnsOfTheMap=5;
        testMap= new MapBuilder(testList,null);
        testMap.addNewLine();
        Assert.assertEquals(30,testList.size());
    }
    @Test
    public void removeLine(){
        UserConfiguration.rowsOfTheMap=5;
        UserConfiguration.columnsOfTheMap=5;
        testMap= new MapBuilder(testList,null);
        testMap.removeLine();
        Assert.assertEquals(20,testList.size());
    }
    @Test
    public void addColumn(){
        UserConfiguration.rowsOfTheMap=5;
        UserConfiguration.columnsOfTheMap=5;
        testMap= new MapBuilder(testList,null);
        testMap.addNewColumn(0);
        Assert.assertEquals(30,testList.size());
    }
    @Test
    public void removeColumn(){
        UserConfiguration.rowsOfTheMap=5;
        UserConfiguration.columnsOfTheMap=5;
        testMap= new MapBuilder(testList,null);
        testMap.removeColumn();
        Assert.assertEquals(20,testList.size());
    }
}
