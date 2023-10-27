import javax.swing.*;
import java.util.ArrayList;

public class Simulation extends SwingWorker<Void, Void> {

    ArrayList<Shape> cells;
    MapBuilder map;

    public Simulation(ArrayList<Shape> cells, MapBuilder map) {
        this.cells = cells;
        this.map=map;
    }

    protected void setCellsTemporaryStatus(){
        for (Shape cell : cells) {
            long numberOfLivingNeighbours = cell.neighbours.stream().filter(c -> (c.activePhase == Shape.Phases.ACTIVE) || (c.activePhase == Shape.Phases.DYING)).count();
            if (numberOfLivingNeighbours == UserConfiguration.optimalPopulation && cell.activePhase!= Shape.Phases.ACTIVE) {
                cell.activePhase = Shape.Phases.BORN;
            } else {
                if (numberOfLivingNeighbours <= UserConfiguration.underPopulation && cell.activePhase!=Shape.Phases.INACTIVE){
                    cell.activePhase= Shape.Phases.DYING;
                } else {
                    if(numberOfLivingNeighbours >= UserConfiguration.overPopulation && cell.activePhase!=Shape.Phases.INACTIVE){
                        cell.activePhase= Shape.Phases.DYING;
                    }
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
    protected Void doInBackground() {
        synchronized (cells) {
            while (true) {
                if (UserConfiguration.activeSimulation) {
                    setCellsTemporaryStatus();
                    setCellsFinalStatus();
                    map.repaint();
                }
                try {
                    cells.wait(UserConfiguration.speedOfSimulation);
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
