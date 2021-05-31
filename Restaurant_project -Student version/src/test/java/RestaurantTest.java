import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
	
		Restaurant restaurant ;
		@BeforeEach
		public void setUp() {
			LocalTime openingTime = LocalTime.parse("10:30:00");
	        LocalTime closingTime = LocalTime.parse("22:00:00");
	        restaurant=new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
		}
		//setting up the menu for testing
		public void setUpAddMenu() {
			restaurant.addToMenu("Sweet corn soup",119);
			restaurant.addToMenu("Vegetable lasagne", 269);
		}
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant spy= Mockito.spy(restaurant);
        LocalTime closedTime=LocalTime.parse("20:30:00");
        Mockito.when(spy.getCurrentTime()).thenReturn(closedTime);
        boolean result= spy.isRestaurantOpen();
        assertEquals(true,result);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
    	Restaurant spy= Mockito.spy(restaurant);
        LocalTime closedTime=LocalTime.parse("22:30:00");
        Mockito.when(spy.getCurrentTime()).thenReturn(closedTime);
        boolean result= spy.isRestaurantOpen();
       assertEquals(false,result);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
    	setUpAddMenu();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
    	setUpAddMenu();
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
    	setUpAddMenu();
        assertThrows(itemNotFoundException.class,()->restaurant.removeFromMenu("French fries"));
    }
    
    //TODO
    //When adding item from the list, it should return the total amount
    
    @Test
    public void adding_items_should_return_the_total_amount(){
    	setUpAddMenu();
    	String[] itemsName= {"Sweet corn soup","Vegetable lasagne"};  //sending a list of selected items as string
    	int orderTotal= restaurant.getOrderTotal(itemsName); //get the total amount of selected items
    	assertEquals(388,orderTotal);
    }
    
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}