package database_driven_payroll_application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class BasePlusCommissionEmployeeQueries
{

   private Connection connection = null; // manages connection
   private PreparedStatement selectEmployeeBySS = null; 
   private PreparedStatement selectAllBasePlusCommissionEmployees = null; 
   private PreparedStatement insertNewBasePlusCommissionEmployee = null; 
   private PreparedStatement updateBasePlusCommissionEmployee = null; 
   private PreparedStatement deleteBasePlusCommissionEmployee = null; 
    
   // constructor
   public BasePlusCommissionEmployeeQueries(ConnectDB connect)
   {
	   
	  connection = connect.getConnection();
      try 
      {
        // create query that selects all entries in the AddressBook
        selectAllBasePlusCommissionEmployees = 
            connection.prepareStatement( "SELECT * FROM basePlusCommissionEmployees" );
         
		selectEmployeeBySS  = 
            connection.prepareStatement( "SELECT * FROM basePlusCommissionEmployees WHERE socialSecurityNumber = ?" );
         	// create insert that adds a new entry into the database
        insertNewBasePlusCommissionEmployee = connection.prepareStatement( 
       		"INSERT INTO basePlusCommissionEmployees " + 
            "( socialSecurityNumber, grossSales, commissionRate, baseSalary, bonus   ) " + 
           	"VALUES ( ?, ?, ?, ?, ? )" );

		updateBasePlusCommissionEmployee =  connection.prepareStatement( 
            "UPDATE basePlusCommissionEmployees " + 
           	"set  grossSales = ?,"+ " commissionRate = ?, "+" baseSalary = ?,"+" bonus = ? "+" WHERE socialSecurityNumber = ?" );

		deleteBasePlusCommissionEmployee = connection.prepareStatement( 
      		"DELETE FROM basePlusCommissionEmployees WHERE socialSecurityNumber = ?" );
      } // end try
      catch ( SQLException sqlException )
      {
       	sqlException.printStackTrace();
       	System.exit( 1 );
      } // end catch
      

   } // end 
 

	public int deleteBasePlusCommissionEmployee(String ss)
	{
     	int result = 0;

      	// set parameters, then execute insertNewPerson
   		try 
  		{
         		deleteBasePlusCommissionEmployee.setString( 1, ss );
         		result = deleteBasePlusCommissionEmployee.executeUpdate(); 
      	} // end try
      	catch ( SQLException sqlException )
      	{
       		sqlException.printStackTrace();
       		close();
   		} // end catch
      
      	return result;
	}




	public int updateBasePlusCommissionEmployee( String socialSecurityN, double grossS, int commission, double baseS, double bon )
	{
      	int result = 0;
      	// set parameters, then execute insertNewPerson
      	try 
      	{
         	updateBasePlusCommissionEmployee.setDouble( 1, grossS );
         	updateBasePlusCommissionEmployee.setInt( 2, commission );
         	updateBasePlusCommissionEmployee.setDouble( 3,baseS );
         	updateBasePlusCommissionEmployee.setDouble( 4, bon );
         		
         	updateBasePlusCommissionEmployee.setString( 5, socialSecurityN );

         	result = updateBasePlusCommissionEmployee.executeUpdate(); 
      	} // end try
      	catch ( SQLException sqlException )
      	{
         	sqlException.printStackTrace();
         	close();
      	} // end catch
      
      	return result;
	}




   	public List< BasePlusCommissionEmployee > getBasePlusCommissionEmployeeBySS(String ss)
   	{
   		
      	List< BasePlusCommissionEmployee > results = null;
      	ResultSet resultSet = null;

      	try 
      	{
      		selectEmployeeBySS.setString( 1, ss );

         	resultSet = selectEmployeeBySS.executeQuery(); 
         	results = new ArrayList< BasePlusCommissionEmployee >();
       
         	while ( resultSet.next() )
         	{
           		results.add( new BasePlusCommissionEmployee(
      			resultSet.getString( "socialSecurityNumber" ),
      			resultSet.getDouble( "grossSales" ),
      			resultSet.getInt( "commissionRate" ),
          		resultSet.getDouble( "baseSalary" ),
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


   	public List< BasePlusCommissionEmployee > getAllBasePlusCommissionEmployees()
   	{
      	List< BasePlusCommissionEmployee > results = null;
  		ResultSet resultSet = null;

      	try 
      	{

      		resultSet = selectAllBasePlusCommissionEmployees.executeQuery(); 
        	results = new ArrayList< BasePlusCommissionEmployee >();
         
      		while ( resultSet.next() )
      		{
          		results.add( new BasePlusCommissionEmployee(
      			resultSet.getString( "socialSecurityNumber" ),
               	resultSet.getDouble( "grossSales" ),
      			resultSet.getInt( "commissionRate" ),
               	resultSet.getDouble( "baseSalary" ),
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
   } // end method getAllPeople




   	// add an entry
   	public int addBasePlusCommissionEmployee( String socialSecurityN, double grossS, int commission, double baseS, double bon )
   	{

      	int result = 0;
      
      	try 
      	{
       		insertNewBasePlusCommissionEmployee.setString( 1, socialSecurityN );
      		insertNewBasePlusCommissionEmployee.setDouble( 2, grossS );
         	insertNewBasePlusCommissionEmployee.setInt( 3, commission );
         	insertNewBasePlusCommissionEmployee.setDouble( 4, baseS );
         	insertNewBasePlusCommissionEmployee.setDouble( 5, bon );

         	// insert the new entry; returns # of rows updated
       		result = insertNewBasePlusCommissionEmployee.executeUpdate(); 
      	} // end try
      	catch ( SQLException sqlException )
      	{
       		sqlException.printStackTrace();
         	close();
      	} // end catch

   		return result;
   } // end method addPerson



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

} // end interface PersonQueries

