import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TestUserConfiguration {
    @Test
    public void saveConfiguration(){
        UserConfiguration.setTileShape(UserConfiguration.TileShape.SQUARE);
        UserConfiguration.setColumnsOfTheMap(5);
        UserConfiguration.setRowsOfTheMap(5);
        UserConfiguration.setUnderPopulation(1);
        UserConfiguration.setOverPopulation(4);
        UserConfiguration.setOptimalPopulation(3);
        UserConfiguration.setLifetimeOfACell(0);
        UserConfiguration.setSpeedOfSimulation(500);
        try {
            UserConfiguration.saveConfiguration(new File("testconfig.txt"));
        } catch (IOException e){
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void loadConfiguration(){
        try {
            UserConfiguration.loadConfiguration(new File("testconfig.txt"));
            Assert.assertEquals(UserConfiguration.TileShape.SQUARE,UserConfiguration.tileShape);
            Assert.assertEquals(5,UserConfiguration.columnsOfTheMap);
            Assert.assertEquals(5,UserConfiguration.rowsOfTheMap);
            Assert.assertEquals(1,UserConfiguration.underPopulation);
            Assert.assertEquals(4,UserConfiguration.overPopulation);
            Assert.assertEquals(3,UserConfiguration.optimalPopulation);
            Assert.assertEquals(0,UserConfiguration.lifeTime);
            Assert.assertEquals(500,UserConfiguration.speedOfSimulation);
        } catch (IOException e){
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void activeSimulationSetter(){
        UserConfiguration.setActiveSimulation(true);
        Assert.assertEquals(true,UserConfiguration.activeSimulation);
    }
}
