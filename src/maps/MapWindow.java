package maps;

import java.awt.*;

import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.lang.Runnable;
import java.io.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.Font;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.border.EmptyBorder;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.NumberFormat.Style;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.Graphics;

import javax.swing.JComboBox;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
/**
 * MapWindow class represent the main window of our program, extends the JFrame class.
 * @author leomurphy
 *
 */
public class MapWindow extends JFrame {
	
	public static MapWindow instance;
	

    public static void main(String[] args) {
    	MapWindow frame = new MapWindow();
    	// load the custom icon for the frame
        ImageIcon duckComputerIcon = new ImageIcon("images/duckPc.png");
        frame.setIconImage(duckComputerIcon.getImage());
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    	MapWindow.instance = frame;
    }
   
    //Font decalration 
	private Font roman16Bold = new Font("Times New Roman", Font.BOLD, 16);
	private Font roman12Bold = new Font("Times New Roman", Font.BOLD, 12);
	
	//colour declaration
	private Color westernPurple = new Color(79, 38, 132);
	private Color westernGrey = new Color(128, 127, 131);

    //dimensions of users screen
    private int screenWidth = 1280;
    private int screenHeight = 720;

    //menu button offset
    private int menuOffset = 210;

    private JLabel imageLabel;
    
    private JComboBox buildingComboBox;
    private JComboBox floorComboBox;
    
    private JPanel contentPanel;
    private static JTable table1;
    private static JTable table2;
    private static JTable table3;
    private static DefaultTableModel tableModel1,tableModel2,tableModel3;

    /**
     * Constructor methodcreates the frame and uses user data to create.
     */
    public MapWindow() {

    	instance = this;
    	User userInstance = Driver.instance.getUser();
    	
        
        setTitle("Western GIS Group 21");
      
        //size of window
        setBounds(0, 0, screenWidth  , screenHeight);


        // Create a new JPanel to hold the content
        contentPanel = new JPanel(null); 
        //size of content inside the window
        contentPanel.setPreferredSize(new Dimension(screenWidth, screenHeight));
        

        // Create a new JScrollPane and add the panel to it
        JScrollPane window = new JScrollPane(contentPanel);


    
        
        //checkboxes for displaying layers
        JCheckBox checkBoxAccessibility = new JCheckBox("Accessibility");
        JCheckBox checkBoxWashrooms = new JCheckBox("Washrooms");
        JCheckBox checkBoxClassrooms = new JCheckBox("Classrooms");
        JCheckBox checkBoxLabs = new JCheckBox("Labs");
        JCheckBox checkBoxFavorites = new JCheckBox("Favorites");
        JCheckBox checkBoxUserDefined = new JCheckBox("User Defined");
        JCheckBox checkBoxRestaurants = new JCheckBox(Driver.RESTAURANT_LAYER_NAME);
        JCheckBox checkBoxExits = new JCheckBox(Driver.EXITS_LAYER_NAME);
        
        // Set the bounds of each checkbox
        checkBoxAccessibility.setBounds(10, 130, 100, 25);
        checkBoxWashrooms.setBounds(10, 150, 100, 25);
        checkBoxClassrooms.setBounds(10, 170, 100, 25);
        checkBoxLabs.setBounds(10, 190, 100, 25);
        checkBoxFavorites.setBounds(10, 210, 100, 25);
        checkBoxUserDefined.setBounds(10, 230, 100, 25);
        checkBoxRestaurants.setBounds(10, 250, 100, 25);
        checkBoxExits.setBounds(10, 270, 100, 25);

        // Add the checkboxes to the content panel
        contentPanel.add(checkBoxAccessibility);
        contentPanel.add(checkBoxWashrooms);
        contentPanel.add(checkBoxClassrooms);
        contentPanel.add(checkBoxLabs);
        contentPanel.add(checkBoxFavorites);
        contentPanel.add(checkBoxUserDefined);
        contentPanel.add(checkBoxExits);
        contentPanel.add(checkBoxRestaurants);


        //text field for search bar
        JTextField searchField = new JTextField();
        searchField.setBounds(215, 26, 300, 50);

        //hint label for search field
        JLabel hintLabelSearch = new JLabel("Enter a string to search for...");
        hintLabelSearch.setForeground(Color.GRAY);
        hintLabelSearch.setFont(roman16Bold);
        hintLabelSearch.setBounds(5, 0, searchField.getWidth(), searchField.getHeight());
        
        searchField.add(hintLabelSearch);
        contentPanel.add(searchField);

        //button for search
        JButton searchButton = new JButton("Search");
        searchButton.setFont(roman16Bold);
        searchButton.setForeground(Color.WHITE);
        searchButton.setBackground(westernPurple);
        searchButton.setBounds(520, 26, 100, 50);
        contentPanel.add(searchButton);

        //button for about
        JButton aboutButton = new JButton("About");
        aboutButton.setFont(roman16Bold);
        aboutButton.setForeground(Color.WHITE);
        aboutButton.setBackground(westernPurple);
        aboutButton.setBounds(screenWidth - menuOffset, 26, 200, 50);
        contentPanel.add(aboutButton);
        
        // button for returning back to sign in
        JButton backButton = new JButton("Back to Sign-In");
        backButton.setFont(roman16Bold);
        backButton.setForeground(Color.WHITE);
        backButton.setBackground(westernPurple);
        backButton.setBounds(screenWidth - menuOffset-300, 26, 200, 50);
        contentPanel.add(backButton);

        //buildings string
        String[] buildingsLst = {"Select a Building", "Middlesex College", "Visual Arts Center", "Sieben's-Drake Research Institute"};
        String[] middlesexLst = {"Select a Floor","Basement", "First Floor", "Second Floor", "Third Floor", "Fourth Floor"};
        String[] visualArtsLst = {"Select a Floor","Ground Floor", "Second Floor", "Third Floor"};
        String[] siebensLst = {"Select a Floor","Ground Floor", "Second Floor", "Third Floor"};
      
        //combo boxes for building and floor selection
        buildingComboBox = new JComboBox(buildingsLst);
        buildingComboBox.setForeground(Color.WHITE);
        floorComboBox = new JComboBox();
        floorComboBox.setFont(roman12Bold);
        imageLabel = new JLabel();
        
        buildingComboBox.setBackground(westernGrey);
        buildingComboBox.setFont(roman12Bold);
        buildingComboBox.setBounds(10, 26, 200, 50);
        contentPanel.add(buildingComboBox);


 
        

        // setting an initial area for where the maps will be shown
        int displayWidth = 850;
        int displayHeight = 550;

        ImageIcon imageIcon = new ImageIcon("DUCKS.jpg");
        imageLabel.setIcon(imageIcon);
       
        Image image = imageIcon.getImage();
       	Image scaledImage = image.getScaledInstance(displayWidth, displayHeight, Image.SCALE_SMOOTH);
       	imageIcon.setImage(scaledImage);
       	imageLabel.setIcon(imageIcon);
       	
        // Create a JScrollPane and add the JLabel to it
        JScrollPane scrollPane = new JScrollPane(imageLabel);

        // Set the preferred size of the JScrollPane
        scrollPane.setPreferredSize(new Dimension(displayWidth, displayHeight));

        // Add the JScrollPane to the contentPanel
        contentPanel.add(scrollPane);

        // Set the position of the JScrollPane
        int x = (screenWidth - displayWidth) / 2;
        int y = (screenHeight - displayHeight) / 2;
        scrollPane.setBounds(x, y, displayWidth, displayHeight);


        //Label for list of pois 
        JLabel poiLabel = new JLabel("POIs");

        // Set the position of the JLabel using setBounds(x, y, width, height)
        poiLabel.setBounds(screenWidth - menuOffset, 120, 200, 30);
        poiLabel.setFont(roman16Bold);
        // Add the JLabel to the contentPanel
        contentPanel.add(poiLabel);

        //Label for Favourites List
        JLabel favsLabel = new JLabel("FAVOURITES");

        // Set the position of the JLabel using setBounds(x, y, width, height)
        favsLabel.setBounds(screenWidth - menuOffset, 375, 200, 30);
        favsLabel.setFont(roman16Bold);
        // Add the JLabel to the contentPanel
        contentPanel.add(favsLabel);
        
        JLabel userLabel = new JLabel("USER DEFINED POIs");

        // Set the position of the JLabel using setBounds(x, y, width, height)
        userLabel.setBounds(10, 375, 200, 30);
        userLabel.setFont(roman16Bold);
        // Add the JLabel to the contentPanel
        contentPanel.add(userLabel);



       
        
        // Sets the layer toggles to the correct setting
        
        checkBoxAccessibility.setSelected(userInstance.getLayerVisibility(Driver.ACCESS_LAYER_NAME));
        checkBoxClassrooms.setSelected(userInstance.getLayerVisibility(Driver.CLASS_LAYER_NAME));
        checkBoxWashrooms.setSelected(userInstance.getLayerVisibility(Driver.WASHROOM_LAYER_NAME));
        checkBoxLabs.setSelected(userInstance.getLayerVisibility(Driver.LABS_LAYER_NAME));
        checkBoxFavorites.setSelected(userInstance.getLayerVisibility(Driver.FAVORITES_LAYER_NAME));
        checkBoxUserDefined.setSelected(userInstance.getLayerVisibility(Driver.USER_LAYER_NAME));
        checkBoxExits.setSelected(userInstance.getLayerVisibility(Driver.EXITS_LAYER_NAME));
        checkBoxRestaurants.setSelected(userInstance.getLayerVisibility(Driver.RESTAURANT_LAYER_NAME));
        
        //action listener for saving data on window close
        addWindowListener(new WindowAdapter() {
        	@Override
        	public void windowClosing(WindowEvent e)
        	{
        		System.out.println("Saving Data...");
        		try
        		{
        			User u = Driver.instance.getUser();
        			u.saveData();
        			
        		}
        		catch (Exception ex)
        		{
        			ex.printStackTrace();
        		}
        		e.getWindow().dispose();
        	}
        });
           // add a document listener to check for changes in the search field
           searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkIfEmptySearch();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                checkIfEmptySearch();
            }
            // set hint label state
            private void checkIfEmptySearch() {
                if (searchField.getText().isEmpty()) {
                	hintLabelSearch.setVisible(true);
                } else {
                	hintLabelSearch.setVisible(false);
                }
            }
			@Override
			public void changedUpdate(DocumentEvent e) {
				
			}
        });
           //Accessibility poi display
           checkBoxAccessibility.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   boolean isChecked = checkBoxAccessibility.isSelected();
                   // Perform actions based on the state of the checkbox
                   Driver.instance.getUser().setLayerVisibility(Driver.ACCESS_LAYER_NAME, isChecked);
                   System.out.println("Set Layer " + Driver.ACCESS_LAYER_NAME + " to visibility: " + isChecked);
                   MapWindow.instance.displayPoints();
               }
           });
           
           //Washroom poi display
           checkBoxWashrooms.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   boolean isChecked = checkBoxWashrooms.isSelected();
                   // Perform actions based on the state of the checkbox
                   Driver.instance.getUser().setLayerVisibility(Driver.WASHROOM_LAYER_NAME, isChecked);
                   System.out.println("Set Layer " + Driver.WASHROOM_LAYER_NAME + " to visibility: " + isChecked);
                   MapWindow.instance.displayPoints();
               }
           });
           
           //Classroom poi display
           checkBoxClassrooms.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   boolean isChecked = checkBoxClassrooms.isSelected();
                   // Perform actions based on the state of the checkbox
                   Driver.instance.getUser().setLayerVisibility(Driver.CLASS_LAYER_NAME, isChecked);
                   System.out.println("Set Layer " + Driver.CLASS_LAYER_NAME + " to visibility: " + isChecked);
                   MapWindow.instance.displayPoints();
               }
           });
           
           //Labs poi display
           checkBoxLabs.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   boolean isChecked = checkBoxLabs.isSelected();
                   // Perform actions based on the state of the checkbox
                   Driver.instance.getUser().setLayerVisibility(Driver.LABS_LAYER_NAME, isChecked);
                   System.out.println("Set Layer " + Driver.LABS_LAYER_NAME + " to visibility: " + isChecked);
                   MapWindow.instance.displayPoints();
               }
           });
           
           //Favorites poi display
           checkBoxFavorites.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   boolean isChecked = checkBoxFavorites.isSelected();
                   // Perform actions based on the state of the checkbox
                   Driver.instance.getUser().setLayerVisibility(Driver.FAVORITES_LAYER_NAME, isChecked);
                   System.out.println("Set Layer " + Driver.FAVORITES_LAYER_NAME + " to visibility: " + isChecked);
                   MapWindow.instance.displayPoints();
               }
           });
           
           //User poi display
           checkBoxUserDefined.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   boolean isChecked = checkBoxUserDefined.isSelected();
                   // Perform actions based on the state of the checkbox
                   Driver.instance.getUser().setLayerVisibility(Driver.USER_LAYER_NAME, isChecked);
                   System.out.println("Set Layer " + Driver.USER_LAYER_NAME + " to visibility: " + isChecked);
                   MapWindow.instance.displayPoints();
               }
           });
           
         //Restaurants poi display
           checkBoxRestaurants.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   boolean isChecked = checkBoxRestaurants.isSelected();
                   // Perform actions based on the state of the checkbox
                   Driver.instance.getUser().setLayerVisibility(Driver.RESTAURANT_LAYER_NAME, isChecked);
                   System.out.println("Set Layer " + Driver.RESTAURANT_LAYER_NAME + " to visibility: " + isChecked);
                   MapWindow.instance.displayPoints();
               }
           });
           
         //Exits poi display
           checkBoxExits.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   boolean isChecked = checkBoxExits.isSelected();
                   // Perform actions based on the state of the checkbox
                   Driver.instance.getUser().setLayerVisibility(Driver.EXITS_LAYER_NAME, isChecked);
                   System.out.println("Set Layer " + Driver.EXITS_LAYER_NAME + " to visibility: " + isChecked);
                   MapWindow.instance.displayPoints();
               }
           });
           


        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String searchText = searchField.getText(); 
                
                ArrayList<POI> allPoints = Driver.instance.getUser().getPoints();
                for (POI p : Driver.instance.getDefaultData().getPoints())
                {
                	allPoints.add(p);
                }
                POI found = null;
                for (POI p : allPoints)
                {
                	if (p.getName().contains(searchText))
                	{
                		found = p;
                		break;
                	}
                }
                if(found == null) {
                
                	Set<String> substrings = generateSubstrings(searchText);
                	for (POI p : allPoints)
                    {
                    	if (p.getDesc().contains(searchText))
                    	{
                    		found = p;
                    		break;
                    	}
                    }
                
                }
                if (found == null)
                {
                	JOptionPane.showMessageDialog(MapWindow.instance, "No data found for: " + searchText, "Error", JOptionPane.ERROR_MESSAGE, imageIcon);
                	Driver.playQuack();
                }
                else
                {
                	String buildingName = found.getBuilding();
                	int floor = found.getFloor();
                	
                	buildingComboBox.setSelectedItem(buildingName);
                	floorComboBox.setSelectedIndex(floor);
                	EditPOIWindow editor = new EditPOIWindow(MapWindow.instance, found);
                }
            }
        });

        //action listener for about button
        aboutButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
               AboutWindow aW = new AboutWindow();
            }
        });
        
      //action listener for back button
        backButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
               instance.dispose();
               if(Driver.instance.getEditor()==true)
            	   Driver.main(new String[]{"-edit"});
               else
            	   Driver.main(new String[]{""});
               
            }
        });
        //action listener for building combo box
        buildingComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ImageIcon imageIcon = new ImageIcon("images/DUCKS.jpg");
                imageLabel.setIcon(imageIcon);
   
                Image image = imageIcon.getImage();
                Image scaledImage = image.getScaledInstance(displayWidth, displayHeight, Image.SCALE_SMOOTH);
                
                String building = (String) buildingComboBox.getSelectedItem();
                 if (building.equals("Middlesex College")) {
                    imageIcon.setImage(scaledImage);
                	imageLabel.setIcon(imageIcon);
                    floorComboBox.setModel(new DefaultComboBoxModel(middlesexLst));
                    contentPanel.add(floorComboBox);
                } else if (building.equals("Visual Arts Center")) {
                    imageIcon.setImage(scaledImage);
                	imageLabel.setIcon(imageIcon);
                    floorComboBox.setModel(new DefaultComboBoxModel(visualArtsLst));
                    contentPanel.add(floorComboBox);
                } else if (building.equals("Sieben's-Drake Research Institute")) {
                    imageIcon.setImage(scaledImage);
                	imageLabel.setIcon(imageIcon);
                    floorComboBox.setModel(new DefaultComboBoxModel(siebensLst));
                    contentPanel.add(floorComboBox);
        

                } else {
                   
                	imageIcon.setImage(scaledImage);
                	imageLabel.setIcon(imageIcon);
                    // remove the floor combo box from the content panel
                    contentPanel.remove(floorComboBox);
                    floorComboBox.setModel(new DefaultComboBoxModel());
                    contentPanel.revalidate();
                    contentPanel.repaint();
                    return;
                }
                floorComboBox.setForeground(Color.WHITE);
                floorComboBox.setBackground(westernGrey);
                floorComboBox.setBounds(10, 80, 200, 50);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        //action listener for floor combo box
        floorComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String building = (String) buildingComboBox.getSelectedItem();
                String floor = (String) floorComboBox.getSelectedItem();
                if (building.equals("Middlesex College")) {
                    /// code to display Middlessex floor plans
                	if (floor.equals("Basement")) {
                        ImageIcon imageIcon = new ImageIcon("images/MC PHOTOS/c6e8251d_1.png");
                        
                     
                        imageLabel.setIcon(imageIcon);
                        
                        
                        Image image = imageIcon.getImage();
                        
                        Image scaledImage = image.getScaledInstance(displayWidth, displayHeight, Image.SCALE_SMOOTH);
                        imageIcon.setImage(scaledImage);
                        imageLabel.setIcon(imageIcon);
                    }   else if (floor.equals("First Floor")) {
                        ImageIcon imageIcon = new ImageIcon("images/MC PHOTOS/c6e8251d_2.png");
                        
                        
                        imageLabel.setIcon(imageIcon);
                        
                        Image image = imageIcon.getImage();
                      
                        Image scaledImage = image.getScaledInstance(displayWidth, displayHeight, Image.SCALE_SMOOTH);
                        imageIcon.setImage(scaledImage);
                        imageLabel.setIcon(imageIcon);
                    } 
                   else if (floor.equals("Second Floor")) {
                        ImageIcon imageIcon = new ImageIcon("images/MC PHOTOS/c6e8251d_3.png");
                        
                        
                        imageLabel.setIcon(imageIcon);
                        
                        Image image = imageIcon.getImage();
                        
                        Image scaledImage = image.getScaledInstance(displayWidth, displayHeight, Image.SCALE_SMOOTH);
                        imageIcon.setImage(scaledImage);
                        imageLabel.setIcon(imageIcon);
                    } 
                   else if (floor.equals("Third Floor")) {
                        ImageIcon imageIcon = new ImageIcon("images/MC PHOTOS/c6e8251d_4.png");
                        
                        
                        imageLabel.setIcon(imageIcon);
                        
                        Image image = imageIcon.getImage();
                      
                        Image scaledImage = image.getScaledInstance(displayWidth, displayHeight, Image.SCALE_SMOOTH);
                        imageIcon.setImage(scaledImage);
                        imageLabel.setIcon(imageIcon);
                    } 
                   else if (floor.equals("Fourth Floor")) {
                        ImageIcon imageIcon = new ImageIcon("images/MC PHOTOS/c6e8251d_5.png");
                        
                        
                        imageLabel.setIcon(imageIcon);
                        
                        Image image = imageIcon.getImage();
                        Image scaledImage = image.getScaledInstance(displayWidth, displayHeight, Image.SCALE_SMOOTH);
                        imageIcon.setImage(scaledImage);
                        imageLabel.setIcon(imageIcon);
                    } 
                    
                  else { 
                    ImageIcon imageIcon = new ImageIcon("images/DUCKS.jpg");
                    imageLabel.setIcon(imageIcon);
       
                    Image image = imageIcon.getImage();
                	Image scaledImage = image.getScaledInstance(displayWidth, displayHeight, Image.SCALE_SMOOTH);
                	imageIcon.setImage(scaledImage);
                	imageLabel.setIcon(imageIcon);
                    
                    }
                }
                    /// code to display Visual Arts Center floor plans
                    if (building.equals("Visual Arts Center")) {
                        if (floor.equals("Ground Floor")) {
                            ImageIcon imageIcon = new ImageIcon("images/VAC PHOTOS/88e0ed0c_1.png");
                            
                            
                            imageLabel.setIcon(imageIcon);
                            
                            Image image = imageIcon.getImage();
                            Image scaledImage = image.getScaledInstance(displayWidth, displayHeight, Image.SCALE_SMOOTH);
                            imageIcon.setImage(scaledImage);
                            imageLabel.setIcon(imageIcon);
                        }
                       else if (floor.equals("Second Floor")) {
                            ImageIcon imageIcon = new ImageIcon("images/VAC PHOTOS/88e0ed0c_2.png");
                            
                            
                            imageLabel.setIcon(imageIcon);
                            
                            Image image = imageIcon.getImage();
                            
                            Image scaledImage = image.getScaledInstance(displayWidth, displayHeight, Image.SCALE_SMOOTH);
                            imageIcon.setImage(scaledImage);
                            imageLabel.setIcon(imageIcon);
                        } 
                        else if (floor.equals("Third Floor")) {
                            ImageIcon imageIcon = new ImageIcon("images/VAC PHOTOS/88e0ed0c_3.png");
                            
                            
                            imageLabel.setIcon(imageIcon);
                            
                            Image image = imageIcon.getImage();
                            
                            Image scaledImage = image.getScaledInstance(displayWidth, displayHeight, Image.SCALE_SMOOTH);
                            imageIcon.setImage(scaledImage);
                            imageLabel.setIcon(imageIcon);
                        }  else { 
                            ImageIcon imageIcon = new ImageIcon("images/DUCKS.jpg");
                            imageLabel.setIcon(imageIcon);
               
                            Image image = imageIcon.getImage();
                            Image scaledImage = image.getScaledInstance(displayWidth, displayHeight, Image.SCALE_SMOOTH);
                            imageIcon.setImage(scaledImage);
                            imageLabel.setIcon(imageIcon);
                            
                            }
                        
                }
                    /// code to display SDRI
                    if (building.equals("Sieben's-Drake Research Institute")) {
                    	if (floor.equals("Ground Floor")) {
                            ImageIcon imageIcon = new ImageIcon("images/SDRI PHOTOS/d80c7d4e_1.png");
                            
                            
                            imageLabel.setIcon(imageIcon);
                            
                            Image image = imageIcon.getImage();
                            
                            Image scaledImage = image.getScaledInstance(displayWidth, displayHeight, Image.SCALE_SMOOTH);
                            imageIcon.setImage(scaledImage);
                            imageLabel.setIcon(imageIcon);
                        }
                       else if (floor.equals("Second Floor")) {
                            ImageIcon imageIcon = new ImageIcon("images/SDRI PHOTOS/d80c7d4e_2.png");
                            
                            
                            imageLabel.setIcon(imageIcon);
                            
                            Image image = imageIcon.getImage();
                           
                            Image scaledImage = image.getScaledInstance(displayWidth, displayHeight, Image.SCALE_SMOOTH);
                            imageIcon.setImage(scaledImage);
                            imageLabel.setIcon(imageIcon);
                        } 
                       else if (floor.equals("Third Floor")) {
                            ImageIcon imageIcon = new ImageIcon("images/SDRI PHOTOS/d80c7d4e_3.png");
                            
                            
                            imageLabel.setIcon(imageIcon);
                            
                            Image image = imageIcon.getImage();
                            
                            Image scaledImage = image.getScaledInstance(displayWidth, displayHeight, Image.SCALE_SMOOTH);
                            imageIcon.setImage(scaledImage);
                            imageLabel.setIcon(imageIcon);
                        } 
                        else { 
                            ImageIcon imageIcon = new ImageIcon("images/DUCKS.jpg");
                            imageLabel.setIcon(imageIcon);
               
                            Image image = imageIcon.getImage();
                            Image scaledImage = image.getScaledInstance(displayWidth, displayHeight, Image.SCALE_SMOOTH);
                            imageIcon.setImage(scaledImage);
                            imageLabel.setIcon(imageIcon);
                            
                            }
                   
                }
                    
                    if (building.equals("Select a Building")) {
                    	
                    	ImageIcon imageIcon = new ImageIcon("images/DUCKS.jpg");
                        imageLabel.setIcon(imageIcon);
                        
                        Image image = imageIcon.getImage();
                        
                        Image scaledImage = image.getScaledInstance(displayWidth, displayHeight, Image.SCALE_SMOOTH);
                        imageIcon.setImage(scaledImage);
                        imageLabel.setIcon(imageIcon);
                    	
                    	
                    }
                    MapWindow.instance.displayPoints();
            }
            
        });
        
        // add mouse listener to label
        imageLabel.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            	
            	// Ensures that a building and floor are selected
            	if (buildingComboBox.getSelectedIndex() == 0)
            		return;
            	
            	if (floorComboBox.getSelectedIndex() == 0)
            		return;
            	
                // get the x and y coordinates of the mouse click
                int x = e.getX();
                int y = e.getY();
                AddPOIWindow addWindow = new AddPOIWindow(MapWindow.instance, x, y, buildingComboBox.getSelectedItem().toString(), floorComboBox.getSelectedIndex());
                
                System.out.println("Label clicked at (" + x + ", " + y + ")");
                displayPoints();
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        // setting up the weather panel
        try {
        WeatherApiClient weather = new WeatherApiClient();
        WeatherData forecast = weather.getCurrentWeather();
        //JPanel weatherPanel = new JPanel();
        JLabel weatherForecast = new JLabel(forecast.toString());
        Font labelFont = weatherForecast.getFont();
        int fontSize = 14;
        
       // JInternalFrame internalFrame = new JInternalFrame("New JInternalFrame");
       // weatherPanel.add(internalFrame);
       // internalFrame.setVisible(true);
        weatherForecast.setFont(new Font(labelFont.getName(),labelFont.getStyle(),fontSize));
        contentPanel.add(weatherForecast);    
        weatherForecast.setSize(1000, 100);
        weatherForecast.setLocation(220,600);
        
        
       
        contentPanel.revalidate();
        contentPanel.repaint();}
        catch(Exception e) {
        		System.out.println(e.getMessage());
        	
             JLabel weatherForecast = new JLabel("Error occured! Forecast is currently unavailable!");
             
             Font labelFont = weatherForecast.getFont();
             int fontSize = 14;
             weatherForecast.setFont(new Font(labelFont.getName(),labelFont.getStyle(),fontSize));
             contentPanel.add(weatherForecast);    
             weatherForecast.setSize(1000, 100);
             weatherForecast.setLocation(220,600);
             
             contentPanel.revalidate();
             contentPanel.repaint();
             
        }
        
        
        window.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        window.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

     
        // Set the scroll pane as the content pane of the frame
        setContentPane(window);
       
    }
    

   /**
    * User created method creates the clickable table for user defined POIs.
    */
    public void userCreated() {
    	SwingUtilities.invokeLater(() -> {
            // Create table model
            tableModel3 = new DefaultTableModel(new String[]{"List of Selectable POIs"}, 0) {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return JButton.class;
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            table3 = new JTable(tableModel3);

            // Set custom cell renderer
            table3.setDefaultRenderer(JButton.class, new TableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table3, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    return (Component) value;
                }
            });

            // Add mouse listener to handle button click events
            table3.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = table3.rowAtPoint(e.getPoint());
                    int column = table3.columnAtPoint(e.getPoint());

                    if (row >= 0 && column >= 0) {
                        JButton button = (JButton) table3.getValueAt(row, column);
                        button.doClick();
                        System.out.println("Button clicked: " + button.getText());
                    }
                }
            });

            // Create a JScrollPane and add the JList to it
            JScrollPane scrollPanePOI = new JScrollPane(table3);
            
            // Set the preferred size of the JScrollPane
            scrollPanePOI.setPreferredSize(new Dimension(200, 200));
            
            // Add the JScrollPane to the contentPanel
            contentPanel.add(scrollPanePOI);
            
            // Set the position of the JScrollPane
            scrollPanePOI.setBounds(10, 400, 200, 200);

            updateTable1WithButtons();
        });    }
    /**
     * Method that allows the window to update the user created POI table.
     */
    public void updateTable3WithButtons() {
    	
    	ArrayList<POI> points = Driver.instance.getUser().getUserDefined();
    	
        // Clear the table
        tableModel3.setRowCount(0);

        // Add rows with buttons
        for (int i = 0; i < points.size(); i++) {
        	POI p = points.get(i);
        		{
        			JButton button = new JButton(points.get(i).getName());
        			button.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							new EditPOIWindow(MapWindow.instance, p);
						}
        				
        			});
                    tableModel3.addRow(new Object[]{button});
        		}
        	} 
        System.out.println("user defined " + points.size());
        // Notify the table that the data has changed
        tableModel3.fireTableDataChanged();
    }

  /**
   * Favorites method creates the table of favorited POIS.
   */
    public void favorites() {
    	SwingUtilities.invokeLater(() -> {
          

            // Create table model
            tableModel2 = new DefaultTableModel(new String[]{"List of Selectable POIs"}, 0) {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return JButton.class;
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            table2 = new JTable(tableModel2);

            // Set custom cell renderer
            table2.setDefaultRenderer(JButton.class, new TableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table2, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    return (Component) value;
                }
            });

            // Add mouse listener to handle button click events
            table2.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = table2.rowAtPoint(e.getPoint());
                    int column = table2.columnAtPoint(e.getPoint());

                    if (row >= 0 && column >= 0) {
                        JButton button = (JButton) table2.getValueAt(row, column);
                        button.doClick();
                        System.out.println("Button clicked: " + button.getText());
                    }
                }
            });

           
            // Create a JScrollPane and add the JList to it
            JScrollPane scrollPaneFavs = new JScrollPane(table2);
            
            // Set the preferred size of the JScrollPane
            scrollPaneFavs.setPreferredSize(new Dimension(200, 200));
            
            // Add the JScrollPane to the contentPanel
            contentPanel.add(scrollPaneFavs);
            
            // Set the position of the JScrollPane
            scrollPaneFavs.setBounds(screenWidth - menuOffset, 400, 200, 200);
            

            updateTable2WithButtons();
        });    }
   /**
    * Method to update the favorites table on MapWindow.
    */
    public void updateTable2WithButtons() {
    	
    	ArrayList<POI> favs = Driver.instance.getUser().getFavorites();
        System.out.println("favs "+ favs.size());
       
        // Clear the table
        tableModel2.setRowCount(0);

        // Add rows with buttons
        for (int i = 0; i < favs.size(); i++) {
        	POI p = favs.get(i);
        	JButton button = new JButton(favs.get(i).getName());
        	button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					new EditPOIWindow(MapWindow.instance, p);
				}
        		
        	});
            tableModel2.addRow(new Object[]{button});
        }

        // Notify the table that the data has changed
        tableModel2.fireTableDataChanged();
    }
    
    /**
     * Creates the table of discoverable POIs, different for each map and floor.
     */
    public void discovarable() {
    	SwingUtilities.invokeLater(() -> {
            // Create table model
            tableModel1 = new DefaultTableModel(new String[]{"List of Selectable POIs"}, 0) {
                @Override
                public Class<?> getColumnClass(int columnIndex) {
                    return JButton.class;
                }

                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };

            table1 = new JTable(tableModel1);

            // Set custom cell renderer
            table1.setDefaultRenderer(JButton.class, new TableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table1, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    return (Component) value;
                }
            });

            // Add mouse listener to handle button click events
            table1.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = table1.rowAtPoint(e.getPoint());
                    int column = table1.columnAtPoint(e.getPoint());

                    if (row >= 0 && column >= 0) {
                        JButton button = (JButton) table1.getValueAt(row, column);
                        button.doClick();
                        System.out.println("Button clicked: " + button.getText());
                    }
                }
            });

            // Create a JScrollPane and add the JList to it
            JScrollPane scrollPanePOI = new JScrollPane(table1);
            
            // Set the preferred size of the JScrollPane
            scrollPanePOI.setPreferredSize(new Dimension(200, 200));
            
            // Add the JScrollPane to the contentPanel
            contentPanel.add(scrollPanePOI);
            
            // Set the position of the JScrollPane
            scrollPanePOI.setBounds(screenWidth - menuOffset, 150, 200, 200);
        
            

            updateTable1WithButtons();
        });    }
    /**
     * Updates the discoverable table.
     */
    public void updateTable1WithButtons() {
    	
    	ArrayList<POI> points = new ArrayList<POI>();
    	ArrayList<POI> POIpool = Driver.instance.getUser().getPoints();
    	POIpool.addAll(Driver.instance.getDefaultData().getPoints());
    	
    	
    	for(POI p : POIpool)
    		{ boolean isThere = false;
    		for(POI j: points)
    			if(p.getName().equals(j.getName())) {
    				isThere = true;
    			}
    		if(!isThere)
    			points.add(p);
    		}
    		
       
        // Clear the table
        tableModel1.setRowCount(0);

        // Add rows with buttons
        for (int i = 0; i < points.size(); i++) {
        	POI p = points.get(i);
        	if (p.getLayerName().equals(Driver.FAVORITES_LAYER_NAME))
        		continue;
        	if (p.getBuilding().equals(buildingComboBox.getSelectedItem().toString()))
        	{
        		if (p.getFloor() == floorComboBox.getSelectedIndex())
        		{
        			JButton button = new JButton(points.get(i).getName());
        			button.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							// TODO Auto-generated method stub
							new EditPOIWindow(MapWindow.instance, p);
						}
        				
        			});
                    tableModel1.addRow(new Object[]{button});
        		}
        	} 
        }

        // Notify the table that the data has changed
        tableModel1.fireTableDataChanged();
    }
    /**
     *  displays updates at every event that involves the pois, change of map, creation removal of poi,...
     */
    public void displayPoints()
    {
    	
    	imageLabel.removeAll();
    	
    	if (buildingComboBox.getSelectedIndex() == 0)
    		return;
    	
    	if (floorComboBox.getSelectedIndex() == 0)
    		return;
    	
    	System.out.println("Displaying Points");
    	
    	
    	User user = Driver.instance.getUser();
    	ArrayList<Layer> layers = user.getLayers();
    	
    	ArrayList<Layer> defaultLayers = Driver.instance.getDefaultData().getLayers();
    	
    	for (Layer l : defaultLayers)
    	{
    		layers.add(l);
    	}
    	
    	String buildingName = buildingComboBox.getSelectedItem().toString();
    	int floorIndex = floorComboBox.getSelectedIndex();
    	
    	
    	for (Layer l : layers)
    	{
    		if (!(user.getLayerVisibility(l.getName()))) continue;
    		
    		String imageName = l.getName();
    		ImageIcon poiIcon = new ImageIcon("images/" + imageName + ".png");
        	Image scaled = poiIcon.getImage();
        	poiIcon.setImage(scaled.getScaledInstance(16, 16, Image.SCALE_FAST));
    		for (POI p : l.getPoints())
    		{
    			if (user.isPointFavorite(p.getName()) && !(l.getName().equals(Driver.FAVORITES_LAYER_NAME)) && user.getLayerVisibility(Driver.FAVORITES_LAYER_NAME))
    			{
    				continue;
    			}
    			
    			if (!(p.getBuilding().equals(buildingName)) || p.getFloor() != floorIndex)
    			{
    				continue;
    			}
    			
    			JButton pointButton = new JButton(poiIcon);
    			Point buttonLoc = new Point(p.getPoint().x-8, p.getPoint().y-8);
    			pointButton.setBounds(buttonLoc.x, buttonLoc.y, 16, 16);
    			
    			pointButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						
						EditPOIWindow editor = new EditPOIWindow(MapWindow.instance, p);
						displayPoints();
					}
    			});
    			
    			imageLabel.add(pointButton);
    			
    			JLabel nameLabel = new JLabel(p.getName());
    			nameLabel.setBounds(buttonLoc.x, buttonLoc.y-25, 100, 30);
    			imageLabel.add(nameLabel);
    			
    			
    		}
    		
    		
    	}
    	this.discovarable();
    	this.favorites();
    	this.userCreated();
    	this.repaint();
    }
    
    /**
     * Generates hash set of sub strings given a string.
     * @param input
     * @return
     */
    private static Set<String> generateSubstrings(String input) {
        Set<String> substrings = new HashSet<>();

        for (int i = 0; i < input.length(); i++) {
            for (int j = i + 1; j <= input.length(); j++) {
                substrings.add(input.substring(i, j));
            }
        }

        return substrings;
    }

}
