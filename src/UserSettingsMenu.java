import javax.swing.*;

public class UserSettingsMenu extends JPanel {
    public UserSettingsMenu() {
        JButton startButton = new JButton("Start");
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 50, 25);

        startButton.addActionListener(e -> {
            UserConfiguration.activeSimulation=!UserConfiguration.activeSimulation;
            System.out.println(UserConfiguration.activeSimulation);
            startButton.setText((startButton.getText().equals("Start"))?"Stop":"Start");
        });

        // Add the button and slider to the panel
        this.add(startButton);
        this.add(slider);
    }
}
