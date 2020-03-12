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
  public String name;
  public String birthYear;
  public String originCountry;


  public Employee(int employeeId) {
    this.employeeId = employeeId;
  }

  public String getName() {
    return name;
  }

  public int getEmployeeId() {
    return employeeId;
  }

  public abstract void initialize(Connection connection);

  public void refresh(Connection conn) {
    initialize(conn);
  }

  public void insert(Connection conn) {
    try {
      String sql = "INSERT INTO Employee(Name, BirthYear, OriginCountry) " + "VALUES(" + name + ", "
          + birthYear + ", " + originCountry + ")";
      PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      stmt.executeUpdate();
      ResultSet rs = stmt.getGeneratedKeys();
      rs.next();
      employeeId = rs.getInt(1);

    } catch (Exception e) {
      System.out.println("db error during insert of employee with id:" + e);
      return;
    }
  }

  }

  public void save(Connection conn) {
    try {
      Statement stmt = conn.createStatement();
      stmt.executeQuery("UPDATE Employee set Name=" + name + ", BirthYear=" + birthYear
          + ", OriginCountry=" + originCountry + " where EmployeeId=" + employeeId);
    } catch (Exception e) {
      System.out.println("db error during update of employee with id: " + employeeId + e);
      return;
    }
  }

}
