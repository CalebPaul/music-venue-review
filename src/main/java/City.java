import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import org.sql2o.*;

public class City {
  private String name;
  private int id;

  public City(String name) {
    this.name = name;
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public int getId() {
    return id;
  }

  public static List<City> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id, name FROM cities";
      return con.createQuery(sql)
                .executeAndFetch(City.class);
    }
  }

  public List<Venue> getVenues() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM venues where city_id=:id";
      return con.createQuery(sql)
                .addParameter("id", this.id)
                .executeAndFetch(Venue.class);
    }
  }

  public static City find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM cities where id = :id";
      City city = con.createQuery(sql)
                     .addParameter("id", id)
                     .executeAndFetchFirst(City.class);
      return city;
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO cities (name) VALUES (:name)";
      this.id = (int) con.createQuery(sql, true)
                         .addParameter("name", this.name)
                         .executeUpdate()
                         .getKey();
    }
  }

  @Override
  public boolean equals(Object otherCity) {
    if(!(otherCity instanceof City)) {
      return false;
    } else {
      City newCity = (City) otherCity;
      return this.getName().equals(newCity.getName()) &&
             this.getId() == newCity.getId();

    }
  }
}
