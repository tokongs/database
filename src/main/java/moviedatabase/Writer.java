package moviedatabase;

import java.sql.*;

public class Writer extends Employee {


  public Writer(final int employeeId) {
    super(employeeId);
  }

  public void initialize(final Connection connection) {
    try {
      final Statement stmt = connection.createStatement();
      final ResultSet rs = stmt.executeQuery(
          "SELECT * FROM (Employee INNER JOIN EmployeeWrote ON Employee.EmployeeID=EmployeeWrote.EmployeeID) WHERE Employee.EmployeeID="
              + employeeId);
      while (rs.next()) {
        name = rs.getString("Name");
        birthYear = rs.getString("BirthYear");
        originCountry = rs.getString("OriginCountry");
      }

    } catch (final Exception e) {
      System.out.println("db error during select of Writer with id: " + employeeId + e);
      return;
    }
  }



}
