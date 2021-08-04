
public interface Orbit {
    public Vector getPosition(double t);
    public String getName();
    public double getVelocity(double t);
    public double getMass();
    public double getA();
    public double getE();
    public Orbit getParent();
}
