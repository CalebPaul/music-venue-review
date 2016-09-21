import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class VenueTest {
  private Venue firstVenue;
  private Venue secondVenue;


  @Before
  public void initialize() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/venue_test", null, null);
    firstVenue = new Venue("Roseland", "Place 1", true, 1500, true, 1);
    secondVenue = new Venue("Schnitz", "Place 2", false, 3000, true, 1);
  }

  @Test
  public void venue_instantiatesCorrectly_true() {
    assertEquals(true, firstVenue instanceof Venue);
  }

  @Test
  public void getName_instantiatesCorrectlyWithName_String() {
    assertEquals("Roseland", firstVenue.getName());
  }

  @Test
  public void getDescription_instantiatesCorrectlyWithDescription_String() {
    assertEquals("Place 1", firstVenue.getDescription());
  }

  @Test
  public void getAges_instantiatesCorrectlyWithAges_Boolean() {
    assertTrue(firstVenue.getAges());
  }

  @Test
  public void getCapacity_instantiatesCorrectlyWithCapacity_int() {
    assertEquals(1500, firstVenue.getCapacity());
  }

  @Test
  public void getSeating_instantiatesCorrectlyWithSeating_boolean() {
    assertTrue(firstVenue.getSeating());
  }

  @Test
  public void getCityId_instantiatesCorrectlyWithCidyId_int() {
    assertEquals(1, firstVenue.getCityId());
  }

  @Test
  public void save_returnsTrueIfDescriptionsAreTheSame() {
    Venue newVenue = new Venue("Roseland", "Place 1", true, 1500, true, 1);
    newVenue.save();
    Venue savedVenue = Venue.all().get(0);
    assertTrue(Venue.all().get(0).equals(newVenue));
    assertEquals(newVenue.getId(), savedVenue.getId());
  }

  @Test
  public void save_saveCityIdIntoDB() {
    City newCity = new City("Portland");
    myCity.save();
    Venue newVenue = new Venue("Schnitz", "Place 2", false, 3000, true, newCity.getId());
    newVenue.save();
    Venue savedVenue = Venue.find(newVenue.getId());
    assertEquals(savedVenue.getCityId(), newCity.getId());
  }


}
