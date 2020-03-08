package moviedatabase;

import java.sql.*;

public abstract class ActiveDomainObject {
    public abstract void initialize (Connection connection);
    public abstract void refresh (Connection connection);
    public abstract void save (Connection connection);
}