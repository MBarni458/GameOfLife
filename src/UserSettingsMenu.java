import javax.swing.*;
import java.awt.*;

public class UserSettingsMenu extends JPanel {

    private MapBuilder map;
    public UserSettingsMenu(MapBuilder map) {

        super(new GridLayout(20,2));

        this.map=map;
        JButton startButton = new JButton("Start");

        startButton.addActionListener(e -> {
            UserConfiguration.activeSimulation=!UserConfiguration.activeSimulation;
            startButton.setText((startButton.getText().equals("Start"))?"Stop":"Start");
        });

        JSlider simulationSpeedSlider = new JSlider(JSlider.HORIZONTAL, 100, 2000, 1000);
        simulationSpeedSlider.addChangeListener(e -> UserConfiguration.speedOfSimulation=simulationSpeedSlider.getValue());
        simulationSpeedSlider.setMajorTickSpacing(1900);
        simulationSpeedSlider.setPaintLabels(true);

        SpinnerModel optimalPopulationModel = new SpinnerNumberModel(UserConfiguration.optimalPopulation, 1, 10, 1);
        JSpinner optimalPopulationSpinner = new JSpinner(optimalPopulationModel);
        optimalPopulationSpinner.addChangeListener(e -> UserConfiguration.optimalPopulation=Integer.parseInt(optimalPopulationSpinner.getValue().toString()));

        SpinnerModel underPopulationModel = new SpinnerNumberModel(UserConfiguration.underPopulation, 1, 10, 1);
        JSpinner underPopulationSpinner = new JSpinner(underPopulationModel);
        underPopulationSpinner.addChangeListener(e -> UserConfiguration.optimalPopulation=Integer.parseInt(optimalPopulationSpinner.getValue().toString()));

        SpinnerModel overPopulationModel = new SpinnerNumberModel(UserConfiguration.overPopulation, 1, 10, 1);
        JSpinner overPopulationSpinner = new JSpinner(overPopulationModel);
        overPopulationSpinner.addChangeListener(e -> UserConfiguration.overPopulation=Integer.parseInt(overPopulationSpinner.getValue().toString()));

        JSlider numberOfRowsSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, UserConfiguration.rowsOfTheMap);
        numberOfRowsSlider.addChangeListener(e -> {
            int oldValue=UserConfiguration.rowsOfTheMap;
                UserConfiguration.rowsOfTheMap=numberOfRowsSlider.getValue();
                if (oldValue<UserConfiguration.rowsOfTheMap && UserConfiguration.rowsOfTheMap<101){
                        this.map.addNewLine();
                } else {
                    if (UserConfiguration.rowsOfTheMap>0){
                            this.map.removeLine();
                    } else {
                        UserConfiguration.rowsOfTheMap=oldValue;
                        numberOfRowsSlider.setValue(UserConfiguration.rowsOfTheMap);
                    }
                }
            this.map.repaint();
        });
        numberOfRowsSlider.setMajorTickSpacing(100);
        numberOfRowsSlider.setMinorTickSpacing(10);

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
    }
}
