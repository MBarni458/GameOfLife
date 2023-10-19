import javax.swing.*;
import java.util.ArrayList;

public class Simulation extends SwingWorker<Void, Void> {

    ArrayList<Shape> cells;
    MapBuilder map;

    public Simulation(ArrayList<Shape> cells, MapBuilder map) {
        this.cells = cells;
        this.map=map;
    }

    @Override
    protected Void doInBackground(){
        synchronized (cells){
            while (true) {
                if (UserConfiguration.activeSimulation){
                    for (Shape cell : cells) {
                        long numberOfLivingNeighbours = cell.neighbours.stream().filter(c -> (c.activePhase == Shape.Phases.Active) || (c.activePhase == Shape.Phases.Die)).count();
                        if (numberOfLivingNeighbours == UserConfiguration.optimalPopulation && cell.activePhase!= Shape.Phases.Active) {
                            cell.activePhase = Shape.Phases.Born;
                        } else {
                            if (numberOfLivingNeighbours <= UserConfiguration.underPopulation && cell.activePhase!=Shape.Phases.Inactive){
                                cell.activePhase= Shape.Phases.Die;
                            } else {
                                if(numberOfLivingNeighbours >= UserConfiguration.overPopulation && cell.activePhase!=Shape.Phases.Inactive){
                                   cell.activePhase= Shape.Phases.Die;
                                }
                            }
                        }
                    }
                    for (Shape cell : cells) {
                        if (cell.activePhase == Shape.Phases.Born) {
                            cell.activePhase = Shape.Phases.Active;
                        } else {
                            if (cell.activePhase == Shape.Phases.Die){
                                cell.activePhase = Shape.Phases.Inactive;
                            }
                        }
                    }
                    map.repaint();
                }
                try {
                    cells.wait(UserConfiguration.speedOfSimulation);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
}
