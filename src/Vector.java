package MilkyWay.MilkyWay.src;
import java.lang.Math;
public class Vector {
    private double x;
    private double y;
    private boolean polar;
    /**
     * constructor initializes x and y
     * 
     * @param x x component
     * @param y y component
     */
    public Vector(double x, double y, boolean polar) {
        this.x = x;
        this.y = y;
        this.polar = polar;
    }
    /**
     * getter method for x component
     * 
     * @return returns x component value
     */
    public double getX() {
        return this.x;
    }
    
    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public boolean getPolar(){
        return this.polar;
    }

    public void setPolar(boolean polar){
        this.polar = polar;
    }
    /**
     * method performs vector addition
     * 
     * @param a first input vector
     * @param b second input vector
     * @return returns the sum of vectors a and b
     */
    public static Vector addition(Vector a, Vector b){
        return new Vector(a.getX() + b.getX(), a.getY() + b.getY(), false);
    }

    public static double dotProduct(Vector a, Vector b){
        return (a.getX() * b.getX()) + (a.getY() * b.getY());
    }

    public static Vector scalarProduct(Vector a, double n){
        return new Vector(n * a.getX(), n * a.getY(), false);
    }

    public static double scalarDistance(Vector a, Vector b){
        return Math.abs(magnitude(a) - magnitude(b));
    }
    
    public static Vector vectorDistance(Vector a, Vector b){
        return new Vector(Math.abs(a.getX() - b.getX()), Math.abs(a.getY() - b.getY()), false);
    }

    public static double magnitude(Vector a){
        return Math.sqrt(Math.pow(a.getX(), 2) + Math.pow(a.getY(), 2));
    }

    public static Vector cartToPolar(Vector a){
        if (a.getPolar() == true){
            return a;
        }
        return new Vector(magnitude(a), Math.atan(a.getY() / a.getX()), true);
    }

    public static Vector polarToCart(Vector a){
        if (a.getPolar() == false){
            return a;
        }
        return new Vector(a.getX()*Math.cos(a.getY()), a.getX()*Math.sin(a.getY()), false);
    }
}