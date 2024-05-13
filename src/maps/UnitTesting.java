package maps;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit Testing Class has JUnit5 tests for every public method.
 * GUI components are NOT TESTED HERE.
 * This includes AboutWindow, AddPOIWindow, EditPOIWindow, LoginWindow, MapWindow, and WeatherApiClient.
 * Those classes will be tested in other ways. Unit tests will not suffice.
 * @author leomurphy
 *
 */
class UnitTesting {
	
	//DUPLICATE LAYER EXCEPTION CLASS
	@Test
    public void testDuplicateLayerExceptionConstructor() {
        //Creating a case to test.
		String expected = "This is a custom error message.";
        DuplicateLayerException exception = new DuplicateLayerException(expected);
        //Checking our case matches up with our method.
        assertNotNull(exception);
        assertEquals(expected, exception.getMessage());
    }
	
	//DUPLICATE POI NAME CLASS
	@Test
    public void testDuplicatePOINameConstructor() {
        
		//Creating a case to test.
		String expected = "POI name already exists";
        DuplicatePOIName exception = new DuplicatePOIName(expected);
        
        //Checking our case matches up with our method
        assertNotNull(exception);
        assertEquals(expected, exception.getMessage());
    }
	
	//NONEXISTENT LAYER EXCEPTION CLASS
	@Test
    public void testNonExistentLayerExceptionConstructor() {
        
		//Creating a case to test.
		String expected = "Layer does not exist";
        NonExistentLayerException exception = new NonExistentLayerException(expected);
        
        //Checking our case matches up with our method.
        assertNotNull(exception);
        assertEquals(expected, exception.getMessage());
    }
	
	//NONEXISTENT POI CLASS
	@Test
    public void testNonExistentPOIConstructor() {
        
		//Creating a case to test.
		String expected = "POI does not exist";
        NonExistentPOI exception = new NonExistentPOI(expected);
        
        //Checking our case matches up with our method.
        assertNotNull(exception);
        assertEquals(expected, exception.getMessage());
    }
	
	
	//WEATHER DATA CLASS
	@Test
    public void testWeatherDataConstructor() {
        
		//Creating a case to test the construction.
		double temperature = 20.5;
        String description = "Cloudy";
        WeatherData weatherData = new WeatherData(temperature, description);

        //Seeing if our constructor initializes variables correctly.
        assertEquals(temperature, weatherData.getTemperature());
        assertEquals(description, weatherData.getDescription());
    }
	
	@Test
    public void testWeatherToString() {
        
		//Creating case to test the string.
		double temperature = 20.5;
        String description = "Cloudy";
        WeatherData weatherData = new WeatherData(temperature, description);

        //Comparing our expected with the method itself.
        String result = weatherData.toString();
        assertEquals("The temperature now in the city of London is 20.5 celsius with Cloudy.", result);
    }
	
	@Test
    public void testGetTemperature() {
        
		//Creating a test case and comparing the expected result with the method itself.
		WeatherData weatherData = new WeatherData(25.0, "Sunny");
        double temperature = weatherData.getTemperature();
        assertEquals(25.0, temperature, 0.01);
    }
	
	@Test
    public void testGetDescription() {
		
		//Creating a test case and comparing the expected result with the method itself.
		WeatherData weatherData = new WeatherData(25.0, "Sunny");
        String description = weatherData.getDescription();
        assertEquals("Sunny", description);
    }
	
	
	//POI CLASS
	@Test
    public void testPOIConstructor1() {
        
		//Creating test case
		POI poi = new POI(10,20,"Test POI","This is a test POI","Test Layer","Test Type",true,false);
		
		//Checking our case is not null.
        assertNotNull(poi);
        
        //Checking all variables have been initialized correctly.
        assertEquals("Test POI", poi.getName());
        assertEquals("This is a test POI", poi.getDesc());
        assertEquals(10, poi.getPoint().getX());
        assertEquals(20, poi.getPoint().getY());
        assertEquals("Test Layer", poi.getLayerName());
        assertTrue(poi.getDefault());
        assertFalse(poi.getFavorite());
    }
	
    @Test
    public void testPOIConstructor2() {
        
    	//Creating test case.
    	Point p = new Point(10,20);
    	POI poi = new POI(p,"Test POI","This is a test POI","Test Layer",true,false,"Test Building",1);
    	
    	//Checking our case is not null.
        assertNotNull(poi);
        
       //Checking all variables have been initialized correctly.
        assertEquals("Test POI", poi.getName());
        assertEquals("This is a test POI", poi.getDesc());
        assertEquals(new Point(10, 20), poi.getPoint());
        assertEquals("Test Layer", poi.getLayerName());
        assertTrue(poi.getDefault());
        assertFalse(poi.getFavorite());
        assertEquals("Test Building", poi.getBuilding());
        assertEquals(1, poi.getFloor());
    }
    
    @Test
    public void testPOIsetName() throws DuplicatePOIName {
    	//Creating temporary driver and user instance.
    	Driver driver = new Driver();
    	driver.instance = driver;
    	driver.instance.setUser(new User("User","Password"));
    	
    	//Creating test case.
    	Point p = new Point(10,20);
    	POI poi = new POI(p,"Test POI","This is a test POI","Test Layer",true,false,"Test Building",1);
    	
    	//Checking method works on our test case.
    	poi.setName("Name Test");
    	assertEquals("Name Test",poi.getName());
    }
    
    @Test
    void testPOISetDesc() {
        
    	//Creating test case.
    	Point p = new Point(10,20);
    	POI poi = new POI(p,"Test POI","This is a test POI","Test Layer",true,false,"Test Building",1);
    
    	//Checking method
        poi.setDesc("Test description");
        assertEquals("Test description", poi.getDesc());
    }

    @Test
    void testPOISetFavorite() {
        
    	//Creating test case.
    	Point p = new Point(10,20);
    	POI poi = new POI(p,"Test POI","This is a test POI","Test Layer",true,false,"Test Building",1);
    	
    	//Checking method.
        poi.setFavorite(true);
        assertTrue(poi.getFavorite());
    }

    @Test
    void testPOISetLayer() {
    	//Creating temporary driver and user instance.
    	Driver driver = new Driver();
    	driver.instance = driver;
    	User user = new User("User","Password");
    	driver.instance.setUser(user);
    
    	
    	//Creating test case.
    	Point p = new Point(10,20);
    	POI poi = new POI(p,"Test POI","This is a test POI","Washrooms",true,false,"Test Building",1);
 
    	//Checking method.
        poi.setLayer("Washrooms");
        assertEquals("Washrooms", poi.getLayerName());
    }
    
    @Test
	public void testPOIGetPoint() {
		
    	//Creating test case.
    	Point p = new Point(10,20);
    	POI poi = new POI(p,"Test POI","This is a test POI","Test Layer",false,true,"Test Building",3);
    	
    	// Test getPoint() method
		Point expected = new Point(10, 20);
		Point actual = poi.getPoint();
		assertEquals(expected, actual, "Failed to get correct Point object");
	}
	
	@Test
	public void testPOIGetName() {
		
		//Creating test case.
    	Point p = new Point(10,20);
    	POI poi = new POI(p,"Test POI","This is a test POI","Test Layer",false,true,"Test Building",3);
    	
		// Test getName() method
		String expected = "Test POI";
		String actual = poi.getName();
		assertEquals(expected, actual, "Failed to get correct name");
	}
	
	@Test
	public void testPOIGetLayerName() {
		
		//Creating test case.
    	Point p = new Point(10,20);
    	POI poi = new POI(p,"Test POI","This is a test POI","Test Layer",false,true,"Test Building",3);
    	
		// Test getLayerName() method
		String expected = "Test Layer";
		String actual = poi.getLayerName();
		assertEquals(expected, actual, "Failed to get correct layer name");
	}
	
	@Test
	public void testPOIGetDesc() {
		
		//Creating test case.
    	Point p = new Point(10,20);
    	POI poi = new POI(p,"Test POI","This is a test POI","Test Layer",false,true,"Test Building",3);
    	
		// Test getDesc() method
		String expected = "This is a test POI";
		String actual = poi.getDesc();
		assertEquals(expected, actual, "Failed to get correct description");
	}
	
	@Test
	public void testPOIGetBuilding() {
		
		//Creating test case.
    	Point p = new Point(10,20);
    	POI poi = new POI(p,"Test POI","This is a test POI","Test Layer",false,true,"Test Building",3);
    	
		// Test getBuilding() method
		String expected = "Test Building";
		String actual = poi.getBuilding();
		assertEquals(expected, actual, "Failed to get correct building");
	}
	
	@Test
	public void testPOIGetFloor() {
		
		//Creating test case.
    	Point p = new Point(10,20);
    	POI poi = new POI(p,"Test POI","This is a test POI","Test Layer",false,true,"Test Building",3);
    	
		// Test getFloor() method
		int expected = 3;
		int actual = poi.getFloor();
		assertEquals(expected, actual, "Failed to get correct floor");
	}
	
	@Test
	public void testPOIGetFavorite() {
		
		//Creating test case.
    	Point p = new Point(10,20);
    	POI poi = new POI(p,"Test POI","This is a test POI","Test Layer",false,true,"Test Building",3);
    	
		// Test getFavorite() method
		boolean expected = true;
		boolean actual = poi.getFavorite();
		assertEquals(expected, actual, "Failed to get correct favorite status");
	}
	
	@Test
	public void testPOIGetDefault() {
		//Creating test case.
    	Point p = new Point(10,20);
    	POI poi = new POI(p,"Test POI","This is a test POI","Test Layer",false,true,"Test Building",3);
    	
		// Test getDefault() method
		boolean expected = false;
		boolean actual = poi.getDefault();
		assertEquals(expected, actual, "Failed to get correct default status");
	}
	

    @Test
    public void testPOIToString() {
    	//Creating test case.
    	Point p = new Point(10,20);
    	POI poi = new POI(p,"Test POI","This is a test POI","Test Layer",false,true,"Test Building",3);
    	
        
        // Expected string representation of the POI
        String expected = "Name: " + poi.getName() + ", Description: " + poi.getDesc() + ", Coords: (" + poi.getPoint().x + "," + poi.getPoint().y + "), Favorite: " + poi.getFavorite() + ", Layer: " + poi.getLayerName() + ", Building: " + poi.getBuilding() + "-" + poi.getFloor();
        
        // Compare the actual and expected string representation of the POI
        assertEquals(expected, poi.toString());
    }
    
    //LAYER CLASS
    @Test
    public void testLayerConstructor() {
    	
    	//Setting up test case.
    	Layer layer = new Layer("Test Layer");
    	
    	//Checking if everything has been initialized correctly.
    	assertNotNull(layer.getPoints());
    	assertEquals("Test Layer",layer.getName());
    	assertTrue(layer.getPoints().isEmpty());
    }

    @Test
    void testLayerGetName() {
    	
    	//Making a layer, initializing variable then checking getter is working.
    	Layer layer = new Layer("Test Layer");
    	assertEquals("Test Layer", layer.getName());
    }

    @Test
    void testLayerAddPoint() {
    	
    	//Setting up test case.
    	Layer layer = new Layer("Test Layer");
    	POI poi1 = new POI(1,2,"Test1","Test1","Test1","Test1",true,true);
    	
    	//Checking points is empty.
    	assertEquals(0, layer.getPoints().size());
        
    	//Adding then checking if it has been successful.
    	layer.addPoint(poi1);
        assertEquals(1, layer.getPoints().size());
        assertTrue(layer.getPoints().contains(poi1));
    }

    @Test
    void testLayerRemovePoint() {
    	
    	//Setting up test cases.
    	Layer layer = new Layer("Test Layer");
    	POI poi1 = new POI(1,2,"Test1","Test1","Test1","Test1",true,true);
    	POI poi2 = new POI(2,1,"Test2","Test2","Test2","Test2",true,true);
    	//Adding POIs to points.
    	layer.addPoint(poi1);
        layer.addPoint(poi2);
        
        //Checking they have been added, then removing and confirming.
        assertEquals(2, layer.getPoints().size());
        layer.removePoint(poi1);
        assertEquals(1, layer.getPoints().size());
        assertFalse(layer.getPoints().contains(poi1));
    }

    @Test
    void testLayerGetPoints() {
    	
    	//Setting up test case.
    	Layer layer = new Layer("Test Layer");
    	
    	//Checking that points is not null and empty.
    	ArrayList<POI> points = layer.getPoints();
        assertNotNull(points);
        assertEquals(0, points.size());
    }

    @Test
    void testLayerGetVisible() {
    	
    	//Setting up test case.
    	Layer layer = new Layer("Test Layer");
    	
    	//Making sure it returns correctly.
    	assertFalse(layer.getVisible());
        layer.setVisible(true);
        assertTrue(layer.getVisible());
    }

    @Test
    void testLayerSetVisible() {
        
    	//Setting up test case.
    	Layer layer = new Layer("Test Layer");
    	
    	//Making sure it sets the variable correctly.
    	assertFalse(layer.getVisible());
        layer.setVisible(true);
        assertTrue(layer.getVisible());
        layer.setVisible(false);
        assertFalse(layer.getVisible());
    }
    
    //USER CLASS
    
	
	
	
	//DRIVER CLASS
	//tests for driver class, need to complete tests for user class before finishing 
	@Test
	void driverSetUserTest() {
		Driver testDriver = new Driver();
		User user1 = new User("leo", "12345");
		User user2 = new User("Donald Trump", "Sleepy Joe");
		
		testDriver.setUser(user1);
		Assertions.assertEquals(user1, testDriver.getUser(), "The setUser method should set the user.");
		
		testDriver.setUser(user2);
		Assertions.assertEquals(user2, testDriver.getUser(), "The setUser method should set the user.");
	}
	
	@Test
	void driverGetUserTest() {
		Driver testDriver = new Driver();
		User user1 = new User("Marcus Rashford", "UnitedFan");
		User user2 = new User("Rob Burega", "wimpy24");
		
		testDriver.setUser(user1);
        Assertions.assertEquals(user1, testDriver.getUser(), "The getUser method should return the user set by setUser.");
        
        testDriver.setUser(user2);
        Assertions.assertEquals(user2, testDriver.getUser(), "The getUser method should return the user set by setUser.");
	}
	
	
	
	
	
	
}

