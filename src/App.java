import java.util.ArrayList;
public class App {
    public static void main(String[] args) throws Exception {
        ArrayList<Orbit> solarSystem = new ArrayList(100);
        Planet sun = new Planet("Sun", 3e6, 1, 0, 0, null);
        //(Planet sun, String fileName, ArrayList<Orbit> solarSystem)
        FileManager.readFile(sun, "Planets.txt", solarSystem);
        // for (Orbit orbit : solarSystem) {
        //     System.out.println(orbit.toString());
        // }
        FileManager.writeFile("Output.txt", solarSystem);
    }
}
