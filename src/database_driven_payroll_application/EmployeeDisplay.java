package database_driven_payroll_application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List; 
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import java.text.*;
import javax.swing.JComboBox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.sql.Date;
import java.util.regex.Pattern;

public class EmployeeDisplay extends JFrame
{

   	private Employee currentEntry;
   	private GenericEmployeeQueries employeeQueries;
   	private List< Employee > results;   
   	private int numberOfEntries = 0;
   	private int currentEntryIndex;
	private SalariedEmployeeQueries salariedEmployee;
	private CommissionEmployeeQueries commissionEmployee;
	private HourlyEmployeeQueries hourlyEmployee;
	private BasePlusCommissionEmployeeQueries basePlusCommissionEmployee;


   	private JButton browseButton = new JButton();
   	private JLabel socialSecurityNumberLabel = new JLabel();
   	private JTextField socialSecurityNumberTextField = new JTextField( 10 );
   	private JLabel firstNameLabel  = new JLabel();
   	private JTextField firstNameTextField = new JTextField( 10 );
   	private JTextField indexTextField = new JTextField( 2 );
   	private JLabel lastNameLabel = new JLabel();
   	private JTextField lastNameTextField = new JTextField( 10 );
   	private JTextField maxTextField = new JTextField( 2 );
   	private JButton nextButton = new JButton();
   	private JLabel ofLabel = new JLabel();
   	private JLabel birthdayLabel  = new JLabel();
   	private JTextField birthdayTextField = new JTextField( 10 );
   	private JLabel employeeTypeLabel = new JLabel();
   	private JLabel departmentNameLabel = new JLabel();
   	private JTextField departmentNameTextField =  new JTextField( 10 );

   	private JButton previousButton = new JButton();
   	private JButton queryButton = new JButton();
   	private JLabel queryLabel = new JLabel();
   	private JPanel queryPanel = new JPanel();
   	private JPanel navigatePanel = new JPanel();
   	private JPanel displayPanel = new JPanel();
   	private JTextField queryTextField = new JTextField( 10 );
   	private JButton insertButton = new JButton();
   	private JButton updateButton = new JButton();
   	private JPanel mmddyyyyPanel = new JPanel();
	private JTextField yyyyTextField = new JTextField( 10 );


	private String employeeTypes[] = { "salariedEmployee", "commissionEmployee", "basePlusCommissionEmployee", "hourlyEmployee"};
   	private JComboBox<String> comboBoxEmployeeType;
	private String employeeSelected;
	private String currentEmployee;

   // no-argument constructor
   public EmployeeDisplay(ConnectDB connect)
   {
      	super( "Generic Employee" ); 
      	setDBConnections(connect);

      	yyyyTextField.setColumns(10);
		addNavegationToPanel();
      	setLayout( new FlowLayout( FlowLayout.CENTER, 10, 10 ) );
      	setSize( 500, 600 );
      	setResizable( false );

      	addFieldsToPanel();
      	addFindToPanel();
      	addBrowsAllEntriesToPanel();
      	addInsertOptionToPanel();
      	addUpdateOptionToPanel();

      	setVisible( true );
      	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      	browseButtonActionPerformed( );

   } // end no-argument constructor
   
   private void setDBConnections(ConnectDB connect){
    	
	employeeQueries = new  GenericEmployeeQueries(connect);
	salariedEmployee = new SalariedEmployeeQueries(connect);
	commissionEmployee = new CommissionEmployeeQueries(connect);    
	hourlyEmployee = new HourlyEmployeeQueries(connect); 
	basePlusCommissionEmployee = new BasePlusCommissionEmployeeQueries(connect);

   }
   private void addUpdateOptionToPanel(){
 	updateButton.setText( "Update Entry" );
 	updateButton.addActionListener(
    	new ActionListener()
    	{
       		public void actionPerformed( ActionEvent evt )
       		{
          			updateButtonActionPerformed( evt );
       		} // end method actionPerformed
    	} // end anonymous inner class
 	 ); // end call to addActionListener

 	 add( updateButton );
   }
   
   private void addInsertOptionToPanel(){
	   
     	insertButton.setText( "Insert New Entry" );
     	insertButton.addActionListener(
        	new ActionListener()
        	{
           		public void actionPerformed( ActionEvent evt )
           		{
              			insertButtonActionPerformed( evt );
           		} // end method actionPerformed
        	} // end anonymous inner class
     	); // end call to addActionListener

     	add( insertButton );
   }
   
   private void addBrowsAllEntriesToPanel(){
     	
     browseButton.setText( "Browse All Entries" );
     browseButton.addActionListener(
        new ActionListener()
        {
           public void actionPerformed( ActionEvent evt )
           {
              browseButtonActionPerformed( evt );
           } // end method actionPerformed
        } // end anonymous inner class
     ); // end call to addActionListener

	add( browseButton );
   }
   
   private void addFindToPanel(){
	  queryPanel.setLayout( 
	    new BoxLayout( queryPanel, BoxLayout.X_AXIS) );

	   queryPanel.setBorder( BorderFactory.createTitledBorder(
	    	 "Find an entry by Social Security Number" ) );
	   queryLabel.setText( "Social Security Number:" );
	   queryPanel.add( Box.createHorizontalStrut( 5 ) );
	   queryPanel.add( queryLabel );
	   queryPanel.add( Box.createHorizontalStrut( 10 ) );
	   queryPanel.add( queryTextField );
	   queryPanel.add( Box.createHorizontalStrut( 10 ) );

	   queryButton.setText( "Find" );

	   queryButton.addActionListener(
	    	  new ActionListener()
	    	  {
	    	       public void actionPerformed( ActionEvent evt )
	    	       {
	    	    	   queryButtonActionPerformed( evt );
	    	       } // end method actionPerformed
	    	  } // end anonymous inner class
	   ); // end call to addActionListener

	   queryPanel.add( queryButton );
	   queryPanel.add( Box.createHorizontalStrut( 5 ) );
	   add( queryPanel );
   }
   
   private void addFieldsToPanel(){

	   displayPanel.setLayout( new GridLayout( 8, 2, 4, 4 ) );

	   socialSecurityNumberLabel.setText( "Social Security Number:" );
	   displayPanel.add( socialSecurityNumberLabel );

	   socialSecurityNumberTextField.setEditable( false );
	   displayPanel.add( socialSecurityNumberTextField );

	   firstNameLabel.setText( "First Name:" );
	   displayPanel.add( firstNameLabel );
	   displayPanel.add( firstNameTextField );

	   lastNameLabel.setText( "Last Name:" );
	   displayPanel.add( lastNameLabel );
	   displayPanel.add( lastNameTextField );

	   birthdayLabel.setText( "Birthday (yyyy-mm-dd)" );
	   yyyyTextField.setText("2007-11-02");
	   mmddyyyyPanel.add(yyyyTextField);
	   displayPanel.add( birthdayLabel );
	   displayPanel.add( mmddyyyyPanel );
	   employeeTypeLabel.setText( "Employee type:" );
	   displayPanel.add( employeeTypeLabel );

	   employeeSelected = "salariedEmployee";
	   comboBoxEmployeeType = new JComboBox<String>( employeeTypes ); 
	   comboBoxEmployeeType.addItemListener(
	 		new ItemListener() // anonymous inner class
	 		{
	 			public void itemStateChanged( ItemEvent event )
				{
					// determine whether check box selected
					if ( event.getStateChange() == ItemEvent.SELECTED )
					{
						employeeSelected = employeeTypes[comboBoxEmployeeType.getSelectedIndex()];
					}
				} // end method itemStateChanged
			} // end anonymous inner class
		); // end call to addItemListener

	    displayPanel.add( comboBoxEmployeeType );
	    departmentNameLabel.setText( "Department name:" );
	    displayPanel.add( departmentNameLabel );
	    displayPanel.add( departmentNameTextField );

	    add( displayPanel );
   }

   private void addNavegationToPanel(){

     	navigatePanel.setLayout(new BoxLayout( navigatePanel, BoxLayout.X_AXIS ) );

     	previousButton.setText( "Previous" );
     	previousButton.setEnabled( false );
     	previousButton.addActionListener(
        	new ActionListener()
        	{
           		public void actionPerformed( ActionEvent evt )
           		{
              			previousButtonActionPerformed( evt );
           		} // end method actionPerformed
        	} // end anonymous inner class
     ); // end call to addActionListener


     navigatePanel.add( previousButton );
     navigatePanel.add( Box.createHorizontalStrut( 10 ) );

     indexTextField.setHorizontalAlignment(
        JTextField.CENTER );

     indexTextField.addActionListener(
        new ActionListener()
        {
           public void actionPerformed( ActionEvent evt )
           {
              indexTextFieldActionPerformed( evt );
           } // end method actionPerformed
        } // end anonymous inner class
     ); // end call to addActionListener


     navigatePanel.add( indexTextField );
     navigatePanel.add( Box.createHorizontalStrut( 10 ) );

     ofLabel.setText( "of" );
     navigatePanel.add( ofLabel );
     navigatePanel.add( Box.createHorizontalStrut( 10 ) );

     maxTextField.setHorizontalAlignment(
        JTextField.CENTER );
     maxTextField.setEditable( false );
     navigatePanel.add( maxTextField );
     navigatePanel.add( Box.createHorizontalStrut( 10 ) );

     nextButton.setText( "Next" );
     nextButton.setEnabled( false );

     nextButton.addActionListener(
        new ActionListener()
        {
           public void actionPerformed( ActionEvent evt )
           {
              nextButtonActionPerformed( evt );
           } // end method actionPerformed
        } // end anonymous inner class
     ); // end call to addActionListener


     navigatePanel.add( nextButton );
     add( navigatePanel );
   }

   private void updateButtonActionPerformed( ActionEvent evt ) 
   {
	updateEmployeeType(socialSecurityNumberTextField.getText());

	String fechaLeida = yyyyTextField.getText();
	boolean cumplePatron = Pattern.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d", fechaLeida);
	if(!cumplePatron)
	{
		JOptionPane.showMessageDialog( this, "Wrong brirthday!", "Error", JOptionPane.PLAIN_MESSAGE );

	}
	else
	{
		try
		{
    			java.sql.Date sqlDate;
				sqlDate = Date.valueOf(fechaLeida);
      			int result = employeeQueries.updateEmployee( socialSecurityNumberTextField.getText(), firstNameTextField.getText(), lastNameTextField.getText(), sqlDate , employeeSelected, departmentNameTextField.getText()   );
				currentEmployee = employeeSelected;
      
      			if ( result == 1 )
         			JOptionPane.showMessageDialog( this, "Employee updated!", "Employee updated", JOptionPane.PLAIN_MESSAGE );
      			else
         			JOptionPane.showMessageDialog( this, "Employee not updated!", "Error", JOptionPane.PLAIN_MESSAGE );
          
      			browseButtonActionPerformed( evt );
		}catch(IllegalArgumentException ex)
		{
			JOptionPane.showMessageDialog( this, "Wrong birthday!", "Error", JOptionPane.PLAIN_MESSAGE );
		}
		catch (Exception ex) {
         		JOptionPane.showMessageDialog( this, "Employee not updated!", "Error", JOptionPane.PLAIN_MESSAGE );
		}
	}
   } // end method insertButtonActionPerformed


   // handles call when previousButton is clicked
   private void previousButtonActionPerformed( ActionEvent evt )
   {

      currentEntryIndex--;
      
      if ( currentEntryIndex < 0 )
         currentEntryIndex = numberOfEntries - 1;
      
      indexTextField.setText( "" + ( currentEntryIndex + 1 ) );
      indexTextFieldActionPerformed( evt );  
   } // end method previousButtonActionPerformed

   // handles call when nextButton is clicked
   private void nextButtonActionPerformed( ActionEvent evt ) 
   {
      currentEntryIndex++;
      
      if ( currentEntryIndex >= numberOfEntries )
         currentEntryIndex = 0;
      
      indexTextField.setText( "" + ( currentEntryIndex + 1 ) );
      indexTextFieldActionPerformed( evt );

   } // end method nextButtonActionPerformed

	private int getTypeComboBoxIndex(String type )
	{
		int aux = 0;
		if((type).equals("salariedEmployee"))
			aux = 0;
	
		else if((type).equals("commissionEmployee"))
			aux = 1;
	
		else if((type).equals("basePlusCommissionEmployee"))
			aux = 2;
	
		else if((type).equals("hourlyEmployee"))
			aux = 3;

		return aux;
		
	}

	private int updateEmployeeType(String ss)
	{
		int aux = 0;

		if(!currentEmployee.equals(employeeSelected))
		{
			if(currentEmployee.equals("salariedEmployee"))
				salariedEmployee.deleteSalariedEmployee(ss);
			if(currentEmployee.equals("commissionEmployee"))
				commissionEmployee.deleteCommissionEmployee(ss);
			if(currentEmployee.equals("basePlusCommissionEmployee"))
				basePlusCommissionEmployee.deleteBasePlusCommissionEmployee(ss);
			if(currentEmployee.equals("hourlyEmployee"))
				hourlyEmployee.deleteHourlyEmployee(ss);

			if(employeeSelected.equals("salariedEmployee"))
				salariedEmployee.addSalariedEmployee(ss, 0.0, 0.0);	
			if(employeeSelected.equals("commissionEmployee"))
				commissionEmployee.addCommissionEmployee(ss, 0.0, 0, 0.0);
			if(employeeSelected.equals("basePlusCommissionEmployee"))
				basePlusCommissionEmployee.addBasePlusCommissionEmployee(ss, 0.0, 0, 0.0, 0.0);
			if(employeeSelected.equals("hourlyEmployee"))
				hourlyEmployee.addHourlyEmployee(ss, 0, 0.0, 0.0);
		}
		return aux;
	}


   // handles call when queryButton is clicked
   private void queryButtonActionPerformed( ActionEvent evt )
   {

	   boolean found = false;
       int auxEntryIndex = 0;
       currentEntry = results.get( auxEntryIndex );
       numberOfEntries = results.size();

       for(auxEntryIndex = 0; auxEntryIndex < numberOfEntries; auxEntryIndex++)
       {
    	   currentEntry = results.get( auxEntryIndex );
    	   if((currentEntry.getSocialSecurityNumber()).equals(queryTextField.getText()))
    	   {
    		   currentEntryIndex = auxEntryIndex;
    		   found = false;
    		   socialSecurityNumberTextField.setText("" + currentEntry.getSocialSecurityNumber() );
    		   firstNameTextField.setText("" + currentEntry.getFirstName() );
    		   lastNameTextField.setText("" + currentEntry.getLastName() );
    		   SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
    		   yyyyTextField.setText(dateformatyyyyMMdd.format(currentEntry.getBirthday()));

    		   comboBoxEmployeeType.setSelectedIndex(getTypeComboBoxIndex(currentEntry.getEmployeeType()));
    		   currentEmployee = currentEntry.getEmployeeType();
    		   departmentNameTextField.setText("" + currentEntry.getDepartmentName() );
    		   indexTextField.setText( "" + ( currentEntryIndex + 1 ) );
    		   break;
    	   }
	}
	if(found)
		JOptionPane.showMessageDialog( this, "Security Social not found!", "Error", JOptionPane.PLAIN_MESSAGE );

	browseButtonActionPerformed( evt );

   } // end method queryButtonActionPerformed

   // handles call when a new value is entered in indextTextField
   private void indexTextFieldActionPerformed( ActionEvent evt )
   {
      currentEntryIndex = 
         ( Integer.parseInt( indexTextField.getText() ) - 1 );
      
      if ( numberOfEntries != 0 && currentEntryIndex < numberOfEntries )
      {
         currentEntry = results.get( currentEntryIndex );
         socialSecurityNumberTextField.setText("" + currentEntry.getSocialSecurityNumber() );

         firstNameTextField.setText( currentEntry.getFirstName() );
         lastNameTextField.setText( currentEntry.getLastName() );

         SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
         yyyyTextField.setText(dateformatyyyyMMdd.format(currentEntry.getBirthday()));
         comboBoxEmployeeType.setSelectedIndex(getTypeComboBoxIndex(currentEntry.getEmployeeType()));
         currentEmployee = currentEntry.getEmployeeType();
         departmentNameTextField.setText( currentEntry.getDepartmentName() );
         maxTextField.setText( "" + numberOfEntries );
         indexTextField.setText( "" + ( currentEntryIndex + 1 ) );

      } // end if
    } // end method indexTextFieldActionPerformed



   private void browseButtonActionPerformed( )
   {
     try
      {
    	 modView();
         results = employeeQueries.getAllEmployees();
         numberOfEntries = results.size();
      
         if ( numberOfEntries != 0 )
         {
            currentEntryIndex = 0;
            currentEntry = results.get( currentEntryIndex );

            socialSecurityNumberTextField.setText("" + currentEntry.getSocialSecurityNumber() );
            firstNameTextField.setText( currentEntry.getFirstName() );
            lastNameTextField.setText( currentEntry.getLastName() );

            SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
            yyyyTextField.setText(dateformatyyyyMMdd.format(currentEntry.getBirthday()));
            comboBoxEmployeeType.setSelectedIndex(getTypeComboBoxIndex(currentEntry.getEmployeeType()));
            currentEmployee = currentEntry.getEmployeeType();
            departmentNameTextField.setText( currentEntry.getDepartmentName() );

            maxTextField.setText( "" + numberOfEntries );
            indexTextField.setText( "" + ( currentEntryIndex + 1 ) );
            nextButton.setEnabled( true );
            previousButton.setEnabled( true );

         } // end if
      } // end try
      catch ( Exception e )
      {
         e.printStackTrace();
      } // end catch
}



   // handles call when browseButton is clicked
   private void browseButtonActionPerformed( ActionEvent evt )
   {

      try
      {
    	  modView();
    	  results = employeeQueries.getAllEmployees();
    	  numberOfEntries = results.size();
      
    	  if ( numberOfEntries != 0 )
    	  {

            currentEntry = results.get( currentEntryIndex );
            socialSecurityNumberTextField.setText("" + currentEntry.getSocialSecurityNumber() );
            firstNameTextField.setText( currentEntry.getFirstName() );
            lastNameTextField.setText( currentEntry.getLastName() );

            SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("yyyy-MM-dd");
            yyyyTextField.setText(dateformatyyyyMMdd.format(currentEntry.getBirthday()));
            comboBoxEmployeeType.setSelectedIndex(getTypeComboBoxIndex(currentEntry.getEmployeeType()));
            currentEmployee = currentEntry.getEmployeeType();
            departmentNameTextField.setText( currentEntry.getDepartmentName() );
            
            maxTextField.setText( "" + numberOfEntries );
            indexTextField.setText( "" + ( currentEntryIndex + 1 ) );
            nextButton.setEnabled( true );
            previousButton.setEnabled( true );

         } // end if
      } // end try
      catch ( Exception e )
      {
         e.printStackTrace();
      } // end catch
   } // end method browseButtonActionPerformed

   private void modInsertion()
   {
	insertButton.setText("Add new query");
	socialSecurityNumberTextField.setText(""  );
        firstNameTextField.setText("" );
        lastNameTextField.setText(""  );
        birthdayTextField.setText(""  );
	comboBoxEmployeeType.setSelectedIndex(0);
        departmentNameTextField.setText("" );
	indexTextField.setText("");
        nextButton.setEnabled( false );
        previousButton.setEnabled( false );
	updateButton.setEnabled(false);
	queryButton.setEnabled(false);
      	socialSecurityNumberTextField.setEditable( true );
   }

   private void modView()
   {
	insertButton.setText("Insert New Entry");
        nextButton.setEnabled( true );
        previousButton.setEnabled( true );
	updateButton.setEnabled(true);
	queryButton.setEnabled(true);
      	socialSecurityNumberTextField.setEditable( false );
   }



   // handles call when insertButton is clicked
   private void insertButtonActionPerformed( ActionEvent evt ) 
   {

	if((insertButton.getText()).equals("Insert New Entry"))
	   modInsertion();
	else
	{
	   if((socialSecurityNumberTextField.getText()).length() != 0)
	   {

		   results = employeeQueries.getEmployeeBySocialSecurity( socialSecurityNumberTextField.getText() );
		   numberOfEntries = results.size();

		   if(numberOfEntries == 0)
		   {

			   String fechaLeida = yyyyTextField.getText();
			   boolean cumplePatron = Pattern.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d", fechaLeida);
			   if(!cumplePatron)
			   {
				   JOptionPane.showMessageDialog( this, "Wrong brirthday!", "Error", JOptionPane.PLAIN_MESSAGE );
			   }
			   else
			   {
				   try
				   {
					   java.sql.Date sqlDate;
					   sqlDate = Date.valueOf(fechaLeida);

					   int result = employeeQueries.addEmployee( socialSecurityNumberTextField.getText(), firstNameTextField.getText(), lastNameTextField.getText(), sqlDate, employeeSelected, departmentNameTextField.getText()   );

					   currentEmployee = employeeSelected;

					   if ( result == 1 )
					   {
						   JOptionPane.showMessageDialog( this, "Employee added!", "Employee added", JOptionPane.PLAIN_MESSAGE );

						   if((employeeSelected).equals("salariedEmployee"))
							   salariedEmployee.addSalariedEmployee(socialSecurityNumberTextField.getText(), 0.0, 0.0);	
						   else if((employeeSelected).equals("commissionEmployee"))			
							   commissionEmployee.addCommissionEmployee(socialSecurityNumberTextField.getText(), 0.0, 0, 0.0);
						   else if((employeeSelected).equals("hourlyEmployee"))	
							   hourlyEmployee.addHourlyEmployee(socialSecurityNumberTextField.getText(), 0, 0.0, 0.0);
						   else if((employeeSelected).equals("basePlusCommissionEmployee"))	
							   basePlusCommissionEmployee.addBasePlusCommissionEmployee(socialSecurityNumberTextField.getText(), 0.0, 0, 0.0, 0.0);
						
				   	   }
				   	   else
         					JOptionPane.showMessageDialog( this, "Employee not added!", "Error", JOptionPane.PLAIN_MESSAGE );

      					browseButtonActionPerformed( evt );
				   }
				   catch(IllegalArgumentException ex)
				   {
					JOptionPane.showMessageDialog( this, "Wrong birthday!", "Error", JOptionPane.PLAIN_MESSAGE );
				   }
			     }
			}
			else JOptionPane.showMessageDialog( this, "Social Security already exist!", "Error", JOptionPane.PLAIN_MESSAGE );
		}
		else
		{
			 JOptionPane.showMessageDialog( this, "Social Security can not be empty!", "Error", JOptionPane.PLAIN_MESSAGE );
		}
	}

   } // end method insertButtonActionPerformed
 
} // end class 


