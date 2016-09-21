import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import org.sql2o.*;

public class Venue {
  private String name;
  private String description;
  private boolean ages;
  private int capacity;
  private boolean seating;
  private int city_id;
  private int id;

  public Venue(String name, String description, boolean ages, int capacity, boolean seating, int city_id) {
    this.name = name;
    this.description = description;
    this.ages = ages;
    this.capacity = capacity;
    this.seating = seating;
    this.city_id = city_id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public boolean getAges() {
    return ages;
  }

  public int getCapacity() {
    return capacity;
  }

  public boolean getSeating() {
    return seating;
  }

  public int getCityId() {
    return city_id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO venues (name, description, ages, capacity, seating, city_id)";
      this.id = (int) con.createQuery(sql, true)
                         .addParameter("name", this.name)
                         .addParameter("description", this.description)
                         .addParameter("ages", this.ages)
                         .addParameter("capacity", this.capacity)
                         .addParameter("seating", this.seating)
                         .executeUpdate()
                         .getKey();
    }
  }

  public static List<Venue> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id, name, description, ages, capacity, seating, city_id";
        return con.createQuery(sql)
                  .executeAndFetch(Venue.class);
    }
  }

  public static Venue find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "Select * FROM venues WHERE id = :id";
      Venue venue = con.createQuery(sql)
                       .addParameter("id", id)
                       .executeAndFetchFirst(Venue.class);
      return venue;
    }
  }

  @Override
  public boolean equals(Object otherVenue) {
    if(!(otherVenue instanceof Venue)) {
      return false;
    } else {
      Venue newVenue = (Venue) otherVenue;
      return this.getName().equals(newVenue.getName()) &&
      this.getDescription().equals(newVenue.getDescription()) &&
      this.getAges() == newVenue.getAges() &&
      this.getCapacity() == newVenue.getCapacity() &&
      this.getSeating() == newVenue.getSeating() &&
      this.getCityId() == newVenue.getCityId();
    }
  }

}
