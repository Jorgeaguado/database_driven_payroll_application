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

   } // end five-argument Employee 


   // sets the setSocialSecurityNumber
   public void setSocialSecurityNumber( String socialSecurityN )
   {
      socialSecurityNumber = socialSecurityN;
   } // end method setSocialSecurityNumber

   // returns the getSocialSecurityNumber 
   public String getSocialSecurityNumber()
   {
      return socialSecurityNumber;
   } // end method getSocialSecurityNumber
   
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

   // returns the last name 
   public String getLastName()
   {
      return lastName;
   } // end method getLastName
   
   // sets the Birthday
   public void setBirthday( Date birth )
   {
      birthday = birth;
   } // end method setEmail

   // returns the email address
   public Date getBirthday()
   {
      return birthday;
   } // end method getEmail
   
   // sets the Employee type
   public void setEmployeeType( String type )
   {
      employeeType = type;
   } // end method setEmployeeType

   // returns the email type
   public String getEmployeeType()
   {
      return employeeType;
   } // end method getEmployeeType

   public void setDepartmentName( String departmentN )

   {
      departmentName = departmentN;
   } // end method setDepartmentName


   // returns the department
   public String getDepartmentName()
   {

      return departmentName;
   } // end method getDepartmentName

} // end class Employee

