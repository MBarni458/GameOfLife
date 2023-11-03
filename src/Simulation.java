import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Simulation extends SwingWorker<Void, Void> {

    ArrayList<Shape> cells;
    MapBuilder map;
    boolean active;

    public Simulation(List<Shape> cells, MapBuilder map) {
        this.cells = (ArrayList<Shape>) cells;
        this.map=map;
        this.active=true;
    }

    public void turnOff(){
        active=false;
    }

    protected void setCellsTemporaryStatus(){
        for (Shape cell : cells) {
            long numberOfLivingNeighbours = cell.numberOfLivingNeighbours();
            if (numberOfLivingNeighbours == UserConfiguration.optimalPopulation) {
                if (cell.activePhase!= Shape.Phases.ACTIVE){
                    cell.born();
                } else {
                    cell.lifeTime=UserConfiguration.lifeTime;
                }
            } else {
                if ((numberOfLivingNeighbours <= UserConfiguration.underPopulation || numberOfLivingNeighbours >= UserConfiguration.overPopulation ) && cell.activePhase!=Shape.Phases.INACTIVE){
                    cell.dying();
                }
                }
            }
        }

    protected void setCellsFinalStatus(){
        for (Shape cell : cells) {
            if (cell.activePhase == Shape.Phases.BORN) {
                cell.activePhase = Shape.Phases.ACTIVE;
            } else {
                if (cell.activePhase == Shape.Phases.DYING){
                    cell.activePhase = Shape.Phases.INACTIVE;
                }
            }
        }
    }
    @Override
    protected Void doInBackground() throws InterruptedException {
        synchronized (cells) {
            while (active) {
                if (UserConfiguration.activeSimulation) {
                    //Before the shapes get their final status they got a temporary one
                    //It is because the final status would change the number of neighbours
                    setCellsTemporaryStatus();
                    //After every shape has its temporary status it is safe to change them into the finals
                    setCellsFinalStatus();
                    map.repaint();
                }
                    cells.wait(UserConfiguration.speedOfSimulation);
            }
        }
        return null;
    }
}
