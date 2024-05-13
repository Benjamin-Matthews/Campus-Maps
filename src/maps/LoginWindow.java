package maps;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.io.*;



/**
 * LoginWindow class represents the page where users will login. 
 * Users are then brought to the MapWindow, which is not stored in this class. 
 * We used the Times New Roman font for our text.
 * @author leomurphy, BruceScott
 *
 */
public class LoginWindow  {

	//frame for the login window 
	private JFrame frame;
	
	
	//Font decalration 
	private Font roman16Bold = new Font("Times New Roman", Font.BOLD, 16);
	private Font roman12Bold = new Font("Times New Roman", Font.BOLD, 12);
	
	//colour declaration
	private Color westernPurple = new Color(79, 38, 132);
	private Color westernGrey = new Color(128, 127, 131);
	
	
	/**
	 * Constructor class for LoginWindow. 
	 * Creates new frames and loads in the necessary images.
	 * Creates labels and hints for username and password
	 * Creates buttons for Login and and Signup.
	 * Creates a document listeners that checks for changes in both the username and password fields.
	 */
	public LoginWindow() 
	{	
		
		//login page JFrame
		frame = new JFrame("Western GIS Group 21");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //window size
        frame.setSize(400, 300);
        frame.setLayout(null);
        
		// load the custom icon for the frame
        ImageIcon duckComputerIcon = new ImageIcon("images/duckPc.png");
        frame.setIconImage(duckComputerIcon.getImage());
		
	
       
        
        // load the custom icon
        ImageIcon duckKnifeIcon = new ImageIcon("images/duckKnife.png");
        ImageIcon duckMinionIcon = new ImageIcon("images/minionDuck.jpg");

        // resize the icon to 48x48 pixels
        Image imgDuckKnife = duckKnifeIcon.getImage();
        Image imgDuckMinion = duckMinionIcon.getImage();
        Image scaledImgDuckKnife = imgDuckKnife.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
        Image scaledImgDuckMinion = imgDuckMinion.getScaledInstance(48, 48, Image.SCALE_SMOOTH);
        Icon customDuckKnifeIcon = new ImageIcon(scaledImgDuckKnife);
        Icon customDuckMinionIcon = new ImageIcon(scaledImgDuckMinion);
        
        //Ui manager defaults
        UIManager.put("Button.background", westernPurple);
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("OptionPane.errorIcon", customDuckKnifeIcon);
        UIManager.put("OptionPane.informationIcon", customDuckMinionIcon);

	

        //label for username
        JLabel labelUsername = new JLabel("Username:");
        labelUsername.setFont(roman16Bold);
        labelUsername.setBounds(50, 50, 100, 30);
        frame.add(labelUsername);
        
        //text field for username
        JTextField usernameField = new JTextField();
        usernameField.setBounds(150, 50, 150, 30);
        
        //hint label for username field
        JLabel hintLabelUsername = new JLabel("Enter username here...");
        hintLabelUsername.setForeground(Color.GRAY);
        hintLabelUsername.setFont(roman12Bold);
        hintLabelUsername.setBounds(5, 0, usernameField.getWidth(), usernameField.getHeight());
        //add to frame
        usernameField.add(hintLabelUsername);
        frame.add(usernameField);

   
        //label for password
        JLabel labelPassword = new JLabel("Password:");
        labelPassword.setFont(roman16Bold);
        labelPassword.setBounds(50, 100, 100, 30);
        frame.add(labelPassword);
        
        //text field for password
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 150, 30);
        
        //hint label for password field
        JLabel hintLabelPassword = new JLabel("Enter password here...");
        hintLabelPassword.setForeground(Color.GRAY);
        hintLabelPassword.setFont(roman12Bold);
        hintLabelPassword.setBounds(5, 0, passwordField.getWidth(), passwordField.getHeight());
        //add to frame
        passwordField.add(hintLabelPassword);
        frame.add(passwordField);
       
        //login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(50, 150, 100, 30);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(roman16Bold);
        loginButton.setBackground(westernPurple);
        loginButton.setFocusPainted(false);
        frame.add(loginButton);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(200, 150, 100, 30);
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFont(roman16Bold);
        signUpButton.setBackground(westernGrey);
        signUpButton.setFocusPainted(false);
        frame.add(signUpButton);
        
        // add a document listener to check for changes in the username field
        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkIfEmptyPassword();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                checkIfEmptyPassword();
            }
            // set hint label state
            private void checkIfEmptyPassword() {
                if (passwordField.getPassword().length == 0) {
                	hintLabelPassword.setVisible(true);
                } else {
                	hintLabelPassword.setVisible(false);
                }
            }
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
        });
        
        // add a document listener to check for changes in the password field
       usernameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                checkIfEmptyUsername();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                checkIfEmptyUsername();
            }
            // set hint label state
            private void checkIfEmptyUsername() {
                if (usernameField.getText().isEmpty()) {
                	hintLabelUsername.setVisible(true);
                } else {
                	hintLabelUsername.setVisible(false);
                }
            }
			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				
			}
        });
        
        // action listener when login is clicked
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                String username = usernameField.getText();
                String password = charArrayToString(passwordField.getPassword());

                File userFile = new File(username + ".ser");
                if (userFile.canRead() && userFile.isFile())
                { 
                	try {
						FileInputStream fileInput = new FileInputStream(username + ".ser");
						ObjectInputStream objectInput = new ObjectInputStream(fileInput);
						User temp = (User)objectInput.readObject();
                        //correct login info
						if (temp.getPassword().equals(password) && !(username.equals("default")))
						{
							Driver.instance.setUser(temp);
							MapWindow.main(null);
							frame.dispose();
						}
                        //wrong password
						else
						{					
							Driver.playQuack();
							JOptionPane.showMessageDialog(frame, "Incorrect username or password", "Error", JOptionPane.ERROR_MESSAGE);	
							
						}
						
					} 
                	catch (IOException ex)
                	{
						System.out.println(ex);
                	}
					catch (ClassNotFoundException ex)
                	{
						System.out.println(ex);
                	}
                }
                //wrong username
                else
                {
                	Driver.playQuack();
                	JOptionPane.showMessageDialog(frame,  "Incorrect username or password", "Error", JOptionPane.ERROR_MESSAGE);           	
                }
            }
        });

        //Action listener when Signup is clicked
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = charArrayToString(passwordField.getPassword());
                try
                //if password and username field empty
                {	if (usernameField.getText().isEmpty()&& passwordField.getPassword().length == 0) {
                	Driver.playQuack();
                	JOptionPane.showMessageDialog(frame, "Please enter a username and a password", "Error", JOptionPane.ERROR_MESSAGE);
                	}
                    //if username field empty
                	else if (usernameField.getText().isEmpty()) {
                		Driver.playQuack();
                		JOptionPane.showMessageDialog(frame, "Please enter a username", "Error", JOptionPane.ERROR_MESSAGE);
                	}
                    //if password field empty
                	else if(passwordField.getPassword().length == 0) {
                		Driver.playQuack();
                		JOptionPane.showMessageDialog(frame, "Please enter a password", "Error", JOptionPane.ERROR_MESSAGE);
                	}else{

                	File file = new File(username + ".ser");
                    //create new user
                	if (file.createNewFile())
                	{
                        JOptionPane.showMessageDialog(frame, "Successfully created account for " + username, "Success", JOptionPane.INFORMATION_MESSAGE);
                		User temp = new User(username, password);
                		usernameField.setText("");
                		passwordField.setText("");
                		temp.saveData();
                	}
                    //if user already exists with that name
                	else
                	{
                		Driver.playQuack();
                		JOptionPane.showMessageDialog(frame, "User already exists with that name", "Error", JOptionPane.ERROR_MESSAGE);
                	}
                	}
                }
                catch (Exception ex)
                {
                	ex.printStackTrace();
                }
                
            }
        });

        frame.setVisible(true);		
	}
	

	/**
	 * charArrayToString method turns the password (which is stored as a char array) into a string.
	 * @param arr Passes through an array of characters.
	 * @return  Returns a string formed from the given character array.
	 */
	public String charArrayToString(char[] arr)
	{
		String str = "";
		
		for (int i = 0; i < arr.length; i++)
		{
			str += arr[i];
		}
		
		return str;
	}
}
