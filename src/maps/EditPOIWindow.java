package maps;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import java.awt.Image;
import java.awt.Point;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

/**
 * Represents the window that pops up when you click on a POI to edit it.
 * @author leomurphy
 *
 */
public class EditPOIWindow extends JDialog implements ActionListener{
	
	private JTextField nameField;
	private JTextArea descField;
	private JButton completeButton;
	private JButton cancelButton;
	private JCheckBox isFavoriteCheck;
	private JLabel isFavoriteLabel;
	private JLabel layerLabel;
	private JComboBox layerDropdown;
	private JButton deleteButton;
	
	private POI poi;
	
	/**
	 * Constructor method very similar to addPOIWindow, creates the window andtext/check boxes for when user wants to change POI properties.
	 * @param mainWindow
	 * @param p
	 */
	public EditPOIWindow(JFrame mainWindow, POI p)
	{
		super(mainWindow, "Edit POI", true);
		
		//Checking if user is an editor.
		this.poi = p;
		boolean isEditor = Driver.instance.getEditor();
		if (p.getLayerName().equals(Driver.USER_LAYER_NAME))
			isEditor = true; 
		
		//Setting size and bounds of window.
		setLayout(null);
		setBounds(0, 0, 200, 430);
		setLocationRelativeTo(mainWindow);
		
		//Creating name label and text field.
		JLabel nameLabel = new JLabel("POI Name");
		nameLabel.setBounds(10, 10, 100, 30);
		add(nameLabel);
		
		nameField = new JTextField();
		nameField.setBounds(10, 35, 160, 30);
		nameField.setText(p.getName());
		nameField.setEditable(isEditor);
		add(nameField);
		
		//Creating desc label and text field.
		JLabel descLabel = new JLabel("POI Description");
		descLabel.setBounds(10, 70, 100, 30);
		add(descLabel);
		
		descField = new JTextArea();
		descField.setBounds(10, 100, 160, 100);
		descField.setLineWrap(true);
		descField.setText(p.getDesc());
		descField.setEditable(isEditor);
		add(descField);
		
		//Creating layer field and the options.
		layerLabel = new JLabel("Layer:");
		layerLabel.setBounds(10, 240, 100, 30);
		add(layerLabel);
		
		String[] tempLayers = new String[] {
				Driver.USER_LAYER_NAME,
				Driver.ACCESS_LAYER_NAME,
				Driver.CLASS_LAYER_NAME,
				Driver.LABS_LAYER_NAME,
				Driver.RESTAURANT_LAYER_NAME,
				Driver.WASHROOM_LAYER_NAME,
				Driver.EXITS_LAYER_NAME
		};
		
		layerDropdown = new JComboBox(tempLayers);
		layerDropdown.setBounds(70, 240, 100, 30);
		layerDropdown.setEnabled(Driver.instance.getEditor());
		layerDropdown.setDoubleBuffered(false);
		int selectedIndex = 0;
		for (int i = 0; i < tempLayers.length; i++)
		{
			if (tempLayers[i].equals(poi.getLayerName()))
			{
				selectedIndex = i;
				break;
			}
		}
		layerDropdown.setSelectedIndex(selectedIndex);
		add(layerDropdown);
		
		//Creating label and checkbox for the favourite option.
		isFavoriteLabel = new JLabel("Is Favorite: ");
		isFavoriteLabel.setBounds(10, 280, 100, 30);
		add(isFavoriteLabel);
		
		isFavoriteCheck = new JCheckBox();
		isFavoriteCheck.setBounds(110, 285, 20, 20);
		isFavoriteCheck.setSelected(p.getFavorite());
		add(isFavoriteCheck);
		
		//Creating a delete poi button.
		ImageIcon icon = new ImageIcon("images/Trash.png");
    	Image scaled = icon.getImage();
    	icon.setImage(scaled.getScaledInstance(24, 24, Image.SCALE_FAST));
		deleteButton = new JButton(icon);
		deleteButton.setBounds(10, 320, 24, 24);
		deleteButton.addActionListener(this);
		deleteButton.setEnabled(isEditor);
		add(deleteButton);
		
		//Creating a complete changes button.
		completeButton = new JButton("Confirm");
		completeButton.setBounds(10, 355, 80, 30);
		completeButton.addActionListener(this);
		add(completeButton);
		
		//Creating a cancel changes button.
		cancelButton = new JButton("Cancel");
		cancelButton.setBounds(100, 355, 80, 30);
		cancelButton.addActionListener(this);
		add(cancelButton);
		
		setVisible(true);
		System.out.println(p.toString());
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == completeButton)
		{	
			//TODO edit the properties of poi
			if (!(poi.getName().equals(nameField.getText())))
			{
				try {
					poi.setName(nameField.getText());
				} catch (DuplicatePOIName e1) {
					// TODO Auto-generated catch block
					Driver.playQuack();
	            	JOptionPane.showMessageDialog(this,  "A POI already exists with the name " + nameField.getText(), "Error", JOptionPane.ERROR_MESSAGE);   
					return;
				}
			}
			// sets fields of poi
			poi.setDesc(descField.getText());
			poi.setFavorite(isFavoriteCheck.isSelected());
			poi.setLayer(layerDropdown.getSelectedItem().toString());
			
			Driver.instance.getUser().saveData();
			Driver.instance.getDefaultData().saveData();
			
			dispose();
		}
		else if (e.getSource() == cancelButton)
		{
			dispose();
		}
		else if (e.getSource() == deleteButton)
		{
			int status = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete the poi: " + poi.getName());
			if (status != 0)
				return;
			
			try {
				if (DefaultData.isDefaultLayer(layerDropdown.getSelectedItem().toString()))
				{
					DefaultData u = Driver.instance.getDefaultData();
					u.deletePoint(poi.getName());
					u.saveData();
				}
				else
				{
					User user = Driver.instance.getUser();
					user.deletePoint(poi.getName());
					user.saveData();
				}
				
			} catch (NonExistentPOI e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			MapWindow.instance.displayPoints();
			dispose();
		}
	}
	
	/**
	 * Getter method for the text field of name.
	 */
	public String getName()
	{
		return nameField.getText();
	}
	
	/**
	 * Getter method for the text field of desc.
	 * @return
	 */
	public String getDesc()
	{
		return descField.getText();
	}
	
}
