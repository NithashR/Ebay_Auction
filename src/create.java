//Ebay Auction DataBase GUI
//Author:Nithash Rajendram
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//This file consists the code that creates the tables for the database
public class create {

	public static void main(String[] args) {
		createtables();
	}
	
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
			
			
			try (Statement stmt = conn1.createStatement()) { //enters queries and catches error

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
