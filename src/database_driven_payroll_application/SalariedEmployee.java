package database_driven_payroll_application;

public class SalariedEmployee
{
   private String socialSecurityNumber;
   private double weeklySalary;
   private double bonus;

   // no-argument constructor
   public SalariedEmployee()
   {
   } // end no-argument Person constructor

   // constructor
   public SalariedEmployee( String socialSecurityN, double weeklyS, double bon )
   {

      	setSocialSecurityNumber( socialSecurityN );
      	setWeeklySalary(weeklyS);
      	setBonus(bon);

   } 

   // sets the SocialSecurityNumber
   public void setSocialSecurityNumber( String socialSecurityN )
   {
	   	socialSecurityNumber = socialSecurityN;
   } // end method 

   // sets the WeeklySalary
   public void setWeeklySalary( double weeklyS )
   {
	   	weeklySalary = weeklyS;
   } // end method 

   // sets the Bonus
   public void setBonus( double bon )
   {
	   	bonus = bon;
   } // end method 


   // returns the SocialSecurityNumber 
   public String getSocialSecurityNumber()
   {
	   	return socialSecurityNumber;
   } // end method 

   // returns the WeeklySalary 
   public double getWeeklySalary()
   {
      return weeklySalary;
   } // end method 

   // returns the Bonus 
   public double getBonus()
   {
      return bonus;
   } // end method 

} // end class 

