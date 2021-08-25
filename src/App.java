import javafx.event.EventHandler;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
public class App extends Application {
    static Button btn = new Button();
    static Button last = new Button();
    static Button next = new Button();
    static Button toggleLines = new Button();
    static Button toggleTrace = new Button();
    static int cookies = 0;
    static int WIDTH = 600;
    static int HEIGHT = 600;
    static double D = 10;
    static Label cookieAmount = new Label("Cookies: "+ cookies);
    static Canvas canvas = new Canvas(WIDTH, HEIGHT);
    static GraphicsContext gc = canvas.getGraphicsContext2D();
    static double t = 0;
    static ArrayList<Orbit> solarSystem = new ArrayList(100);
    static Planet sun = new Planet("Sun", 3e6, 1, 0, 0, null);
    static double zoom = 30;
    static HashMap<String, Vector> planetMap = new HashMap<String, Vector>();
    static Slider timeScale = new Slider();
    static double speed = 1;
    static int center = 0;
    static Vector[] tracers;
    static int traceIndex = 0;
    static int f = 0;
    static boolean _toggleLines = false;
    static boolean _toggleTrace = false;
    public static void main(String[] args) throws Exception {
        //(Planet sun, String fileName, ArrayList<Orbit> solarSystem)
        FileManager.readFileStream(sun, "Planets.txt", solarSystem);
        // for (Orbit orbit : solarSystem) {
        //     System.out.println(orbit.toString());
        // }
        planetMap.put(sun.getName(), Vector.polarToCart(sun.getPosition(t)));
        // FileManager.writeFile("Output.txt", solarSystem);
        tracers = new Vector[solarSystem.size() * 500];
        launch(args);
    }
    public void start(Stage primaryStage){
        primaryStage.setTitle("Solar System");
        StackPane root = new StackPane();
        cookieAmount.setTranslateY(30);
        btn.setText("Click Me");
        btn.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                cookies++;
                cookieAmount.setText("Cookies: " + cookies);
            }
        });
        toggleTrace.setText("Toggle Tracers");
        toggleTrace.setTranslateY(- (HEIGHT / 2) + 30);
        toggleTrace.setTranslateX(100);
        toggleTrace.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                _toggleTrace = !_toggleTrace;
            }
        });
        toggleLines.setText("Toggle Parent Lines");
        toggleLines.setTranslateY(- (HEIGHT / 2) + 30);
        toggleLines.setTranslateX(-100);
        toggleLines.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                _toggleLines = !_toggleLines;
            }
        });
        last.setTranslateX(- (WIDTH / 2) + 30);
        last.setTranslateY(- (HEIGHT / 2) + 30);
        last.setText("last");
        last.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                for (int i = 0; i < tracers.length; i++){
                    tracers[i] = null;
                }
                center--;
                if (center < 0){
                    center = solarSystem.size() - 1;
                }
            }
        });
        next.setTranslateX((WIDTH / 2) - 30);
        next.setTranslateY(- (HEIGHT / 2) + 30);
        next.setText("next");
        next.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                for (int i = 0; i < tracers.length; i++){
                    tracers[i] = null;
                }
                center++;
                if (center > solarSystem.size() - 1){
                    center = 0;
                }
            }
        });
        timeScale.setMin(0);
        timeScale.setMax(50);
        timeScale.setValue(1);
        timeScale.setTranslateY(HEIGHT/2 - 20);
        timeScale.valueProperty().addListener(new ChangeListener<Number>(){
            public void changed(ObservableValue<? extends Number> OV, Number oldVal, Number newVal){
                for (int i = 0; i < tracers.length; i++){
                    tracers[i] = null;
                }
                speed = (double) newVal;
            }
        });
        canvas.setOnScroll(new EventHandler<ScrollEvent>(){
            public void handle(ScrollEvent scroll){
                for (int i = 0; i < tracers.length; i++){
                    tracers[i] = null;
                }
                if (zoom > 0 && zoom <= 20){
                    zoom += 0.1 * scroll.getDeltaY();
                } else if ((zoom > 20 && zoom <= 50)){
                    zoom += 0.5 * scroll.getDeltaY();
                } else {
                    zoom += scroll.getDeltaY();
                }
                if (zoom < 0){
                    zoom = 0;
                }
                if (zoom > 500){
                    zoom = 500;
                }
            }
        });
        root.getChildren().add(canvas);
        root.getChildren().add(timeScale);
        // root.getChildren().add(btn);
        // root.getChildren().add(cookieAmount);
        root.getChildren().add(toggleLines);
        root.getChildren().add(last);
        root.getChildren().add(next);
        root.getChildren().add(toggleTrace);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        // scene.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        primaryStage.setScene(scene);
        gc.setFill(Color.AQUAMARINE);
        new AnimationTimer(){
            public void handle(long now){
                drawPlanets();
                t += speed;
                f++;
            }
        }.start();
        primaryStage.show();
    }
    public static void drawPlanets(){
        gc.setFill(Color.web("#1f2f3f"));
        gc.fillRect(0, 0, WIDTH, HEIGHT);
        for (Orbit orbit : solarSystem) {
            Vector parentPos;
            if (orbit.getParent() == null){
                parentPos = new Vector(0, 0, false);
            } else {
                parentPos = planetMap.get(orbit.getParent().getName());
            }
            Vector pos = Vector.polarToCart(orbit.getPosition(t));
            pos = Vector.getRelative(parentPos, pos);
            planetMap.put(orbit.getName(), pos);
            pos = Vector.centerPlanet(planetMap.get(solarSystem.get(center).getName()), pos);
            pos = Vector.scalarProduct(pos, zoom);
            if (f % 5 == 0){
                tracers[traceIndex] = pos;
                traceIndex++;
                if (traceIndex >= tracers.length){
                    traceIndex = 0;
                }
            }
            if (_toggleLines && !orbit.getName().equals("Sun")){
                parentPos = Vector.centerPlanet(planetMap.get(solarSystem.get(center).getName()), parentPos);
                parentPos = Vector.scalarProduct(parentPos, zoom);
                gc.setStroke(Color.WHEAT);
                gc.strokeLine(WIDTH / 2 + pos.getX(), HEIGHT / 2 + pos.getY()
                    , WIDTH / 2 + parentPos.getX(), HEIGHT / 2 + parentPos.getY());
            }
            gc.setFill(Color.WHEAT);
            pos = Vector.adjustDiameter(pos, D / 2);
            gc.fillOval(WIDTH / 2 + pos.getX(), HEIGHT / 2 + pos.getY(), D, D);
            gc.fillText(orbit.getName(), WIDTH / 2 + pos.getX(), HEIGHT / 2 + pos.getY());
        }
        if (_toggleTrace){
            for (int i = 0; i < tracers.length; i++){
                if (tracers[i] != null){
                    gc.fillOval(WIDTH / 2 + tracers[i].getX(), HEIGHT / 2 + tracers[i].getY(), 2, 2);
                }
            }
        }
    }
}