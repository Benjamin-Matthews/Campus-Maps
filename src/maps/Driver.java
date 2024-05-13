package maps;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Driver class is what begins our program, and contains our main method.
 * Stores the user and the given default data.
 * @author leomurphy
 *
 */
public class Driver {
	// initializes all classes required to run the program: user, default data login window and the map window
	private User user;
	private DefaultData defaultData;
	
	private LoginWindow window;
	private boolean isEditor;
	
	public static Driver instance;
	
	public static final String USER_LAYER_NAME = "User Defined";
	public static final String ACCESS_LAYER_NAME = "Accessibility";
	public static final String WASHROOM_LAYER_NAME = "Washrooms";
	public static final String CLASS_LAYER_NAME = "Classrooms";
	public static final String LABS_LAYER_NAME = "Labs";
	public static final String FAVORITES_LAYER_NAME = "Favorites";
	public static final String RESTAURANT_LAYER_NAME = "Restaurants";
	public static final String EXITS_LAYER_NAME = "Exits";
	// when run from terminal with command line -edit, you access it in editor mode., if ran normally  it will be in user mode.
	public static void main(String[] args) {
		boolean editor = false;
		if (args.length == 1)
		{
			if (args[0].equals("-edit"))
				editor = true;
		}
		
		Driver d = new Driver();
		d.startApplication(editor);
	}
	/**
	 * Private helper method that uses default data and starts the program.
	 * @param editor
	 */
	private void startApplication(boolean editor)
	{
		File userFile = new File("default.ser");
		if (!(userFile.canRead() && userFile.isFile()))
		{
			DefaultData def = new DefaultData("default", "strongpassword");
			def.saveData();
			defaultData = def;
		}
		else
		{
			try {
				FileInputStream fileInput = new FileInputStream("default.ser");
				ObjectInputStream objectInput = new ObjectInputStream(fileInput);
				DefaultData temp = (DefaultData)objectInput.readObject();
				defaultData = temp;
				
				Layer exitsLayer = defaultData.getLayer(Driver.EXITS_LAYER_NAME);
				if (exitsLayer == null) {
					defaultData.addLayer(new Layer(Driver.EXITS_LAYER_NAME));
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			
		}
		this.isEditor = editor;
		instance = this;
		window = new LoginWindow();
		user = null;
	}
	
	/**
	 * Getter method for editor variable.
	 * @return returns editor.
	 */
	public boolean getEditor()
	{
		return this.isEditor;
	}
	
	/**
	 * Setter method for the current user variable.
	 * @param u
	 */
	public void setUser(User u)
	{
		user = u;
	}
	
	/**
	 * Prints out the current user.
	 */
	public void printUser()
	{
		System.out.println(user.toString());
	}
	
	/**
	 * Getter method for the default data variable.
	 * @return
	 */
	public DefaultData getDefaultData()
	{
		return defaultData;
	}
	
	/**
	 * Getter method for the current user variable.
	 * @return
	 */
	public User getUser() {
		return user;
	}
	
	/**
	 * Sound method that plays when an error occurs.
	 */
	public static void playQuack()
	{
		AudioInputStream audioIn;
		try {
			File f = new File("sounds/quack.wav");
			audioIn = AudioSystem.getAudioInputStream(f);
			Clip clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.start();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
