
package tudresden.ocl.test.royloy;

import java.util.*;

public class Company extends RLObject
{
  
  public String description;

  /**
     @invariant numberOfEmployees: self.numberOfEmployees=employees->size
  */
  public int numberOfEmployees=0;

  // Associations

  public Person manager;

  /**
     @element-type Person
  */
  public HashSet employees=new HashSet();
  
  /**
     @element-type Person
  */
  public List topTenEmployees=new ArrayList();
  
  public Person[] topTwentyEmployees=new Person[20];
  
  public Company(String description, Person manager)
  {
    this.description=description;
    this.manager=manager;
    employees.add(manager);
    manager.employers.add(this);
    numberOfEmployees=employees.size();
    topTenEmployees.add(manager);
    topTwentyEmployees[0]=manager;
  }

  public Person getOldestEmployee() {
  return null;
  }

  public int getOldestEmployeeAge() {
  return 0;
  }
  
  public void employ(Person p)
  {
    employees.add(p);
    p.employers.add(this);
    numberOfEmployees=employees.size();
  }
  
  public String toString()
  {
    return super.toString()+'['+description+']';
  }
  
  public boolean assert()
  {
    return true;
  }
  
} /* end class Company */

