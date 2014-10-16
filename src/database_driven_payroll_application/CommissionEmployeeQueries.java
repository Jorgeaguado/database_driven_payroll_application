package database_driven_payroll_application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class CommissionEmployeeQueries
{

   private Connection connection = null; // manages connection
   private PreparedStatement selectEmployeeBySS = null; 
   private PreparedStatement selectAllCommissionEmployees = null; 
   private PreparedStatement insertNewCommissionEmployee = null; 
   private PreparedStatement updateCommissionEmployee = null; 
   private PreparedStatement deleteCommissionEmployee = null; 
    
   // constructor
   public CommissionEmployeeQueries(ConnectDB connect)
   {
	   connection = connect.getConnection();
	   
	   try 
	   {
         	// create query that selects all entries in the AddressBook
         	selectAllCommissionEmployees = 
         	connection.prepareStatement( "SELECT * FROM commissionEmployees" );
         
         	selectEmployeeBySS  = 
            		connection.prepareStatement( "SELECT * FROM commissionEmployees WHERE socialSecurityNumber = ?" );
         	// create insert that adds a new entry into the database
         	insertNewCommissionEmployee = connection.prepareStatement( 
            		"INSERT INTO commissionEmployees " + 
            		"( socialSecurityNumber, grossSales, commissionRate, bonus  ) " + 
            		"VALUES ( ?, ?, ?, ? )" );

         	updateCommissionEmployee =  connection.prepareStatement( 
            		"UPDATE commissionEmployees " + 
            		"set  grossSales = ?,"+ " commissionRate = ?, "+" bonus = ? "+" WHERE socialSecurityNumber = ?" );

         	deleteCommissionEmployee = connection.prepareStatement( 
            		"DELETE FROM commissionEmployees WHERE socialSecurityNumber = ?" );
	   } // end try
	   catch ( SQLException sqlException )
	   {
         	sqlException.printStackTrace();
         	System.exit( 1 );
	   } // end catch

   } // end PersonQueries constructor
 

   public int deleteCommissionEmployee(String ss)
   {
     	int result = 0;

  	// set parameters, then execute insertNewPerson
      	try 
      	{
      		deleteCommissionEmployee.setString( 1, ss );

       		result = deleteCommissionEmployee.executeUpdate(); 
      	} // end try
      	catch ( SQLException sqlException )
      	{
         	sqlException.printStackTrace();
         	close();
      	} // end catch
      
      	return result;
   }


   public int updateCommissionEmployee(String ss, double grossS,int rate, double bonus)
   {
      	int result = 0;

      	// set parameters, then execute update
      	try 
      	{
      		updateCommissionEmployee.setDouble( 1, grossS );
      		updateCommissionEmployee.setInt( 2, rate );
      		updateCommissionEmployee.setDouble( 3, bonus );
       		updateCommissionEmployee.setString( 4, ss );

       		result = updateCommissionEmployee.executeUpdate(); 
      	} // end try
      	catch ( SQLException sqlException )
      	{
     		sqlException.printStackTrace();
       		close();
      	} // end catch
      
      	return result;

   }


   // select all of the addresses in the database
   public List< CommissionEmployee > getCommissionEmployeeBySS(String ss)
   {
      	List< CommissionEmployee > results = null;
      	ResultSet resultSet = null;

      	// set parameters, then execute insertNewPerson
      	try 
      	{
     		selectEmployeeBySS.setString( 1, ss );

       		resultSet = selectEmployeeBySS.executeQuery(); 
       		results = new ArrayList< CommissionEmployee >();
         
       		while ( resultSet.next() )
       		{
       			results.add( new CommissionEmployee(
       			resultSet.getString( "socialSecurityNumber" ),
      			resultSet.getDouble( "grossSales" ),
               		resultSet.getInt( "commissionRate" ),
               		resultSet.getDouble( "bonus" ) 
            			) 
            		);
         	} // end w

      	} // end try
      	catch ( SQLException sqlException )
      	{
      		sqlException.printStackTrace();
      		close();
      	} // end catch
      
      	return results;

   } // end method 




   public List< CommissionEmployee > getAllCommissionEmployees()
   {
      	List< CommissionEmployee > results = null;
      	ResultSet resultSet = null;
      	try 
      	{
      		resultSet = selectAllCommissionEmployees.executeQuery(); 
       		results = new ArrayList< CommissionEmployee >();
         
       		while ( resultSet.next() )
       		{
      			results.add( new CommissionEmployee(
               		resultSet.getString( "socialSecurityNumber" ),
               		resultSet.getDouble( "grossSales" ),
     			resultSet.getInt( "commissionRate" ),
       			resultSet.getDouble( "bonus" ) 	) );
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
   public int addCommissionEmployee(String ss, double grossS, int rate, double bonus)
   {
      	int result = 0;
      	// set parameters, then execute insertNewPerson
      	try 
      	{
       		insertNewCommissionEmployee.setString( 1, ss );
       		insertNewCommissionEmployee.setDouble( 2, grossS );
       		insertNewCommissionEmployee.setInt( 3, rate );
       		insertNewCommissionEmployee.setDouble( 4, bonus );

         	// insert the new entry; returns # of rows updated
         	result = insertNewCommissionEmployee.executeUpdate(); 
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

