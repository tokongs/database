package moviedatabase;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class Company extends ActiveDomainObject {
    public int companyId;
    public String name;
    public String URL;
    public String country;
    public String address;

    public Company(int companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void initialize(Connection connection) {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Company WHERE CompanyID=" + companyId);
            while (rs.next()) {
                name = rs.getString("Name");
                URL = rs.getString("URL");
                country = rs.getString("Country");
                address = rs.getString("Address");
            }

        } catch (Exception e) {
            System.out.println("db error during select of company with id: " + companyId + e);
            return;
        }
    }

    public void refresh(Connection conn) {
        initialize(conn);
    }

  public void insert(Connection conn) {
    try {
      String sql = "INSERT INTO Company(Name, URL, Country, Address) " + "VALUES(\"" + name + "\", \""
          + URL + "\", \"" + country + "\", \"" + address +"\")";
      PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      stmt.executeUpdate();
      ResultSet rs = stmt.getGeneratedKeys();
      rs.next();
        companyId = rs.getInt(1);

    } catch (Exception e) {
      System.out.println("db error during insert of company" + e);
      return;
    }
  }

    public void save(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeQuery("UPDATE Company set Name=" + name + " URL=" + URL + " Country=" + country + " Address=" + address + " where CompanyId=" + companyId);
        } catch (Exception e) {
            System.out.println("db error during update of company with id: " + companyId + e);
            return;
        }
    }

}
