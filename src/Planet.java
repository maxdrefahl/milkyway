package MilkyWay.MilkyWay.src;

import java.lang.Math;
public class Planet implements Orbit{
    final static double G = 6.6743e-11;
    final private String name;
    final private double mass;
    final private double rad;
    private double a;
    private double e;
    final private Orbit parent;

    public Planet(String name, double mass, double rad, double a, double e, Orbit parent) {
        this.name = name;
        this.mass = mass;
        this.rad = rad;
        this.a = a;
        this.e = e;
        this.parent = parent;
        Vector position = new Vector(a, 0, true);
    }

    public Orbit getParent(){
        return this.parent;
    }

    public double getMass() {
        return this.mass;
    }

    public double getRad() {
        return this.rad;
    }

    public double getA() {
        return this.a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getE() {
        return this.e;
    }

    public void setE(double e) {
        this.e = e;
    }

    public double getPeriod(){
        return((2 * Math.PI) / Math.sqrt(G * parent.getMass())) * Math.pow(a, 1.5);
    }

    public double getMeanAnomaly(double t){
        return (2 * Math.PI * t) / getPeriod();
    }

    public double getEccAnomaly(double t){
        double temp = getMeanAnomaly(t);
        double numerator;
        for (int i = 0; i < 10; i++){
            numerator = getMeanAnomaly(t) + this.e * Math.sin(temp) - this.e * temp * Math.cos(temp);
            temp = numerator / (1 - this.e * Math.cos(temp));
        }
        return temp;
    }

    public double getTrueAnomaly(double t){
        return 2 * Math.atan(Math.sqrt((1 + this.e) / (1 - this.e)) * Math.tan(getEccAnomaly(t) / 2));
    }

    public Vector getPosition(double t) {
        double theta = getTrueAnomaly(t);
        double radius = (this.a * (1 - this.e * Math.cos(theta))) / (1 + (this.e * Math.cos(theta)));
        return new Vector(radius, theta, true);
    }

    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }

    public double getCurrentVelocity() {
        // TODO Auto-generated method stub
        return 0;
    }
    
}
