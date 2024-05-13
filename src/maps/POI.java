package maps;

import java.awt.Point;
import java.io.Serializable;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
/**
 * This class represents a POI (Point of Interest) on our map.
 * POI's are saved using the java.io.serializable package.
 * We also use the java.io.Point package to save points.
 * Each POI has a point (x,y), a name, and a description.
 * @author leomurphy
 *
 */
public class POI implements Serializable{
    
    private static final long serialVersionUID = 3L;
    private Point point;
    private String name;
    private String description;
    private String layerName;
    private String building;
    private int floor;
    private boolean isDefault;
    private boolean isFavorite;
    
    
    /**
     * Constructor method for a POI object. Creates a 'point' instance variable.
     * Uses 2 integers to create a point variable.
     * @param x used as the x coordinate in our point variable.
     * @param y used as the y coordinate in our point variable.
     * @param name initializes the name instance variable.
     * @param description initializes the description instance variable.
     * @param layerName initializes the layerName instance variable.
     * @param isDefault initializes whether or not the POI is a default.
     * @param isFavourite initializes whether or not the POI is a favourite.
     */
    public POI(int x, int y, String name, String description, String layerName,String type, boolean isDefault, boolean isFavorite)
    {
        //Initializing variables using parameters.
    	this.name = name;
        this.description = description;
        this.point = new Point(x, y);
        this.layerName = layerName;
        this.isDefault = isDefault;
        this.isFavorite = isFavorite;
    }
    /**
     * Constructor method for a POI object. Differs from previous constructor.
     * Takes in an already created point variable.
     * Also uses the building and its floor.
     * @param p initializes the point instance variable.
     * @param name initializes the name instance variable.
     * @param description initializes the description instance variable.
     * @param layerName initializes the layerName instance variable.
     * @param isDefault initializes whether or not the POI is a default.
     * @param isFavourite initializes whether or not the POI is a favourite.
     * @param building initializes the building of the POI.
     * @param floor initializes the floor of the POI.
     */
    public POI (Point p, String name, String description, String layerName, boolean isDefault, boolean isFavorite, String building, int floor)
    {
        this.description = description;
        this.name = name;
        this.point = p;
        this.layerName = layerName;
        this.isDefault = isDefault;
        this.isFavorite = isFavorite;
        this.building = building;
        this.floor = floor;
    }
    
    /**
     * Setter method sets name of POI, checks for duplicates of same name.
     * @param name initializes name variable so long as it's not a duplicate.
     * @throws DuplicatePOIName thrown when a POI of the same name exists.
     */
    public void setName(String name) throws DuplicatePOIName
    {
    	//Using method in user to check if given POI name is valid.
    	if (!Driver.instance.getUser().isValidPoiName(name))
    		throw new DuplicatePOIName("Point with name " + name + " exists");
    	if (!Driver.instance.getDefaultData().isValidPoiName(name))
    		throw new DuplicatePOIName("Point with name " + name + " exists");
    	
    	this.name = name;
    }
    
    /**
     * Setter method for the description variable.
     * @param desc initializes description instance variable.
     */
    public void setDesc(String desc)
    {
    	this.description = desc;
    }
    
    /**
     * Setter method for the isFavourite variable.
     * @param fav initializes isFavourite instance variable.
     */
    public void setFavorite(boolean fav)
    {
    	this.isFavorite = fav;
    	
    	//Obtaining user and adding POI to their favourites.
    	User u = Driver.instance.getUser();
    	u.setPointFavorite(this, fav);
    }
    
    /**
     * Setter method for the layerName variable.
     * @param layerName initializes the layerName variable.
     */
    public void setLayer(String layerName)
    {
    	Driver.instance.getUser().setPointLayer(this, layerName);
    	this.layerName = layerName;
    }
    
    /**
     * Getter method for point instance variable.
     * @return returns the point variable.
     */
    public Point getPoint()
    {
        return this.point;
    }
    /**
     * Getter method for name instance variable.
     * @return returns the name variable.
     */
    public String getName()
    {
        return this.name;
    }
    
    /**
     * Getter for the layer name
     * @return Layer name
     */
    public String getLayerName()
    {
    	return this.layerName;
    }
    
    /**
     * Getter method for the description instance variable.
     * @return returns the description instance variable.
     */
    public String getDesc()
    {
        return this.description;
    }
    
    /**
     * Getter method for the building instance variable.
     * @return returns the building of the POI.
     */
    public String getBuilding()
    {
    	return building;
    }
    
    /**
     * Getter method for the floor instance variable.
     * @return returns the floor of the POI.
     */
    public int getFloor()
    {
    	return floor;
    }
    
    /**
     * Getter method for the isFavorite instance variable.
     * @return returns whether or not the POI is a favorite.
     */
    public boolean getFavorite()
    {
    	return isFavorite;
    }
    
    /**
     * Getter method for the isDefault instance variable
     * @return returns whether or not the POI is a default.
     */
    public boolean getDefault() {
    	return isDefault;
    }
    
    /**
     * toString method used to format the contents of a POI for easier reading.
     */
    @Override
    public String toString()
    {
    	String str = "Name: " + name + ", Description: " + description + ", Coords: (" + point.x + "," + point.y + "), Favorite: " + isFavorite + ", Layer: " + layerName + ", Building: " + building + "-" + floor;
    	return str;
    }
    
}
