package moviedatabase;

/**
 *
 * @author sveinbra
 */

import java.sql.*;

public class Director extends Employee {


  public Director(final int employeeId) {
    super(employeeId);
  }

  public void initialize(final Connection connection) {
    try {
      final Statement stmt = connection.createStatement();
      final ResultSet rs = stmt.executeQuery(
          "SELECT * FROM (Employee INNER JOIN EmployeeDirected ON Employee.EmployeeID=EmployeeDirected.EmployeeID) WHERE Employee.EmployeeID="
              + employeeId);
      while (rs.next()) {
        name = rs.getString("Name");
        birthYear = rs.getString("BirthYear");
        originCountry = rs.getString("OriginCountry");
      }

    } catch (final Exception e) {
      System.out.println("db error during select of Director with id: " + employeeId + e);
      return;
    }
  }



}
