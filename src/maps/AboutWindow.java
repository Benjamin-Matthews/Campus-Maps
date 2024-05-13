package maps;
import javax.swing.*;
import java.awt.*;

/**
 * AboutWindow represents a JFrame that pops up for the user.
 * Describes our program and how to use it, includes authors, release date, background knowledge etc.
 * @author leomurphy
 *
 */
public class AboutWindow extends JFrame {
	//Creating a public instance of our window.
	public static AboutWindow instance;
	
	//Main method for testing, will be removed eventually.
	public static void main(String[] args) {
		
		AboutWindow frame = new AboutWindow();
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	/**
	 * About Window constructor creates the JFrame and text areas that display the intended messages.
	 */
	public AboutWindow() {
		
		//Creating our JFrame and assigning a title, setting size.
		JFrame frame = new JFrame("About Section Western GIS ");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        ImageIcon duckComputerIcon = new ImageIcon("images/duckPc.png");
        frame.setIconImage(duckComputerIcon.getImage());
        frame.setSize(1280, 720);

        // Create JTextArea to display the help text
        JTextArea helpTextArea = new JTextArea();
        helpTextArea.setEditable(false);
        helpTextArea.setWrapStyleWord(true);
        helpTextArea.setLineWrap(true);
        helpTextArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));

        // Set the help text
        String helpText = "1. Overview\n";
        helpText += "The product that we are developing will be used by students of the University of Western Ontario as a navigation tool. This application will provide a useful\n";
        helpText += "toolkit for browsing building maps, and setting personalized waypoints. User data for waypoints will be persistent and saved in their system files, where\n";
        helpText += "they can log back in, and access them at a later date. Western students who use the application will have an easier time locating their classes, and other\n";
        helpText += "rooms as they attend school.\n";
        helpText += "2. Objectives\n";
        helpText += "Users will be able to log into their own account, and access saved data\n";
        helpText += "A menu will allow the user to select which building map they would like to view\n";
        helpText += "A menu will allow the user to show/hide certain layers of waypoints and points of interest\n";
        helpText += "The maps will be scrollable and zoomable\n";
        helpText += "The user will be able to add their own waypoints, with certain custom metadata\n";
        helpText += "Users will be able to search for classes by name/code\n";
        helpText += "Admin accounts can edit the metadata on maps and add default waypoints\n";
        helpText += "3 Functionality\n";
        helpText += "The functionality includes: seaching for a specific floor map of a building, search for POIs on the selected floor, add user-defined POIs, show POIs marked as favourites.\n";
        helpText += "Search POI Function\n";
        helpText += "When the search button is clicked user will be prompted to input in a textbox a string that will used to search for matches in the metadata \n \n";
        helpText += "Search Floor Plan Function\n";
        helpText += "Using a drag-down menu the user can select what floor from what window to see \n\n";
        helpText += "Add POI Function\n";
        helpText += "When the add POI button is clicked a menu will prompt the user to enter information required to save a POI. Required information is name, class number and a short description of it. \n\n";
        helpText += "Favourites Function\n";
        helpText += "When the Favourites button is clicked, a window containing the user's saved favourite POIs. When the user clicks on one of these POIs, they  will be redicted to the POI's location on map \n\n";
        helpText += "4. Development Team\n";
        helpText += "Alex Caraman(acaraman@uwo.ca), Benny Matthews, Bruce Scott, Matthew Young, Leo Murphy\n\n\n\n\n\n\n\n\n\n";
        helpText += "Release Date 1 April 2023\n";
        helpText += "Build v420.69\n";
       

        helpTextArea.setText(helpText);

        // Add a JScrollPane to make the text scrollable
        JScrollPane scrollPane = new JScrollPane(helpTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        // Add the JScrollPane to the JFrame
        frame.add(scrollPane, BorderLayout.CENTER);

        // Display the JFrame
        frame.setVisible(true);;
        
		
		
	}
}
