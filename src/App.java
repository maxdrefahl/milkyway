package MilkyWay.MilkyWay.src;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        ArrayList<Orbit> solarSystem = new ArrayList(100);
        Planet sun = new Planet("Sun", 3e6, 1, 0, 0, null);
        Planet earth = new Planet("Earth", 1, 1, 1, 0.0167, sun);
        Vector pos0 = earth.getPosition(0);
        Vector pos = earth.getPosition(1);
        System.out.println(pos.getX()+", "+pos0.getY()+pos0.getPolar());
        double i = 1;
        // while(pos.getY() != 0){
        //     pos = earth.getPosition(i);
        //     if(i % 10 == 0){
        //         System.out.println(pos.getX()+", "+pos.getY());
        //     }
        //     i++;
        // }
        System.out.println(earth.getCurrentVelocity(pos0.getX()));
    }
}
