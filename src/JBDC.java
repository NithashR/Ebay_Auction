//Ebay Auction DataBase GUI
//Files needed to run:
//Functions.java
//create.java
//Insert.java
//Author:Nithash Rajendram 

//This program is the GUI for the ebay auction. 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.GrayFilter;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class JBDC {
	
    public static void main(String[] args) {
 
    	Connection conn1 = null;
        int[] connect = {0};
        try {
            Class.forName("oracle.jdbc.OracleDriver");											
         	
            String dbURL1 = "jdbc:oracle:thin:hr/hr@localhost:1521:xe";   //username=hr and password=hr
			conn1 = DriverManager.getConnection(dbURL1);
            if (conn1 != null) {
                System.out.println("Connected with connection #1");
                connect[0]=connect[0]+1;
            }
 
			JFrame frame = new JFrame ("Ebay Auction Database");
			frame.setLayout(new BorderLayout());
			frame.setSize(600,500);
			frame.setLocation(600,400);
						
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(4,5));
			
			//Buttons
			Button test = new Button("Test Connection");
			buttonPanel.add(test);
			
			Button create = new Button("Create Tables");
			buttonPanel.add(create);
			
			Button insert = new Button("Populate Tables");
			buttonPanel.add(insert);
			
			Button drop = new Button("Drop Tables");
			buttonPanel.add(drop);
			
			Button display = new Button("Display Tables");
			buttonPanel.add(display);
			
			Button simpqueries = new Button("Simple Quieres");
			buttonPanel.add(simpqueries);
			
			Button advqueries = new Button("Advanced Quieres");
			buttonPanel.add(advqueries);
			
			Button adduser = new Button("Add User");
			buttonPanel.add(adduser);
			
			Button addseller = new Button("Add Seller");
			buttonPanel.add(addseller);
			
			Button addbidder= new Button("Add Bidder");
			buttonPanel.add(addbidder);
			
			Button additem = new Button("Add Item");
			buttonPanel.add(additem);
			
			Button addbid = new Button("Add Bid");
			buttonPanel.add(addbid);
			
			Button nothing = new Button("");   //to make button layout even
			buttonPanel.add(nothing);
			nothing.setVisible(false);
			
			Button delitem = new Button("Delete Item");
			buttonPanel.add(delitem);
			
			Button exit = new Button("Exit");
			buttonPanel.add(exit);
						
			JTextField textbox = new JTextField();
			
			
			final ImageIcon imageIcon = new ImageIcon("e.png");
		    JTextArea area = new JTextArea() {
		      Image image = imageIcon.getImage();
		      {setOpaque(false);}
		      public void paint(Graphics g) {
		        g.drawImage(image, 45, 10, this);
		        super.paint(g);
		      }
		    };   
		    
		    JScrollPane scroll= new JScrollPane(area);
			scroll.setSize(100,100);
			
			frame.add(textbox, BorderLayout.SOUTH);
			frame.add(buttonPanel,BorderLayout.NORTH);
			frame.add(scroll,BorderLayout.CENTER);
			
		    
			
			frame.setVisible(true);
			
			//Test Connection//
			test.addActionListener(new ActionListener()
			{public void actionPerformed(ActionEvent e)
				{if (connect[0] == 1){textbox.setText("Connected to server.");textbox.setEditable(false);}else{textbox.setText("Unable to connect to Server.");textbox.setEditable(false);}}});
			
			//create tables//
			create.addActionListener(new ActionListener()
			{public void actionPerformed(ActionEvent e){Functions.createtables();}});
			
			//add data to tables//
			insert.addActionListener(new ActionListener()
			{public void actionPerformed(ActionEvent e){Functions.Insertdata();}});
			
			//drop tables//
			drop.addActionListener(new ActionListener()
			{public void actionPerformed(ActionEvent e){Functions.droptables();}});
			
			//display tables//
			display.addActionListener(new ActionListener()
			{public void actionPerformed(ActionEvent e){String output=Functions.display();area.setEditable(true);area.setText("");area.append(output);area.setEditable(false);;}});
			
			
			//run simple queries//
			simpqueries.addActionListener(new ActionListener()
			{public void actionPerformed(ActionEvent e){String output=Functions.simpleq();area.setEditable(true);area.setText("");area.append(output);area.setEditable(false);;}});
			
			//run advanced queries//
			advqueries.addActionListener(new ActionListener()
			{public void actionPerformed(ActionEvent e){String output=Functions.complex();area.setEditable(true);area.setText("");area.append(output);area.setEditable(false);;}});
			
			//add user//
			adduser.addActionListener(new ActionListener()
			{public void actionPerformed(ActionEvent e){Functions.adduser();}});
			
			//add seller//
			addseller.addActionListener(new ActionListener()
			{public void actionPerformed(ActionEvent e){Functions.addseller();}});
			
			//add bidder//
			addbidder.addActionListener(new ActionListener()
			{public void actionPerformed(ActionEvent e){Functions.addbidder();}});
			
			//add item//
			additem.addActionListener(new ActionListener()
			{public void actionPerformed(ActionEvent e){Functions.additem();}});
			
			//add bid//
			addbid.addActionListener(new ActionListener()
			{public void actionPerformed(ActionEvent e){Functions.addbid();}});
			
			//delete item//
			delitem.addActionListener(new ActionListener()
			{public void actionPerformed(ActionEvent e){Functions.deleteitem();}});
			
			//exit//
			exit.addActionListener(new ActionListener()
			{public void actionPerformed(ActionEvent e){System.exit(0);}});
			
        } catch (ClassNotFoundException ex) 
        {
            ex.printStackTrace();
        } catch (SQLException ex) 
        {
            ex.printStackTrace();
        } finally {
            try {
                if (conn1 != null && !conn1.isClosed()) {
                    conn1.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

	private static String getResource(String string) {
		// TODO Auto-generated method stub
		return null;
	}
 }