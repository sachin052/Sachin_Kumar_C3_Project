import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    RestaurantService service;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void init() {
        restaurant=null;
        service = new RestaurantService();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    Restaurant restaurant;

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("23:30:00");
        service.getRestaurants().clear();
        service.addRestaurant("Chefs made", "Noida", openingTime, closingTime);
        restaurant = service.getRestaurants().get(0);
        assertTrue(restaurant.isRestaurantOpen(LocalTime.now()), "");
    }
    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
        //WRITE UNIT TEST CASE HERE
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        service.addRestaurant("Chefs made", "Noida", openingTime, closingTime);
        LocalTime localTime2 = LocalTime.of(23, 0);
        restaurant = service.getRestaurants().get(0);
        assertFalse(restaurant.isRestaurantOpen(localTime2), "");

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1() throws noMenuFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException, noMenuFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(itemNotFoundException.class, ()->restaurant.removeFromMenu("French fries"));
    }

    @Test
    public void check_order_value_must_be_zero_if_no_item_is_ordered() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

        assertThrows(itemNotFoundException.class, ()->restaurant.removeFromMenu("French fries"));
    }

    @Test
    public void throw_exception_if_there_is_no_menu_items() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        ArrayList<String> items=new ArrayList<>();
        items.add("Sweet corn soup");
        assertThrows(noMenuFoundException.class,()->restaurant.getItemsByName(items));
    }

    @Test
    public void throw_exception_if_there_is_no_item_selected_from_menu() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        ArrayList<String> items=new ArrayList<>();
        assertThrows(noMenuItemSelectedException.class,()->restaurant.getItemsByName(items));
    }


    @Test
    public void get_total_order_items_if_all_chosen_items_are_valid() throws noMenuFoundException, noMenuItemSelectedException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Veg Burger", 100);
        restaurant.addToMenu("Noodles", 200);
        restaurant.addToMenu("Pasta", 300);
        List<String> selectedItem = new ArrayList<>();
        selectedItem.add("Veg Burger");
        selectedItem.add("Noodles");
        int expected = 300;
        int actual = restaurant.getOrderTotal(selectedItem);
        assertEquals(expected, actual);
    }


    @Test
    public void is_method_giving_us_false_current_time() {
        DateTimeFormatter formatDateTime = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        assertNotEquals(restaurant.getCurrentTime().plusHours(1).format(formatDateTime), LocalTime.now().format(formatDateTime));
    }

    @Test
    public void is_displaying_in_correct_format() throws noMenuFoundException {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        service.addRestaurant("Chefs made", "Noida", openingTime, closingTime);
        restaurant = service.getRestaurants().get(0);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        String printStatement = "Restaurant:" + restaurant.getName() + "\n"
                + "Location:" + restaurant.getLocation() + "\n"
                + "Opening time:" + restaurant.openingTime + "\n"
                + "Closing time:" + restaurant.closingTime + "\n"
                + "Menu:" + "\n" + restaurant.getMenu();
        assertEquals(restaurant.displayDetails(),printStatement);
    }

    @Test
    public void should_thrown_exception_if_menu_items_are_not_present_while_displaying_details() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        service.getRestaurants().clear();
        service.addRestaurant("Chefs made", "Noida", openingTime, closingTime);
        restaurant = service.getRestaurants().get(0);
        assertThrows(noMenuFoundException.class,()->restaurant.displayDetails());
    }


    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}