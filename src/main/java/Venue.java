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

  public Venue(String name, String description, int capacity, int city_id) {
    this.name = name;
    this.description = description;
    ages = false;
    this.capacity = capacity;
    seating = false;
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

  public void setAges(boolean ages, int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE venues SET ages = :ages WHERE id = :id";
      con.createQuery(sql)
         .addParameter("ages", ages)
         .addParameter("id", id)
         .executeUpdate();
    }
    this.ages = ages;
  }

  public void setSeating(boolean seating, int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "UPDATE venues SET seating = :seating WHERE id = :id";
      con.createQuery(sql)
         .addParameter("seating", seating)
         .addParameter("id", id)
         .executeUpdate();
    }
    this.seating = seating;
  }

  public int getCapacity() {
    return capacity;
  }

  public boolean getSeating() {
    return seating;
  }

  public int getId() {
    return id;
  }

  public int getcity_id() {
    return city_id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO venues (name, description, ages, capacity, seating, city_id) VALUES (:name, :description, :ages, :capacity, :seating, :city_id)";
      this.id = (int) con.createQuery(sql, true)
                         .addParameter("name", this.name)
                         .addParameter("description", this.description)
                         .addParameter("ages", this.ages)
                         .addParameter("capacity", this.capacity)
                         .addParameter("seating", this.seating)
                         .addParameter("city_id", this.city_id)
                         .executeUpdate()
                         .getKey();
    }
  }

  public static List<Venue> all() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT id, name, description, ages, capacity, seating, city_id FROM venues";
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
      this.getcity_id() == newVenue.getcity_id();
    }
  }

}
