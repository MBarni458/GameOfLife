import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;

public class TestApplication {
    Application testApp;
    ArrayList<Shape> testList;
    @Before
    public void init(){
        UserConfiguration.tileShape= UserConfiguration.TileShape.SQUARE;
        testList=new ArrayList<>();
        testApp=new Application(testList);
    }

    @Test
    public void findClicked(){
        testApp.findClickedShape(testList.get(0).center);
        Assert.assertEquals(Shape.Phases.ACTIVE,testList.get(0).activePhase);
    }

    @Test
    public void findClickedFailure(){
        UserConfiguration.tileShape= UserConfiguration.TileShape.HEXAGON;
        testApp.findClickedShape(testList.get(0).center);
    }
}
