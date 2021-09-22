import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class A3 extends Thread 
{
    // Set default number of threads to 10
    private static int NUM_OF_THREADS = 2;

    int m_myId;

    static int c_nextId = 1;
    static Connection s_conn = null;
    static Semaphore semaphore = new Semaphore(1);

    synchronized static int getNextId()
    {
        return c_nextId++;
    }

    public static void main (String args [])
    {
        try  
        {  
            // Load the JDBC driver //
            DriverManager.registerDriver 
                     (new oracle.jdbc.driver.OracleDriver());
             Class.forName("oracle.jdbc.OracleDriver");
            // If NoOfThreads is specified, then read it
            if (args.length > 1) {
                System.out.println("Error: Invalid Syntax. ");
                System.out.println("java JdbcMTSample [NoOfThreads]");
                System.exit(0);
            }
            else if (args.length == 1)

                NUM_OF_THREADS = Integer.parseInt (args[0]);
    
            // Create the threads
            Thread[] threadList = new Thread[NUM_OF_THREADS];

            // spawn threads
            for (int i = 0; i < NUM_OF_THREADS; i++)
            {
                threadList[i] = new A3();
                threadList[i].start();
            }

            // wait for all threads to end
            for (int i = 0; i < NUM_OF_THREADS; i++)
            {
                    threadList[i].join();
            }
            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }  
 
    public A3()
    {
       super();
       // Assign an ID to the thread
       m_myId = getNextId();
    }

  public void run()
  {
      Connection conn1 = null;
      String[] dbURL ={"", "jdbc:oracle:thin:hr/hr@localhost:1521:xe", "jdbc:oracle:thin:n4rajend/04192702@oracle.scs.ryerson.ca:1521:orcl"};
      
      String nameToInsert = "Nithash";
  	  int num = 10;
  	  
      try{
    	  semaphore.acquire(); // providing mutual exclusion
          
          Class.forName("oracle.jdbc.OracleDriver");											
       	
          //connection
		  conn1 = DriverManager.getConnection(dbURL[m_myId]);
          if (conn1 != null) {
              System.out.println("Connected with connection #"+m_myId);
          }
  		
  		//recording names
  		String query = "SELECT NAME, NUM from TESTJDBC";
        ArrayList<String> namesSite = new ArrayList<String>();
        try (Statement stmt = conn1.createStatement()) {
          	ResultSet rs2 = stmt.executeQuery(query);
          	while (rs2.next()) {
          			String name2 = rs2.getString("NAME");
          			namesSite.add(name2.toLowerCase());
          			}
          		} catch (SQLException e) {
          			System.out.println(e.getErrorCode());
          			}
          
  		//adding name
  		if (namesSite.contains(nameToInsert.toLowerCase())){
      		System.out.println("name already present, abort transaction");
      		}
      	else {
      		String insert = "INSERT INTO TESTJDBC VALUES ('" + nameToInsert + "'," +num+")";
      		try (Statement stmt = conn1.createStatement()) {
      				stmt.executeQuery(insert);
      		}catch(SQLException e) {}
      	}
      			
  		
  		//printing names
          query = "select NAME, NUM from TESTJDBC";
          try (Statement stmt = conn1.createStatement()) {
          	ResultSet rs = stmt.executeQuery(query);
          	System.out.println("SITE "+m_myId);
          	while (rs.next()) {
          		String name = rs.getString("NAME");
          		System.out.println(name);
          		}
          	
          	} catch (SQLException e) {
          		System.out.println(e.getErrorCode());
          		} 
          
        //rerecording names
    	query = "SELECT NAME, NUM from TESTJDBC";
        namesSite = new ArrayList<String>();
        try (Statement stmt = conn1.createStatement()) {
           	ResultSet rs2 = stmt.executeQuery(query);
           	while (rs2.next()) {
           			String name2 = rs2.getString("NAME");
           			namesSite.add(name2.toLowerCase());
           			}
           		} catch (SQLException e) {
           			System.out.println(e.getErrorCode());
           			}
          
          //delete name
        
        if (namesSite.contains(nameToInsert.toLowerCase())){
        	String delete = "DELETE FROM TESTJDBC WHERE Name='"+nameToInsert+"' and NUM="+num;
      		try (Statement stmt2 = conn1.createStatement()) {
      				stmt2.executeQuery(delete);
      		}catch(SQLException e) {}
      		if (conn1 != null) {
                conn1.close();
                System.out.println("Thread " + m_myId +  " is finished. ");
      		}
      	}
      	else {
      		System.out.println("Name not present, abort transaction");
      		}
      
      }
      
      
      catch (Exception e)
      {
          System.out.println("Thread " + m_myId + " got Exception: " + e);
          e.printStackTrace();
          return;
      }
      semaphore.release();
  
      }
  }
