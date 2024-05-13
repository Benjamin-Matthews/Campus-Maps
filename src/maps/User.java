package maps;

import java.util.ArrayList;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * User class holds all of user data.
 * This includes their username, password, admin status, and an arraylist of their POI's.
 * Implements java.util.Arraylist to hold POI's
 * Implements java.io.serializable, along with ObjectOutputStream and FileOutputStream to save user data.
 * @author leomurphy, BruceScott
 *
 */
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	protected String username;
	protected String password;
	protected ArrayList<POI> points;
	protected ArrayList<Layer> layers;
	
	/**
	 * Empty constructor to allow DefaultData class to not call other constructor
	 */
	public User()
	{
		// GRRRR woof WOOF
	}
	
	/**
	 * Constructor method that initializes our instance variables.
	 * Creates a new arraylist to hold our POI's.
	 * @param name initializes username instance variable.
	 * @param password initializes password instance variable.
	 * @param isadmin initializes admin instance variable.
	 */
	public User(String name, String password) {		
		this.username = name;
		this.password = password;
		this.points = new ArrayList<POI>();
		this.layers = new ArrayList<Layer>();
		this.layers.add(new Layer(Driver.USER_LAYER_NAME));
		this.layers.add(new Layer(Driver.FAVORITES_LAYER_NAME));
		
		// Empty layers for saving visibility info
		this.layers.add(new Layer(Driver.WASHROOM_LAYER_NAME));
		this.layers.add(new Layer(Driver.ACCESS_LAYER_NAME));
		this.layers.add(new Layer(Driver.CLASS_LAYER_NAME));
		this.layers.add(new Layer(Driver.LABS_LAYER_NAME));
		this.layers.add(new Layer(Driver.RESTAURANT_LAYER_NAME));
		this.layers.add(new Layer(Driver.EXITS_LAYER_NAME));
	}
	/**
	 * ToString method that formats a user's information into a string and returns it.
	 * Will be used primarily for testing purposes.
	 */
	public String toString() {
		String userRepresented = "";
		userRepresented += ("Username: " + this.username);
		userRepresented += ("\nPassword: " + this.password);
		userRepresented += ("\nPoints: ");
		for (POI p : points)
		{
			userRepresented += "\n" + p.toString();
		}
		return userRepresented + "\n";
		
	}
	
	/**
	 * Takes in a given name of a POI and checks if it exists inside of favourites.
	 * Returns true if POI is in favourite, false if not.
	 * @param pName name of a certain POI.
	 * @return returns whether or not the given POI is a favourite
	 */
	public boolean isPointFavorite(String pName)
	{
		//Grabbing the current favourites layer from the Driver.
		ArrayList<POI> favorites = getLayer(Driver.FAVORITES_LAYER_NAME).getPoints();
		
		//Checking if our POI exists in the current favourites.
		for (POI p : favorites)
		{
			//If it does, returns true. If not, returns false.
			if (p.getName().equals(pName))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Given a layerName and a boolean value, sets the visibility of a layer.
	 * @param layerName name of the Layer to become visible or invisible.
	 * @param visible if true, layer becomes visible. If false, becomes invisible.
	 */
	public void setLayerVisibility(String layerName, boolean visible)
	{
		//Searching for the given layer name in our layers.
		for (Layer l : layers)
		{
			//If found, sets the layer to either visible (true) or invisible (false).
			if (l.getName().equals(layerName))
			{
				l.setVisible(visible);
				
				return;
			}
		}
	}
	
	/**
	 * Returns the visibility status of a given layer. True = visible, False = Invisible.
	 * @param layerName name of layer we want to see the visibility of.
	 * @return returns true if our layer is visible, false if not.
	 */
	public boolean getLayerVisibility(String layerName)
	{
		//Getting our layer then getting its visibility.
		Layer l = getLayer(layerName);
		return l.getVisible();
	}
	
	/**
	 * Adds a layer to our layer list.
	 * @param newLayer our layer to be added
	 * @throws DuplicateLayerException thrown if layerName is a duplicate.
	 */
	public void addLayer(Layer newLayer) throws DuplicateLayerException
	{
		//Checks if layer name is a duplicate. 
		for (Layer l : layers)
		{
			//If duplicate, throws exception.
			if (l.getName().equals(newLayer.getName()))
			{
				throw new DuplicateLayerException("Layer with name: " + newLayer.getName() + ", exists");
			}
			
		}
		//If not a duplicate, layer is added.
		layers.add(newLayer);
	}
	
	
	/**
	 * addPoint method adds a new point to the users 'points' ArrayList.
	 * If the name is found in any of the POI's, throws an exception.
	 * @param newPoint given a POI object, adds it to the users 'points' ArrayList.
	 * @throws DuplicatePOIName; a custom exception.
	 * @throws NonExistentLayerException 
	 */
	public void addPoint(POI point) throws DuplicatePOIName, NonExistentLayerException {
		//Getting the layerName of our point.
		String layerName = point.getLayerName();
		
		//Checking if the layer exists.
		Layer layer = null;
		for (Layer l : layers)
		{
			if (l.getName().equals(layerName))
			{
				layer = l;
				break;
			}
		}
		
		//If layer does not exist, throws exception.
		if (layer == null)
		{
			throw new NonExistentLayerException("Layer by name of: " + layerName + ", does not exist");
		}
		
		//Checking if POI is a duplicate, throwing exception if it is.
        for (POI p : points) {
            if (p.getName().equals(point.getName())) {
                throw new DuplicatePOIName("A POI with the name '" + point.getName() + "' already exists.");
            }
        }
        
        //If no exceptions, adds the point to the points list and the layers list.
        points.add(point);
        layer.addPoint(point);
        
        //If point is a favourite, adds to favourites
        if (point.getFavorite())
        	layers.get(layers.size() - 2).addPoint(point);
        
        //Saves the data, then prints to the screen.
        saveData();
    }
	
	/**
	 * Checks if there are any other POI's with same name.
	 * @param pName POI in question.
	 * @return true if POI name is valid, false if invalid.
	 */
	public boolean isValidPoiName(String pName)
	{	
        //Checking for duplicates.
		for (POI p : points) {
            if (p.getName().equals(pName)) {
                return false;
            }
        }
		
		//If no duplicates found, returns true.
		return true;
	}
	
	/**
	 * deletePoint deletes a point in the users 'points' ArrayList.
	 * If the name is not found in any of the POI's, throws an exception.
	 * @param name used to identify the POI to be deleted.
	 * @throws NonExistentPOI; a custom exception.
	 */
	public void deletePoint(String name) throws NonExistentPOI {
        //Creating a variable for if the POI exists.
		boolean poiFound = false;
		
		for (Layer l : layers)
		{
			ArrayList<POI> currentPoints = l.getPoints();
			for (int i = 0; i < currentPoints.size(); i++)
			{
				if (currentPoints.get(i).getName().equals(name))
				{
					l.removePoint(currentPoints.get(i));
				}
			}
			for (POI p : l.getPoints())
			{
				if (p.getName().equals(name))
				{
					l.removePoint(p);
				}
			}
		}
       
		//Serching for POI by name in the list, removing if found.
		for (POI p : points) {
            if (p.getName().equals(name)) {
                
            	//Setting to true to avoid throwing exception.
            	poiFound = true;
                points.remove(p);
                
                //If it's a favourite, removes it from that list as well.
                if (p.getFavorite())
                {
                	Layer fav = getLayer(Driver.FAVORITES_LAYER_NAME);
                	fav.removePoint(p);
                }
                
                break;
            }
        }
		//If it is not found, throws exception.
        if (!poiFound) {
            throw new NonExistentPOI("A POI with the name '" + name + "' does not exist.");
        }
    }
	
	/**
	 * returns a layer given it's name.
	 * @param layerName the layer name we want to find and return.
	 * @return returns layer of the given name.
	 */
	public Layer getLayer(String layerName)
	{
		//Checking there is a layer of that name. If found, returns it.
		for (Layer l : layers)
		{
			if (l.getName().equals(layerName))
			{
				return l;
			}
		}
		//If layer not found, returns null.
		return null;
	}
	
	/**
	 * Sets a given POI to a given Layer
	 * @param p the POI we which to set it's layer.
	 * @param layerName the layer we wish to set our POI to.
	 */
	public void setPointLayer(POI p, String layerName)
	{
		//Getting the existing layer, removing our point from it.
		getLayer(p.getLayerName()).removePoint(p);
		//Adding our point to the new layer.
		getLayer(layerName).addPoint(p);
	}
	
	/**
	 * Getter method for the layers instance variable.
	 * @return returns layers variable.
	 */
	public ArrayList<Layer> getLayers() {
		return this.layers;
	}
	
	/**
	 * Getter method for the points instance variable.
	 * @return returns points variable.
	 */
	public ArrayList<POI> getPoints() {
		return this.points;
	}
	
	/**
	 * Getter method for the password instance variable.
	 * @return returns password variable.
	 */
	public String getPassword() {
		return this.password;
	}
	
	/**
	 * Gets the user's list of favourites.
	 * @return returns the current user favourite list.
	 */
	public ArrayList<POI> getFavorites()
	{
		return getLayer(Driver.FAVORITES_LAYER_NAME).getPoints();
	}
	
	/**
	 * Getter method for user defined points.
	 * @return
	 */
	public ArrayList<POI> getUserDefined()
	{
		return getLayer(Driver.USER_LAYER_NAME).getPoints();
	}
	
	/**
	 * Getter method for username instance variable.
	 * @return returns username variable.
	 */
	public String getUsername() { 
		return this.username;
	}
	
	/**
	 * setPointFavorite() method takes a given POI and a boolean value.
	 * If it is true, POI is added to this user's favourites.
	 * @param p if favourite, added to user's favourites.
	 * @param isFav boolean value that represents if this POI is a favourite or not.
	 */
	public void setPointFavorite(POI p, boolean isFav)
	{
		if (isFav)
		{
			getLayer(Driver.FAVORITES_LAYER_NAME).addPoint(p);
		}
		else
		{
			getLayer(Driver.FAVORITES_LAYER_NAME).removePoint(p);
		}
	}
	
	/**
	 * saveData method uses the FileOutputStream and ObjectOuputStream to save user data.
	 * Also uses java.io.serializable.
	 * @return returns 0 if successful, -1 if an IOException is caught.
	 */
	public int saveData()
    {    
        try
        {
            FileOutputStream fileOutput = new FileOutputStream(username + ".ser");
            ObjectOutputStream objOutput = new ObjectOutputStream(fileOutput);
            objOutput.writeObject(this);
            return 0;
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return -1;
        }
    }
	
}
