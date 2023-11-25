import java.util.ArrayList;

class Main{
    private static Application app;
    public static void main(String[] args) {
        createApp(new ArrayList<>());
    }

    public static void createApp(ArrayList<Shape> newList){
        app=new Application(newList);
    }
    public static void deleteApp(){
        app.dispose();
    }
 }