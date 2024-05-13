package maps;

/**
 * WeatherData class is used to represent the data pulled from the online weather forecast.
 * Used in WeatherAPI feature.
 * @author leomurphy
 *
 */
public class WeatherData {
	private double temperature;
    private String description;

    /**
     * Constructor class for WeatherData.
     * @param temperature used to intialize the temperature instance variable.
     * @param description used to initialize the description instance variable.
     */
    public WeatherData(double temperature, String description) {
        this.temperature = temperature;
        this.description = description;
      
    }
    /**
     * Modified toString method formats the temperature and description to be more visually pleasing.
     */
    @Override
    public String toString() {
        return "The temperature now in the city of London is " + this.temperature+" celsius with " + this.description+".";
    }
    
    /**
     * Getter method for temperature variable.
     * @return returns temperature instance variable.
     */
    public double getTemperature() {
    	return this.temperature;
    }
    
    /**
     * Getter method for description variable.
     * @return returns description instance variable.
     */
    public String getDescription() {
    	return this.description;
    }
    

}
