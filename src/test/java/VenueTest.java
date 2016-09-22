import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class VenueTest {
  private Venue firstVenue;
  private Venue secondVenue;

  @Rule
  public DatabaseRule database = new DatabaseRule();

  @Before
  public void initialize() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/venue_review_test", null, null);
    firstVenue = new Venue("Roseland", "Place 1", 1500, 1);
    secondVenue = new Venue("Schnitz", "Place 2", 3000, 1);
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
    assertFalse(firstVenue.getAges());
  }

  @Test
  public void setAges_testForCorrectlySettingAgesToTrue_true() {
    firstVenue.setAges(true, 1);
    assertTrue(firstVenue.getAges());
  }

  @Test
  public void setSeating_testForCorrectlySettingSeatingToTrue_true() {
    firstVenue.setSeating(true, 1);
    assertTrue(firstVenue.getSeating());
  }

  @Test
  public void getCapacity_instantiatesCorrectlyWithCapacity_int() {
    assertEquals(1500, firstVenue.getCapacity());
  }

  @Test
  public void getSeating_instantiatesCorrectlyWithSeating_boolean() {
    assertFalse(firstVenue.getSeating());
  }

  @Test
  public void getId_instantiatesCorrectlyWithId_int() {
    firstVenue.save();
    assertTrue(firstVenue.getId() > 0);
  }

  @Test
  public void getcity_id_instantiatesCorrectlyWithcity_id_int() {
    assertEquals(1, firstVenue.getcity_id());
  }

  @Test
  public void save_returnsTrueIfDescriptionsAreTheSame() {
    Venue newVenue = new Venue("Roseland", "Place 1", 1500, 1);
    newVenue.save();
    Venue savedVenue = Venue.all().get(0);
    assertTrue(Venue.all().get(0).equals(newVenue));
    assertEquals(newVenue.getId(), savedVenue.getId());
  }

  @Test
  public void save_savecity_idIntoDB() {
    City newCity = new City("Portland");
    newCity.save();
    Venue newVenue = new Venue("Schnitz", "Place 2", 3000, newCity.getId());
    newVenue.save();
    Venue savedVenue = Venue.find(newVenue.getId());
    assertEquals(savedVenue.getcity_id(), newCity.getId());
  }

  @Test
  public void all_returnsAllInstancesOfVenue_true() {
    firstVenue.save();
    secondVenue.save();
    assertTrue(Venue.all().get(0).equals(firstVenue));
    assertTrue(Venue.all().get(1).equals(secondVenue));
  }

  @Test
  public void find_returnVenueWithTheSameId_secondVenue() {
    firstVenue.save();
    secondVenue.save();
    assertEquals(Venue.find(secondVenue.getId()), secondVenue);
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame_true() {
    Venue testVenue = new Venue("Roseland", "Place 1", 1500, 1);
    assertTrue(firstVenue.equals(testVenue));
  }

}
