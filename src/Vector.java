
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
    /**
     * setter method for x component
     * 
     * @param x sets x component value
     */
    public void setX(double x) {
        this.x = x;
    }
    /**
     * getter method for y component
     * 
     * @return returns y component value
     */
    public double getY() {
        return this.y;
    }
    /**
     * setter method for y component
     * @param y sets y component
     */
    public void setY(double y) {
        this.y = y;
    }
    /**
     * getter method for boolean value of polar
     * 
     * @return true if vector is polar, false if vector is cartesian
     */
    public boolean getPolar(){
        return this.polar;
    }
    /**
     * setter method for polar
     * 
     * @param polar
     */
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
    /**
     * method performs dot product between two vectors
     * @param a first input vector
     * @param b second input vector
     * @return returns vector dot product
     */
    public static double dotProduct(Vector a, Vector b){
        return (a.getX() * b.getX()) + (a.getY() * b.getY());
    }
    /**
     * method multiplies vector by a scalar
     * 
     * @param a vector being multiplies
     * @param n scalar by which the vector is being multiplied
     * @return returns new vector after scalar multiplication is performed
     */
    public static Vector scalarProduct(Vector a, double n){
        return new Vector(n * a.getX(), n * a.getY(), false);
    }
    /**
     * finds the scalar distance between two vectors
     * @param a first vector
     * @param b second vector
     * @return distance between vectors a double
     */
    public static double scalarDistance(Vector a, Vector b){
        return Math.abs(magnitude(a) - magnitude(b));
    }
    /**
     * finds the vector distance between two vectors
     * 
     * @param a first vector
     * @param b second vector
     * @return vector distance between vectors
     */
    public static Vector vectorDistance(Vector a, Vector b){
        return new Vector(Math.abs(a.getX() - b.getX()), Math.abs(a.getY() - b.getY()), false);
    }
    /**
     * finds the magnitude of a cartesian vector
     * 
     * @param a vector
     * @return magnitude as a double
     */
    public static double magnitude(Vector a){
        return Math.sqrt(Math.pow(a.getX(), 2) + Math.pow(a.getY(), 2));
    }
    /**
     * converts a cartesian vector to polar
     * 
     * @param a cartesian vector
     * @return polar vector
     */
    public static Vector cartToPolar(Vector a){
        if (a.getPolar() == true){
            return a;
        }
        return new Vector(magnitude(a), Math.atan(a.getY() / a.getX()), true);
    }
    /**
     * 
     * 
     * @param a parent position vector
     * @param b child position vector
     * @return
     */
    public static Vector getRelative(Vector a, Vector b){
        if (a.getPolar() == true){
            return null;
        }
        return new Vector(a.getX() + b.getX(), a.getY() + b.getY(), false);
    }
    /**
     * recenters planet
     * 
     * @param a new center
     * @param b planet being shifted
     * @return
     */
    public static Vector centerPlanet(Vector a, Vector b){
        if (a.getPolar() == true){
            return null;
        }
        return new Vector(a.getX() - b.getX(), a.getY() - b.getY(), false);
    }
    public static Vector adjustDiameter(Vector a, double shift){
        if (a.getPolar() == true){
            return null;
        }
        return new Vector(a.getX() - shift, a.getY(), false);
    }
    /**
     * converts polar vector into cartesian vector
     * 
     * @param a polar vector
     * @return cartesian vector
     */
    public static Vector polarToCart(Vector a){
        if (a.getPolar() == false){
            return a;
        }
        return new Vector(a.getX()*Math.cos(a.getY()), a.getX()*Math.sin(a.getY()), false);
    }
}