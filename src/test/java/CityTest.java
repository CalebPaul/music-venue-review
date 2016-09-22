import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class CityTest {
  private City firstCity;
  private City secondCity;
  private Venue newVenue;

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Before
  public void initialize() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/venue_review_test", null, null);
    firstCity = new City("Portland");
    secondCity = new City("Seattle");
    newVenue = new Venue("Crystal", "Place 3", 900, 1);
  }

  @Test
  public void City_instantiatesCorrectly_true() {
    assertTrue(firstCity instanceof City);
  }

  @Test
  public void getName_instantiatesCorrectlyWithName_String() {
    assertEquals("Portland", firstCity.getName());
  }

  @Test
  public void getId_instantiatesCorrectlyWithId_int() {
    firstCity.save();
    assertTrue(firstCity.getId() > 0);
  }

  @Test
  public void all_checksIfCityListContainsInstance_true() {
    firstCity.save();
    secondCity.save();
    assertTrue(City.all().get(0).equals(firstCity));
    assertTrue(City.all().get(1).equals(secondCity));
  }

  @Test
  public void getVenues_retrievesAllVenuesFromDB_venuesList() {
    firstCity.save();
    Venue firstVenue = new Venue("Roseland", "Place 1", 1500, firstCity.getId());
    firstVenue.save();
    Venue secondVenue = new Venue("Schnitz", "Place 1", 1100, firstCity.getId());
    secondVenue.save();
    Venue[] venues = new Venue[] { firstVenue, secondVenue };
    assertTrue(firstCity.getVenues().containsAll(Arrays.asList(venues)));
  }

  @Test
  public void find_returnsCityWithSameId_secondCity() {
    firstCity.save();
    secondCity.save();
    assertEquals(City.find(secondCity.getId()), secondCity);
  }

  @Test
  public void save_assignsIdToObject() {
    firstCity.save();
    City savedCity = City.all().get(0);
    assertEquals(firstCity.getId(), savedCity.getId());
  }

  @Test
  public void save_savesIntoDatabase_true() {
    firstCity.save();
    assertTrue(City.all().get(0).equals(firstCity));
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame_true() {
    City testCity = new City("Portland");
    assertTrue(firstCity.equals(testCity));
  }

}
