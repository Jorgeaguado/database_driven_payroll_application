package database_driven_payroll_application;

public class HourlyEmployee
{
	private String socialSecurityNumber;
	private int  hours;
	private double wage;
	private double bonus;

   	// constructor
   	public HourlyEmployee( String socialSecurityN, int h, double w, double b )
   	{

	    setSocialSecurityNumber( socialSecurityN );
		setHours(h);
		setWage(w);
		setBonus(b);
	}  


	public void setSocialSecurityNumber( String socialSecurityN )
   	{
      		socialSecurityNumber = socialSecurityN;
   	} 


	public void setHours( int h )
   	{
      		hours = h;
   	} 


	public void setBonus( double b )
   	{
      		bonus = b;
   	} 

	public void setWage( double w )
	{
      		wage = w;
   	} 


	public String getSocialSecurityNumber( )
   	{
      		return socialSecurityNumber;
   	} 


	public int getHours( )
   	{
      		return hours;
   	} 


	public double getBonus( )
   	{
      		return bonus;
   	} 

   	public double getWage( )
   	{
      		return wage;
   	} 


} // end class HourlyEmployee

