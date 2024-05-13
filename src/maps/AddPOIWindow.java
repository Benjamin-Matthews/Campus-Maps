package maps;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import java.awt.Point;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
/**
 * AddPOIWindow class represents the pop-up that appears when the user clicks the map.
 * Users are prompted to add a name and description for their POI.
 * @author leomurphy
 *
 */
public class AddPOIWindow extends JDialog implements ActionListener{
	
	//Declaring appropriate variables.
	private JTextField nameField;
	private JTextArea descField;
	private JButton completeButton;
	private JButton cancelButton;
	private JCheckBox isDefaultCheck;
	private JLabel isDefaultLabel;
	private JCheckBox isFavoriteCheck;
	private JLabel isFavoriteLabel;
	private JLabel layerLabel;
	private JComboBox layerDropdown;
	
	private String building;
	private int floor;
	private Point location;
	private int status = -1;
	
	/**
	 * Constructor method creates labels, text boxes, and checkboxes and allows user to provide info.
	 * Also initializes the coordinates, building and floor that is clicked on.
	 * @param mainWindow the window that is currently in question.
	 * @param x coordinate, used to specify location of POI on the screen.
	 * @param y coordinate, used to specify location of POI on the screen.
	 * @param building used to specify the building of the POI
	 * @param floor used to specify the floor of the POI
	 */
	public AddPOIWindow(JFrame mainWindow, int x, int y, String building, int floor)
	{
		super(mainWindow, "Add POI", true);
		
		//Initializing location variable with our coordinates.
		this.location = new Point(x,y);
		
		//Initializing building and floor variables.
		this.building = building;
		this.floor = floor;
		
		//Setting the layout, location and bounds of the window.
		setLayout(null);
		setBounds(0, 0, 200, 430);
		setLocationRelativeTo(mainWindow);
		
		//Creating label for the name field.
		JLabel nameLabel = new JLabel("POI Name");
		nameLabel.setBounds(10, 10, 100, 30);
		add(nameLabel);
		
		//Creating text field for the user to use.
		nameField = new JTextField();
		nameField.setBounds(10, 35, 160, 30);
		add(nameField);
		
		//Creating label for the description field.
		JLabel descLabel = new JLabel("POI Description");
		descLabel.setBounds(10, 70, 100, 30);
		add(descLabel);
		
		//Creating text area for the user to use.
		descField = new JTextArea();
		descField.setBounds(10, 100, 160, 100);
		descField.setLineWrap(true);
		add(descField);
		
		//Creating label for the default checkbox.
		isDefaultLabel = new JLabel("Is Default Data: ");
		isDefaultLabel.setBounds(10, 210, 100, 30);
		add(isDefaultLabel);
		
		//Creating checkbox for user to use.
		isDefaultCheck = new JCheckBox();
		isDefaultCheck.setEnabled(Driver.instance.getEditor());
		isDefaultCheck.setBounds(110, 215, 20, 20);
		add(isDefaultCheck);
		
		//Creating label for the layer field.
		layerLabel = new JLabel("Layer:");
		layerLabel.setBounds(10, 240, 100, 30);
		add(layerLabel);
		
		//Initializing the list of potential layers.
		String[] tempLayers = new String[] {
				Driver.USER_LAYER_NAME,
				Driver.ACCESS_LAYER_NAME,
				Driver.CLASS_LAYER_NAME,
				Driver.LABS_LAYER_NAME,
				Driver.RESTAURANT_LAYER_NAME,
				Driver.WASHROOM_LAYER_NAME,
				Driver.EXITS_LAYER_NAME
		};
		
		//Allowing user to pick layer with a combo box.
		layerDropdown = new JComboBox(tempLayers);
		layerDropdown.setBounds(70, 240, 100, 30);
		layerDropdown.setEnabled(Driver.instance.getEditor());
		layerDropdown.setDoubleBuffered(false);
		add(layerDropdown);
		
		//Creating label for the favourte field.
		isFavoriteLabel = new JLabel("Is Favorite: ");
		isFavoriteLabel.setBounds(10, 280, 100, 30);
		add(isFavoriteLabel);
		
		//Creating checkbox for user to use.
		isFavoriteCheck = new JCheckBox();
		isFavoriteCheck.setBounds(110, 285, 20, 20);
		add(isFavoriteCheck);
		
		//Creating button for user to confirm selections with.
		completeButton = new JButton("Confirm");
		completeButton.setBounds(10, 355, 80, 30);
		completeButton.addActionListener(this);
		add(completeButton);
		
		//Creating button for user to cancel selections with.
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(100, 355, 80, 30);
		cancelButton.addActionListener(this);
		add(cancelButton);
		
		//Finally, setting our window to visible.
		setVisible(true);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == completeButton)
		{
			status = 0;
			
			User userInstance = Driver.instance.getUser();
			POI p = new POI(location, nameField.getText(), descField.getText(), layerDropdown.getSelectedItem().toString(), false, isFavoriteCheck.isSelected(), building, floor);
			try
			{
				if (p.getLayerName().equals(Driver.USER_LAYER_NAME) || p.getLayerName().equals(Driver.FAVORITES_LAYER_NAME))
					userInstance.addPoint(p);
				else
					Driver.instance.getDefaultData().addPoint(p);
				;
			}
			catch (DuplicatePOIName ex)
			{
				Driver.playQuack();
				
				JOptionPane.showMessageDialog(this, "POI with that name already exists >:(");
				return;
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				
			}
			
			dispose();
		}
		else if (e.getSource() == cancelButton)
		{
			status = 1;
			dispose();
		}
	}
	
	/**
	 * Getter method for the name of the POI to be added.
	 * @return returns the text provided in the name field by user.
	 */
	public String getName()
	{
		return nameField.getText();
	}
	
	/**
	 * Getter method for the description of the POI to be added.
	 * @return returns the text provided in the description field by user.
	 */
	public String getDesc()
	{
		return descField.getText();
	}
	
	/**
	 * Getter method for the status variable.
	 * @return returns the status instance variable.
	 */
	public int getStatus()
	{
		return status;
	}
	
}
