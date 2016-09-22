import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

  @Override
  protected void before() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/venue_review_test", null, null);
  }

  @Override
  protected void after() {
    try(Connection con = DB.sql2o.open()) {
      String deleteVenuesQuery = "DELETE FROM venues *;";
      String deleteCitiesQuery = "DELETE FROM cities *;";
      con.createQuery(deleteVenuesQuery).executeUpdate();
      con.createQuery(deleteCitiesQuery).executeUpdate();
    }
  }

}
