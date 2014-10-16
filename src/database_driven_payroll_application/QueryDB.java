package database_driven_payroll_application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class QueryDB {
	private String querySql[] = {"Select * from employees","select * from employees where departmentName='SALES'", "select * from employees where socialSecurityNumber in (select socialSecurityNumber from hourlyEmployees where hourlyEmployees.hours > 30)",  "select employees.*  from employees, commissionEmployees where employees.socialSecurityNumber = commissionEmployees.socialSecurityNumber order by commissionEmployees.commissionRate desc", "update basePlusCommissionEmployees set baseSalary = baseSalary * 1.10", "update commissionEmployees set bonus = bonus+100 where grossSales > 10000", " update salariedEmployees, employees set salariedEmployees.bonus = salariedEmployees.bonus +100  where salariedEmployees.socialSecurityNumber = employees.socialSecurityNumber and (month(employees.birthday)) = month(now()) "+" update commissionEmployees, employees set commissionEmployees.bonus = commissionEmployees.bonus +100  where commissionEmployees.socialSecurityNumber = employees.socialSecurityNumber and (month(employees.birthday)) = month(now())"+" update hourlyEmployees, employees set hourlyEmployees.bonus = hourlyEmployees.bonus +100  where hourlyEmployees.socialSecurityNumber = employees.socialSecurityNumber and (month(employees.birthday)) = month(now())"+" update basePlusCommissionEmployees, employees set basePlusCommissionEmployees.bonus = basePlusCommissionEmployees.bonus +100  where basePlusCommissionEmployees.socialSecurityNumber = employees.socialSecurityNumber and (month(employees.birthday)) = month(now())"};

   	private PreparedStatement increaseBaseSalary10 = null; 
   	private PreparedStatement addBonusCommissionEmployee = null; 
   	private PreparedStatement birthdayBonusCommissionEmployee = null; 
   	private PreparedStatement birthdayBonusSalariedEmployee = null; 
   	private PreparedStatement birthdayBonusHourlyEmployee = null; 
   	private PreparedStatement birthdayBonusBasePlusCommissionEmployee = null; 
   	private ResultSetTableModel tableModel;
   	private static final String DEFAULT_QUERY = "SELECT * FROM employees";

   	
   	public QueryDB(ConnectDB connectDB)
   	{
   		if(connectDB != null)
   		{
   			
         		try
         		{
         	   		Connection connection  = connectDB.getConnection();
        		
         			tableModel = new ResultSetTableModel( connectDB.getDriver(), connectDB.getDatabaseDriver(), connectDB.getUserName(), connectDB.getPassword(), DEFAULT_QUERY );
         		
        			increaseBaseSalary10 = connection.prepareStatement( querySql[4] );

        			addBonusCommissionEmployee =  connection.prepareStatement( querySql[5] );

        			birthdayBonusSalariedEmployee = connection.prepareStatement( " update salariedEmployees, employees set salariedEmployees.bonus = salariedEmployees.bonus +100  where salariedEmployees.socialSecurityNumber = employees.socialSecurityNumber and (month(employees.birthday)) = month(now())" );

        			birthdayBonusCommissionEmployee = connection.prepareStatement( " update commissionEmployees, employees set commissionEmployees.bonus = commissionEmployees.bonus +100  where commissionEmployees.socialSecurityNumber = employees.socialSecurityNumber and (month(employees.birthday)) = month(now())" );

        			birthdayBonusHourlyEmployee = connection.prepareStatement( " update hourlyEmployees, employees set hourlyEmployees.bonus = hourlyEmployees.bonus +100  where hourlyEmployees.socialSecurityNumber = employees.socialSecurityNumber and (month(employees.birthday)) = month(now())" );

        			birthdayBonusBasePlusCommissionEmployee = connection.prepareStatement( " update basePlusCommissionEmployees, employees set basePlusCommissionEmployees.bonus = basePlusCommissionEmployees.bonus +100  where basePlusCommissionEmployees.socialSecurityNumber = employees.socialSecurityNumber and (month(employees.birthday)) = month(now())" );

         		}
            		catch ( ClassNotFoundException classNotFound ) 
            		{
               			JOptionPane.showMessageDialog( null, "Database Driver not found", "Driver not found", JOptionPane.ERROR_MESSAGE );       
               			System.exit( 1 ); // terminate application
            		} // end catch
            		catch ( SQLException sqlException ) 
            		{
               			JOptionPane.showMessageDialog( null, sqlException.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE );
               			// ensure database connection is closed
               			tableModel.disconnectFromDatabase();
               			System.exit( 1 ); // terminate application
            		} // end catch

   		}
   			
   	}
}
