package database_driven_payroll_application;

public class BasePlusCommissionEmployee
{
   private String socialSecurityNumber;
   private double  grossSale;
   private int commissionRate;
   private double baseSalary;
   private double bonus;

   // constructor
   public BasePlusCommissionEmployee( String socialSecurityN, double grossS, int commission, double baseS, double bon )
   {
      	setSocialSecurityNumber( socialSecurityN );
      	setGrossSale(grossS);
      	setCommissionRate(commission);
      	setBaseSalary(baseS);
      	setBonus(bon);

   } // end five-argument BasePlusCommissionEmployee

   public void setSocialSecurityNumber( String socialSecurityN )
   {
	   socialSecurityNumber = socialSecurityN;
   } // end method setSocialSecurityNumber


   public void setGrossSale( double grossS )
   {
	   grossSale = grossS;
   } // end method setGrossSale

   public void setCommissionRate( int commission )
   {
	   commissionRate = commission;
   } // end method setAddressID

   public void setBaseSalary( double salary )
   {
	   baseSalary = salary;
   } // end method setCommissionRate

  public void setBonus( double bon )
   {
      bonus = bon;
   } // end method setBonus



   public String getSocialSecurityNumber( )
   {
	   return socialSecurityNumber;
   } // end method getSocialSecurityNumber


   public double getBaseSalary( )
   {
	   return baseSalary;
   } // end method getBaseSalary



   public int getCommissionRate( )
   {
	   return commissionRate;
   } // end method getCommissionRate


   public double getGrossSales( )
   {
	   return grossSale;
   } // end method getGrossSales



   public double getBonus( )
   {
	   return bonus;
   } // end method getBonus


} // end class 

