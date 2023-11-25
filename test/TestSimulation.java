import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class TestSimulation {
    MapBuilder testMap;
    ArrayList<Shape> testList;
    Simulation testSimulation;
    @Before
    public void init(){
        testList= new ArrayList<>();
        UserConfiguration.setTileShape(UserConfiguration.TileShape.SQUARE);
        UserConfiguration.setRowsOfTheMap(5);
        UserConfiguration.setColumnsOfTheMap(5);
        MapBuilder testMap= new MapBuilder(testList,null);
        testSimulation= new Simulation(testList,testMap);
    }

    @Test
    public void finalizeStatus(){
        for(Shape element:testList){
            element.activePhase= Shape.Phases.DYING;
        }
        testList.get(0).born();
        testSimulation.setCellsFinalStatus();
        long activeCells=testList.stream().filter(x->x.activePhase.equals(Shape.Phases.ACTIVE)).count();
        Assert.assertEquals(1,activeCells);
        long deadCells=testList.stream().filter(x->x.activePhase.equals(Shape.Phases.INACTIVE)).count();
        Assert.assertEquals(24,deadCells);
    }

    @Test
    public void temporaryStatus(){
        testList.get(0).activePhase= Shape.Phases.ACTIVE;
        testList.get(1).activePhase= Shape.Phases.ACTIVE;
        testList.get(5).activePhase= Shape.Phases.ACTIVE;
        testList.get(24).activePhase= Shape.Phases.ACTIVE;
        testSimulation.setCellsTemporaryStatus();
        long activeCells=testList.stream().filter(x->x.activePhase.equals(Shape.Phases.ACTIVE)).count();
        Assert.assertEquals(3,activeCells);
        long deadCells=testList.stream().filter(x->x.activePhase.equals(Shape.Phases.DYING)).count();
        Assert.assertEquals(1,deadCells);
        long bornCells=testList.stream().filter(x->x.activePhase.equals(Shape.Phases.BORN)).count();
        Assert.assertEquals(1,bornCells);
    }
}
