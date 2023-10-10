//The ShapeConverter's main task is to help the other classes to work with the different kinds of shapes
public class ShapeConverter {

    public static Shape newTile(int[] xPoints,int[] yPoints){
        switch (UserConfiguration.tileShape){
            case Square:{
                break;
            }
            case Hexagon:
                return new Hexagon(xPoints,yPoints);
            default:
                return null;
        }
        return null;
    }

    public static int[] defaultXPositions(int xOffset){
        switch (UserConfiguration.tileShape){
            case Hexagon:{
                return Hexagon.defaultXPositions(xOffset);
            }
        }
        return null;
    }
    public static int[] defaultYPositions(int yOffset){
        switch (UserConfiguration.tileShape){
            case Hexagon:{
                return Hexagon.defaultYPositions(yOffset);
            }
        }
        return null;
    }

}
