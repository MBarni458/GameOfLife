import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class UserSettingsMenu extends JPanel {

    private MapBuilder map;

    private  JButton startButton;
    private JSlider numberOfRowsSlider;
    private JSlider numberOfColumnsSlider;
    private JSlider simulationSpeedSlider;
    private JSpinner optimalPopulationSpinner;
    private JSpinner underPopulationSpinner;
    private JSpinner overPopulationSpinner;
    private JRadioButton squareRadiobutton;
    private JRadioButton hexagonRadiobutton;
    private JButton saveButton;
    private JButton loadButton;
    public UserSettingsMenu(MapBuilder map) {

        super(new GridLayout(20,2));

        this.map=map;

        //Create the form elements
        startButton=createStartButton();
        numberOfRowsSlider= createNumberOfRowsSlider();
        numberOfColumnsSlider= createNumberOfColumnsSlider();
        simulationSpeedSlider= createSimulationSpeedSlider();
        optimalPopulationSpinner= createOptimalPopulationSpinner();
        underPopulationSpinner= createUnderPopulationSpinner();
        overPopulationSpinner= createOverPopulationSpinner();
        squareRadiobutton= createSquareRadioButton();
        hexagonRadiobutton= createHexagonRadioButton();
        createShapeSelection();
        saveButton= createSaveButton();
        loadButton= createLoadButton();

        //Add the form elements to the panel
        this.add(startButton);
        this.add(new JLabel("Simulation Settings",SwingConstants.CENTER));
        this.add(new JLabel("Speed of Simulation",SwingConstants.CENTER));
        this.add(simulationSpeedSlider);
        this.add(new JLabel("Optimal Population",SwingConstants.CENTER));
        this.add(optimalPopulationSpinner);
        this.add(new JLabel("Under Population",SwingConstants.CENTER));
        this.add(underPopulationSpinner);
        this.add(new JLabel("Over Population",SwingConstants.CENTER));
        this.add(overPopulationSpinner);
        this.add(new JLabel("Map Settings",SwingConstants.CENTER));
        this.add(new JLabel("Number of Rows",SwingConstants.CENTER));
        this.add(numberOfRowsSlider);
        this.add(new JLabel("Number of Columns",SwingConstants.CENTER));
        this.add(numberOfColumnsSlider);
        this.add(new JLabel("Choose shape"));
        this.add(squareRadiobutton);
        this.add(hexagonRadiobutton);
        this.add(new JLabel("Import/Export configuration",SwingConstants.CENTER));
        this.add(saveButton);
        this.add(loadButton);
    }

    private JButton createStartButton(){
        String startText="Start";
        String stopText="Stop";
        JButton  tmpStartButton= new JButton(startText);
        tmpStartButton.addActionListener(e -> {
            UserConfiguration.setActiveSimulation(!UserConfiguration.activeSimulation);
            tmpStartButton.setText((tmpStartButton.getText().equals(startText))?stopText:startText);
            numberOfRowsSlider.setEnabled(!numberOfRowsSlider.isEnabled());
            numberOfColumnsSlider.setEnabled(!numberOfColumnsSlider.isEnabled());
            squareRadiobutton.setEnabled(!squareRadiobutton.isEnabled());
            hexagonRadiobutton.setEnabled(!hexagonRadiobutton.isEnabled());
        });
        return tmpStartButton;
    }
    private JSlider createNumberOfRowsSlider(){
        JSlider tmpNumberOfRowsSlider = new JSlider(SwingConstants.HORIZONTAL, 1, 40, UserConfiguration.rowsOfTheMap);
        tmpNumberOfRowsSlider.addChangeListener(e -> {
            int oldValue=UserConfiguration.rowsOfTheMap;
            UserConfiguration.setRowsOfTheMap(tmpNumberOfRowsSlider.getValue());
            if (oldValue<UserConfiguration.rowsOfTheMap && UserConfiguration.rowsOfTheMap<101){
                for (;oldValue<UserConfiguration.rowsOfTheMap;oldValue++){
                    this.map.addNewLine();
                }
            } else {
                if (UserConfiguration.rowsOfTheMap>0){
                    for (;oldValue>UserConfiguration.rowsOfTheMap;oldValue--) {
                        this.map.removeLine();
                    }
                } else {
                    UserConfiguration.setRowsOfTheMap(oldValue);
                    tmpNumberOfRowsSlider.setValue(UserConfiguration.rowsOfTheMap);
                }
            }
            this.map.repaint();
        });
        return tmpNumberOfRowsSlider;
    }
    private JSlider createNumberOfColumnsSlider(){
        JSlider tmpNumberOfColumnsSlider = new JSlider(SwingConstants.HORIZONTAL, 1, 40, UserConfiguration.columnsOfTheMap);
        tmpNumberOfColumnsSlider.addChangeListener(e -> {
            int oldValue=UserConfiguration.columnsOfTheMap*map.tiles.get(0).virtualColumnNumber;
            UserConfiguration.setColumnsOfTheMap(tmpNumberOfColumnsSlider.getValue());
            if (oldValue<UserConfiguration.columnsOfTheMap*map.tiles.get(0).virtualColumnNumber && UserConfiguration.columnsOfTheMap<101){
                for(; oldValue<UserConfiguration.columnsOfTheMap*map.tiles.get(0).virtualColumnNumber; oldValue++){
                    this.map.addNewColumn(oldValue);
                }
            } else {
                if (UserConfiguration.columnsOfTheMap*map.tiles.get(0).virtualColumnNumber >0){
                    for (; oldValue>UserConfiguration.columnsOfTheMap*map.tiles.get(0).virtualColumnNumber; oldValue--) {
                        this.map.removeColumn();
                    }
                } else {
                    UserConfiguration.setColumnsOfTheMap(oldValue);
                    tmpNumberOfColumnsSlider.setValue(UserConfiguration.columnsOfTheMap);
                }
            }
            this.map.repaint();
        });
        return tmpNumberOfColumnsSlider;
    }
    private static JSlider createSimulationSpeedSlider(){
        JSlider tmpSimulationSpeedSlider = new JSlider(SwingConstants.HORIZONTAL, 100, 2000, 1000);
        tmpSimulationSpeedSlider.addChangeListener(e -> UserConfiguration.speedOfSimulation= tmpSimulationSpeedSlider.getValue());
        tmpSimulationSpeedSlider.setMajorTickSpacing(1900);
        tmpSimulationSpeedSlider.setPaintLabels(true);
        return tmpSimulationSpeedSlider;
    }
    private static JSpinner createOptimalPopulationSpinner(){
        SpinnerModel optimalPopulationModel = new SpinnerNumberModel(UserConfiguration.optimalPopulation, 1, 10, 1);
        JSpinner tmpOptimalPopulationSpinner = new JSpinner(optimalPopulationModel);
        tmpOptimalPopulationSpinner.addChangeListener(e -> UserConfiguration.optimalPopulation=Integer.parseInt(tmpOptimalPopulationSpinner.getValue().toString()));
        return tmpOptimalPopulationSpinner;
    }
    private JSpinner createUnderPopulationSpinner(){
        SpinnerModel underPopulationModel = new SpinnerNumberModel(UserConfiguration.underPopulation, 1, 10, 1);
        JSpinner tmpUnderPopulationSpinner = new JSpinner(underPopulationModel);
        tmpUnderPopulationSpinner.addChangeListener(e -> UserConfiguration.setOptimalPopulation(Integer.parseInt(optimalPopulationSpinner.getValue().toString())));
        return tmpUnderPopulationSpinner;
    }
    private static JSpinner createOverPopulationSpinner(){
        SpinnerModel overPopulationModel = new SpinnerNumberModel(UserConfiguration.overPopulation, 1, 10, 1);
        JSpinner tmpOverPopulationSpinner = new JSpinner(overPopulationModel);
        tmpOverPopulationSpinner.addChangeListener(e -> UserConfiguration.overPopulation=Integer.parseInt(tmpOverPopulationSpinner.getValue().toString()));
        return tmpOverPopulationSpinner;
    }

    private JRadioButton createSquareRadioButton(){
        JRadioButton tmpSquareRadiobutton = new JRadioButton();
        tmpSquareRadiobutton.setSelected(true);
        tmpSquareRadiobutton.setText("Square");
        tmpSquareRadiobutton.addActionListener(e -> {
            UserConfiguration.setTileShape(UserConfiguration.TileShape.SQUARE);
            map.createMap();
            map.repaint();
        });
        return tmpSquareRadiobutton;
    }
    private JRadioButton createHexagonRadioButton(){
        JRadioButton tmpHexagonRadiobutton = new JRadioButton();
        tmpHexagonRadiobutton.setText("Hexagon");
        tmpHexagonRadiobutton.addActionListener(e -> {
            UserConfiguration.setTileShape(UserConfiguration.TileShape.HEXAGON);
            map.createMap();
            map.repaint();
        });
        return tmpHexagonRadiobutton;
    }
    private ButtonGroup createShapeSelection(){
        ButtonGroup tmpShapeSelection = new ButtonGroup();
        tmpShapeSelection.add(squareRadiobutton);
        tmpShapeSelection.add(hexagonRadiobutton);
        return tmpShapeSelection;
    }

    private JButton createSaveButton(){
        JButton tmp_saveButton= new JButton("Save");
        JFileChooser outputChooser= new JFileChooser();
        tmp_saveButton.addActionListener(e-> {
            int approvedValue = outputChooser.showSaveDialog(null);
            if (approvedValue==JFileChooser.APPROVE_OPTION){
                File output= outputChooser.getSelectedFile();
                try {
                    UserConfiguration.saveConfiguration(output);
                } catch (IOException error){
                    JDialog ioError = new JDialog();
                    ioError.add(new JLabel(error.getMessage(),SwingConstants.CENTER));
                    ioError.show();
                }
            }
        });
        return tmp_saveButton;
    }

    private JButton createLoadButton(){
        JButton tmp_loadButton= new JButton("Load");
        JFileChooser inputChooser= new JFileChooser();
        tmp_loadButton.addActionListener(e-> {
            int approvedValue = inputChooser.showSaveDialog(null);
            if (approvedValue==JFileChooser.APPROVE_OPTION){
                File input= inputChooser.getSelectedFile();
                try {
                    UserConfiguration.loadConfiguration(input);
                    Main.deleteApp();
                    Main.createApp();
                } catch (IOException error){
                    JDialog ioError = new JDialog();
                    ioError.add(new JLabel(error.getMessage(),SwingConstants.CENTER));
                    ioError.show();
                }
            }
        });
        return tmp_loadButton;
    }

}
