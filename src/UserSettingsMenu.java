import javax.swing.*;

public class UserSettingsMenu extends JPanel {
    public UserSettingsMenu() {
        JButton startButton = new JButton("Start");
        JSlider velocitySlider = new JSlider(JSlider.HORIZONTAL, 100, 2000, 1000);

        startButton.addActionListener(e -> {
            UserConfiguration.activeSimulation=!UserConfiguration.activeSimulation;
            startButton.setText((startButton.getText().equals("Start"))?"Stop":"Start");
        });

        SpinnerModel optimalPopulationModel = new SpinnerNumberModel(UserConfiguration.optimalPopulation, 1, 10, 1);
        JSpinner optimalPopulationSpinner = new JSpinner(optimalPopulationModel);

        optimalPopulationSpinner.addChangeListener(e -> UserConfiguration.optimalPopulation=Integer.parseInt(optimalPopulationSpinner.getValue().toString()));

        velocitySlider.addChangeListener(e -> UserConfiguration.speedOfSimulation=velocitySlider.getValue());

        this.add(startButton);
        this.add(velocitySlider);
        this.add(optimalPopulationSpinner);
    }
}
