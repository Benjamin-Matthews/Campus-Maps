package maps;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONObject;

/**
 * WeatherAPIClient class takes the current weather of London Ontario and uses it in the application.
 * @author leomurphy
 *
 */
public class WeatherApiClient {
    private static final String API_KEY = "0da630753ddfb7af4a6d085bd2c65f3b";
    private static final String BASE_URL = "https://api.openweathermap.org/data/2.5/weather";

    /**
     * getCurrentWeather method takes the weather from the online London forecast.
     * Stores the data as a WeatherData object and returns it.
     * @return WeatherData object that holds the temperature and description of weaher.
     * @throws Exception throws a runtime excepton if the operation fails.
     */
    public WeatherData getCurrentWeather() throws Exception {
        //Pulls data using url
    	HttpClient client = HttpClient.newHttpClient();
        String url = String.format("%s?q=%s&appid=%s&units=metric", BASE_URL, "London,CA", API_KEY);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Failed to fetch weather data");
        }
        
        //Creates JSONObject to hold the response and obtain the necessary data.
        JSONObject jsonResponse = new JSONObject(response.body());
        JSONObject mainObject = jsonResponse.getJSONObject("main");
        double temperature = mainObject.getDouble("temp");

        //Takes necessary data and stores it in a WeatherData object.
        JSONObject weatherObject = jsonResponse.getJSONArray("weather").getJSONObject(0);
        String description = weatherObject.getString("description");

        WeatherData weatherData = new WeatherData(temperature, description);

        //Returns the WeatherData object.
        return weatherData;
    }
}