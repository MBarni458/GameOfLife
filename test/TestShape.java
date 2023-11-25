import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TestShape {

    int [] xPoints;
    int [] yPoints;
    Shape element;
    ArrayList<Shape> testList;
    @Before
    public void init(){
        xPoints=Square.defaultXPositions(0);
        yPoints=Square.defaultYPositions(0);
        element = new Square(xPoints,yPoints);
        testList = new ArrayList<>();
        testList.add(element);
    }
    @Test
    public void shapeBorn(){
        element.born();
        Assert.assertEquals(element.activePhase,Shape.Phases.BORN);
        Assert.assertEquals(element.lifeTime,UserConfiguration.lifeTime);
        Assert.assertEquals(UserConfiguration.defaultColor,element.color);
    }

    @Test
    public void shapeDying(){
        element.born();
        element.lifeTime=1;
        element.activePhase= Shape.Phases.ACTIVE;
        element.dying();
        Assert.assertEquals(Shape.Phases.ACTIVE,element.activePhase);
        Assert.assertEquals(0,element.lifeTime);
    }

    @Test
    public void shapeDead(){
        element.born();
        element.lifeTime=1;
        element.activePhase= Shape.Phases.ACTIVE;
        element.dying();
        element.dying();
        Assert.assertEquals(Shape.Phases.DYING,element.activePhase);
        Assert.assertEquals(0,element.lifeTime);
    }

    @Test (expected = NullPointerException.class)
    public void saveFigureNullFailure(){
        File output = new File("test.txt");
        Shape.saveFigure(null,output);
    }

    @Test
    public void saveFigureIOFailure(){
        testList.add(element);
        Shape.saveFigure(testList,new File(""));
    }

    @Test
    public void saveFigureSuccess(){
        Shape.saveFigure(testList,new File("test.txt"));
    }

    @Test
    public void loadFigureSuccess(){
        File input = new File("test.txt");
        Shape.saveFigure(testList,new File("test.txt"));
        ArrayList<Shape> resultList= Shape.loadFigure(input);
        for (int i=0;i<xPoints.length;i++){
            Assert.assertEquals(testList.get(0).shape.xpoints[i],resultList.get(0).shape.xpoints[i]);
            Assert.assertEquals(testList.get(0).shape.ypoints[i],resultList.get(0).shape.ypoints[i]);
        }
    }

    @Test
    public void loadFigureIOFailure(){
        File input = new File("");
        ArrayList<Shape> resultList= Shape.loadFigure(input);
    }

    @Test
    public void neighbours(){
        element.neighbours=null;
        long numberOfLivingNeighbours= element.numberOfLivingNeighbours();
        Assert.assertEquals(0,numberOfLivingNeighbours);
    }

}
