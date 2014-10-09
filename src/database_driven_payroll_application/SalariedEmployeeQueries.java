package database_driven_payroll_application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class SalariedEmployeeQueries
{
   	private Connection connection = null; // manages connection
   	private PreparedStatement selectEmployeeBySS = null; 
   	private PreparedStatement selectAllSalariedEmployees = null; 
   	private PreparedStatement insertNewSalariedEmployee = null; 
   	private PreparedStatement updateSalariedEmployee = null; 
   	private PreparedStatement deleteSalariedEmployee = null; 
    
   	// constructor
   	public SalariedEmployeeQueries(ConnectDB connect)
   	{
		connection = connect.getConnection();
      		try 
      		{

		        selectAllSalariedEmployees = 
		            connection.prepareStatement( "SELECT * FROM salariedEmployees" );
         
		        selectEmployeeBySS  = 
            			connection.prepareStatement( "SELECT * FROM salariedEmployees WHERE socialSecurityNumber = ?" );

         		insertNewSalariedEmployee = connection.prepareStatement( 
            			"INSERT INTO salariedEmployees " + 
            			"( socialSecurityNumber, weeklySalary, bonus   ) " + 
            			"VALUES ( ?, ?, ? )" );

         		updateSalariedEmployee =  connection.prepareStatement( 
            			"UPDATE salariedEmployees " + 
            			"set  weeklySalary = ?,"+ " bonus= ? "+" WHERE socialSecurityNumber = ?" );

         		deleteSalariedEmployee = connection.prepareStatement( 
            			"DELETE FROM salariedEmployees WHERE socialSecurityNumber = ?" );
      		} // end try
      		catch ( SQLException sqlException )
      		{
         		sqlException.printStackTrace();
         		System.exit( 1 );
      		} // end catch

   	} // end 
 

	public int deleteSalariedEmployee(String ss)
	{
     		int result = 0;

      		try 
      		{
         		deleteSalariedEmployee.setString( 1, ss );

         		result = deleteSalariedEmployee.executeUpdate(); 
      		} // end try
      		catch ( SQLException sqlException )
      		{
         		sqlException.printStackTrace();
         		close();
      		} // end catch
      
      		return result;
	}




	public int updateSalariedEmployee(String ss, double salary, double bonus)
	{
      		int result = 0;

      		try 
      		{
         		updateSalariedEmployee.setDouble( 1, salary );
         		updateSalariedEmployee.setDouble( 2, bonus );
         		updateSalariedEmployee.setString( 3, ss );

         		result = updateSalariedEmployee.executeUpdate(); 
      		} // end try
      		catch ( SQLException sqlException )
      		{
         		sqlException.printStackTrace();
         		close();
      		} // end catch
      
      		return result;

	}


   	public List< SalariedEmployee > getSalariedEmployeeBySS(String ss)
   	{
      		List< SalariedEmployee > results = null;
      		ResultSet resultSet = null;

      		try 
      		{
         		selectEmployeeBySS.setString( 1, ss );

         		resultSet = selectEmployeeBySS.executeQuery(); 
         		results = new ArrayList< SalariedEmployee >();
         
         		while ( resultSet.next() )
         		{
            			results.add( new SalariedEmployee(
               			resultSet.getString( "socialSecurityNumber" ),
               			resultSet.getDouble( "weeklySalary" ),
               			resultSet.getDouble( "bonus" ) ) );
         		} // end w

      		} // end try
      		catch ( SQLException sqlException )
      		{
         		sqlException.printStackTrace();
         		close();
      		} // end catch
      
      		return results;

   } // end method 



   	public List< SalariedEmployee > getAllSalariedEmployees()
   	{
      		List< SalariedEmployee > results = null;
      		ResultSet resultSet = null;
      
      		try 
      		{

         		resultSet = selectAllSalariedEmployees.executeQuery(); 
         		results = new ArrayList< SalariedEmployee >();
         
         		while ( resultSet.next() )
         		{
            			results.add( new SalariedEmployee(
               			resultSet.getString( "socialSecurityNumber" ),
               			resultSet.getDouble( "weeklySalary" ),
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
   	} // end method 


   	// add an entry
   	public int addSalariedEmployee( String socialSecurityNumber, double weeklySalary, double bonus )
   	{

      		int result = 0;
      		try 
      		{
         		insertNewSalariedEmployee.setString( 1, socialSecurityNumber );
         		insertNewSalariedEmployee.setDouble( 2, weeklySalary );
         		insertNewSalariedEmployee.setDouble( 3, bonus );

		        result = insertNewSalariedEmployee.executeUpdate(); 
      		} // end try
      		catch ( SQLException sqlException )
      		{
         		sqlException.printStackTrace();
         		close();
      		} // end catch
      
      		return result;
   	} // end method 


   	// close the database connection
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

} // end 

