package database_driven_payroll_application;

import java.util.*;
public class Employee
{
   private String socialSecurityNumber;
   private String firstName;
   private String lastName;
   private Date birthday;
   private String employeeType;
   private String departmentName;

   // no-argument constructor
   public Employee()
   {
   } // end no-argument Person constructor

   // constructor
   public Employee( String socialSecurityN, String firstN, String lastN, 
      Date birth, String type, String departmentN )
   {
      setSocialSecurityNumber( socialSecurityN );
      setFirstName( firstN );
      setLastName( lastN );
      setBirthday( birth );
      setEmployeeType( type );
      setDepartmentName( departmentN );

   } // end five-argument Person constructor 


   // sets the addressID
   public void setSocialSecurityNumber( String socialSecurityN )
   {
      socialSecurityNumber = socialSecurityN;
   } // end method setAddressID

   // returns the addressID 
   public String getSocialSecurityNumber()
   {
      return socialSecurityNumber;
   } // end method getAddressID
   
   // sets the firstName
   public void setFirstName( String firstN )
   {
      firstName = firstN;
   } // end method setFirstName

   // returns the first name 
   public String getFirstName()
   {
      return firstName;
   } // end method getFirstName
   
   // sets the lastName
   public void setLastName( String lastN )
   {
      lastName = lastN;
   } // end method setLastName

   // returns the first name 
   public String getLastName()
   {
      return lastName;
   } // end method getLastName
   
   // sets the email address
   public void setBirthday( Date birth )
   {
      birthday = birth;
   } // end method setEmail

   // returns the email address
   public Date getBirthday()
   {
      return birthday;
   } // end method getEmail
   
   // sets the phone number
   public void setEmployeeType( String type )
   {
      employeeType = type;
   } // end method setPhoneNumber

   // returns the email address
   public String getEmployeeType()
   {
      return employeeType;
   } // end method getPhoneNumber

   public void setDepartmentName( String departmentN )

   {
      departmentName = departmentN;
   } // end method setPhoneNumber


   // returns the email address
   public String getDepartmentName()
   {

      return departmentName;
   } // end method getPhoneNumber

} // end class Person

