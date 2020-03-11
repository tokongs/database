package moviedatabase;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AddMediaCtrl extends DBConnection {

    private Media media;

    public AddMediaCtrl () {
        connect();
        // La laging av avtale vÃ¦re en transaksjon
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("db error during setAutoCommit of AddMediaCtrl="+e);
            return;
        }
    }

    public void add (String title, int length){
        media = new Media(title, length);
    }

    /*
    public void getPerson(int id ){
        Person p = new Person(id);
        p.initialize(conn);
    }

    public void getAll() {
        Videomedia.getAll(conn);
    }
  public void hentVideomedia (String tittel) {
    film.getInfo(tittel, conn);
  }
    */

    public void finish () {
        media.save(connection);
        try {
            connection.commit();
        } catch (SQLException e) {
            System.out.println("db error during commit of AddMediaCtrl="+e);
            return;
        }
    }
}