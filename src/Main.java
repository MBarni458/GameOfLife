import javax.swing.*;

class Main{
    public static void main(String[] args) {

        UserConfiguration.tileShape= UserConfiguration.TileShape.Square;

        JFrame frame = new JFrame("Hexagon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MapBuilder());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
 }