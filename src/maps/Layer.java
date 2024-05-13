package maps;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * Layer class represents a layer in one of our maps. 
 * Has a layer string variable that carries the name of the current layer.
 * @author leomurphy
 *
 */
public class Layer implements Serializable{
	private String layer;
	private static final long serialVersionUID = 2;
	private ArrayList<POI> points;
	private boolean visible = false;
	
	/**
	 * Constructor method that initializes our layer variable.
	 * @param layer initializes layer instance variable.
	 */
	 public Layer(String layer) {
        this.layer = layer;
        
        points = new ArrayList<POI>();
    }

    /**
     * Getter method for layer instance variable.
     * @return returns layer variable.
     */
    public String getName() {
        return this.layer;
    }
    
    /**
     * Adds a given POI to the points array list for the layer.
     * @param p added to points.
     */
    public void addPoint(POI p)
    {
    	points.add(p);
    }
    
    /**
     * Removes a given POI from the points array list for the layer.
     * @param p
     */
    public void removePoint(POI p) {
    	points.remove(p);
    }
    
    /**
     * Getter method returns the points instance variable.
     * @return returns points variable.
     */
    public ArrayList<POI> getPoints()
    {
    	return points;
    }
    
    /**
     * Getter method returns visible instance variable
     * @return returns visible variable.
     */
    public boolean getVisible()
    {
    	return visible;
    }
    
    /**
     * Setter method sets the visible variable.
     * @param v used to initialize the visible instance variable.
     */
    public void setVisible(boolean v)
    {
    	visible = v;
    }
    
}
