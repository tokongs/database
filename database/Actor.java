package moviedatabase;

/**
 *
 * @author sveinbra
 */

import java.sql.*;
import java.util.*;
import java.util.Date;

public class Actor extends Employee {

  public Actor(int employeeId) {
    super(employeeId);
  }

  public void initialize(Connection connection) {
    try {
      Statement stmt = connection.createStatement();
      ResultSet rs =
          stmt.executeQuery("SELECT name, birthYear, originCountry FROM Employee WHERE EmployeeId="
              + employeeId + "INNER JOIN EmployeeActs USING(EmployeeId)");
      while (rs.next()) {
        name = rs.getString("name");
        birthYear = rs.getString("birthYear");
        originCountry = rs.getString("originCountry");
      }

    } catch (Exception e) {
      System.out.println("db error during select of Actor with id: " + employeeId + e);
      return;
    }

  }

}
