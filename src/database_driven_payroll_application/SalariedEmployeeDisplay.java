package database_driven_payroll_application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.FlowLayout;
import java.util.List; 
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import java.awt.*;
import javax.swing.*;
import java.text.*;


public class SalariedEmployeeDisplay extends JFrame 
{

   	private SalariedEmployee currentEntry;
   	private SalariedEmployeeQueries salariedEmployeeQueries;
   	private List< SalariedEmployee > salariedResults;   
   	private int numberOfEntries = 0;
   	private int currentEntryIndex;


   	private JButton browseButton = new JButton();
   	private JLabel socialSecurityNumberLabel = new JLabel();
   	private JTextField socialSecurityNumberTextField = new JTextField( 10 );

   	private JTextField indexTextField = new JTextField( 2 );
   	private JTextField maxTextField = new JTextField( 2 );
   	private JButton nextButton = new JButton();
   	private JLabel ofLabel = new JLabel();

   	private JButton previousButton = new JButton();
   	private JButton queryButton = new JButton();
   	private JLabel queryLabel = new JLabel();
   	private JPanel queryPanel = new JPanel();
   	private JPanel navigatePanel = new JPanel();
   	private JPanel displayPanel = new JPanel();
   	private JTextField queryTextField = new JTextField( 10 );
   	private JButton updateButton = new JButton();


   	private JLabel weeklySalaryLabel = new JLabel();
   	private JLabel bonusLabel = new JLabel();

	private double amount = 0;
	private JFormattedTextField salaryTextField;
	private NumberFormat salaryFormat;

	private JFormattedTextField bonusTextField;
	private NumberFormat bonusFormat;


   // no-argument constructor
   	public SalariedEmployeeDisplay(ConnectDB connect)
   	{
      		super( "Salaried Employee" ); 
      		salariedEmployeeQueries = new SalariedEmployeeQueries(connect);

			initTextFields();

      		setLayout( new FlowLayout( FlowLayout.CENTER, 10, 10 ) );
      		setSize( 500, 600 );
      		setResizable( false );
      		
      		addNavegationToPanel();
      		addFieldsToPanel();
      	   	addFindToPanel();
      	   	addBrowsOptionToPanel();
      	   	addUpdateOptionToPanel();

      	   	setVisible( true );
      	   	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      	   	browseButtonActionPerformed();

   } // end
   	
   	private void  initTextFields(){

		salaryTextField = new JFormattedTextField(salaryFormat);
		salaryTextField.setValue(new Double(amount));
        salaryTextField.setColumns(10);

		bonusTextField = new JFormattedTextField(bonusFormat);
		bonusTextField.setValue(new Double(amount));
        bonusTextField.setColumns(10);
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
   	
   	private void addBrowsOptionToPanel(){

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

  		queryPanel.setLayout( new BoxLayout( queryPanel, BoxLayout.X_AXIS) );

  		queryPanel.setBorder( BorderFactory.createTitledBorder("Find an entry by Social Security Number" ) );
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

		displayPanel.setLayout( new GridLayout( 4, 2, 4, 4 ) );

		socialSecurityNumberLabel.setText( "Social Security Number:" );
		displayPanel.add( socialSecurityNumberLabel );

		socialSecurityNumberTextField.setEditable( false );
		displayPanel.add( socialSecurityNumberTextField );

		weeklySalaryLabel.setText( "Salary:" );
		displayPanel.add( weeklySalaryLabel );
		displayPanel.add( salaryTextField );

		bonusLabel.setText( "Bonus:" );
		displayPanel.add( bonusLabel );
		displayPanel.add( bonusTextField );

		add( displayPanel );
}

private void addNavegationToPanel(){

		navigatePanel.setLayout(
 		new BoxLayout( navigatePanel, BoxLayout.X_AXIS ) );

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

		indexTextField.setHorizontalAlignment(JTextField.CENTER );

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

		maxTextField.setHorizontalAlignment(JTextField.CENTER );
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
		int result = salariedEmployeeQueries.updateSalariedEmployee( socialSecurityNumberTextField.getText(),  Double.parseDouble(salaryTextField.getText()), Double.parseDouble(bonusTextField.getText()) );
//bonusTextField.getValue()

      	if ( result == 1 )
       			JOptionPane.showMessageDialog( this, "SalariedEmployee updated!", "Employee updated", JOptionPane.PLAIN_MESSAGE );
		else
         		JOptionPane.showMessageDialog( this, "SalariedEmployee not updated!", "Error", JOptionPane.PLAIN_MESSAGE );

      	browseButtonActionPerformed( evt );

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



   // handles call when queryButton is clicked
   private void queryButtonActionPerformed( ActionEvent evt )
   {


	   boolean found = false;
       int auxEntryIndex = 0;
       numberOfEntries = salariedResults.size();

      for(auxEntryIndex = 0; auxEntryIndex < numberOfEntries; auxEntryIndex++)
      {
		currentEntry = salariedResults.get( auxEntryIndex );
		if((currentEntry.getSocialSecurityNumber()).equals(queryTextField.getText()))
		{

			currentEntryIndex = auxEntryIndex;
			found = false;
         	socialSecurityNumberTextField.setText("" + currentEntry.getSocialSecurityNumber() );
			salaryTextField.setValue(currentEntry.getWeeklySalary());
			bonusTextField.setValue(currentEntry.getBonus());
			
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

    	  //Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         currentEntry = salariedResults.get( currentEntryIndex );

         socialSecurityNumberTextField.setText("" + currentEntry.getSocialSecurityNumber() );
         salaryTextField.setValue(currentEntry.getWeeklySalary());
         bonusTextField.setValue(currentEntry.getBonus());

         maxTextField.setText( "" + numberOfEntries );
         indexTextField.setText( "" + ( currentEntryIndex + 1 ) );

      } // end if

    } // end method indexTextFieldActionPerformed




   	private void browseButtonActionPerformed(  )
	{
      	try
      	{
      		modView();
			salariedResults = salariedEmployeeQueries.getAllSalariedEmployees();
         	numberOfEntries = salariedResults.size();

         	if ( numberOfEntries != 0 )
         	{
           		currentEntry = salariedResults.get( currentEntryIndex );

         		socialSecurityNumberTextField.setText("" + currentEntry.getSocialSecurityNumber() );
				salaryTextField.setValue(currentEntry.getWeeklySalary());
				bonusTextField.setValue(currentEntry.getBonus());
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
			salariedResults = salariedEmployeeQueries.getAllSalariedEmployees();
         		numberOfEntries = salariedResults.size();
  
         		if ( numberOfEntries != 0 )
         		{
            			currentEntry = salariedResults.get( currentEntryIndex );
            			socialSecurityNumberTextField.setText("" + currentEntry.getSocialSecurityNumber() );
            			salaryTextField.setValue(currentEntry.getWeeklySalary());
            			bonusTextField.setValue(currentEntry.getBonus());
         		} // end if

      		} // end try
      		catch ( Exception e )
      		{
         		e.printStackTrace();
      		} // end catch
   	} // end method browseButtonActionPerformed

	private void modInsertion()
	{
		socialSecurityNumberTextField.setText(""  );
		indexTextField.setText("");
        nextButton.setEnabled( false );
        previousButton.setEnabled( false );
		updateButton.setEnabled(false);
		queryButton.setEnabled(false);
      	socialSecurityNumberTextField.setEditable( true );
	}


	private void modView()
	{
        nextButton.setEnabled( true );
        previousButton.setEnabled( true );
        updateButton.setEnabled(true);
        queryButton.setEnabled(true);
      	socialSecurityNumberTextField.setEditable( false );
	}

} // end class 
