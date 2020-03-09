package moviedatabase;

/**
 *
 * @author sveinbra
 */

import java.sql.*;
import java.util.*;
import java.util.Date;

public class Actor extends Employee {

  private ArrayList<String> roles = new ArrayList<String>();

  public Actor(final int employeeId) {
    super(employeeId);
  }

  private ArrayList<String> getRoles(){
    return roles;
  }

  public void initialize(final Connection connection) {
    try {
      final Statement stmt = connection.createStatement();
      final ResultSet rs =
          stmt.executeQuery("SELECT * FROM Employee WHERE EmployeeId="
              + employeeId + "INNER JOIN EmployeeActs USING(EmployeeId)");
      while (rs.next()) {
        name = rs.getString("name");
        birthYear = rs.getString("birthYear");
        originCountry = rs.getString("originCountry");
        roles.add(rs.getString("role"));
      }

    } catch (final Exception e) {
      System.out.println("db error during select of Actor with id: " + employeeId + e);
      return;
    }
  }

  

}
