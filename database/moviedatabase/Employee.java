package moviedatabase;

/**
 *
 * @author sveinbra
 */

import java.sql.*;
import java.util.*;
import java.util.Date;

public abstract class Employee extends ActiveDomainObject {
  protected int employeeId;
  protected String name;
  protected String birthYear;
  protected String originCountry;


  public Employee(int employeeId) {
    this.employeeId = employeeId;
  }

  public int getEmployeeId() {
    return employeeId;
  }

  public abstract void initialize(Connection connection);

  public void refresh(Connection conn) {
    initialize(conn);
  }

  public void save(Connection conn) {
    try {
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("update Employee set name=" + name + ", birthYear=" + birthYear
          + ", originCountry=" + originCountry + " where employeeId=" + employeeId);
    } catch (Exception e) {
      System.out.println("db error during update of employee with id: " + employeeId + e);
      return;
    }
  }

}
