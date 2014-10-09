package database_driven_payroll_application;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;
import java.awt.*;
import javax.swing.*;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class PayrollApplication extends JFrame {


   	private Connection connection = null; // manages connection
   
   	// default query retrieves all data from authors table
   	static final String DEFAULT_QUERY = "SELECT * FROM employees";

   	private ResultSetTableModel tableModel;
   	private JTextArea queryArea;
	private JButton addSalariedEmployeeButton = new JButton( "Add Salaried Employee" );
	private JButton addHourlyEmployeeButton = new JButton( "Add Hourly Employee" );
	private JButton addCommissionEmployeeButton = new JButton( "Add Commission Employee" );
	private JButton addBasePlusCommissionEmployeeButton = new JButton( "Add Base Plus Commission Employee" );
	private JButton addGenericEmployeeButton = new JButton( "Generic Employee" );
	private JComboBox<String> queryCombo;
	private String queryLabel[] = {"Run your query","Select all employees working in Department SALES", "Select hourly employees working over 30 hours", "Select all commission employees in descending order of the commission rate", "Increase base salary by 10% for all base-plus-commission employees", "For all commission employees with gross sales over $10,000, add a $100 bonus",  "If the employee’s birthday is in the current month, add a $100 bonus"};

	private String querySql[] = {"Select * from employees","select * from employees where departmentName='SALES'", "select * from employees where socialSecurityNumber in (select socialSecurityNumber from hourlyEmployees where hourlyEmployees.hours > 30)",  "select employees.*  from employees, commissionEmployees where employees.socialSecurityNumber = commissionEmployees.socialSecurityNumber order by commissionEmployees.commissionRate desc", "update basePlusCommissionEmployees set baseSalary = baseSalary * 1.10", "update commissionEmployees set bonus = bonus+100 where grossSales > 10000", " update salariedEmployees, employees set salariedEmployees.bonus = salariedEmployees.bonus +100  where salariedEmployees.socialSecurityNumber = employees.socialSecurityNumber and (month(employees.birthday)) = month(now()) "+" update commissionEmployees, employees set commissionEmployees.bonus = commissionEmployees.bonus +100  where commissionEmployees.socialSecurityNumber = employees.socialSecurityNumber and (month(employees.birthday)) = month(now())"+" update hourlyEmployees, employees set hourlyEmployees.bonus = hourlyEmployees.bonus +100  where hourlyEmployees.socialSecurityNumber = employees.socialSecurityNumber and (month(employees.birthday)) = month(now())"+" update basePlusCommissionEmployees, employees set basePlusCommissionEmployees.bonus = basePlusCommissionEmployees.bonus +100  where basePlusCommissionEmployees.socialSecurityNumber = employees.socialSecurityNumber and (month(employees.birthday)) = month(now())"};

   	private PreparedStatement increaseBaseSalary10 = null; 
   	private PreparedStatement addBonusCommissionEmployee = null; 
   	private PreparedStatement birthdayBonusCommissionEmployee = null; 
   	private PreparedStatement birthdayBonusSalariedEmployee = null; 
   	private PreparedStatement birthdayBonusHourlyEmployee = null; 
   	private PreparedStatement birthdayBonusBasePlusCommissionEmployee = null; 
	private String selectedQuery;
	private int indexQuery = 0;
	private final ConnectDB connectDB;
   
   // create ResultSetTableModel and GUI
   public PayrollApplication(ConnectDB connect) 
   {   
      super( "Displaying Generic Employees" );
	connectDB = connect;
      // create ResultSetTableModel and display database table
      try 
      {
	
		connection = connectDB.getConnection();
         	// create TableModel for results of query SELECT * FROM authors
         	tableModel = new ResultSetTableModel( connectDB.getDriver(), connectDB.getDatabaseDriver(), connectDB.getUserName(), connectDB.getPassword(), DEFAULT_QUERY );

		increaseBaseSalary10 = connection.prepareStatement( querySql[4] );

		addBonusCommissionEmployee =  connection.prepareStatement( querySql[5] );

		birthdayBonusSalariedEmployee = connection.prepareStatement( " update salariedEmployees, employees set salariedEmployees.bonus = salariedEmployees.bonus +100  where salariedEmployees.socialSecurityNumber = employees.socialSecurityNumber and (month(employees.birthday)) = month(now())" );

		birthdayBonusCommissionEmployee = connection.prepareStatement( " update commissionEmployees, employees set commissionEmployees.bonus = commissionEmployees.bonus +100  where commissionEmployees.socialSecurityNumber = employees.socialSecurityNumber and (month(employees.birthday)) = month(now())" );

		birthdayBonusHourlyEmployee = connection.prepareStatement( " update hourlyEmployees, employees set hourlyEmployees.bonus = hourlyEmployees.bonus +100  where hourlyEmployees.socialSecurityNumber = employees.socialSecurityNumber and (month(employees.birthday)) = month(now())" );

		birthdayBonusBasePlusCommissionEmployee = connection.prepareStatement( " update basePlusCommissionEmployees, employees set basePlusCommissionEmployees.bonus = basePlusCommissionEmployees.bonus +100  where basePlusCommissionEmployees.socialSecurityNumber = employees.socialSecurityNumber and (month(employees.birthday)) = month(now())" );

		queryCombo = new JComboBox<String>( queryLabel ); 
		queryCombo.addItemListener(
 			new ItemListener() // anonymous inner class
 			{
 				public void itemStateChanged( ItemEvent event )
				{
					// determine whether check box selected
					if ( event.getStateChange() == ItemEvent.SELECTED )
					{

						if(queryCombo.getSelectedIndex() != 0)
						{
			
							selectedQuery = queryLabel[queryCombo.getSelectedIndex()];		
							indexQuery = queryCombo.getSelectedIndex();
							queryArea.setText(querySql[indexQuery]);
         						queryArea.setEditable( false );
         						queryArea.setBackground( Color.LIGHT_GRAY );
						}
						else 
						{
							selectedQuery = queryLabel[queryCombo.getSelectedIndex()];
							indexQuery = queryCombo.getSelectedIndex();
							queryArea.setText(querySql[indexQuery]);
							queryArea.setEditable( true );
         					queryArea.setBackground( Color.WHITE);
						}
					}
				} // end method itemStateChanged
			} // end anonymous inner class
		); // end call to addItemListener


         JButton submitButton = new JButton( "Submit Query" );
         // set up JTextArea in which user types queries
         queryArea = new JTextArea( DEFAULT_QUERY, 3, 100 );
         queryArea.setWrapStyleWord( true );
         queryArea.setLineWrap( true );
         queryArea.setEditable( true );
	
         JScrollPane scrollPane = new JScrollPane( queryArea,
         ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, 
         ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER );
         // submitButton in GUI
         Box boxNorth = Box.createHorizontalBox();
         boxNorth.add( scrollPane );
         boxNorth.add( queryCombo );
         boxNorth.add( submitButton );


	JPanel panel = new JPanel();
	panel.setLayout( new BorderLayout() );
	Box boxes[] = new Box[ 2 ];
      	boxes[ 0 ] = Box.createHorizontalBox();
	boxes[ 0 ].add( Box.createHorizontalGlue() );
	boxes[ 0 ].add( addGenericEmployeeButton );
	boxes[ 0 ].add( Box.createHorizontalGlue() );

     	boxes[ 1 ] = Box.createHorizontalBox();
	boxes[ 1 ].add( Box.createHorizontalGlue() );
	boxes[ 1 ].add(addSalariedEmployeeButton );
	boxes[ 1 ].add( Box.createHorizontalGlue() );
	boxes[ 1 ].add( addCommissionEmployeeButton );
	boxes[ 1 ].add( Box.createHorizontalGlue() );
	boxes[ 1 ].add( addBasePlusCommissionEmployeeButton );
	boxes[ 1 ].add( Box.createHorizontalGlue() );
	boxes[ 1 ].add( addHourlyEmployeeButton );
	boxes[ 1 ].add( Box.createHorizontalGlue() );

      addGenericEmployeeButton.addActionListener(
         new ActionListener()
         {
            public void actionPerformed( ActionEvent evt )
            {
		new EmployeeDisplay(connectDB);
            } // end method actionPerformed
         } // end anonymous inner class
      ); // end call to addActionListener

      addSalariedEmployeeButton.addActionListener(
         new ActionListener()
         {
            public void actionPerformed( ActionEvent evt )
            {
		new SalariedEmployeeDisplay(connectDB);
            } // end method actionPerformed
         } // end anonymous inner class
      ); // end call to addActionListener


      addCommissionEmployeeButton.addActionListener(
         new ActionListener()
         {
            public void actionPerformed( ActionEvent evt )
            {
		new CommissionEmployeeDisplay(connectDB);
            } // end method actionPerformed
         } // end anonymous inner class
      ); // end call to addActionListener

	

      addHourlyEmployeeButton.addActionListener(
         new ActionListener()
         {
            public void actionPerformed( ActionEvent evt )
            {
		new HourlyEmployeeDisplay(connectDB);
            } // end method actionPerformed
         } // end anonymous inner class
      ); // end call to addActionListener

	

      addBasePlusCommissionEmployeeButton.addActionListener(
         new ActionListener()
         {
            public void actionPerformed( ActionEvent evt )
            {
		new BasePlusCommissionEmployeeDisplay(connectDB);
            } // end method actionPerformed
         } // end anonymous inner class
      ); // end call to addActionListener

	

	panel.add( boxes[ 0 ], BorderLayout.NORTH );
	panel.add( boxes[ 1 ], BorderLayout.SOUTH );

	
         // create JTable delegate for tableModel 
         JTable resultTable = new JTable( tableModel );
         
         // place GUI components on content pane
         add( panel, BorderLayout.NORTH );
	//add( queryCombo, BorderLayout.SOUTH );
	add(boxNorth, BorderLayout.SOUTH );

         add( new JScrollPane( resultTable ), BorderLayout.CENTER );
         //add( boxSouth, BorderLayout.SOUTH );
         
         final TableRowSorter< TableModel > sorter = 
            new TableRowSorter< TableModel >( tableModel );
         resultTable.setRowSorter( sorter );
         setSize( 1000, 500 ); // set window size
         setVisible( true ); // display window  

         // create event listener for submitButton
         submitButton.addActionListener( 
         
            new ActionListener() 
            {
               // pass query to table model
               public void actionPerformed( ActionEvent event )
               {
                  // perform a new query
                  try 
                  {
			if(indexQuery < 4)
			{
                   		tableModel.setQuery( queryArea.getText() );
			}
			else if(indexQuery == 4)
			{

				int result = 0;
		      		try 
      				{
        		 		result = increaseBaseSalary10.executeUpdate();
					if ( result >= 1 )
         					JOptionPane.showMessageDialog( null, "Base salary increased!", "Employees updated", JOptionPane.PLAIN_MESSAGE );
      					else
         					JOptionPane.showMessageDialog( null, "Employees not updated!", "No items found", JOptionPane.PLAIN_MESSAGE ); 
      				} // end try
      				catch ( SQLException sqlException )
      				{
        		 		sqlException.printStackTrace();
                     			JOptionPane.showMessageDialog( null, 
                        			sqlException.getMessage(), "Database error", 
                        			JOptionPane.ERROR_MESSAGE );
      				} // end catch

			}
			else if(indexQuery == 5)
			{
				int result = 0;
		      		try 
      				{
        		 		result = addBonusCommissionEmployee.executeUpdate(); 
        		 		if ( result >= 1 )
         					JOptionPane.showMessageDialog( null, "Bonus added!", "Employees updated", JOptionPane.PLAIN_MESSAGE );
      					else
         					JOptionPane.showMessageDialog( null, "Employees not updated!", "No items founds", JOptionPane.PLAIN_MESSAGE ); 
      				} // end try
      				catch ( SQLException sqlException )
      				{
        		 		sqlException.printStackTrace();
                     	JOptionPane.showMessageDialog( null, 
                        			sqlException.getMessage(), "Database error", 
                        			JOptionPane.ERROR_MESSAGE );
      				} // end catch
			}

			else if(indexQuery == 6)
			{

				int result = birthdayBonusSalariedEmployee.executeUpdate();
				if ( result >= 1 )
         				JOptionPane.showMessageDialog( null, "Updated bonus for salariedEmployee!", "Employees updated", JOptionPane.PLAIN_MESSAGE );

				result = birthdayBonusCommissionEmployee.executeUpdate();
				if ( result >= 1 )
         				JOptionPane.showMessageDialog( null, "Updateb bonus for commissionEmployee!", "Employees updated", JOptionPane.PLAIN_MESSAGE );

				result = birthdayBonusHourlyEmployee.executeUpdate();
				if ( result >= 1 )
         				JOptionPane.showMessageDialog( null, "Updated bonus for hourlyEmployee!", "Employees updated", JOptionPane.PLAIN_MESSAGE );

				result = birthdayBonusBasePlusCommissionEmployee.executeUpdate();
				if ( result >= 1 )
         				JOptionPane.showMessageDialog( null, "Updated bonus for basePlusCommissionEmployee!", "Employees updated", JOptionPane.PLAIN_MESSAGE );

			}

                  } // end try
                  catch ( SQLException sqlException ) 
                  {
                     JOptionPane.showMessageDialog( null, 
                        sqlException.getMessage(), "Database error", 
                        JOptionPane.ERROR_MESSAGE );
                     try 
                     {
                        tableModel.setQuery( DEFAULT_QUERY );
                        queryArea.setText( DEFAULT_QUERY );
                     } // end try
                     catch ( SQLException sqlException2 ) 
                     {
                        JOptionPane.showMessageDialog( null, 
                           sqlException2.getMessage(), "Database error", 
                           JOptionPane.ERROR_MESSAGE );
         
                        // ensure database connection is closed
                        tableModel.disconnectFromDatabase();
                        System.exit( 1 ); // terminate application
                     } // end inner catch                   
                  } // end outer catch
               } // end actionPerformed
            }  // end ActionListener inner class          
         ); // end call to addActionListener


      } // end try
      catch ( ClassNotFoundException classNotFound ) 
      {
         JOptionPane.showMessageDialog( null, 
            "Database Driver not found", "Driver not found",
            JOptionPane.ERROR_MESSAGE );
         
         System.exit( 1 ); // terminate application
      } // end catch
      catch ( SQLException sqlException ) 
      {
         JOptionPane.showMessageDialog( null, sqlException.getMessage(), 
            "Database error", JOptionPane.ERROR_MESSAGE );
               
         // ensure database connection is closed
         tableModel.disconnectFromDatabase();
         System.exit( 1 ); // terminate application
      } // end catch
      
      // dispose of window when user quits application (this overrides
      // the default of HIDE_ON_CLOSE)
      setDefaultCloseOperation( DISPOSE_ON_CLOSE );
      
      // ensure database connection is closed when user quits application
      addWindowListener(
      
         new WindowAdapter() 
         {
            // disconnect from database and exit when window has closed
            public void windowClosed( WindowEvent event )
            {
               tableModel.disconnectFromDatabase();
               System.exit( 0 );
            } // end method windowClosed
         } // end WindowAdapter inner class
      ); // end call to addWindowListener
   } // end DisplayQueryResults constructor
   


  
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



   // execute application
   public static void main( String args[] ) 
   {
      ConnectDB db = new ConnectDB();
      new PayrollApplication(db);     
   } // end main
}
