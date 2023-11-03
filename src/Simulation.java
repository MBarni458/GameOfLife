import javax.swing.*;
import java.util.ArrayList;

public class Simulation extends SwingWorker<Void, Void> {

    ArrayList<Shape> cells;
    MapBuilder map;
    boolean active;

    public Simulation(ArrayList<Shape> cells, MapBuilder map) {
        this.cells = cells;
        this.map=map;
        this.active=true;
    }

    public void turnOff(){
        active=false;
    }

    protected void setCellsTemporaryStatus(){
        for (Shape cell : cells) {
            long numberOfLivingNeighbours = cell.numberOfLivingNeighbours();
            if (numberOfLivingNeighbours == UserConfiguration.optimalPopulation && cell.activePhase!= Shape.Phases.ACTIVE) {
                cell.born();
            } else {
                if (numberOfLivingNeighbours <= UserConfiguration.underPopulation && cell.activePhase!=Shape.Phases.INACTIVE){
                    cell.dying();
                } else {
                    if(numberOfLivingNeighbours >= UserConfiguration.overPopulation && cell.activePhase!=Shape.Phases.INACTIVE){
                        cell.dying();
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

    public int x=0;
    @Override
    protected Void doInBackground() {
        synchronized (cells) {
            while (active) {
                if (UserConfiguration.activeSimulation) {
                    System.out.println("Round: "+ (x++));
                    //Before the shapes get their final status they got a temporary one
                    //It is because the final status would change the number of neighbours
                    setCellsTemporaryStatus();
                    //After every shape has its temporary status it is safe to change them into the finals
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
        return null;
    }
}
