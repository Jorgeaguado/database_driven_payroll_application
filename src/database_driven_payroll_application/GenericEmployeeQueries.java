package database_driven_payroll_application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class GenericEmployeeQueries
{

   private Connection connection = null; // manages connection
   private PreparedStatement selectEmployeeByLastName = null; 
   private PreparedStatement selectEmployeeByType = null; 
   private PreparedStatement selectEmployeeBySS = null; 
   private PreparedStatement selectAllEmployees = null; 
   private PreparedStatement insertNewEmployee = null; 
   private PreparedStatement updateEmployee = null; 
    
   // constructor
   	public GenericEmployeeQueries(ConnectDB connect)
   	{
		connection = connect.getConnection();
      		try 
      		{
        	 	// create query that selects all entries in the AddressBook
        	 	selectAllEmployees = 
        	 	   connection.prepareStatement( "SELECT * FROM employees" );
        	 
        	 	// create insert that adds a new entry into the database
        	 	insertNewEmployee = connection.prepareStatement( 
        	 	   "INSERT INTO employees " + 
        	    		"( socialSecurityNumber, firstName, lastName, birthday, employeeType, departmentName   ) " 					+"VALUES ( ?, ?, ?, ?, ?, ? )" );

        		updateEmployee = connection.prepareStatement( 
            			"UPDATE employees " + 
            			"set firstName = ?,"+ " lastName = ?,"+ " birthday=?,"+" employeeType=?, "+" 					departmentName=?" + "WHERE socialSecurityNumber = ?" );

			selectEmployeeBySS = connection.prepareStatement( "SELECT * FROM employees WHERE socialSecurityNumber = ? " );

			selectEmployeeByType = connection.prepareStatement( "SELECT * FROM employees WHERE employeeType = ? " );

      		} // end try
      		catch ( SQLException sqlException )
      		{
         		sqlException.printStackTrace();
         		System.exit( 1 );
      		} // end catch

   	} // end PersonQueries constructor
   


   	// select all of the addresses in the database
   	public List< Employee > getAllEmployees()
   	{
      		List< Employee > results = null;
      		ResultSet resultSet = null;
      
      		try 
      		{
         		// executeQuery returns ResultSet containing matching entries
         		resultSet = selectAllEmployees.executeQuery(); 
         		results = new ArrayList< Employee >();
         
         		while ( resultSet.next() )
         		{
            			results.add( new Employee(
               			resultSet.getString( "socialSecurityNumber" ),
               			resultSet.getString( "firstName" ),
               			resultSet.getString( "lastName" ),
               			resultSet.getDate( "birthday" ),
               			resultSet.getString( "employeeType" ),
               			resultSet.getString( "departmentName" ) ) );
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

   
   	public List< Employee > getEmployeeByLastName( String name )
   	{

      		List< Employee > results = null;
      		ResultSet resultSet = null;

      		try 
      		{
         		selectEmployeeByLastName.setString( 1, name ); // specify last name
         		resultSet = selectEmployeeByLastName.executeQuery(); 
         		results = new ArrayList< Employee >();

         		while ( resultSet.next() )
         		{
            			results.add( new Employee(
               			resultSet.getString( "socialSecurityNumber" ),
               			resultSet.getString( "firstName" ),
               			resultSet.getString( "lastName" ),
               			resultSet.getDate( "birthday" ),
               			resultSet.getString( "employeeType" ),
               			resultSet.getString( "departmentName" ) ) );
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


   // select person by last name
   
   public List< Employee > getEmployeeBySocialSecurity( String socialSecurity )
   {

      	List< Employee > results = null;
      	ResultSet resultSet = null;

      	try 
      	{
         	selectEmployeeBySS.setString( 1, socialSecurity ); // specify last name

         	resultSet = selectEmployeeBySS.executeQuery(); 

         	results = new ArrayList< Employee >();

         	while ( resultSet.next() )
         	{
            		results.add( new Employee(
               		resultSet.getString( "socialSecurityNumber" ),
               		resultSet.getString( "firstName" ),
               		resultSet.getString( "lastName" ),
               		resultSet.getDate( "birthday" ),
               		resultSet.getString( "employeeType" ),
               		resultSet.getString( "departmentName" ) ) );
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
   } // end method getPeopleByName


   
   public List< Employee > getEmployeeByType( String type )
   {

      List< Employee > results = null;
      ResultSet resultSet = null;

      try 
      {
         selectEmployeeByType.setString( 1, type ); // specify last name
         // executeQuery returns ResultSet containing matching entries
         resultSet = selectEmployeeByType.executeQuery(); 

         results = new ArrayList< Employee >();

         while ( resultSet.next() )
         {
            results.add( new Employee(
               resultSet.getString( "socialSecurityNumber" ),
               resultSet.getString( "firstName" ),
               resultSet.getString( "lastName" ),
               resultSet.getDate( "birthday" ),
               resultSet.getString( "employeeType" ),
               resultSet.getString( "departmentName" ) ) );
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
   } // end method getPeopleByName


   

   // add an entry
   public int addEmployee( 
      String ssN, String fname, String lname, java.sql.Date birthday, String employeeT, String departmentT )
   {

      int result = 0;
	
      // set parameters, then execute insertNewPerson
      try 
      {
         insertNewEmployee.setString( 1, ssN );
         insertNewEmployee.setString( 2, fname );
         insertNewEmployee.setString( 3, lname );
         insertNewEmployee.setDate( 4, birthday );
         insertNewEmployee.setString( 5, employeeT );
         insertNewEmployee.setString( 6, departmentT );

         // insert the new entry; returns # of rows updated
         result = insertNewEmployee.executeUpdate(); 
      } // end try
      catch ( SQLException sqlException )
      {
         sqlException.printStackTrace();
         close();
      } // end catch
      
      return result;
   } // end method addPerson


   // add an entry
   public int updateEmployee( 
      String ssN, String fname, String lname, java.sql.Date birthday, String employeeT, String departmentT )
   {

      int result = 0;
      // set parameters, then execute updateEmployee
      try 
      {

         	updateEmployee.setString( 1, fname );
         	updateEmployee.setString( 2, lname );
         	updateEmployee.setDate( 3, birthday );
         	updateEmployee.setString( 4, employeeT );
         	updateEmployee.setString( 5, departmentT );
         	updateEmployee.setString( 6, ssN );

         	// insert the new entry; returns # of rows updated
         	result = updateEmployee.executeUpdate(); 
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


} // end


