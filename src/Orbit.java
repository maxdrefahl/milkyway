package MilkyWay.MilkyWay.src;

public interface Orbit {
    public Vector getPosition(double t);
    public String getName();
    public double getCurrentVelocity();
    public double getMass();
}
