package maps;

import java.util.ArrayList;

public class DefaultData extends User{
	//constructor for class. initializes all layers
	public DefaultData(String name, String password) {
		this.username = name;
		this.password = password;
		this.points = new ArrayList<POI>();
		this.layers = new ArrayList<Layer>();
		this.layers.add(new Layer(Driver.WASHROOM_LAYER_NAME));
		this.layers.add(new Layer(Driver.ACCESS_LAYER_NAME));
		this.layers.add(new Layer(Driver.CLASS_LAYER_NAME));
		this.layers.add(new Layer(Driver.LABS_LAYER_NAME));
		this.layers.add(new Layer(Driver.RESTAURANT_LAYER_NAME));
		this.layers.add(new Layer(Driver.EXITS_LAYER_NAME));
	}
	//checks if a layer is a default layer.
	public static boolean isDefaultLayer(String lName)
	{
		System.out.println(lName);
		if (lName.equals(Driver.USER_LAYER_NAME) || lName.equals(Driver.FAVORITES_LAYER_NAME))
			return false;
		return true;
	}
	
}
