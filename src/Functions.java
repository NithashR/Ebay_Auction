//Ebay Auction DataBase GUI
//Author:Nithash Rajendram 
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//This file hold functions the GUI uses for the buttons
//Functions:
//deleteitem
//deletes
//display
//addsbid
//addbidder
//addseller
//additem
//Insertdata
//droptables
//createtables

public class Functions {
	public static void main(String[] args) {
	}
	
	public static void deleteitem()
	{
		JFrame frame = new JFrame ("Delete Item");
		frame.setLayout(new BorderLayout());
		frame.setSize(300,130);
		frame.setLocation(600,400);
		
		JPanel panel = (JPanel) frame.getContentPane();
	    panel.setLayout(null);
	    
		JLabel id = new JLabel("Auction Item ID: ");
		id.setBounds(20, 22, 100, 15);
		JTextField idt = new JTextField();
		idt.setBounds(130, 20, 130, 25);
		
		Button deleteu = new Button("Delete Item");
		deleteu.setBounds(100, 60, 80, 20);
		
		frame.add(deleteu);
		frame.add(id);
		frame.add(idt);
		
		frame.setVisible(true);
		
		deleteu.addActionListener(new ActionListener()
		{public void actionPerformed(ActionEvent e)
			{
				String username = idt.getText();
				
				deletes(username);
				frame.setVisible(false);
			}
		});
		
	}
	
	public static void deletes(String auctionitemid)
	{//deletes auction item
		Connection conn1 = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");											
         	
            String dbURL1 = "jdbc:oracle:thin:hr/hr@localhost:1521:xe";
			conn1 = DriverManager.getConnection(dbURL1);
			String[] query = {("DELETE FROM ITEMCATEGORY WHERE AUCTIONITEMID="+auctionitemid),
					("DELETE FROM AUCTIONITEM WHERE AUCTIONITEMID="+auctionitemid)};
			try (Statement stmt = conn1.createStatement()) {
				
				for ( int i=0; i<query.length; i++ )
				{
					stmt.executeQuery(query[i]);
				}
				
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null,  "There is a error in the information entered.", "Error: " + "Delete Item", JOptionPane.INFORMATION_MESSAGE);
					System.out.println(e.getErrorCode());
				}
			
	        }catch (ClassNotFoundException ex) 
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
	
	
	
	//Display//
	public static String display()
	{//displays the database info
		String output="\n\n\n\n\n\n\n\n\n\n\n\n";
		Connection conn1 = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");											
         	
            String dbURL1 = "jdbc:oracle:thin:hr/hr@localhost:1521:xe";
			conn1 = DriverManager.getConnection(dbURL1);
			
			String[] query = {
					"SELECT * FROM AUCTIONUSER",
					"SELECT * FROM AUCTIONITEM",
					"SELECT * FROM BID",
					"SELECT * FROM BIDDER",
					"SELECT * FROM ITEMCATEGORY",
					"SELECT * FROM SELLER"
					
			};
			
			try (Statement stmt = conn1.createStatement()) {
				
				ResultSet rs = stmt.executeQuery(query[0]);
				output=output+"AUTIONSUSER Table\n";
				while (rs.next()) {
					String name = rs.getString("USERNAME");			
					int userid = rs.getInt("USERID");
					String email = rs.getString("EMAILADDRESS");
					String pass = rs.getString("PASSWORD");	
					String pn = rs.getString("PHONENUMBER");	
					String addy = rs.getString("HOMEADDRESS");	
					String gender = rs.getString("GENDER");	
					int age = rs.getInt("AGE");
					output=output+(name+", "+ userid + ", " + email+ ", " + pass+ ", " + pn+ ", " + addy+ ", " + gender+ ", " + age+"\n");
				}
				
				rs = stmt.executeQuery(query[1]);
				output=output+"\nAUCTIONITEM Table\n";
				while (rs.next()) {		
					int id = rs.getInt("AUCTIONITEMID");
					String title = rs.getString("TITLE");
					String des = rs.getString("DESCRIPTION");
					int sp = rs.getInt("STARTPRICE");
					int ep = rs.getInt("ENDPRICE");
					String st = rs.getString("STARTTIME");
					String et = rs.getString("ENDTIME");
					int sid = rs.getInt("SELLERID");
					int bidid = rs.getInt("BIDID");
					output=output+(id+", "+ title + ", " + des+ ", " + sp + ", " + ep+ ", " + st+ ", " + et+ ", " + sid+ ", " + bidid+"\n");
				}
				
				rs = stmt.executeQuery(query[2]);
				output=output+"\nBid Table\n";
				while (rs.next()) {		
					int price = rs.getInt("PRICE");
					int bidid = rs.getInt("BIDID");
					int bidderid = rs.getInt("BIDDERID");
					String stat = rs.getString("STATUS");
					String sub = rs.getString("SUBMITTIME");
					output=output+(price+", "+ bidid + ", " + bidderid+ ", " + stat + ", " + sub +"\n");
				}
				
				rs = stmt.executeQuery(query[3]);
				output=output+"\nBIDDER Table\n";
				while (rs.next()) {		
					int bidderid = rs.getInt("BIDDERID");
					String ship = rs.getString("SHIPPINGADDRESS");
					String info = rs.getString("PAYMENTINFO");
					int id = rs.getInt("USERID");
					output=output+(bidderid+", "+ ship + ", " + info + ", " + id + "\n");
				}
				
				rs = stmt.executeQuery(query[4]);
				output=output+"\nITEMCATEGORY Table\n";
				while (rs.next()) {		
					String cat = rs.getString("CATEGORYNAME");
					int catid = rs.getInt("CATEGORYID");
					int aucid = rs.getInt("AUCTIONITEMID");
					output=output+(cat+", "+ catid + ", " + aucid + "\n");
				}
				
				rs = stmt.executeQuery(query[5]);
				output=output+"\nSELLER Table\n";
				while (rs.next()) {		
					int sellid = rs.getInt("SELLERID");
					String dep = rs.getString("DEPOSITINFO");
					int id = rs.getInt("USERID");
					output=output+(sellid+", "+ dep + ", " + id + "\n");
				}
				
				
				} catch (SQLException e) {
					System.out.println(e.getErrorCode());
				}
				

	        }catch (ClassNotFoundException ex) 
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
		return output;
	}
	
	//add bid
	public static void addbid()
	{
		JFrame frame = new JFrame ("Add Bid");
		frame.setLayout(new BorderLayout());
		frame.setSize(300,240);
		frame.setLocation(600,400);
		
		JPanel panel = (JPanel) frame.getContentPane();
	    panel.setLayout(null);
	    
		JLabel price = new JLabel("Price: ");
		price.setBounds(20, 22, 100, 15);
		JTextField pricet = new JTextField();
		pricet.setBounds(130, 20, 130, 25);
		
		JLabel bidid = new JLabel("Bid ID: ");
		bidid.setBounds(20, 52, 120, 15);
		JTextField bididt = new JTextField();
		bididt.setBounds(130, 50, 130, 25);
		
		JLabel bidderid = new JLabel("Bidder ID: ");
		bidderid.setBounds(20, 82, 100, 15);
		JTextField bidderidt = new JTextField();
		bidderidt.setBounds(130, 80, 130, 25);
		
		JLabel stat = new JLabel("Status: ");
		stat.setBounds(20, 112, 100, 15);
		JTextField statt = new JTextField();
		statt.setBounds(130, 110, 130, 25);
		
		JLabel st = new JLabel("Submit Time: ");
		st.setBounds(20, 142, 100, 15);
		JTextField stt = new JTextField();
		stt.setBounds(130, 140, 130, 25);
		
		Button addbid = new Button("Add Bid");
		addbid.setBounds(110, 170, 60, 20);
		
		frame.add(addbid);
		frame.add(price);
		frame.add(pricet);
		frame.add(bidid);
		frame.add(bididt);
		frame.add(bidderid);
		frame.add(bidderidt);
		frame.add(stat);
		frame.add(statt);
		frame.add(st);
		frame.add(stt);
		
		frame.setVisible(true);
		
		addbid.addActionListener(new ActionListener()
		{public void actionPerformed(ActionEvent e)
			{
				String uprice = pricet.getText();
				String uid = bididt.getText();
				String ubidderid = bidderidt.getText();
				String ustat = statt.getText();
				String ust = stt.getText();
				
				addsbid(uprice,uid,ubidderid,ustat,ust);
				frame.setVisible(false);
			}
		});
		
	}
	
	public static void addsbid(String price,String bidid,String bidderid,String status, String subtime)
	{
		Connection conn1 = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");											
         	
            String dbURL1 = "jdbc:oracle:thin:hr/hr@localhost:1521:xe";
			conn1 = DriverManager.getConnection(dbURL1);
			
			String query = ("INSERT INTO BID(PRICE, BIDID, BIDDERID, STATUS, SUBMITTIME) VALUES ("
					+"'"+price+"','"+bidid+"','"+bidderid+"','"+status+"',TO_TIMESTAMP('"+subtime+"', 'YYYY-MM-DD HH24:MI:SS.FF'))");
			
			try (Statement stmt = conn1.createStatement()) {
				stmt.executeUpdate(query);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null,  "There is a error in the information entered.", "Error: " + "Adding Bid", JOptionPane.INFORMATION_MESSAGE);
					System.out.println(e.getErrorCode());
				}
	        }catch (ClassNotFoundException ex) 
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
	
	public static void addbidder()
	{
		JFrame frame = new JFrame ("Add Bidder");
		frame.setLayout(new BorderLayout());
		frame.setSize(300,220);
		frame.setLocation(600,400);
		
		JPanel panel = (JPanel) frame.getContentPane();
	    panel.setLayout(null);
	    
		JLabel bid = new JLabel("Bidder ID: ");
		bid.setBounds(20, 22, 100, 15);
		JTextField bidt = new JTextField();
		bidt.setBounds(130, 20, 130, 25);
		
		JLabel ship = new JLabel("Shipping Address: ");
		ship.setBounds(20, 52, 120, 15);
		JTextField shipt = new JTextField();
		shipt.setBounds(130, 50, 130, 25);
		
		JLabel bank = new JLabel("Bank Info: ");
		bank.setBounds(20, 82, 100, 15);
		JTextField bankt = new JTextField();
		bankt.setBounds(130, 80, 130, 25);
		
		JLabel uid = new JLabel("User ID: ");
		uid.setBounds(20, 112, 100, 15);
		JTextField uidt = new JTextField();
		uidt.setBounds(130, 110, 130, 25);
		
		Button addbidder = new Button("Add Bidder");
		addbidder.setBounds(110, 150, 60, 20);
		
		frame.add(addbidder);
		frame.add(bid);
		frame.add(bidt);
		frame.add(ship);
		frame.add(shipt);
		frame.add(bank);
		frame.add(bankt);
		frame.add(uid);
		frame.add(uidt);
		
		frame.setVisible(true);
		
		addbidder.addActionListener(new ActionListener()
		{public void actionPerformed(ActionEvent e)
			{
				String ubid = bidt.getText();
				String uship = shipt.getText();
				String ubank = bankt.getText();
				String uuid = uidt.getText();
				
				addsbidder(ubid,uship,ubank,uuid);
				frame.setVisible(false);
			}
		});
		
	}
	
	public static void addsbidder(String bid,String ship,String bank,String uid)
	{
		Connection conn1 = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");											
         	
            String dbURL1 = "jdbc:oracle:thin:hr/hr@localhost:1521:xe";
			conn1 = DriverManager.getConnection(dbURL1);
			
			String query = ("INSERT INTO BIDDER(BIDDERID, SHIPPINGADDRESS, PAYMENTINFO, USERID) VALUES ("
					+ "'"+bid+"','"+ship+"','"+bank+"','"+uid+"')");
			
			try (Statement stmt = conn1.createStatement()) {
				stmt.executeUpdate(query);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null,  "There is a error in the information entered.", "Error: " + "Adding Bidder", JOptionPane.INFORMATION_MESSAGE);
					System.out.println(e.getErrorCode());
				}
	        }catch (ClassNotFoundException ex) 
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
	
	public static void addseller()
	{
		JFrame frame = new JFrame ("Add Seller");
		frame.setLayout(new BorderLayout());
		frame.setSize(300,200);
		frame.setLocation(600,400);
		
		JPanel panel = (JPanel) frame.getContentPane();
	    panel.setLayout(null);
	    
		JLabel sid = new JLabel("Seller ID: ");
		sid.setBounds(20, 20, 100, 15);
		JTextField sidt = new JTextField();
		sidt.setBounds(130, 20, 130, 25);
		
		JLabel bank = new JLabel("Bank Info: ");
		bank.setBounds(20, 50, 100, 15);
		JTextField bankt = new JTextField();
		bankt.setBounds(130, 50, 130, 25);
		
		JLabel uid = new JLabel("User ID: ");
		uid.setBounds(20, 80, 100, 15);
		JTextField uidt = new JTextField();
		uidt.setBounds(130, 80, 130, 25);
		
		Button addseller = new Button("Add Seller");
		addseller.setBounds(110, 120, 60, 20);
		
		frame.add(addseller);
		frame.add(sid);
		frame.add(sidt);
		frame.add(bank);
		frame.add(bankt);
		frame.add(uid);
		frame.add(uidt);
		
		frame.setVisible(true);
		
		addseller.addActionListener(new ActionListener()
		{public void actionPerformed(ActionEvent e)
			{
				String usid = sidt.getText();
				String ubank = bankt.getText();
				String uuid = uidt.getText();
				
				addsseller(usid,ubank,uuid);
				frame.setVisible(false);
			}
		});
		
	}
	
	public static void addsseller(String sid,String bank,String uid)
	{
		Connection conn1 = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");											
         	
            String dbURL1 = "jdbc:oracle:thin:hr/hr@localhost:1521:xe";
			conn1 = DriverManager.getConnection(dbURL1);
			
			String query = ("INSERT INTO SELLER(SELLERID, DEPOSITINFO, USERID) VALUES ("
					+ "'"+sid+"','"+bank+"','"+uid+"')");
			
			try (Statement stmt = conn1.createStatement()) {
				stmt.executeUpdate(query);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null,  "There is a error in the information entered.", "Error: " + "Adding Seller", JOptionPane.INFORMATION_MESSAGE);
					System.out.println(e.getErrorCode());
				}
	        }catch (ClassNotFoundException ex) 
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
	
	
	public static void additem()
	{
		JFrame frame = new JFrame ("Add Item");
		frame.setLayout(new BorderLayout());
		frame.setSize(300,360);
		frame.setLocation(600,400);

		JPanel panel = (JPanel) frame.getContentPane();
	    panel.setLayout(null);
	    
		JLabel item = new JLabel("Item ID: ");
		item.setBounds(20, 20, 100, 15);
		JTextField itemt = new JTextField();
		itemt.setBounds(130, 20, 130, 25);
		
		JLabel title = new JLabel("Title: ");
		title.setBounds(20, 50, 100, 15);
		JTextField titlet = new JTextField();
		titlet.setBounds(130, 50, 130, 25);
		
		JLabel des = new JLabel("Description: ");
		des.setBounds(20, 80, 100, 15);
		JTextField dest = new JTextField();
		dest.setBounds(130, 80, 130, 25);
		
		JLabel stp = new JLabel("Start Price: ");
		stp.setBounds(20, 110, 100, 15);
		JTextField stpt = new JTextField();
		stpt.setBounds(130, 110, 130, 25);
		
		JLabel sp = new JLabel("Sold Price: ");
		sp.setBounds(20, 140, 100, 15);
		JTextField spt = new JTextField();
		spt.setBounds(130, 140, 130, 25);
		
		JLabel sat = new JLabel("Sale Time: ");
		sat.setBounds(20, 170, 100, 15);
		JTextField satt = new JTextField();
		satt.setBounds(130, 170, 130, 25);
		
		JLabel st = new JLabel("Sold Time: ");
		st.setBounds(20, 200, 100, 15);
		JTextField stt = new JTextField();
		stt.setBounds(130, 200, 130, 25);
		
		JLabel sellid = new JLabel("Seller ID: ");
		sellid.setBounds(20, 230, 100, 15);
		JTextField sellidt = new JTextField();
		sellidt.setBounds(130, 230, 130, 25);
		
		JLabel bidid = new JLabel("Bid ID: ");
		bidid.setBounds(20, 260, 100, 15);
		JTextField bididt = new JTextField();
		bididt.setBounds(130, 260, 130, 25);
		
		Button additem = new Button("Add Item");
		additem.setBounds(110, 290, 60, 20);
		
		frame.add(additem);
		frame.add(item);
		frame.add(title);
		frame.add(des);
		frame.add(stp);
		frame.add(sp);
		frame.add(sat);
		frame.add(st);
		frame.add(sellid);
		frame.add(bidid);
		
		frame.add(itemt);
		frame.add(titlet);
		frame.add(dest);
		frame.add(stpt);
		frame.add(spt);
		frame.add(satt);
		frame.add(stt);
		frame.add(sellidt);
		frame.add(bididt);
		frame.setVisible(true);
		
		additem.addActionListener(new ActionListener()
		{public void actionPerformed(ActionEvent e)
			{
				String uitem = itemt.getText();
				String utitle = titlet.getText();
				String udes = dest.getText();
				String ustpt = stpt.getText();
				String uspt = spt.getText();
				String usatt = satt.getText();
				String ustt = stt.getText();
				String usellid = sellidt.getText();
				String ubidid = bididt.getText();
				
				addsitem(uitem, utitle,udes,ustpt,uspt,usatt,ustt,usellid,ubidid);
				frame.setVisible(false);
			}
		});
	}
	
	public static void addsitem(String uitem,String utitle,String udes,String ustpt,String uspt,String usatt,String ustt,String usellid,String ubidid)
	{
		Connection conn1 = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");											
         	
            String dbURL1 = "jdbc:oracle:thin:hr/hr@localhost:1521:xe";
			conn1 = DriverManager.getConnection(dbURL1);
			
			String query = ("INSERT INTO AUCTIONITEM(AUCTIONITEMID, TITLE, DESCRIPTION, STARTPRICE, ENDPRICE, STARTTIME, ENDTIME, SELLERID, BIDID) VALUES ("
					+ "'"+uitem+"','"+utitle+"','"+udes+"','"+ustpt+"','"+uspt+"', TO_DATE('"+usatt+"', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('"+ustt+"', 'YYYY-MM-DD HH24:MI:SS'),'"+usellid+"','"+ubidid+"')");

			try (Statement stmt = conn1.createStatement()) {
				stmt.executeUpdate(query);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null,  "There is a error in the information entered.", "Error: " + "Adding Item", JOptionPane.INFORMATION_MESSAGE);
					System.out.println(e.getErrorCode());
				}
	        }catch (ClassNotFoundException ex) 
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
	
	public static void adduser()
	{
		JFrame frame = new JFrame ("Add User");
		frame.setLayout(new BorderLayout());
		frame.setSize(300,330);
		frame.setLocation(600,400);
		
		JPanel panel = (JPanel) frame.getContentPane();
	    panel.setLayout(null);
	    
		JLabel name = new JLabel("Name: ");
		name.setBounds(20, 20, 100, 15);
		JTextField namet = new JTextField();
		namet.setBounds(130, 20, 130, 25);
		
		JLabel userid = new JLabel("User ID(#): ");
		userid.setBounds(20, 50, 100, 15);
		JTextField useridt = new JTextField();
		useridt.setBounds(130, 50, 130, 25);
		
		JLabel email = new JLabel("Email Address: ");
		email.setBounds(20, 80, 100, 15);
		JTextField emailt = new JTextField();
		emailt.setBounds(130, 80, 130, 25);
		
		JLabel pnum = new JLabel("Phone Number: ");
		pnum.setBounds(20, 110, 100, 15);
		JTextField pnumt = new JTextField();
		pnumt.setBounds(130, 110, 130, 25);
		
		JLabel password = new JLabel("Password: ");
		password.setBounds(20, 140, 100, 15);
		JTextField passwordt = new JTextField();
		passwordt.setBounds(130, 140, 130, 25);
		
		JLabel addy = new JLabel("Address: ");
		addy.setBounds(20, 170, 100, 15);
		JTextField addyt = new JTextField();
		addyt.setBounds(130, 170, 130, 25);
		
		JLabel gender = new JLabel("Gender (M/F): ");
		gender.setBounds(20, 200, 100, 15);
		JTextField gendert = new JTextField();
		gendert.setBounds(130, 200, 130, 25);
		
		JLabel age = new JLabel("Age: ");
		age.setBounds(20, 230, 100, 15);
		JTextField aget = new JTextField();
		aget.setBounds(130, 230, 130, 25);
		
		Button adduser = new Button("Add User");
		adduser.setBounds(110, 260, 60, 20);
		
		frame.add(adduser);
		frame.add(name);
		frame.add(userid);
		frame.add(email);
		frame.add(pnum);
		frame.add(password);
		frame.add(addy);
		frame.add(gender);
		frame.add(age);
		
		frame.add(namet);
		frame.add(useridt);
		frame.add(emailt);
		frame.add(pnumt);
		frame.add(passwordt);
		frame.add(addyt);
		frame.add(gendert);
		frame.add(aget);
		frame.setVisible(true);
		
		adduser.addActionListener(new ActionListener()
		{public void actionPerformed(ActionEvent e)
			{
				String uname = namet.getText();
				String uid = useridt.getText();
				String uemail = emailt.getText();
				String unumber = pnumt.getText();
				String upassword = passwordt.getText();
				String uaddy = addyt.getText();
				String ugender = gendert.getText();
				String uage = aget.getText();
				addsuser(uname, uid, uemail, unumber, upassword,uaddy, ugender, uage);
				frame.setVisible(false);
			}
		});
}
    
	public static void addsuser(String uname, String uid, String uemail, String unumber,String upassword, String uaddy, String ugender, String uage)
	{
		Connection conn1 = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");											
         	
            String dbURL1 = "jdbc:oracle:thin:hr/hr@localhost:1521:xe";
			conn1 = DriverManager.getConnection(dbURL1);
			
			String query = ("INSERT INTO AUCTIONUSER(USERNAME, USERID, EMAILADDRESS, PHONENUMBER, PASSWORD, HOMEADDRESS, GENDER, AGE) "
					+ "VALUES ('"+uname+"','"+uid+"','"+uemail+"','"+unumber+"','"+upassword+"','"+uaddy+"','"+ugender+"','"+uage+"')");
			try (Statement stmt = conn1.createStatement()) {
				stmt.executeUpdate(query);
				} catch (SQLException e) {
					JOptionPane.showMessageDialog(null,  "There is a error in the information entered.", "Error: " + "Adding User", JOptionPane.INFORMATION_MESSAGE);
					System.out.println(e.getErrorCode());
				}
	        }catch (ClassNotFoundException ex) 
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
			
	
	public static void Insertdata()
	{
		Connection conn1 = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");											
         	
            String dbURL1 = "jdbc:oracle:thin:hr/hr@localhost:1521:xe";
			conn1 = DriverManager.getConnection(dbURL1);
			
			String[] query = {
					"INSERT INTO AUCTIONUSER(USERNAME, USERID, EMAILADDRESS, PHONENUMBER, PASSWORD, HOMEADDRESS, GENDER, AGE) VALUES ('Sherry', '5', 'Sherry@ryerson.ca', '6479999999', 'sherryy', '66 Bishop Ave W', 'F', '20')",
					"INSERT INTO AUCTIONUSER(USERNAME, USERID, EMAILADDRESS, PHONENUMBER, PASSWORD, HOMEADDRESS, GENDER, AGE) VALUES ('Tae', '2', 'tae@ryerson.ca', '6471231234', 'thisismapw', '1001 Blah Ave', 'M', '23')",
					"INSERT INTO AUCTIONUSER(USERNAME, USERID, EMAILADDRESS, PHONENUMBER, PASSWORD, HOMEADDRESS, GENDER, AGE) VALUES ('Kevin', '1', 'tahn@ryerson.ca', '6471111111', '123456', '77 Finch Ave E', 'M', '25')",
					"INSERT INTO AUCTIONUSER(USERNAME, USERID, EMAILADDRESS, PHONENUMBER, PASSWORD, HOMEADDRESS, GENDER, AGE) VALUES ('Kate', '3', 'hee@ryerson.ca', '4167777777', 'hello', '1 dundas Ave E', 'F', '25')",
					"INSERT INTO AUCTIONUSER(USERNAME, USERID, EMAILADDRESS, PHONENUMBER, PASSWORD, HOMEADDRESS, GENDER, AGE) VALUES ('Tom', '4', 'tom@ryerson.ca', '4167778989', 'tomtom', '5 dundas Ave E', 'M', '24')",
					"INSERT INTO AUCTIONUSER(USERNAME, USERID, EMAILADDRESS, PHONENUMBER, PASSWORD, HOMEADDRESS, GENDER, AGE) VALUES ('Sharon', '6', 'Sharon@ryerson.ca', '4161234567', 'ssharon', '25 Greenview Avenue', 'F', '22')",
					"INSERT INTO AUCTIONUSER(USERNAME, USERID, EMAILADDRESS, PHONENUMBER, PASSWORD, HOMEADDRESS, GENDER, AGE) VALUES ('Nithash', '7', 'nithash.rajendram@ryerson.ca', '6475642871', 'nithash', '47 Yonge St W', 'M', '20')",
					"INSERT INTO BIDDER(BIDDERID, SHIPPINGADDRESS, PAYMENTINFO, USERID) VALUES ('1', '5 dundas Ave E', 'paypal - 56789', '4')",
					"INSERT INTO BIDDER(BIDDERID, SHIPPINGADDRESS, PAYMENTINFO, USERID) VALUES ('2', '66 Bishop Ave W', 'paypal - 34567', '5')",
					"INSERT INTO BIDDER(BIDDERID, SHIPPINGADDRESS, PAYMENTINFO, USERID) VALUES ('3', '25 Greenview Avenue', 'paypal - 23456', '6')",
					"INSERT INTO SELLER(SELLERID, DEPOSITINFO, USERID) VALUES ('1', 'TD Bank 003-56789', '1')",
					"INSERT INTO SELLER(SELLERID, DEPOSITINFO, USERID) VALUES ('2', 'RBC 005-34567', '2')",
					"INSERT INTO SELLER(SELLERID, DEPOSITINFO, USERID) VALUES ('3', 'CIBC 009-23456', '3')",
					"INSERT INTO BID(PRICE, BIDID, BIDDERID, STATUS, SUBMITTIME) VALUES ('55', '5', '1', 'c', TO_TIMESTAMP('2020-10-09 12:46:39.808790900', 'YYYY-MM-DD HH24:MI:SS.FF'))",
					"INSERT INTO BID(PRICE, BIDID, BIDDERID, STATUS, SUBMITTIME) VALUES ('72', '2', '1', 'c', TO_TIMESTAMP('2020-10-10 12:46:52.970394100', 'YYYY-MM-DD HH24:MI:SS.FF'))",
					"INSERT INTO BID(PRICE, BIDID, BIDDERID, STATUS, SUBMITTIME) VALUES ('299', '6', '2', 'c', TO_TIMESTAMP('2020-10-07 12:46:56.150845500', 'YYYY-MM-DD HH24:MI:SS.FF'))",
					"INSERT INTO BID(PRICE, BIDID, BIDDERID, STATUS, SUBMITTIME) VALUES ('480', '8', '3', 'c', TO_TIMESTAMP('2020-10-06 12:47:00.744006500', 'YYYY-MM-DD HH24:MI:SS.FF'))",
					"INSERT INTO BID(PRICE, BIDID, BIDDERID, STATUS, SUBMITTIME) VALUES ('52', '1', '1', 'c', TO_TIMESTAMP('2020-10-17 12:47:07.551059700', 'YYYY-MM-DD HH24:MI:SS.FF'))",
					"INSERT INTO BID(PRICE, BIDID, BIDDERID, STATUS, SUBMITTIME) VALUES ('280', '3', '2', 'c', TO_TIMESTAMP('2020-10-17 13:47:11.189294400', 'YYYY-MM-DD HH24:MI:SS.FF'))",
					"INSERT INTO BID(PRICE, BIDID, BIDDERID, STATUS, SUBMITTIME) VALUES ('450', '7', '3', 'c', TO_TIMESTAMP('2020-10-24 14:47:15.284508600', 'YYYY-MM-DD HH24:MI:SS.FF'))",
					"INSERT INTO BID(PRICE, BIDID, BIDDERID, STATUS, SUBMITTIME) VALUES ('410', '4', '3', 'c', TO_TIMESTAMP('2020-10-18 10:47:26.406848700', 'YYYY-MM-DD HH24:MI:SS.FF'))",
					"INSERT INTO AUCTIONITEM(AUCTIONITEMID, TITLE, DESCRIPTION, STARTPRICE, ENDPRICE, STARTTIME, ENDTIME, SELLERID, BIDID) VALUES ('1', 'bicycle', '1-year old bicycle for 10-15 years old kids', '150', '299', TO_DATE('2020-10-10 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2020-10-15 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), '1', '6')",
					"INSERT INTO AUCTIONITEM(AUCTIONITEMID, TITLE, DESCRIPTION, STARTPRICE, ENDPRICE, STARTTIME, ENDTIME, SELLERID, BIDID) VALUES ('2', 'office desk', 'office desk for sale', '50', '55', TO_DATE('2020-10-01 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2020-10-03 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), '2', '5')",
					"INSERT INTO AUCTIONITEM(AUCTIONITEMID, TITLE, DESCRIPTION, STARTPRICE, ENDPRICE, STARTTIME, ENDTIME, SELLERID, BIDID) VALUES ('3', 'perfume', 'chanel perfume brand new', '60', '72', TO_DATE('2020-10-15 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2020-10-17 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), '2', '2')",
					"INSERT INTO AUCTIONITEM(AUCTIONITEMID, TITLE, DESCRIPTION, STARTPRICE, ENDPRICE, STARTTIME, ENDTIME, SELLERID, BIDID) VALUES ('4', 'HP laptop', '5 months old laptop - Core i5, 8GB RAM', '400', '480', TO_DATE('2020-10-09 00:00:00', 'YYYY-MM-DD HH24:MI:SS'), TO_DATE('2020-10-19 14:49:01', 'YYYY-MM-DD HH24:MI:SS'), '3', '8')",
					"INSERT INTO ITEMCATEGORY(CATEGORYNAME, CATEGORYID, AUCTIONITEMID) VALUES ('SPORTS', '1', '1')",
					"INSERT INTO ITEMCATEGORY(CATEGORYNAME, CATEGORYID, AUCTIONITEMID) VALUES ('FURNITURE', '2', '2')",
					"INSERT INTO ITEMCATEGORY(CATEGORYNAME, CATEGORYID, AUCTIONITEMID) VALUES ('ELECTRONICS', '3', '4')",
					"INSERT INTO ITEMCATEGORY(CATEGORYNAME, CATEGORYID, AUCTIONITEMID) VALUES ('BEAUTY', '4', '3')",
			};
			
			try (Statement stmt = conn1.createStatement()) {
				for ( int i=0; i<query.length; i++ )
				{
					stmt.executeQuery(query[i]);
				}
				
				} catch (SQLException e) {
					System.out.println(e.getErrorCode());
				}
				

	        }catch (ClassNotFoundException ex) 
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
	
	//Complex queries
		public static String complex()
		{
			String output="\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
			Connection conn1 = null;
	        try {
	            Class.forName("oracle.jdbc.OracleDriver");											
	         	
	            String dbURL1 = "jdbc:oracle:thin:hr/hr@localhost:1521:xe";
				conn1 = DriverManager.getConnection(dbURL1);
				
				String[] query = {	
						"SELECT * FROM AUCTIONITEM WHERE (ENDPRICE-STARTPRICE)>100 UNION SELECT *  FROM AUCTIONITEM WHERE (ENDTIME-STARTTIME)<5",
						"SELECT i.title, i.endtime FROM AUCTIONITEM i, bid b WHERE status = 'c' MINUS (SELECT ai.title, ai.endtime FROM auctionitem ai WHERE ai.ENDTIME >= CURRENT_DATE - 15 )"
				};
				
				try (Statement stmt = conn1.createStatement()) {
					
					
					ResultSet rs = stmt.executeQuery(query[0]);
					output=output+query[0]+"\n";
					while (rs.next()) {
						int id = rs.getInt("AUCTIONITEMID");
						String title = rs.getString("TITLE");
						String des = rs.getString("DESCRIPTION");
						int sp = rs.getInt("STARTPRICE");
						int ep = rs.getInt("ENDPRICE");
						String st = rs.getString("STARTTIME");
						String et = rs.getString("ENDTIME");
						int sid = rs.getInt("SELLERID");
						int bidid = rs.getInt("BIDID");
						output=output+(id+", "+title+", "+des+", "+sp+", "+ep+", "+st+", "+et+", "+sid+", "+bidid+"\n");
					}
					
					rs = stmt.executeQuery(query[1]);
					output=output+"\n"+query[1]+"\n";
					while (rs.next()) {
						String title = rs.getString("TITLE");
						String et = rs.getString("ENDTIME");
						output=output+(title+", "+et+"\n");
					}
					
					
					} catch (SQLException e) {
						System.out.println(e.getErrorCode());
					}
					

		        }catch (ClassNotFoundException ex) 
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
			return output;
		}
	
	//simple queries
	public static String simpleq()
	{
		String output="\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
		Connection conn1 = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");											
         	
            String dbURL1 = "jdbc:oracle:thin:hr/hr@localhost:1521:xe";
			conn1 = DriverManager.getConnection(dbURL1);
			
			String[] query = {
					"SELECT username,gender, age FROM AUCTIONUSER ORDER BY gender ASC, age Asc",
					"SELECT * FROM BIDDER WHERE paymentinfo like '%paypal%' AND shippingaddress like '%dundas%'",
					"SELECT USERNAME, EMAILADDRESS FROM AUCTIONUSER",
					"SELECT * FROM SELLER WHERE NOT (DEPOSITINFO like '%TD%')"			
			};
			
			try (Statement stmt = conn1.createStatement()) {
				
				ResultSet rs = stmt.executeQuery(query[0]);
				output=output+query[0]+"\n";
				while (rs.next()) {
					String name = rs.getString("username");
					String gender = rs.getString("gender");
					int age = rs.getInt("age");
					output=output+(name+", "+gender + ", " + age+"\n");
				}
				
				rs = stmt.executeQuery(query[1]);
				output=output+"\n"+query[1]+"\n";
				while (rs.next()) {
					int bidid = rs.getInt("BIDDERID");
					String addy = rs.getString("SHIPPINGADDRESS");
					String pay = rs.getString("PAYMENTINFO");
					int useid = rs.getInt("USERID");
					output=output+(bidid+", "+addy + ", " + pay+", "+useid+"\n");
				}
				
				rs = stmt.executeQuery(query[2]);
				output=output+"\n"+query[2]+"\n";
				while (rs.next()) {
					String usname = rs.getString("USERNAME");
					String email = rs.getString("EMAILADDRESS");
					output=output+(usname+", "+email+"\n");
				}
				
				rs = stmt.executeQuery(query[3]);
				output=output+"\n"+query[3]+"\n";
				while (rs.next()) {
					int sellid = rs.getInt("SELLERID");
					String pay = rs.getString("DEPOSITINFO");
					int useid = rs.getInt("USERID");
					output=output+(sellid+", " + pay+", "+useid+"\n");
				}
				
				
				} catch (SQLException e) {
					System.out.println(e.getErrorCode());
				}
				

	        }catch (ClassNotFoundException ex) 
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
		return output;
	}
	
	//drop tables
	public static void droptables()
	{
		Connection conn1 = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");											
         	
            String dbURL1 = "jdbc:oracle:thin:hr/hr@localhost:1521:xe";
			conn1 = DriverManager.getConnection(dbURL1);
			
			String[] query = { "DROP TABLE ITEMCATEGORY", "DROP TABLE AUCTIONITEM","DROP TABLE BID","DROP TABLE BIDDER","DROP TABLE SELLER","DROP TABLE AUCTIONUSER"};
	
		try (Statement stmt = conn1.createStatement()) {
			for ( int i=0; i<query.length; i++ )
			{
				stmt.executeQuery(query[i]);
			}
			
			} catch (SQLException e) {
				System.out.println(e.getErrorCode());
			}
			

        }catch (ClassNotFoundException ex) 
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
	
	
	
	//create tables
	public static void createtables()
	{

		Connection conn1 = null;
        try {
            Class.forName("oracle.jdbc.OracleDriver");											
         	
            String dbURL1 = "jdbc:oracle:thin:hr/hr@localhost:1521:xe";
			conn1 = DriverManager.getConnection(dbURL1);
			
			String query = "CREATE TABLE AUCTIONUSER (\r\n"
					+ "    UserName VARCHAR2(30) NOT NULL,\r\n"
					+ "    UserID int NOT NULL,\r\n"
					+ "    EmailAddress VARCHAR2(255) NOT NULL,\r\n"
					+ "    PhoneNumber VARCHAR2(10),\r\n"
					+ "    Password VARCHAR2(32) NOT NULL,\r\n"
					+ "    HomeAddress VARCHAR2(40),\r\n"
					+ "    Gender VARCHAR2(10),\r\n"
					+ "    Age int NOT NULL,\r\n"
					+ "    CONSTRAINT aucusr_uid_pk PRIMARY KEY (UserID)\r\n"
					+ "    \r\n"
					+ ")";
			
			String query2 = "CREATE TABLE Seller (\r\n"
					+ "    sellerID int NOT NULL, \r\n"
					+ "    DepositInfo VARCHAR2(255) NOT NULL,\r\n"
					+ "    UserID int NOT NULL,\r\n"
					+ "    CONSTRAINT sell_sid_pk PRIMARY KEY (sellerID),\r\n"
					+ "    CONSTRAINT sell_uid_fk FOREIGN KEY (UserID) REFERENCES AUCTIONUSER (UserID)\r\n"
					+ ")";
			
			String query3 ="CREATE TABLE Bidder(\r\n"
					+ "    BidderID int NOT NULL, \r\n"
					+ "    ShippingAddress VARCHAR(255) NOT NULL,\r\n"
					+ "    PaymentInfo VARCHAR(255) NOT NULL,\r\n"
					+ "    UserID int NOT NULL,\r\n"
					+ "    CONSTRAINT bidder_bid_pk PRIMARY KEY (BidderID),\r\n"
					+ "    CONSTRAINT bidder_uid_fk FOREIGN KEY (UserID) REFERENCES AUCTIONUSER (UserID)\r\n"
					+ ")";
					
			String query4 = "CREATE TABLE Bid(\r\n"
					+ "    Price DECIMAL(10,2) NOT NULL,\r\n"
					+ "    BidID int NOT NULL,\r\n"
					+ "    BidderID int NOT NULL, \r\n"
					+ "    Status char(1),\r\n"
					+ "    SubmitTime timestamp,\r\n"
					+ "    CONSTRAINT bid_id_pk PRIMARY KEY (BidID),\r\n"
					+ "    CONSTRAINT bid_bidfk FOREIGN Key (BidderID) REFERENCES Bidder (BidderID)\r\n"
					+ ")";
					
					
			String query5 = "CREATE TABLE AuctionItem( \r\n"
					+ "    AuctionItemID int NOT NULL,\r\n"
					+ "    title VARCHAR2 (255) NOT NULL,\r\n"
					+ "    Description VARCHAR2 (500)  NULL,\r\n"
					+ "    StartPrice DECIMAL (10,2) NOT NULL, \r\n"
					+ "    EndPrice DECIMAL (10,2) NOT NULL, \r\n"
					+ "    StartTime DATE  NOT NULL, \r\n"
					+ "    EndTime DATE  NOT NULL,\r\n"
					+ "    sellerID int NOT NULL, \r\n"
					+ "    BidID int NOT NULL, \r\n"
					+ "    CONSTRAINT auc_item_pk PRIMARY KEY (AuctionItemID),\r\n"
					+ "    CONSTRAINT auc_sid_fk FOREIGN KEY (sellerID) REFERENCES seller (sellerID), \r\n"
					+ "    CONSTRAINT auc_bid_fk FOREIGN KEY (BidID) REFERENCES Bid (BidID)\r\n"
					+ ")";
							
			String query6 = "CREATE TABLE ItemCategory(\r\n"
					+ "    Categoryname VARCHAR2(32),\r\n"
					+ "    CategoryId int NOT NULL,\r\n"
					+ "    AuctionItemID int NOT NULL,\r\n"
					+ "    CONSTRAINT cat_id_pk PRIMARY KEY (CategoryId),\r\n"
					+ "    CONSTRAINT cat_aucid_fk FOREIGN Key (AuctionItemID) REFERENCES AuctionItem (AuctionItemID)\r\n"
					+ ")";
			
			
			try (Statement stmt = conn1.createStatement()) {

			stmt.executeQuery(query);
			stmt.executeQuery(query2);
			stmt.executeQuery(query3);
			stmt.executeQuery(query4);
			stmt.executeQuery(query5);
			stmt.executeQuery(query6);
			
			} catch (SQLException e) {
				System.out.println(e.getErrorCode());
			}
			

        }catch (ClassNotFoundException ex) 
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
	
	
}
