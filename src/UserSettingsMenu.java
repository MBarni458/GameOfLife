import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UserSettingsMenu extends JPanel {

    private final MapBuilder map;

    private final JSlider numberOfRowsSlider;
    private final JSlider numberOfColumnsSlider;
    private final JSpinner optimalPopulationSpinner;
    private final JRadioButton squareRadiobutton;
    private final JRadioButton hexagonRadiobutton;

    public UserSettingsMenu(MapBuilder map) {

        super(new GridLayout(26,1));

        this.map=map;

        //Create the form elements
        JButton startButton = createStartButton();
        numberOfRowsSlider= createNumberOfRowsSlider();
        numberOfColumnsSlider= createNumberOfColumnsSlider();
        JSlider simulationSpeedSlider = createSimulationSpeedSlider();
        optimalPopulationSpinner= createOptimalPopulationSpinner();
        JSpinner underPopulationSpinner = createUnderPopulationSpinner();
        JSpinner overPopulationSpinner = createOverPopulationSpinner();
        JSpinner lifeTimeSpinner = createLifeTimeSpinner();
        squareRadiobutton= createSquareRadioButton();
        hexagonRadiobutton= createHexagonRadioButton();
        createShapeSelection();
        JButton saveConfigurationButton = createSaveConfigurationButton();
        JButton loadConfigurationButton = createLoadConfigurationButton();
        JButton saveFigureButton= createSaveFigureButton();
        JButton loadFigureButton= createLoadFigureButton();

        //Add the form elements to the panel
        this.add(startButton);
        this.add(new JLabel("Simulation Settings",SwingConstants.CENTER));
        this.add(new JLabel("Speed of Simulation",SwingConstants.CENTER));
        this.add(simulationSpeedSlider);
        this.add(new JLabel("Optimal Population",SwingConstants.CENTER));
        this.add(optimalPopulationSpinner);
        this.add(new JLabel("Underpopulation",SwingConstants.CENTER));
        this.add(underPopulationSpinner);
        this.add(new JLabel("Overpopulation",SwingConstants.CENTER));
        this.add(overPopulationSpinner);
        this.add(new JLabel("Lifetime",SwingConstants.CENTER));
        this.add(lifeTimeSpinner);
        this.add(new JLabel("Map Settings",SwingConstants.CENTER));
        this.add(new JLabel("Number of Rows",SwingConstants.CENTER));
        this.add(numberOfRowsSlider);
        this.add(new JLabel("Number of Columns",SwingConstants.CENTER));
        this.add(numberOfColumnsSlider);
        this.add(new JLabel("Choose shape"));
        this.add(squareRadiobutton);
        this.add(hexagonRadiobutton);
        this.add(new JLabel("Import/Export configuration",SwingConstants.CENTER));
        this.add(saveConfigurationButton);
        this.add(loadConfigurationButton);
        this.add(new JLabel("Import/Export Figure",SwingConstants.CENTER));
        this.add(saveFigureButton);
        this.add(loadFigureButton);
    }

    private JButton createStartButton(){
        String startText="Start";
        String stopText="Stop";
        JButton  tmpStartButton= new JButton(startText);
        tmpStartButton.addActionListener(e -> {
            try {
                UserConfiguration.setActiveSimulation(!UserConfiguration.activeSimulation);
                tmpStartButton.setText((tmpStartButton.getText().equals(startText)) ? stopText : startText);
                numberOfRowsSlider.setEnabled(!numberOfRowsSlider.isEnabled());
                numberOfColumnsSlider.setEnabled(!numberOfColumnsSlider.isEnabled());
                squareRadiobutton.setEnabled(!squareRadiobutton.isEnabled());
                hexagonRadiobutton.setEnabled(!hexagonRadiobutton.isEnabled());
            } catch(Exception error){
                System.out.println("The simulation failed");
            }
        });
        return tmpStartButton;
    }
    private JSlider createNumberOfRowsSlider(){
        JSlider tmpNumberOfRowsSlider = new JSlider(SwingConstants.HORIZONTAL, 2, 40, UserConfiguration.rowsOfTheMap);
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
        JSlider tmpNumberOfColumnsSlider = new JSlider(SwingConstants.HORIZONTAL, 2, 40, UserConfiguration.columnsOfTheMap);
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
    private JSpinner createLifeTimeSpinner(){
        SpinnerModel lifeTimeModel = new SpinnerNumberModel(UserConfiguration.lifeTime, 0, 10, 1);
        JSpinner tmpLifeTimeSpinner = new JSpinner(lifeTimeModel);
        tmpLifeTimeSpinner.addChangeListener(e -> {
            int difference= (int)tmpLifeTimeSpinner.getValue() -UserConfiguration.lifeTime;
            UserConfiguration.setLifetimeOfACell(Integer.parseInt(tmpLifeTimeSpinner.getValue().toString()));
            for(Shape element:map.tiles){
                element.lifeTime+=difference;
            }
        });
        return tmpLifeTimeSpinner;
    }
    private JRadioButton createSquareRadioButton(){
        JRadioButton tmpSquareRadiobutton = new JRadioButton();
        tmpSquareRadiobutton.setSelected(true);
        tmpSquareRadiobutton.setText("Square");
        tmpSquareRadiobutton.addActionListener(e -> {
            UserConfiguration.setTileShape(UserConfiguration.TileShape.SQUARE);
            map.setShapeChanged();
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
            map.setShapeChanged();
            map.createMap();
            map.repaint();
        });
        return tmpHexagonRadiobutton;
    }
    private void createShapeSelection(){
        ButtonGroup tmpShapeSelection = new ButtonGroup();
        tmpShapeSelection.add(squareRadiobutton);
        tmpShapeSelection.add(hexagonRadiobutton);
    }
    private JButton createSaveConfigurationButton(){
        JButton tmpSaveButton= new JButton("Save Configuration");
        JFileChooser outputChooser= new JFileChooser();
        tmpSaveButton.addActionListener(e-> {
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
        return tmpSaveButton;
    }
    private JButton createLoadConfigurationButton(){
        JButton tmpLoadButton= new JButton("Load Configuration");
        JFileChooser inputChooser= new JFileChooser();
        tmpLoadButton.addActionListener(e-> {
            int approvedValue = inputChooser.showSaveDialog(null);
            if (approvedValue==JFileChooser.APPROVE_OPTION){
                File input= inputChooser.getSelectedFile();
                try {
                    if(UserConfiguration.loadConfiguration(input)){
                        Main.deleteApp();
                        Main.createApp(new ArrayList<>());
                    }
                } catch (IOException error){
                    JDialog ioError = new JDialog();
                    ioError.add(new JLabel(error.getMessage(),SwingConstants.CENTER));
                    ioError.show();
                }
            }
        });
        return tmpLoadButton;
    }
    private JButton createSaveFigureButton(){
        JButton tmpSaveButton= new JButton("Save Figure");
        JFileChooser outputChooser= new JFileChooser();
        tmpSaveButton.addActionListener(e-> {
            int approvedValue = outputChooser.showSaveDialog(null);
            if (approvedValue==JFileChooser.APPROVE_OPTION){
                File output= outputChooser.getSelectedFile();
                Shape.saveFigure(map.tiles, output);
            }
        });
        return tmpSaveButton;
    }
    private JButton createLoadFigureButton(){
        JButton tmpLoadButton= new JButton("Load Figure");
        JFileChooser inputChooser= new JFileChooser();
        tmpLoadButton.addActionListener(e-> {
            int approvedValue = inputChooser.showSaveDialog(null);
            if (approvedValue==JFileChooser.APPROVE_OPTION){
                File input= inputChooser.getSelectedFile();
                Main.deleteApp();
                Main.createApp(Shape.loadFigure(input));
            }
        });
        return tmpLoadButton;
    }
}