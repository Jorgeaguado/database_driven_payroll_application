package database_driven_payroll_application;

public class CommissionEmployee
{
   private String socialSecurityNumber;
   private double grossSales;
   private int commissionRate;
   private double bonus;



   // constructor
   public CommissionEmployee( String socialSecurityN, double grossS, int commission, double bon )
   {

      setSocialSecurityNumber( socialSecurityN );
      setGrossSales(grossS);
      setCommission(commission);
      setBonus(bon);

   } // end five-argument Person constructor 


   // sets the addressID
   public void setSocialSecurityNumber( String socialSecurityN )
   {
      socialSecurityNumber = socialSecurityN;
   } // end method setAddressID

   // sets the addressID
   public void setGrossSales( double grossS )
   {
      grossSales = grossS;
   } // end method setAddressID

   // sets the addressID
   public void setCommission( int commission )
   {
      commissionRate = commission;
   } // end method setAddressID

   // sets the addressID
   public void setBonus( double bon )
   {
      bonus = bon;
   } // end method setAddressID

   // sets the addressID
   public String getSocialSecurityNumber(  )
   {
      return socialSecurityNumber;
   } // end method setAddressID

   // sets the addressID
   public double getGrossSales( )
   {
      return grossSales;
   } // end method setAddressID

   // sets the addressID
   public int getCommission(  )
   {
      return commissionRate ;
   } // end method setAddressID

   // sets the addressID
   public double getBonus(  )
   {
      return bonus ;
   } // end method setAddressID


} // end class Person

