package database_driven_payroll_application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List; 
import javax.swing.JButton;
import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import java.awt.*;
import javax.swing.*;
import java.text.*;


public class CommissionEmployeeDisplay extends JFrame 
{

   	private CommissionEmployee currentEntry;
   	private CommissionEmployeeQueries commissionEmployeeQueries;
   	private List< CommissionEmployee > commissionResults;   
   	private int numberOfEntries = 0;
   	private int currentEntryIndex;

   	private JButton browseButton = new JButton();
   	private JLabel socialSecurityNumberLabel = new JLabel();
   	private JTextField socialSecurityNumberTextField = new JTextField( 10 );

   	private JTextField  indexTextField = new JTextField( 2 );
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

   	private JLabel grossSalesLabel = new JLabel();
   	private JLabel bonusLabel = new JLabel();
   	private JLabel commissionRateLabel = new JLabel();

	private double amount = 0;
	private int amountInt = 0;
	private JFormattedTextField grossSalesTextField;
	private NumberFormat grossSalesFormat;

	private JFormattedTextField bonusTextField;
	private NumberFormat bonusFormat;
	private JFormattedTextField commissionRateTextField;
	private NumberFormat commissionRateFormat;

   	// no-argument constructor
   	public CommissionEmployeeDisplay(ConnectDB connect)
   	{
      		super( "Commission Employee" ); 
		commissionEmployeeQueries = new CommissionEmployeeQueries(connect);
		
		initTextFields();

     		setLayout( new FlowLayout( FlowLayout.CENTER, 10, 10 ) );
      		setSize( 500, 600 );
      		setResizable( false );
      		
      		addNavegationToPanel();
      		addFieldsToPanel();
      		addFindOptionToPanel();
      		addBrowsOptionToPanel();
      		addUpdateOptionToPanel();
      		
      		setVisible( true );
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		browseButtonActionPerformed(  );

   	} // end no-argument constructor
   	
   	private void initTextFields()
   	{

		grossSalesTextField = new JFormattedTextField(grossSalesFormat);
		grossSalesTextField.setValue(new Double(amount));
        	grossSalesTextField.setColumns(10);

		bonusTextField = new JFormattedTextField(bonusFormat);
		bonusTextField.setValue(new Double(amount));
        	bonusTextField.setColumns(10);

		commissionRateTextField = new JFormattedTextField(commissionRateFormat);
		commissionRateTextField.setValue(new Integer(amountInt));
        	commissionRateTextField.setColumns(10);
   	}
   	
   	private void addUpdateOptionToPanel()
   	{

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
   	
    	private void addBrowsOptionToPanel()
    	{

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
   	
    private void addFindOptionToPanel()
    {

  		queryPanel.setLayout( new BoxLayout( queryPanel, BoxLayout.X_AXIS) );

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
   	
    private void addFieldsToPanel()
    {

  		displayPanel.setLayout( new GridLayout( 4, 2, 4, 4 ) );

  		socialSecurityNumberLabel.setText( "Social Security Number:" );
  		displayPanel.add( socialSecurityNumberLabel );

  		socialSecurityNumberTextField.setEditable( false );
  		displayPanel.add( socialSecurityNumberTextField );

  		grossSalesLabel.setText( "Gross Sales:" );
  		displayPanel.add( grossSalesLabel );
  		displayPanel.add( grossSalesTextField );

  		commissionRateLabel.setText( "Commission Rate:" );
  		displayPanel.add( commissionRateLabel );
  		displayPanel.add( commissionRateTextField );

  		bonusLabel.setText( "Bonus:" );
  		displayPanel.add( bonusLabel );
  		displayPanel.add( bonusTextField );

  		add( displayPanel );
    }

   private void addNavegationToPanel()
   {

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
		int result = commissionEmployeeQueries.updateCommissionEmployee( socialSecurityNumberTextField.getText(),  Double.parseDouble(grossSalesTextField.getText()), Integer.parseInt(commissionRateTextField.getText()),  Double.parseDouble(bonusTextField.getText()) );

	      		if ( result == 1 )
      			JOptionPane.showMessageDialog( this, "CommissionEmployee updated!", "Employee updated", JOptionPane.PLAIN_MESSAGE );
      		else
      			JOptionPane.showMessageDialog( this, "CommissionEmployee not updated!", "Error", JOptionPane.PLAIN_MESSAGE );

      		browseButtonActionPerformed( evt );

   	} // end method updateButtonActionPerformed


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
   		numberOfEntries = commissionResults.size();

		for(auxEntryIndex = 0; auxEntryIndex < numberOfEntries; auxEntryIndex++)
		{
			currentEntry = commissionResults.get( auxEntryIndex );
			if((currentEntry.getSocialSecurityNumber()).equals(queryTextField.getText()))
			{
				currentEntryIndex = auxEntryIndex;
				found = false;
        		 	socialSecurityNumberTextField.setText("" + currentEntry.getSocialSecurityNumber() );
				grossSalesTextField.setValue(currentEntry.getGrossSales());
				commissionRateTextField.setValue(currentEntry.getCommission());
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
      		currentEntryIndex =  ( Integer.parseInt( indexTextField.getText() ) - 1 );
      
      		if ( numberOfEntries != 0 && currentEntryIndex < numberOfEntries )
      		{
        		currentEntry = commissionResults.get( currentEntryIndex );
         		socialSecurityNumberTextField.setText("" + currentEntry.getSocialSecurityNumber() );
         		grossSalesTextField.setValue(currentEntry.getGrossSales());
         		commissionRateTextField.setValue(currentEntry.getCommission());
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

      			commissionResults = commissionEmployeeQueries.getAllCommissionEmployees();
         		numberOfEntries = commissionResults.size();
  
         		if ( numberOfEntries != 0 )
         		{
            			currentEntry = commissionResults.get( currentEntryIndex );
         			socialSecurityNumberTextField.setText("" + currentEntry.getSocialSecurityNumber() );
         			grossSalesTextField.setValue(currentEntry.getGrossSales());
         			commissionRateTextField.setValue(currentEntry.getCommission());
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

      			commissionResults = commissionEmployeeQueries.getAllCommissionEmployees();
         		numberOfEntries = commissionResults.size();

         		if ( numberOfEntries != 0 )
         		{
            			currentEntry = commissionResults.get( currentEntryIndex );

            			socialSecurityNumberTextField.setText("" + currentEntry.getSocialSecurityNumber() );
            			grossSalesTextField.setValue(currentEntry.getGrossSales());
            			commissionRateTextField.setValue(currentEntry.getCommission());
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

