package database_driven_payroll_application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class HourlyEmployeeQueries
{

   	private Connection connection = null; // manages connection
   	private PreparedStatement selectEmployeeBySS = null; 
   	private PreparedStatement selectAllHourlyEmployees = null; 
   	private PreparedStatement insertNewHourlyEmployee = null; 
   	private PreparedStatement updateHourlyEmployee = null; 
   	private PreparedStatement deleteHourlyEmployee = null; 
    
   	// constructor
   	public HourlyEmployeeQueries(ConnectDB connect)
   	{
		connection = connect.getConnection();
      		try 
      		{
	         	selectAllHourlyEmployees = 
		            connection.prepareStatement( "SELECT * FROM hourlyEmployees" );
         
	         	selectEmployeeBySS  = 
		            connection.prepareStatement( "SELECT * FROM hourlyEmployees WHERE socialSecurityNumber = ?" );

	         	insertNewHourlyEmployee = connection.prepareStatement( 
		            "INSERT INTO hourlyEmployees " + 
		            "( socialSecurityNumber, hours, wage, bonus   ) " + 
		            "VALUES ( ?, ?, ?, ? )" );

	         	updateHourlyEmployee =  connection.prepareStatement( 
		            "UPDATE hourlyEmployees " + 
		            "set  hours = ?,"+ " wage = ?, "+" bonus = ? "+" WHERE socialSecurityNumber = ?" );

	         	deleteHourlyEmployee = connection.prepareStatement( 
	        		 "DELETE FROM hourlyEmployees WHERE socialSecurityNumber = ?" );
      		} // end try
      		catch ( SQLException sqlException )
      		{
       			sqlException.printStackTrace();
         		System.exit( 1 );
      		} // end catch
   	} 
 

	public int deleteHourlyEmployee(String ss)
	{
     		int result = 0;
      		try 
      		{
         		deleteHourlyEmployee.setString( 1, ss );
         		result = deleteHourlyEmployee.executeUpdate(); 
      		} // end try
      		catch ( SQLException sqlException )
      		{
         		sqlException.printStackTrace();
         		close();
      		} // end catch

      		return result;
	}

	public int updateHourlyEmployee(String ss, int h, double w, double b)
	{
      		int result = 0;
      		try 
      		{
         		updateHourlyEmployee.setInt( 1, h );
         		updateHourlyEmployee.setDouble( 2, w );
         		updateHourlyEmployee.setDouble( 3, b );
         		updateHourlyEmployee.setString( 4, ss );

         		result = updateHourlyEmployee.executeUpdate(); 
      		} // end try
      		catch ( SQLException sqlException )
      		{
         		sqlException.printStackTrace();
         		close();
      		} // end catch
      
      		return result;
	}

	public List< HourlyEmployee > getHourlyEmployeeBySS(String ss)
   	{
      		List< HourlyEmployee > results = null;
      		ResultSet resultSet = null;

      		try 
      		{
         		selectEmployeeBySS.setString( 1, ss );

         		resultSet = selectEmployeeBySS.executeQuery(); 
         		results = new ArrayList< HourlyEmployee >();
         
         		while ( resultSet.next() )
         		{
            			results.add( new HourlyEmployee(
               			resultSet.getString( "socialSecurityNumber" ),
               			resultSet.getInt( "hours" ),
               			resultSet.getDouble( "wage" ),
               			resultSet.getDouble( "bonus" ) ) );
         		} 

      		} // end try
      		catch ( SQLException sqlException )
      		{
         		sqlException.printStackTrace();
         		close();
      		} // end catch
      
      		return results;
	} 



	public List< HourlyEmployee > getAllHourlyEmployees()
   	{
      		List< HourlyEmployee > results = null;
      		ResultSet resultSet = null;
      
      		try 
      		{
		         resultSet = selectAllHourlyEmployees.executeQuery(); 
		         results = new ArrayList< HourlyEmployee >();
         
		         while ( resultSet.next() )
		         {
	            		results.add( new HourlyEmployee(
	               		resultSet.getString( "socialSecurityNumber" ),
	               		resultSet.getInt( "hours" ),
	               		resultSet.getDouble( "wage" ),
	               		resultSet.getDouble( "bonus" ) ) );
		         } // end while
	      	} // end try
      		catch ( SQLException sqlException )
      		{
         		sqlException.printStackTrace();         
      		} // end catch
      		finally
      		{
 	        	try 
        	 	{
        	    		resultSet.close();
        	 	} // end try
        	 	catch ( SQLException sqlException )
        	 	{
        	    		sqlException.printStackTrace();         
        	    		close();
        		 } // end catch
      		} // end finally
      
     	 	return results;
	} 




	public int addHourlyEmployee(String ss, int h, double w, double b)
   	{

      	int result = 0;

		try 
      		{
			insertNewHourlyEmployee.setString( 1, ss );
       			insertNewHourlyEmployee.setInt( 2, h );
         		insertNewHourlyEmployee.setDouble( 3, w );
         		insertNewHourlyEmployee.setDouble( 4, b );

		         result = insertNewHourlyEmployee.executeUpdate(); 
	        } // end try
      		catch ( SQLException sqlException )
      		{
         		sqlException.printStackTrace();
         		close();
      		} // end catch
      
      		return result;
	} 


   	public void close()
   	{
      		try 
      		{
         		connection.close();
      		} // end try
      		catch ( SQLException sqlException )
      		{
         		sqlException.printStackTrace();
      		} // end catch
   	} // end method close

} 
