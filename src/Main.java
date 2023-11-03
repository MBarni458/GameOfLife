class Main{
    private static Application app;
    public static void main(String[] args) {
        createApp();
    }
    public static void createApp(){
        app=new Application();
    }
    public static void deleteApp(){
        app.dispose();
    }

 }