package database_driven_payroll_application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ConnectDB 
{
   	private String DRIVER;
	private String URL;
   	private String USERNAME;
   	private String PASSWORD;
	private Connection connection = null; // manages connection

	public ConnectDB()
	{
    		Properties prop = new Properties();
    		String fileName = "Config.txt";
    		try 
      		{
    			InputStream is = new FileInputStream(fileName);
    			prop.load(is);
      		} // end try
    		catch(FileNotFoundException fnfe)
    		{
    			fnfe.printStackTrace();
    			//System.exit( 1 );
    		}
    		catch(IOException IOEx)
    		{
				System.out.println("Exception IO");
         		//System.exit( 1 );
    		}

    		DRIVER = prop.getProperty("DRIVER");
    		URL = prop.getProperty("URL");
    		USERNAME = prop.getProperty("USERNAME");
    		PASSWORD = prop.getProperty("PASSWORD");

		try
		{
	      		connection = DriverManager.getConnection( URL, USERNAME, PASSWORD );

		}
      		catch ( SQLException sqle )
      		{
      			sqle.printStackTrace();
         		System.exit( 1 );
      		} // end catch

	}
	
	public Connection getConnection()
	{
		return connection;
	}

	public String getDriver()
	{
		return DRIVER;
	}


	public String getDatabaseDriver()
	{
		return URL;
	}


	public String getUserName()
	{
		return USERNAME;
	}


	public String getPassword()
	{
		return PASSWORD;
	}


}

